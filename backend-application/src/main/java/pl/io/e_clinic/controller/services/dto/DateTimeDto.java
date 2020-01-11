package pl.io.e_clinic.controller.services.dto;

import java.sql.Date;
import java.sql.Time;

public class DateTimeDto {
    private Date date;
    private Time time;

    public Date getDate() {
        return date;
    }

    public DateTimeDto setDate(Date date) {
        this.date = date;
        return this;
    }

    public Time getTime() {
        return time;
    }

    public DateTimeDto setTime(Time time) {
        this.time = time;
        return this;
    }
}
