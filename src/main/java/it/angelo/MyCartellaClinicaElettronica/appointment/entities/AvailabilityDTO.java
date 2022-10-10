package it.angelo.MyCartellaClinicaElettronica.appointment.entities;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AvailabilityDTO {

    private LocalDate endDate;
    private Long doctorId;
}
