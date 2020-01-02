package pl.io.e_clinic.entity.visit.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import pl.io.e_clinic.entity.document.model.Document;
import pl.io.e_clinic.entity.employee.model.Employee;
import pl.io.e_clinic.entity.medicalservice.model.MedicalService;
import pl.io.e_clinic.entity.user.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "visit")
@JsonIgnoreProperties(value = {"documents", "visitMedicalServices"})
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visit_id", nullable = false)
    private Long visitId;

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "user_id", nullable = false)
    private User patient;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id", nullable = false)
    private Employee employee;

    @NotBlank
    @Column(name = "scheduled_date", nullable = false)
    private Date scheduledDate;

    @OneToMany(mappedBy = "visit")
    private Set<Document> documents;

    @NotBlank
    @Column(name = "payment_status", nullable = false)
    @Enumerated(value = EnumType.ORDINAL)
    private PaymentStatus paymentStatus;


    @NotBlank
    @Column(name = "visit_state", nullable = false)
    @Enumerated(value = EnumType.ORDINAL)
    private VisitStatus visitState;


    @NotBlank
    @Column(name = "estimated_duration", nullable = false)
    private Long estimatedDuration;

    @NotBlank
    @Column(name = "starting_time", nullable = false)
    private Time startingTime;

    @ManyToMany(mappedBy = "visits")
    private Set<MedicalService> visitMedicalServices;

    public Set<MedicalService> getMedicalServices() {
        return visitMedicalServices;
    }

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

    public Employee getEmployee() {
        return employee;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public Long getEstimatedDuration() {
        return estimatedDuration;
    }

    public Time getStartingTime() {
        return startingTime;
    }

    public VisitStatus getVisitState() {
        return visitState;
    }
}
