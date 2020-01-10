package pl.io.e_clinic.controller.employees;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pl.io.e_clinic.controller.employees.dto.ScheduleDto;
import pl.io.e_clinic.entity.document.model.Document;
import pl.io.e_clinic.entity.employee.repository.EmployeeRepository;
import pl.io.e_clinic.entity.medicalservice.model.MedicalService;
import pl.io.e_clinic.entity.room.repository.RoomRepository;

import pl.io.e_clinic.entity.employee.model.Employee;
import pl.io.e_clinic.entity.user.model.User;
import pl.io.e_clinic.entity.visit.model.Visit;
import pl.io.e_clinic.entity.weekschedule.model.WeekDay;
import pl.io.e_clinic.entity.weekschedule.model.WeekSchedule;
import pl.io.e_clinic.entity.weekschedule.repository.WeekScheduleRepository;
import pl.io.e_clinic.services.FilteringService;

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

    @GetMapping(value = "/{user_id}/visits", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Set<Visit> getVisits(@PathVariable Long user_id, @RequestParam(value="date", required=false) Date date) {
        Optional<Employee> employee = employeeRepository.findById(user_id);

        if (!employee.isPresent()) return new HashSet<>();

        Set<Visit> visits = employee.get().getVisits();

        if (date != null) {
            visits = visits.stream().filter((Visit visit) -> {
                //sprawdza czy TYLKO DZIEŃ MIESIĄC I ROK są równe
                Calendar cal1 = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();
                cal1.setTime(date);
                cal2.setTime(visit.getScheduledDate());
                return cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                        cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
            }).collect(Collectors.toSet());
        }


        return visits;
    }



    @GetMapping(value = "/{employee_id}/schedule", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<WeekSchedule> getSchedule(@PathVariable Long employee_id) {

        //pobranie wszystkiego dla konkretnego pracownika

        long count = weekScheduleRepository.count();
        Page<WeekSchedule> schedules = weekScheduleRepository.findAll(PageRequest.of(0, (int)count));

        List<WeekSchedule> schedule = schedules.getContent();

        //opcjonalne filtrowanie
        schedule = new FilteringService<>(schedule)
                .contains(employee_id, WeekSchedule::getEmployeeId)
                .getFiltered();

        return schedule;
      //  return schedule;
    }

    @PutMapping(value = "/{employee_id}/schedule", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WeekSchedule>> putSchedule(
            @PathVariable Long employee_id,
            @RequestBody List<ScheduleDto> schedule
    ) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employee_id);
        if (!optionalEmployee.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        //pobranie wszystkiego dla konkretnego pracownika
        long count = weekScheduleRepository.count();
        Page<WeekSchedule> schedulePage = weekScheduleRepository.findAll(PageRequest.of(0, (int)count));
        List<WeekSchedule> scheduleList = schedulePage.getContent();
        //filtrowanie do grafiku danego pracownika
        scheduleList = new FilteringService<>(scheduleList)
                .contains(employee_id, WeekSchedule::getEmployeeId)
                .getFiltered();

        //sprawdzenie ktory z dni tygodnia wymaga aktualizacji i jej wykonanie
        int day=0;
        for(WeekSchedule currentSchedule : scheduleList){
            if(currentSchedule.getWeekDay()== WeekDay.values()[day]){
               for(int i=0;i<schedule.size();i++){
                   if(schedule.get(i).getWeekDay()==WeekDay.values()[day]){
                       currentSchedule.setRoom(schedule.get(i).getRoom());
                       currentSchedule.setStartingTime(schedule.get(i).getStartingTime());
                       currentSchedule.setEndingTime(schedule.get(i).getEndingTime());
                   }
               }
            }
            day++;
        }
        //upload do bazy danych NIE DZIALA
        weekScheduleRepository.saveAll(scheduleList);



        return new ResponseEntity<>(scheduleList, HttpStatus.ACCEPTED);
    }

}
