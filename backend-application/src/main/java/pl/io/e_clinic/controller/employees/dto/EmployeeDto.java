package pl.io.e_clinic.controller.employees.dto;

import pl.io.e_clinic.entity.employee.model.Employee;
import pl.io.e_clinic.entity.employee.model.Role;
import pl.io.e_clinic.entity.privilege.model.Privilege;

import java.util.List;

public class EmployeeDto {

    private Employee employee;
    private List<Privilege> privileges;

    public EmployeeDto(Employee employee, List<Privilege> privileges) {
        this.employee = employee;
        this.privileges = privileges;
    }

    public Employee getEmployee() {
        return employee;
    }

    public List<Privilege> getPrivileges() {
        return privileges;
    }
}
