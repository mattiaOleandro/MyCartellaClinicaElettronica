package it.angelo.MyCartellaClinicaElettronica.appointment.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import it.angelo.MyCartellaClinicaElettronica.user.entities.User;
import it.angelo.MyCartellaClinicaElettronica.utils.entities.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * represent an appointment
 */
@Data
@Entity
@DynamicUpdate
@Table(name = "APPOINTMENT")
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
    private LocalDateTime appointmentStart;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime appointmentEnd;

//--------------- test date for find appointment betweeen DATE -------------
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate appointmentDate;
//--------------------------------------------------------------------------

    private AppointmentStateEnum status = AppointmentStateEnum.CREATED;

    @ManyToOne
    private User patient;

    @ManyToOne
    private User doctor;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "calendar_day_id")
    private CalendarDay calendarDay;

    private TimeSlot timeSlot;

}
