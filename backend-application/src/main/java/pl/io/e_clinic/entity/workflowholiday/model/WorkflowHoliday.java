package pl.io.e_clinic.entity.workflowholiday.model;

import pl.io.e_clinic.entity.employee.model.Employee;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
@Table(name = "workflow_holiday")
public class WorkflowHoliday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workflow_holiday_id", nullable = false)
    private Long workflowHolidayId;

    @ManyToOne
    @JoinColumn (name = "employee_id", referencedColumnName = "employee_id", nullable = false)
    private Employee employee;

    @NotNull
    @Column(name = "holidayday_start", nullable = false)
    private Date holidayDayStart;

    @NotNull
    @Column(name = "holidayday_end", nullable = false)
    private Date holidayDayEnd;

    public Long getWorkflowHolidayId() { return workflowHolidayId; }

    public Employee getEmployee() { return employee; }

    public Date getHolidayDayStart() { return holidayDayStart; }

    public Date getHolidayDayEnd() { return holidayDayEnd; }

    public WorkflowHoliday(@NotBlank Employee employee, @NotBlank WorkflowHoliday workflowHoliday) {
        this.employee = employee;
        this.holidayDayStart = workflowHoliday.holidayDayStart;
        this.holidayDayEnd = workflowHoliday.holidayDayEnd;
    }

    public WorkflowHoliday() {
    }
}
