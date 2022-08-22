package it.angelo.MyCartellaClinicaElettronica.appointment.entities;

import lombok.Data;

@Data
public class AppointmentDTO {

    private String description;

    private String address;
    private String number;
    private String city;
    private String zipCode;
    private String state;

    private Long patient;

}
