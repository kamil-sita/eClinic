package pl.io.e_clinic.entity.employee.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import pl.io.e_clinic.entity.user.model.User;
import pl.io.e_clinic.entity.visit.model.Visit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "employee")
@JsonIgnoreProperties(value = {"visits"})
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
}
