package pl.io.e_clinic.entity.visit.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import pl.io.e_clinic.entity.document.model.Document;
import pl.io.e_clinic.entity.user.model.Sex;
import pl.io.e_clinic.entity.user.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "visit")
@JsonIgnoreProperties(value = {"documents"})
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visit_id", nullable = false)
    private Long visitId;


    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "user_id", nullable = false)
    private User patient;

    @NotBlank
    @Column(name = "scheduled_date", nullable = false)
    private Date scheduledDate;

    @OneToMany(mappedBy = "visit")
    private Set<Document> documents;

    //todo employee i inne fieldy

    public Date getScheduledDate() {
        return scheduledDate;
    }

    public Long getVisitId() {
        return visitId;
    }

    public User getPatient() {
        return patient;
    }

    public Set<Document> getDocuments() {
        return documents;
    }
}
