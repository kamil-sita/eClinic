package pl.io.e_clinic.entity.visit.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.io.e_clinic.entity.visit.model.Visit;

@Repository
public interface VisitRepository extends PagingAndSortingRepository<Visit, Long> {

}
