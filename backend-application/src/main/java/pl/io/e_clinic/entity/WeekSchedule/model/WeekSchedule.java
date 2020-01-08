package pl.io.e_clinic.entity.WeekSchedule.model;



import org.springframework.context.annotation.Primary;
import pl.io.e_clinic.entity.room.model.Room;
import pl.io.e_clinic.entity.user.model.User;
import pl.io.e_clinic.entity.employee.model.Employee;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Time;


@Entity
@Table(name = "week_schedule")
public class WeekSchedule {

    @Id
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
}
