package pl.io.e_clinic.entity.visit.model;

import pl.io.e_clinic.entity.user.model.Sex;
import pl.io.e_clinic.entity.user.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "visit")
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visit_id", nullable = false)
    private Long visitId;


    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "user_id", nullable = false)
    private User patient;

    //todo employee i inne fieldy

    public Long getVisitId() {
        return visitId;
    }

    public User getPatient() {
        return patient;
    }
}
