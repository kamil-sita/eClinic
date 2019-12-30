package pl.io.e_clinic.controller.patients;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.io.e_clinic.services.FilteringService;
import pl.io.e_clinic.services.MedicalHistoryService;
import pl.io.e_clinic.entity.user.model.User;
import pl.io.e_clinic.entity.user.repository.UserRepository;
import pl.io.e_clinic.entity.visit.model.Visit;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/patients")
public class PatientsController {

    @Autowired
    UserRepository userRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<User> getPatients(
            @RequestParam(value="first_name", required=false) String firstName,
            @RequestParam(value="last_name", required=false) String lastName,
            @RequestParam(value="contact_number", required=false) String contactNumber,
            @RequestParam(value="user_id", required=false) Long userId
    ) {
        long count = userRepository.count();

        //pusta baza danych
        if (count == 0) {
            return new ArrayList<>();
        }

        //pobranie wszystkiego
        Page<User> users = userRepository.findAll(PageRequest.of(0, (int)count));

        List<User> patients = users.getContent();

        //opcjonalne filtrowanie
        patients = new FilteringService<>(patients)
                .contains(firstName, User::getFirstName)
                .contains(lastName, User::getLastName)
                .contains(contactNumber, User::getContactNumber)
                .contains(userId, User::getUserId)
                .getFiltered();

        return patients;
    }

    @GetMapping(value = "/{user_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody User getPatient(@PathVariable Long user_id) {

        Optional<User> user = userRepository.findById(user_id);

        return user.orElse(null);
    }

    @GetMapping(value = "/{user_id}/history", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    MedicalHistoryService getHistory(@PathVariable Long user_id) {

        Optional<User> user = userRepository.findById(user_id);

        return user.map(MedicalHistoryService::new).orElse(null);
    }

    @GetMapping(value = "/{user_id}/visits", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Set<Visit> getVisits(@PathVariable Long user_id, @RequestParam(value="date", required=false) Date date) {
        Optional<User> user = userRepository.findById(user_id);

        if (!user.isPresent()) return null;

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
    }

    @GetMapping(value = "/teapot", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JsonNode> getTeapot() {
        return new ResponseEntity<>(null, HttpStatus.I_AM_A_TEAPOT);
    }
}
