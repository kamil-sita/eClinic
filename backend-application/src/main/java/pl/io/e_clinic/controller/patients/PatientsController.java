package pl.io.e_clinic.controller.patients;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.io.e_clinic.entity.user.model.User;
import pl.io.e_clinic.entity.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientsController {

    @Autowired
    UserRepository userRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<User> getPatients(){
        long count = userRepository.count();

        if (count == 0) {
            return new ArrayList<>();
        }

        Page<User> users = userRepository.findAll(PageRequest.of(0, (int)count));
        return users.getContent();
    }

    @GetMapping(value = "/teapot", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JsonNode> getTeapot() {
        return new ResponseEntity<>(null, HttpStatus.I_AM_A_TEAPOT);
    }
}
