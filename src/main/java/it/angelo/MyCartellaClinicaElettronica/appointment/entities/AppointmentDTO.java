package it.angelo.MyCartellaClinicaElettronica.appointment.entities;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentDTO {

    private String description;

    private String address;
    private String number;
    private String city;
    private String zipCode;
    private String state;

    private LocalDateTime AppointmentStart;
    private LocalDateTime AppointmentEnd;

    private Long patient;

}
