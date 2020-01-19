package pl.io.e_clinic.controller.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.hibernate.*;
import org.springframework.web.bind.annotation.*;
import pl.io.e_clinic.controller.services.dto.DateTimeDto;
import pl.io.e_clinic.controller.services.dto.DoctorDto;
import pl.io.e_clinic.entity.employee.model.Employee;
import pl.io.e_clinic.entity.employee.repository.EmployeeRepository;
import pl.io.e_clinic.entity.medicalservice.model.MedicalService;
import pl.io.e_clinic.entity.medicalservice.repository.MedicalServiceRepository;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import pl.io.e_clinic.entity.user.repository.UserRepository;
import pl.io.e_clinic.entity.visit.model.Visit;
import pl.io.e_clinic.entity.visit.repository.VisitRepository;
import pl.io.e_clinic.entity.weekschedule.model.WeekDay;
import pl.io.e_clinic.entity.weekschedule.model.WeekSchedule;
import pl.io.e_clinic.entity.weekschedule.repository.WeekScheduleRepository;
import pl.io.e_clinic.services.FilteringService;

import javax.persistence.EntityManager;

@RestController
@RequestMapping("/medical_services")
public class ServiceController {

    @Autowired
    MedicalServiceRepository medicalServiceRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    VisitRepository visitRepository;

    @Autowired
    WeekScheduleRepository weekScheduleRepository;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<MedicalService> getServices(
            @RequestParam(value = "service_name", required = false) String serviceName
    ) {
        long count = medicalServiceRepository.count();

       if (count == 0)
            return new ArrayList<>();

        Page<MedicalService> allServices = medicalServiceRepository.findAll(PageRequest.of(0, (int) count));

        List<MedicalService> filteredServices = allServices.getContent();

        filteredServices = new FilteringService<>(filteredServices)
                .contains(serviceName, MedicalService::getServiceName)
                .getFiltered();

        return filteredServices;
    }


   @GetMapping(value = "/{service_name}/doctors", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<DoctorDto> getUsers(@PathVariable String service_name, @RequestParam(value = "service_name", required = false) String serviceName) {

        long count = medicalServiceRepository.count();
        Page<Employee> employeePage = employeeRepository.findAll(PageRequest.of(0, (int)count));

        List<Employee> employeeList = employeePage.getContent();

       String sql = "SELECT employee.user_id, user.first_name, user.last_name FROM EMPLOYEE"+
      " join PRIVILEGE_LIST on EMPLOYEE.employee_id=PRIVILEGE_LIST.employee_id"+
      " join PRIVILEGE_REQUIREMENT on PRIVILEGE_LIST.privilege_id=PRIVILEGE_REQUIREMENT.privilege_id"+
      " join MEDICAL_SERVICE on PRIVILEGE_REQUIREMENT.service_id=MEDICAL_SERVICE.service_id"+
      " join USER on USER.USER_ID=EMPLOYEE.USER_ID"+
      " where medical_service.service_name=:serviceName"+
      " group by employee.user_id";

       Session session = entityManager.unwrap(Session.class);
        Query query = session.getSession().createSQLQuery(sql).setParameter("serviceName",service_name);
        List<Object> res = query.getResultList();
        List<DoctorDto> dList =new ArrayList<DoctorDto>();
        Date date=new Date(System.currentTimeMillis());
        date.setDate(date.getDate()+1);
       Iterator it = res.iterator();
       while(it.hasNext()){
           Object[] line = (Object[]) it.next();
           DoctorDto doc = new DoctorDto();
           doc.setUserId(Long.valueOf(String.valueOf(line[0])));
           doc.setFirstName(line[1].toString());
           doc.setLastName(line[2].toString());
           try{
               Date dateToSet=getAvailability(doc.getUserId(),date).getDate();
               doc.setDate(dateToSet);
           }catch(NullPointerException nE){

           }
           dList.add(doc);
       }

        return dList;
     //  System.out.println(elist);

    }

   @GetMapping(value = "/{doctor_id}/availability", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    DateTimeDto getAvailability(@PathVariable Long doctor_id, @RequestParam(value="date", required=false) Date date) {
       DateTimeDto visitDate =new DateTimeDto();
       boolean foundDate=false;
       boolean foundVisit=false;
        long count = visitRepository.count();
        Date tempDate;
        Time tempTime;
        if (date == null) {
            tempDate= new Date(System.currentTimeMillis());
        }else{
            tempDate=date;
        }
       tempDate.setDate((tempDate.getDate()));
       int currentDay = tempDate.getDay();

        if (count == 0)
            return null;

        String sql = "select starting_time from week_schedule w\n" +
                "where w.week_day_id=:currentDay\n" +
                "and w.employee_id=:currentEmployee";

        Session session = entityManager.unwrap(Session.class);
        while(!foundVisit) {//dopoki nie znajdzie wolnego terminu


            //pobranie wizyt pracownika na dany dzien
            Page<Visit> allVisits = visitRepository.findAll(PageRequest.of(0, (int) count));
            List<Visit> filteredVisits = allVisits.getContent();
            filteredVisits = new FilteringService<>(filteredVisits)
                    .contains(doctor_id, Visit::getEmployeeId)
                    .contains(tempDate, Visit::getScheduledDate)
                    .getFiltered();
            //pobranie grafiku pracownika na dany dzien
            try{
                tempTime = (Time) session.getSession().createSQLQuery(sql)
                        .setParameter("currentDay", currentDay)
                        .setParameter("currentEmployee", doctor_id).getSingleResult();
            }catch(Exception E){
                return null;
            }

                // List<WeekSchedule> currentSchedule = query.list();

            if (filteredVisits.isEmpty()) {//jesli pusta to pierwsza dostepna godzina dnia nast

                visitDate.setDate(tempDate);
                visitDate.setTime(tempTime);
                foundVisit=true;
            } else//sprawdzamy ktora wizyta ostatnia, o ktorej godzinie sie skonczy i czy godzina konca jest przed koncem pracy lekarza
            {

                    for (Visit v : filteredVisits) {
                        if (tempTime == v.getStartingTime() || tempTime.before(v.getStartingTime())) {

                            tempTime.setTime(v.getStartingTime().getTime());
                            tempTime.setMinutes(tempTime.getMinutes()+v.getEstimatedDuration().intValue());
                        }
                    }//mamy godzine w dannym dniu
                    sql = "select ending_time from week_schedule w\n" +
                            "where w.week_day_id=:currentDay\n" +
                            "and w.employee_id=:currentEmployee";
                    Time endTime = (Time) session.getSession().createSQLQuery(sql)
                            .setParameter("currentDay", currentDay)
                            .setParameter("currentEmployee", doctor_id).getSingleResult();
                    if (tempTime.before(endTime)) {
                        visitDate.setDate(tempDate);
                        visitDate.setTime(tempTime);
                        foundVisit = true;
                    }


            }
            tempDate.setDate((tempDate.getDate()));
            currentDay = tempDate.getDay();
        }

        return visitDate;
    }
}