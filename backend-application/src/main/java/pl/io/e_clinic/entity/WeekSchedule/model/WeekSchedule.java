package pl.io.e_clinic.entity.weekschedule.model;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import pl.io.e_clinic.entity.room.model.Room;
import pl.io.e_clinic.entity.employee.model.Employee;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Time;


@Entity
@Table(name = "week_schedule")
@JsonIgnoreProperties(value = {"weekScheduleId","employee","employeeId"})
public class WeekSchedule {

    @Id
    @Column(name = "week_schedule_id", nullable = false)
    private Long weekScheduleId;

    @ManyToOne
    @JoinColumn(name = "employee_id",referencedColumnName = "employee_id",nullable = false)
    protected Employee employee;

    
    @Column(name = "week_day_id", nullable = false)
    @Enumerated(value = EnumType.ORDINAL)
    protected WeekDay weekDay;


    @Column(name = "starting_time", nullable = false)
    protected Time startingTime;


    @Column(name = "ending_time", nullable = false)
    protected Time endingTime;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    protected Room room;

    public WeekSchedule(){

    }

    public WeekSchedule(Long weekScheduleId, Employee employee, WeekDay weekDay, Time startingTime, Time endingTime, Room room) {
        this.weekScheduleId=weekScheduleId;
        this.employee=employee;
        this.weekDay=weekDay;
        this.startingTime=startingTime;
        this.endingTime=endingTime;
        this.room=room;
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

    public Long getEmployeeId() {
        return employee.getEmployeeId();
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
