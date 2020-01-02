package pl.io.e_clinic.entity.medicalservice.model;

import pl.io.e_clinic.entity.employee.model.Employee;
import pl.io.e_clinic.entity.privilege.model.Privilege;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "privilege_requirement")
public class PrivilegeRequirement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "privilege_requirement_id", nullable = false)
    private Long privilegeRequirementId;

    @ManyToOne
    @JoinColumn(name = "service_id", referencedColumnName = "service_id", nullable = false)
    private MedicalService medicalService;

    @NotBlank
    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "privilege_id", nullable = false)
    private Privilege privilege;

    public Privilege getPrivilege() {
        return privilege;
    }

    public MedicalService getMedicalService() {
        return medicalService;
    }
}
