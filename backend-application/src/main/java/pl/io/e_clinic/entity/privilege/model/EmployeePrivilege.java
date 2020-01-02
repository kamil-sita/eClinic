package pl.io.e_clinic.entity.privilege.model;

import pl.io.e_clinic.entity.employee.model.Employee;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "privilege_list")
public class EmployeePrivilege {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_privilege_id", nullable = false)
    private Long employeePrivilegeId;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id", nullable = false)
    private Employee employee;

    @NotBlank
    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "privilege_id", nullable = false)
    private Privilege privilege;

    public Employee getEmployee() {
        return employee;
    }

    public Privilege getPrivilege() {
        return privilege;
    }
}
