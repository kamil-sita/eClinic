package pl.io.e_clinic.entity.room.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.io.e_clinic.entity.room.model.Room;

@Repository
public interface RoomRepository extends PagingAndSortingRepository<Room, Long> {

}
