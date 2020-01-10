package pl.io.e_clinic.controller.employees;

import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.io.e_clinic.controller.employees.dto.EmployeeDto;
import pl.io.e_clinic.entity.employee.model.Employee;
import pl.io.e_clinic.entity.employee.model.Role;
import pl.io.e_clinic.entity.employee.repository.EmployeeRepository;
import pl.io.e_clinic.entity.room.repository.RoomRepository;
import pl.io.e_clinic.entity.workflowholiday.model.WorkflowHoliday;
import pl.io.e_clinic.entity.workflowholiday.repository.WorkflowHolidayRepository;
import pl.io.e_clinic.entity.workflowovertime.model.WorkflowOvertime;
import pl.io.e_clinic.entity.workflowovertime.repository.WorkflowOvertimeRepository;
import pl.io.e_clinic.entity.workflowsicknote.model.WorkflowSickNote;
import pl.io.e_clinic.entity.workflowsicknote.repository.WorkflowSickNoteRepository;
import pl.io.e_clinic.services.FilteringService;
import pl.io.e_clinic.entity.weekschedule.repository.WeekScheduleRepository;
import pl.io.e_clinic.entity.weekschedule.model.WeekSchedule;
import pl.io.e_clinic.entity.weekschedule.model.WeekDay;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.io.e_clinic.entity.employee.repository.EmployeeRepository;
import pl.io.e_clinic.entity.user.model.User;
import pl.io.e_clinic.entity.user.repository.UserRepository;
import pl.io.e_clinic.entity.visit.model.Visit;
import pl.io.e_clinic.services.MedicalHistoryService;

