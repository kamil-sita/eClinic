package pl.io.e_clinic.entity.visit.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import pl.io.e_clinic.entity.document.model.Document;
import pl.io.e_clinic.entity.employee.model.Employee;
import pl.io.e_clinic.entity.medicalservice.model.MedicalService;
import pl.io.e_clinic.entity.user.model.User;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @NotNull
    @Column(name = "scheduled_date", nullable = false)
    private Date scheduledDate;

    @OneToMany(mappedBy = "visit")
    private Set<Document> documents;

    @NotNull
    @Column(name = "visit_state", nullable = false)
    @Enumerated(value = EnumType.ORDINAL)
    private VisitStatus visitState;

    @Min(0)
    @Column(name = "estimated_duration", nullable = false)
    private Long estimatedDuration;

    @NotNull
    @Column(name = "starting_time", nullable = false)
    private Time startingTime;

    @ManyToMany(mappedBy = "visits")
    private Set<MedicalService> visitMedicalServices;

    public Visit() {
    }

    public Visit(User patient, Employee employee, @NotNull Date scheduledDate, @Min(0) Long estimatedDuration, @NotNull Time startingTime) {
        this.patient = patient;
        this.employee = employee;
        this.scheduledDate = scheduledDate;
        this.estimatedDuration = estimatedDuration;
        this.startingTime = startingTime;
        this.visitState = VisitStatus.APPOINTED;
        visitMedicalServices = new HashSet<>();
        documents = new HashSet<>();
    }

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

    public Long getEstimatedDuration() {
        return estimatedDuration;
    }

    public Time getStartingTime() {
        return startingTime;
    }

    public VisitStatus getVisitState() {
        return visitState;
    }

    public Visit setDocuments(Set<Document> documents) {
        this.documents = documents;
        return this;
    }

    public void setVisitState(VisitStatus visitState) {
        this.visitState = visitState;
    }
}
