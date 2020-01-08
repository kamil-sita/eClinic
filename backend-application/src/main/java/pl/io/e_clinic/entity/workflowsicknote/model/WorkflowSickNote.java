package pl.io.e_clinic.entity.workflowsicknote.model;

import pl.io.e_clinic.entity.employee.model.Employee;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
@Table(name = "workflow_sick_note")
public class WorkflowSickNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workflow_sick_note_id", nullable = false)
    private Long workflowSickNoteId;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id", nullable = false)
    private Employee employee;

    @NotNull
    @Column(name = "sick_day_start", nullable = false)
    private Date sickDayStart;

    @NotNull
    @Column(name = "sick_day_end", nullable = false)
    private Date sickDayEnd;

    public WorkflowSickNote() {
    }

    public WorkflowSickNote(Employee employee, Date sickDayStart, Date sickDayEnd) {
        this.employee = employee;
        this.sickDayStart = sickDayStart;
        this.sickDayEnd = sickDayEnd;
    }

    public Long getWorkflowSickNoteId() { return workflowSickNoteId; }

    public Employee getEmployee() { return employee; }

    public Date getSickDayStart() { return sickDayStart; }

    public Date getSickDayEnd() { return sickDayEnd; }
}
