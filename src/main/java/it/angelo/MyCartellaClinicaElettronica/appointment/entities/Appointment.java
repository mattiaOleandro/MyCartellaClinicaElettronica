package it.angelo.MyCartellaClinicaElettronica.appointment.entities;

import it.angelo.MyCartellaClinicaElettronica.user.entities.User;
import it.angelo.MyCartellaClinicaElettronica.utils.entities.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "appointment")
public class Appointment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String description;

    private String address;
    private String number;
    private String city;
    private String zipCode;
    private String state;

    private LocalDateTime AppointmentStart;
    private LocalDateTime AppointmentEnd;

    private AppointmentStateEnum status = AppointmentStateEnum.CREATED;

    @ManyToOne
    private User patient;

    @ManyToOne
    private User doctor;

}
