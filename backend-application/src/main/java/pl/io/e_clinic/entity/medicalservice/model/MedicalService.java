package pl.io.e_clinic.entity.medicalservice.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import pl.io.e_clinic.entity.privilege.model.Privilege;
import pl.io.e_clinic.entity.visit.model.Visit;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "medical_service")

@JsonIgnoreProperties(value = {"visits", "privilegeRequirements"})
public class MedicalService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id", nullable = false)
    private Long medicalServiceId;


    @NotBlank
    @Size(max = 50, message = "service name can be up to 50 characters long")
    @Column(name = "service_name")
    private String serviceName;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "visit_medical_services",
            joinColumns = @JoinColumn(name = "service_id"),
            inverseJoinColumns = @JoinColumn(name = "visit_id")
    )
    private Set<Visit> visits;

    @ElementCollection(targetClass = Privilege.class)
    @CollectionTable(name = "privilege_requirement",
            joinColumns = @JoinColumn(name = "service_id")
    )
    @Column(name = "privilege_id")
    @Enumerated(value = EnumType.ORDINAL)
    private Set<Privilege> privileges;

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


    public Long getDuration() {
        return duration;
    }

    public Set<Visit> getVisits() {
        return visits;
    }

    public Set<Privilege> getPrivileges() {
        return privileges;
    }
}
