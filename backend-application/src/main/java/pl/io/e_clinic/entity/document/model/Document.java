package pl.io.e_clinic.entity.document.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import pl.io.e_clinic.entity.visit.model.Visit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Date;

@Entity
@Table(name = "document")
@JsonIgnoreProperties(value = {"visit"})
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_id", nullable = false)
    private Long documentId;

    @NotBlank
    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "document_type_id", nullable = false)
    private DocumentType documentType;

    @ManyToOne
    @JoinColumn(name = "visit_id", referencedColumnName = "visit_id", nullable = false)
    private Visit visit;


    @NotBlank
    @Size(max = 1000, message = "data can be up to 1000 characters long")
    @Column(name = "document_data", nullable = false)
    private String documentData;

    public Long getDocumentId() {
        return documentId;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public Visit getVisit() {
        return visit;
    }

    public String getDocumentData() {
        return documentData;
    }
}
