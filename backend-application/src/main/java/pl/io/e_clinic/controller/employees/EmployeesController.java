package pl.io.e_clinic.controller.employees;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.io.e_clinic.entity.document.model.Document;
import pl.io.e_clinic.entity.employee.model.Employee;
import pl.io.e_clinic.entity.employee.repository.EmployeeRepository;
import pl.io.e_clinic.entity.room.repository.RoomRepository;
import pl.io.e_clinic.entity.user.model.User;
import pl.io.e_clinic.entity.user.repository.UserRepository;
import pl.io.e_clinic.entity.visit.model.Visit;
import pl.io.e_clinic.entity.weekschedule.model.WeekSchedule;
import pl.io.e_clinic.entity.weekschedule.repository.WeekScheduleRepository;
import pl.io.e_clinic.services.MedicalHistoryService;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    WeekScheduleRepository weekScheduleRepository;

    @GetMapping(value = "/{employee_id}/schedule", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<WeekSchedule> getSchedule(@PathVariable Long employee_id) {
       Optional<Employee> employee = employeeRepository.findById(employee_id);

        if(!employee.isPresent()) return new ArrayList<>();

        return new ArrayList<>();

    }

}
