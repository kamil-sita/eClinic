package pl.io.e_clinic.entity.medicalservice.model;


import pl.io.e_clinic.entity.visit.model.VisitMedicalServices;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "medical_service")
public class MedicalService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id", nullable = false)
    private Long medicalServiceId;


    @NotBlank
    @Size(max = 50, message = "service name can be up to 50 characters long")
    @Column(name = "service_name")
    private String serviceName;

    @OneToMany(mappedBy = "medicalService")
    private Set<VisitMedicalServices> visitMedicalServices;


    @OneToMany(mappedBy = "medicalService")
    private Set<PrivilegeRequirement> privilegeRequirements;

    @NotBlank
    @Min(1)
    @Column(name = "estimated_duration", nullable = false)
    private Long duration;

    public Long getMedicalServiceId() {
        return medicalServiceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public Set<VisitMedicalServices> getVisitMedicalServices() {
        return visitMedicalServices;
    }

    public Long getDuration() {
        return duration;
    }

    public Set<PrivilegeRequirement> getPrivilegeRequirements() {
        return privilegeRequirements;
    }
}
