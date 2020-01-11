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
        List<DoctorDto> elist = query.list();
     //  System.out.println(elist);

       return elist;
    }

    @GetMapping(value = "/{doctor_id}/availability", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<DateTimeDto> getAvailability(@PathVariable Long doctor_id, @RequestParam(value="date", required=false) Date date) {

        long count = visitRepository.count();

        if (count == 0)
            return new ArrayList<>();
        //pobranie wizyt pracownika
        Page<Visit> allVisits = visitRepository.findAll(PageRequest.of(0, (int) count));
        List<Visit> filteredVisits = allVisits.getContent();
        filteredVisits = new FilteringService<>(filteredVisits)
                .contains(doctor_id, Visit::getEmployeeId)
                .getFiltered();
        //pobranie grafiku pracownika
        long countS = weekScheduleRepository.count();
        Page<WeekSchedule> schedules = weekScheduleRepository.findAll(PageRequest.of(0, (int)count));
        List<WeekSchedule> schedule = schedules.getContent();
        schedule = new FilteringService<>(schedule)
                .contains(doctor_id, WeekSchedule::getEmployeeId)
                .getFiltered();

        if (date == null) {
            date= new Date(System.currentTimeMillis());
        }
        Date tempDate = date;
        //sprawdzenie czy pracownik pracuje tego dnia i stworzenie listy godzin
        int day=0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        day=calendar.get(Calendar.DAY_OF_WEEK);
        ArrayList<Time> availableHours;
        Time tempTime;
        for(WeekSchedule currentSchedule : schedule){
            if(currentSchedule.getWeekDay() == WeekDay.values()[day-1]){ //czy to jest grafik na ten dzien
                tempTime=currentSchedule.getStartingTime();

                List<Visit> atDateVisits = filteredVisits;
                atDateVisits = new FilteringService<>(atDateVisits)
                        .contains(date, Visit::getScheduledDate)
                        .getFiltered();
                for(Visit currentVisit : atDateVisits){
                    if(tempTime.after(currentVisit.getStartingTime())&&tempTime.before(currentSchedule.getEndingTime())){
                        continue;
                    }else{
                        tempTime.setMinutes(tempTime.getMinutes()+currentVisit.getEstimatedDuration().intValue());
                    }
                }

                break;
                }

        }




        //  System.out.println(elist);

        return new ArrayList<DateTimeDto>();
    }
}