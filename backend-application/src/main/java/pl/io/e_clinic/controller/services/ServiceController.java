package pl.io.e_clinic.controller.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.io.e_clinic.entity.medicalservice.model.MedicalService;
import pl.io.e_clinic.entity.medicalservice.repository.MedicalServiceRepository;
import java.util.*;
import pl.io.e_clinic.services.FilteringService;

@RestController
@RequestMapping("/services")
public class ServiceController {

    @Autowired
    MedicalServiceRepository medicalServiceRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<MedicalService> getServices(
            @RequestParam(value="service_name", required = false) String serviceName
    ) {
        long count = medicalServiceRepository.count();

        if(count == 0)
            return new ArrayList<>();

        Page<MedicalService> allServices = medicalServiceRepository.findAll(PageRequest.of(0, (int)count));

        List<MedicalService> filteredServices = allServices.getContent();

        filteredServices = new FilteringService<>(filteredServices)
                .contains(serviceName, MedicalService::getServiceName)
                .getFiltered();

        return filteredServices;
    }
}
