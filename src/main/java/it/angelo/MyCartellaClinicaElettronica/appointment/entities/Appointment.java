package it.angelo.MyCartellaClinicaElettronica.appointment.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import it.angelo.MyCartellaClinicaElettronica.user.entities.User;
import it.angelo.MyCartellaClinicaElettronica.utils.entities.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

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

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime AppointmentStart;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime AppointmentEnd;

//--------------- test date for find appointment betweeen DATE -------------
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate appointmentDate;
//--------------------------------------------------------------------------

    private AppointmentStateEnum status = AppointmentStateEnum.CREATED;

    @ManyToOne
    private User patient;

    @ManyToOne
    private User doctor;

}
