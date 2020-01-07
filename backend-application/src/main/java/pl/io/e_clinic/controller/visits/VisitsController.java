package pl.io.e_clinic.controller.visits;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import pl.io.e_clinic.controller.visits.dto.VisitDto;
import pl.io.e_clinic.entity.document.model.Document;
import pl.io.e_clinic.entity.document.repository.DocumentRepository;
import pl.io.e_clinic.entity.employee.model.Employee;
import pl.io.e_clinic.entity.employee.repository.EmployeeRepository;
import pl.io.e_clinic.entity.medicalservice.model.MedicalService;
import pl.io.e_clinic.entity.medicalservice.repository.MedicalServiceRepository;
import pl.io.e_clinic.entity.user.model.User;
import pl.io.e_clinic.entity.user.repository.UserRepository;
import pl.io.e_clinic.entity.visit.model.PaymentStatus;
import pl.io.e_clinic.entity.visit.model.Visit;
import pl.io.e_clinic.entity.visit.model.VisitStatus;
import pl.io.e_clinic.entity.visit.repository.VisitRepository;
import pl.io.e_clinic.services.FilteringService;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/visits")
public class VisitsController {

    @Autowired
    VisitRepository visitRepository;

    @Autowired
    MedicalServiceRepository medicalServiceRepository;

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    UserRepository userRepository;

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
            return new ArrayList<>(visit.get().getMedicalServices());
        } else {
            return new ArrayList<>();
        }
    }

    @PutMapping(value = "/{visit_id}/services", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MedicalService>> putServices(
            @PathVariable Long visit_id,
            @RequestBody Set<Long> request
    ) {
        Optional<Visit> optionalVisit = visitRepository.findById(visit_id);


        if (!optionalVisit.isPresent() || request == null || request.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Visit visit = optionalVisit.get();

        for (MedicalService service : medicalServiceRepository.findAll()) {
            if (request.contains(service.getMedicalServiceId())) { //powinno posiadać
                if (!service.getVisits().contains(visit)) { // i nie posiada
                    service.getVisits().add(visit);
                    medicalServiceRepository.save(service);
                }
            } else { //nie powinno posiadać
                if (service.getVisits().contains(visit)) { //i posiada
                    service.getVisits().remove(visit);
                    medicalServiceRepository.save(service);
                }
            }
        }

        return new ResponseEntity<>(getServices(visit_id), HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/{visit_id}/documents", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Document> postDocuments(
            @PathVariable Long visit_id,
            @RequestBody Document document
    ) {
        Optional<Visit> optionalVisit = visitRepository.findById(visit_id);

        if (!optionalVisit.isPresent() || document == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Visit visit = optionalVisit.get();
        Document persistentDocument = new Document(document.getDocumentType(), visit, document.getDocumentData());

        return new ResponseEntity<>(documentRepository.save(persistentDocument), HttpStatus.ACCEPTED);
    }

    @PutMapping(value = "/{visit_id}/documents/{document_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Document> putDocuments(
            @PathVariable Long visit_id,
            @PathVariable Long document_id,
            @RequestBody Document editedDocument
    ) {
        Optional<Visit> optionalVisit = visitRepository.findById(visit_id);
        Optional<Document> optionalDocument = documentRepository.findById(document_id);

        if (!optionalVisit.isPresent() || !optionalDocument.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Visit visit = optionalVisit.get();
        Document originalDocument = optionalDocument.get();

        if (!originalDocument.getVisit().equals(visit)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Document persistentDocument = new Document(
                originalDocument.getDocumentId(),
                editedDocument.getDocumentType(),
                visit,
                editedDocument.getDocumentData()
        );

        return new ResponseEntity<>(documentRepository.save(persistentDocument), HttpStatus.ACCEPTED);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Visit> postVisit(
            @RequestBody VisitDto visitDto
    ) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(visitDto.getEmployeeId());
        Optional<User> optionalPatient = userRepository.findById(visitDto.getPatientId());
        Optional<MedicalService> optionalMedicalService = medicalServiceRepository.findById(visitDto.getServiceId());

        if (! (optionalEmployee.isPresent() && optionalPatient.isPresent() && optionalMedicalService.isPresent())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Visit visit = new Visit(
                optionalPatient.get(),
                optionalEmployee.get(),
                visitDto.getScheduledDate(),
                optionalMedicalService.get().getDuration(),
                visitDto.getStartingTime()
        );


        //poniżej trochę namieszane, ale to dlatego że MedicalService ma ownership nad visit, wiec najpierw zapisujemy
        //wizyte, potem do niego medicalService, ale musimy zwrocic wizyte z poprawnymi uslugami
        Visit visit2 = visitRepository.save(visit);

        visit = visitRepository.findById(visit2.getVisitId()).get();

        optionalMedicalService.get().getVisits().add(visit);
        medicalServiceRepository.save(optionalMedicalService.get());

        //ale WIZYT NIE MA, poniewaz repozytorium sie nie odswieza!
        // wiec udajemy ze odczytalismy obiekt - tworzymy nowy, ktory wyglada tak jak poprawna wersja

        visit.getMedicalServices().clear();
        visit.getMedicalServices().add(optionalMedicalService.get());

        return new ResponseEntity<>(visit, HttpStatus.ACCEPTED);

    }

    @PutMapping(value = "/{visit_id}/pay", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Visit> putVisitPayment(
            @PathVariable Long visit_id
    ) {
        Optional<Visit> optionalVisit = visitRepository.findById(visit_id);

        if (!optionalVisit.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        optionalVisit.get().setVisitState(VisitStatus.PAID);

        visitRepository.save(optionalVisit.get());

        return new ResponseEntity<>(optionalVisit.get(), HttpStatus.ACCEPTED);
    }

}
