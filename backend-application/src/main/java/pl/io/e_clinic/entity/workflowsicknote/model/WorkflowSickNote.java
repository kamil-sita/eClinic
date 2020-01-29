package pl.io.e_clinic.entity.workflowsicknote.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import pl.io.e_clinic.entity.employee.model.Employee;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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

    public Long getWorkflowSickNoteId() { return workflowSickNoteId; }

    public Employee getEmployee() { return employee; }

    public Date getSickDayStart() { return sickDayStart; }

    public Date getSickDayEnd() { return sickDayEnd; }

    public WorkflowSickNote(@NotBlank Employee employee, @NotBlank WorkflowSickNote workflowSickNote) {
        this.employee = employee;
        this.sickDayStart = workflowSickNote.sickDayStart;
        this.sickDayEnd = workflowSickNote.sickDayEnd;
    }

    public WorkflowSickNote() {
    }
}
