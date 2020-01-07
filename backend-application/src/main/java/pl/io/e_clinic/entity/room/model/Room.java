package pl.io.e_clinic.entity.room.model;

import pl.io.e_clinic.entity.WeekSchedule.model.WeekDay;
import pl.io.e_clinic.entity.user.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@Entity
@Table(name = "room")
public class Room {

    @Id
    @JoinColumn(name = "room_id", nullable = false)
    protected Long room_id;

    @NotBlank
    @Column(name = "room_name", nullable = false)
    @Enumerated(value = EnumType.ORDINAL)
    protected String roomName;

}