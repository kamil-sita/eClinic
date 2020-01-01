package pl.io.e_clinic.controller.visits;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.io.e_clinic.entity.user.model.User;
import pl.io.e_clinic.entity.visit.model.Visit;
import pl.io.e_clinic.entity.visit.repository.VisitRepository;
import pl.io.e_clinic.services.FilteringService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/visits")
public class VisitsController {

    @Autowired
    VisitRepository visitRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Visit> getVisits(
            @RequestParam(value="patient_first_name", required=false) String patientFirstName,
            @RequestParam(value="patient_last_name", required=false) String patientLastName,
            @RequestParam(value="doctor_first_name", required=false) String doctorFirstName,
            @RequestParam(value="doctor_last_name", required=false) String doctorLastName,
            @RequestParam(value="date", required=false) Date date
    ) {
        long count = visitRepository.count();

        //pusta baza danych
        if (count == 0) {
            return new ArrayList<>();
        }

        //pobranie wszystkiego
        Page<Visit> allVisitsPages = visitRepository.findAll(PageRequest.of(0, (int)count));

        List<Visit> filteredVisits = allVisitsPages.getContent();

        //opcjonalne filtrowanie
        filteredVisits = new FilteringService<>(filteredVisits)
                .contains(patientFirstName, (Visit visit) -> visit.getPatient().getFirstName())
                .contains(patientLastName, (Visit visit) -> visit.getPatient().getLastName())
                .contains(doctorFirstName, (Visit visit) -> visit.getEmployee().getUser().getFirstName())
                .contains(doctorLastName, (Visit visit) -> visit.getEmployee().getUser().getLastName())
                .contains(date, Visit::getScheduledDate)
                .getFiltered();

        return filteredVisits;
    }
}
