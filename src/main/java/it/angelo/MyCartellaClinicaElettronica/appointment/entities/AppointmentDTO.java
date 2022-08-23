package it.angelo.MyCartellaClinicaElettronica.appointment.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime AppointmentStart;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime AppointmentEnd;

    private Long patient;

}
