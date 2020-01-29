package pl.io.e_clinic.entity.workflowovertime.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.io.e_clinic.entity.workflowovertime.model.WorkflowOvertime;

@Repository
public interface WorkflowOvertimeRepository extends PagingAndSortingRepository<WorkflowOvertime, Long> {

}
