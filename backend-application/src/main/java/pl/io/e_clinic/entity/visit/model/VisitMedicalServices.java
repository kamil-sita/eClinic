package pl.io.e_clinic.entity.visit.model;

import pl.io.e_clinic.entity.medicalservice.model.MedicalService;

import javax.persistence.*;

@Entity
@Table(name = "visit_medical_services")
public class VisitMedicalServices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visit_medical_services_id", nullable = false)
    private Long visitMedicalServicesId;

    @ManyToOne
    @JoinColumn(name = "visit_id", referencedColumnName = "visit_id", nullable = false)
    private Visit visit;

    @ManyToOne
    @JoinColumn(name = "service_id", referencedColumnName = "service_id", nullable = false)
    private MedicalService medicalService;

    public Visit getVisit() {
        return visit;
    }

    public MedicalService getMedicalService() {
        return medicalService;
    }
}
