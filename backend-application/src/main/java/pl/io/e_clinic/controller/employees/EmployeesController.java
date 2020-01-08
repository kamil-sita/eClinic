package pl.io.e_clinic.controller.employees;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.io.e_clinic.controller.employees.dto.EmployeeDto;
import pl.io.e_clinic.entity.employee.model.Employee;
import pl.io.e_clinic.entity.employee.model.Role;
import pl.io.e_clinic.entity.employee.repository.EmployeeRepository;
import pl.io.e_clinic.services.FilteringService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

    @Autowired
    EmployeeRepository employeeRepository;


    @GetMapping(value = "/{employee_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    EmployeeDto getEmployee(@PathVariable Long employee_id) {
        Optional<Employee> employee = employeeRepository.findById(employee_id);

        return new EmployeeDto(employee.orElse(null));
    }

    @GetMapping(value = "/doctors", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<Employee> getDoctors(
            @RequestParam(value = "first_name", required = false) String firstName,
            @RequestParam(value = "last_name", required = false) String lastName,
            @RequestParam(value = "user_id", required = false) Long userId) {
        long count = employeeRepository.count();

        if (count == 0) {
            return new ArrayList<>();
        }

        Page<Employee> allEmployeesPages = employeeRepository.findAll(PageRequest.of(0, (int) count));

        List<Employee> filteredEmployees = allEmployeesPages.getContent();

        filteredEmployees = new FilteringService<>(filteredEmployees)
                .contains(firstName, (Employee employee) -> employee.getUser().getFirstName())
                .contains(lastName, (Employee employee) -> employee.getUser().getLastName())
                .contains(userId, (Employee employee) -> employee.getUser().getUserId())
                .contains(Role.DOCTOR.name(), (Employee employee) -> employee.getRole().name())
                .getFiltered();

        return filteredEmployees;
    }
}
