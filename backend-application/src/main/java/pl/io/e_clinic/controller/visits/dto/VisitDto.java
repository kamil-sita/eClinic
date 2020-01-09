package pl.io.e_clinic.controller.visits.dto;

import pl.io.e_clinic.entity.visit.model.PaymentStatus;

import java.sql.Date;
import java.sql.Time;

public class VisitDto {
    private long employeeId;
    private long patientId;
    private long serviceId;
    private Date scheduledDate;
    private Time startingTime;

    public long getEmployeeId() {
        return employeeId;
    }

    public VisitDto setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    public long getPatientId() {
        return patientId;
    }

    public VisitDto setPatientId(long patientId) {
        this.patientId = patientId;
        return this;
    }

    public long getServiceId() {
        return serviceId;
    }

    public VisitDto setServiceId(long serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public Date getScheduledDate() {
        return scheduledDate;
    }

    public VisitDto setScheduledDate(Date scheduledDate) {
        this.scheduledDate = scheduledDate;
        return this;
    }

    public Time getStartingTime() {
        return startingTime;
    }

    public VisitDto setStartingTime(Time startingTime) {
        this.startingTime = startingTime;
        return this;
    }
}