import java.sql.Date;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    WorkflowHolidayRepository workflowHolidayRepository;

    @Autowired
    WorkflowSickNoteRepository workflowSickNoteRepository;

    @Autowired
    WorkflowOvertimeRepository workflowOvertimeRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    WeekScheduleRepository weekScheduleRepository;


    @GetMapping(value = "/{employee_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    EmployeeDto getEmployee(@PathVariable Long employee_id) {
        Optional<Employee> employee = employeeRepository.findById(employee_id);

        return new EmployeeDto(employee.orElse(null));
    }

    @GetMapping(value = "/doctors", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<Employee> getDoctors(
            @RequestParam(value = "first_name", required = false) String firstName,
            @RequestParam(value = "last_name", required = false) String lastName,
            @RequestParam(value = "user_id", required = false) Long userId) {
        long count = employeeRepository.count();

        if (count == 0) {
            return new ArrayList<>();
        }

        Page<Employee> allEmployeesPages = employeeRepository.findAll(PageRequest.of(0, (int) count));

        List<Employee> filteredEmployees = allEmployeesPages.getContent();

        filteredEmployees = new FilteringService<>(filteredEmployees)
                .contains(firstName, (Employee employee) -> employee.getUser().getFirstName())
                .contains(lastName, (Employee employee) -> employee.getUser().getLastName())
                .contains(userId, (Employee employee) -> employee.getUser().getUserId())
                .contains(Role.DOCTOR.name(), (Employee employee) -> employee.getRole().name())
                .getFiltered();

        return filteredEmployees;
    }

    @PostMapping (value = "/{user_id}/workflow/holiday_notes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkflowHoliday> postWorkflowHoliday(@PathVariable Long user_id, @RequestBody WorkflowHoliday workflowHoliday) {
        long count = employeeRepository.count();

        if (count == 0) {
            return new ResponseEntity<WorkflowHoliday>(HttpStatus.BAD_REQUEST);
        }

        Page<Employee> allEmployeesPages = employeeRepository.findAll(PageRequest.of(0, (int) count));

        List<Employee> filteredEmployees = allEmployeesPages.getContent();

        filteredEmployees = new FilteringService<>(filteredEmployees)
                .contains(user_id, (Employee employee) -> employee.getUser().getUserId())
                .getFiltered();

        if (filteredEmployees.size() != 1){
            return new ResponseEntity<WorkflowHoliday>(HttpStatus.CONFLICT);
        }

        return new ResponseEntity<WorkflowHoliday>(workflowHolidayRepository.save(new WorkflowHoliday(
                filteredEmployees.get(0), workflowHoliday)), HttpStatus.ACCEPTED);
    }

    @PostMapping (value = "/{user_id}/workflow/sick_notes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkflowSickNote> postWorkflowSickNote(@PathVariable Long user_id, @RequestBody WorkflowSickNote workflowSickNote) {
        long count = employeeRepository.count();

        if (count == 0) {
            return new ResponseEntity<WorkflowSickNote>(HttpStatus.BAD_REQUEST);
        }

        Page<Employee> allEmployeesPages = employeeRepository.findAll(PageRequest.of(0, (int) count));

        List<Employee> filteredEmployees = allEmployeesPages.getContent();

        filteredEmployees = new FilteringService<>(filteredEmployees)
                .contains(user_id, (Employee employee) -> employee.getUser().getUserId())
                .getFiltered();

        if (filteredEmployees.size() != 1){
            return new ResponseEntity<WorkflowSickNote>(HttpStatus.CONFLICT);
        }

        return new ResponseEntity<WorkflowSickNote>(workflowSickNoteRepository.save(new WorkflowSickNote(
                filteredEmployees.get(0), workflowSickNote)), HttpStatus.ACCEPTED);
    }

    @PostMapping (value = "/{user_id}/workflow/overtime_notes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkflowOvertime> postWorkflowOvertime(@PathVariable Long user_id, @RequestBody WorkflowOvertime workflowOvertime) {
        long count = employeeRepository.count();

        if (count == 0) {
            return new ResponseEntity<WorkflowOvertime>(HttpStatus.BAD_REQUEST);
        }

        Page<Employee> allEmployeesPages = employeeRepository.findAll(PageRequest.of(0, (int) count));

        List<Employee> filteredEmployees = allEmployeesPages.getContent();

        filteredEmployees = new FilteringService<>(filteredEmployees)
                .contains(user_id, (Employee employee) -> employee.getUser().getUserId())
                .getFiltered();

        if (filteredEmployees.size() != 1){
            return new ResponseEntity<WorkflowOvertime>(HttpStatus.CONFLICT);
        }

        return new ResponseEntity<WorkflowOvertime>(workflowOvertimeRepository.save(new WorkflowOvertime(
                filteredEmployees.get(0), workflowOvertime)), HttpStatus.ACCEPTED);
    }

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
            @RequestBody List<WeekSchedule> schedule
    ) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employee_id);

        //pobranie wszystkiego dla konkretnego pracownika

        long count = weekScheduleRepository.count();
        Page<WeekSchedule> schedulePage = weekScheduleRepository.findAll(PageRequest.of(0, (int)count));

        List<WeekSchedule> scheduleList = schedulePage.getContent();
        //filtrowanie do grafiku danego pracownika
        scheduleList = new FilteringService<>(schedule)
                .contains(employee_id, WeekSchedule::getEmployeeId)
                .getFiltered();

        for(WeekSchedule currentSchedule : scheduleList){
            /*if(currentSchedule.getWeekDay() == WeekDay.PN){
                // cur
            }*/
        }


        if (!optionalEmployee.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
    }

   /* @GetMapping(value = "/{user_id}/schedule", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Schedule getSchedule(@PathVariable Long user_id) {

        Optional<User> user = employeeRepository.findById(user_id);

        return user.map(MedicalHistoryService::new).orElse(null);
    }

    @PutMapping(value = "/{user_id}/schedule", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Set<Visit> getVisits(@PathVariable Long user_id, @RequestParam(value="date", required=false) Date date) {
        Optional<User> user = employeeRepository.findById(user_id);

        if (!user.isPresent()) return new HashSet<>();

        Set<Visit> visits = user.get().getVisits();

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
    }*/
}
