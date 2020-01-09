package pl.io.e_clinic.controller.employees;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import pl.io.e_clinic.entity.employee.repository.EmployeeRepository;
import pl.io.e_clinic.entity.room.repository.RoomRepository;

import pl.io.e_clinic.entity.weekschedule.model.WeekSchedule;
import pl.io.e_clinic.entity.weekschedule.repository.WeekScheduleRepository;
import java.util.*;


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
    public @ResponseBody Optional<WeekSchedule> getSchedule(@PathVariable Long employee_id) {

        //pobranie wszystkiego dla konkretnego pracownika
        Optional<WeekSchedule> schedule = weekScheduleRepository.findById(employee_id);
        return schedule;
      //  return schedule;
    }

}
