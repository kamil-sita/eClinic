package pl.io.e_clinic.entity.weekschedule.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.io.e_clinic.entity.weekschedule.model.WeekSchedule;

@Repository
public interface WeekScheduleRepository extends PagingAndSortingRepository<WeekSchedule, Long> {

}
