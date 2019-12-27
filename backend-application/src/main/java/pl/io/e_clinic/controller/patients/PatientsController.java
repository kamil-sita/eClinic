package pl.io.e_clinic.controller.patients;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.io.e_clinic.entity.user.model.User;
import pl.io.e_clinic.entity.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/patients")
public class PatientsController {

    @Autowired
    UserRepository userRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<User> getPatients(
            @RequestParam(value="first_name", required=false) String firstName, //contains
            @RequestParam(value="last_name", required=false) String lastName, //contains
            @RequestParam(value="contact_number", required=false) String contactNumber, //contains
            @RequestParam(value="user_id", required=false) Long userId //contains
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
        if (firstName != null) {
            patients = patients.stream().filter(user -> user.getFirstName().toLowerCase()
                .contains(firstName.toLowerCase()))
                .collect(Collectors.toList());
        }
        if (lastName != null) {
            patients = patients.stream().filter(user -> user.getLastName().toLowerCase()
                .contains(lastName.toLowerCase()))
                .collect(Collectors.toList());
        }
        if (contactNumber != null) {
            patients = patients.stream().filter(user -> user.getContactNumber()
                .contains(contactNumber))
                .collect(Collectors.toList());
        }
        if (userId != null) {
            String userIdString = "" + userId;
            patients = patients.stream().filter(user -> {
                     String comparedUserIdString = "" + user.getUserId();
                     return comparedUserIdString.contains(userIdString);
                 }).collect(Collectors.toList());
        }

        return patients;
    }

    @GetMapping(value = "/{user_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody User getPatient(@PathVariable Long user_id) {

        Optional<User> user = userRepository.findById(user_id);

        return user.orElse(null);
    }

    @GetMapping(value = "/teapot", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JsonNode> getTeapot() {
        return new ResponseEntity<>(null, HttpStatus.I_AM_A_TEAPOT);
    }
}
