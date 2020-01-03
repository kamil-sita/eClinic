package pl.io.e_clinic.services;

import pl.io.e_clinic.entity.document.model.Document;
import pl.io.e_clinic.entity.document.model.DocumentType;
import pl.io.e_clinic.entity.user.model.User;
import pl.io.e_clinic.entity.visit.model.Visit;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Klasa ta reprezentuje historię choroby, ale nie istnieje w bazie danych i jest generowana na życzenie
 */
public class MedicalHistoryService {

    private List<MedicalHistoryElement> history = new ArrayList();

    /**
     * Tworzy historię choroby danego użytkownika, posortowaną datami (rosnąco)
     * @param user
     */
    public MedicalHistoryService(User user) {
        List<Visit> visits = new ArrayList<>(user.getVisits());

        visits.sort(Comparator.comparing(Visit::getScheduledDate));

        visits.forEach(this::add);
    }

    private void add(Visit visit) {
        visit.getDocuments().forEach(
                (Document doc) -> {
                    history.add(
                            new MedicalHistoryElement(
                                    visit.getScheduledDate(),
                                    doc.getDocumentType(),
                                    doc.getDocumentData()
                            )
                    );
                }
        );
    }

    public List<MedicalHistoryElement> getHistory() {
        return history;
    }

    private class MedicalHistoryElement {
        private Date date;
        private DocumentType documentType;
        private String data;

        public MedicalHistoryElement(Date date, DocumentType documentType, String data) {
            this.date = date;
            this.documentType = documentType;
            this.data = data;
        }

        public Date getDate() {
            return date;
        }

        public DocumentType getDocumentType() {
            return documentType;
        }

        public String getData() {
            return data;
        }
    }
}
