package it.angelo.MyCartellaClinicaElettronica.appointment.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * contains all attributes relating to an appointment
 */
@Data
public class AppointmentDTO {

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
    private Long patient;

    private TimeSlot timeSlot;
}
