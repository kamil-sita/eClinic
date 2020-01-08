package pl.io.e_clinic.controller.employees;


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
