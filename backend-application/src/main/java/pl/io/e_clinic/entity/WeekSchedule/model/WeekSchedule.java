package pl.io.e_clinic.entity.weekschedule.model;



import pl.io.e_clinic.entity.room.model.Room;
import pl.io.e_clinic.entity.employee.model.Employee;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Time;


@Entity
@Table(name = "week_schedule")
public class WeekSchedule {

    @Id
    @JoinColumn(name = "week_schedule_id", nullable = false)
    private Long weekScheduleId;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id",nullable = false)
    protected Employee employee;

    @NotBlank
    @Column(name = "week_day_id", nullable = false)
    @Enumerated(value = EnumType.ORDINAL)
    protected WeekDay weekDay;

    @NotBlank
    @Column(name = "starting_time", nullable = false)
    protected Time startingTime;

    @NotBlank
    @Column(name = "ending_time", nullable = false)
    protected Time endingTime;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    protected Room room;

    public WeekSchedule() {
    }

    public Long getWeekScheduleId() {
        return weekScheduleId;
    }

    public void setWeekScheduleId(Long weekScheduleId) {
        this.weekScheduleId = weekScheduleId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public WeekDay getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(WeekDay weekDay) {
        this.weekDay = weekDay;
    }

    public Time getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(Time startingTime) {
        this.startingTime = startingTime;
    }

    public Time getEndingTime() {
        return endingTime;
    }

    public void setEndingTime(Time endingTime) {
        this.endingTime = endingTime;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
