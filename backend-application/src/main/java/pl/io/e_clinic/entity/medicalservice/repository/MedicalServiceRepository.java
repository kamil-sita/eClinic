package pl.io.e_clinic.entity.medicalservice.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.io.e_clinic.entity.document.model.Document;
import pl.io.e_clinic.entity.medicalservice.model.MedicalService;

@Repository
public interface MedicalServiceRepository extends PagingAndSortingRepository<MedicalService, Long> {
}
