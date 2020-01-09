package pl.io.e_clinic.entity.workflowsicknote.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.io.e_clinic.entity.workflowsicknote.model.WorkflowSickNote;

@Repository
public interface WorkflowSickNoteRepository extends PagingAndSortingRepository<WorkflowSickNote, Long> {

}
