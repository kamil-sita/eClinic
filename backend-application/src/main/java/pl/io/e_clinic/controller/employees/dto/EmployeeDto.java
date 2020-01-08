package pl.io.e_clinic.controller.employees.dto;

import pl.io.e_clinic.entity.employee.model.Employee;
import pl.io.e_clinic.entity.privilege.model.Privilege;

import java.util.Set;

public class EmployeeDto {

    private Employee employee;
    private Set<Privilege> privileges;

    public EmployeeDto(Employee employee) {
        if (employee != null) {
            this.employee = employee;
            this.privileges = employee.getPrivileges();
        }
    }

    public Employee getEmployee() {
        return employee;
    }

    public Set<Privilege> getPrivileges() {
        return privileges;
    }
}
