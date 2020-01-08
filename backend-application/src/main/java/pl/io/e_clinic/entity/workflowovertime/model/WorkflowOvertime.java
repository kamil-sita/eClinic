package pl.io.e_clinic.entity.workflowovertime.model;

import pl.io.e_clinic.entity.employee.model.Employee;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "workflow_overtime")
public class WorkflowOvertime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workflow_overtime_id", nullable = false)
    private Long workflowOvertimeId;

    @ManyToOne
    @JoinColumn (name = "employee_id", referencedColumnName = "employee_id", nullable = false)
    private Employee employee;

    @NotNull
    @Column(name = "overtime_day", nullable = false)
    private Date overtimeDay;

    @NotNull
    @Column(name = "overtime_duration", nullable = false)
    private Time overtimeDuration;

    public WorkflowOvertime() {
    }

    public WorkflowOvertime(Employee employee, Date overtimeDay, Time overtimeDuration) {
        this.employee = employee;
        this.overtimeDay = overtimeDay;
        this.overtimeDuration = overtimeDuration;
    }

    public Long getWorkflowOvertimeId() { return workflowOvertimeId; }

    public Employee getEmployee() { return employee; }

    public Date getOvertimeDay() { return overtimeDay; }

    public Time getOvertimeDuration() { return overtimeDuration; }
}
