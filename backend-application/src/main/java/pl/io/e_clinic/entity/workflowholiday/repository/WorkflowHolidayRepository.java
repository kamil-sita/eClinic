package pl.io.e_clinic.entity.workflowholiday.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.io.e_clinic.entity.workflowholiday.model.WorkflowHoliday;

@Repository
public interface WorkflowHolidayRepository extends PagingAndSortingRepository<WorkflowHoliday, Long> {
}
