package pl.io.e_clinic.controller.employees.dto;

import pl.io.e_clinic.entity.employee.model.Employee;
import pl.io.e_clinic.entity.room.model.Room;
import pl.io.e_clinic.entity.weekschedule.model.WeekDay;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Time;

public class ScheduleDto {
    private WeekDay weekDay;
    private Time startingTime;
    private Time endingTime;
    private Long roomId;
    private String roomName;


    public WeekDay getWeekDay() {
        return weekDay;
    }

    public ScheduleDto setWeekDay(WeekDay weekDay) {
        this.weekDay = weekDay;
        return this;
    }

    public Time getStartingTime() {
        return startingTime;
    }

    public ScheduleDto setStartingTime(Time startingTime) {
        this.startingTime = startingTime;
        return this;
    }

    public Time getEndingTime() {
        return endingTime;
    }

    public ScheduleDto setEndingTime(Time endingTime) {
        this.endingTime = endingTime;
        return this;
    }
    public Long getRoomId() {
        return roomId;
    }

    public ScheduleDto setRoomId(Long roomId) {
        this.roomId = roomId;
        return this;
    }

    public String getRoomName() {
        return roomName;
    }

    public ScheduleDto setRoomName(String roomName) {
        this.roomName = roomName;
        return this;
    }

}
