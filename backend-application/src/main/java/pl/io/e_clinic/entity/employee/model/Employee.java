package pl.io.e_clinic.entity.employee.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import pl.io.e_clinic.entity.privilege.model.EmployeePrivilege;
import pl.io.e_clinic.entity.privilege.model.Privilege;
import pl.io.e_clinic.entity.user.model.User;
import pl.io.e_clinic.entity.visit.model.Visit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "employee")
@JsonIgnoreProperties(value = {"visits", "privileges", "mappedPrivileges"})
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @OneToMany(mappedBy = "employee")
    private Set<Visit> visits;

    @NotBlank
    @Column(name = "role_id", nullable = false)
    @Enumerated(value = EnumType.ORDINAL)
    private Role role;


    @OneToMany(mappedBy = "employee")
    private Set<EmployeePrivilege> privileges;

    public Set<Visit> getVisits() {
        return visits;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public User getUser() {
        return user;
    }

    public Role getRole() {
        return role;
    }

    public Set<Privilege> getMappedPrivileges() {
        Set<Privilege> mappedPrivileges = new HashSet<>();
        privileges.forEach((EmployeePrivilege privilege) -> mappedPrivileges.add(privilege.getPrivilege()));
        return mappedPrivileges;
    }
}
