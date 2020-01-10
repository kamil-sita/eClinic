package pl.io.e_clinic.controller.employees.dto;
import pl.io.e_clinic.entity.room.model.Room;
import pl.io.e_clinic.entity.weekschedule.model.WeekDay;

import java.sql.Time;

public class ScheduleDto {
    private WeekDay weekDay;
    private Time startingTime;
    private Time endingTime;
    private Room room;


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

    public Room getRoom() {
        return room;
    }

    public ScheduleDto setRoom(Room room) {
        this.room = room;
        return this;
    }
}
