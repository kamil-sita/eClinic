package pl.io.e_clinic.controller.services;

import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.hibernate.*;
import org.springframework.web.bind.annotation.*;
import pl.io.e_clinic.entity.employee.model.Employee;
import pl.io.e_clinic.entity.employee.repository.EmployeeRepository;
import pl.io.e_clinic.entity.medicalservice.model.MedicalService;
import pl.io.e_clinic.entity.medicalservice.repository.MedicalServiceRepository;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

import pl.io.e_clinic.entity.user.model.User;
import pl.io.e_clinic.entity.user.repository.UserRepository;
import pl.io.e_clinic.entity.visit.model.Visit;
import pl.io.e_clinic.entity.weekschedule.model.WeekSchedule;
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

   // @Autowired
 //   SessionFactory sessionFactory;


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

}