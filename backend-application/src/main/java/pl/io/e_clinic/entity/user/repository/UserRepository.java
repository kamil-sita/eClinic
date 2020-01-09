package pl.io.e_clinic.entity.user.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.io.e_clinic.entity.user.model.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

}
