package pl.io.e_clinic.entity.document.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.io.e_clinic.entity.document.model.Document;

@Repository
public interface DocumentRepository extends PagingAndSortingRepository<Document, Long> {
}
