package pl.io.e_clinic.controller.visits;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import pl.io.e_clinic.entity.document.model.Document;
import pl.io.e_clinic.entity.medicalservice.model.MedicalService;
import pl.io.e_clinic.entity.user.model.User;
import pl.io.e_clinic.entity.visit.model.Visit;
import pl.io.e_clinic.entity.visit.repository.VisitRepository;
import pl.io.e_clinic.services.FilteringService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @GetMapping(value = "/{visit_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Visit getPatient(@PathVariable Long visit_id) {

        Optional<Visit> visit = visitRepository.findById(visit_id);

        return visit.orElse(null);
    }

    @GetMapping(value = "/{visit_id}/documents", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Document> getDocuments(@PathVariable Long visit_id) {
        Optional<Visit> visit = visitRepository.findById(visit_id);

        if (visit.isPresent()) {
            return new ArrayList<>(visit.get().getDocuments());
        } else {
            return new ArrayList<>();
        }
    }


    @GetMapping(value = "/{visit_id}/services", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<MedicalService> getServices(@PathVariable Long visit_id) {
        Optional<Visit> visit = visitRepository.findById(visit_id);

        if (visit.isPresent()) {
            return new ArrayList<>(visit.get().getVisitMedicalServices());
        } else {
            return new ArrayList<>();
        }
    }

    @PutMapping(value = "/{visit_id}/services", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MedicalService>> putServices(
            @PathVariable Long visit_id,
            @RequestBody MultiValueMap<String, String> formParams //parametry: "service_id"
    ) {
        final String service_id = "service_id";
        if (!formParams.containsKey(service_id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Visit> visit = visitRepository.findById(visit_id);

        return null;

       // if (visit.isPresent()) {
       //     return new ArrayList<>(visit.get().getMappedVisitMedicalServices());
       // } else {
       //     return new ArrayList<>();
       // }
    }
}
