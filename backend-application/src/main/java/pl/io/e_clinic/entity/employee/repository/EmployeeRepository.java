package pl.io.e_clinic.entity.employee.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.io.e_clinic.entity.employee.model.Employee;

import pl.io.e_clinic.entity.user.model.User;


@Repository
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {

}
