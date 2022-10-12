package it.angelo.MyCartellaClinicaElettronica.appointment.entities;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AvailabilityDTO {

    private LocalDate endDate;
    private Long doctorId;

    //----------------UNAVAILABILITY--------------

    private List<LocalDate> dateUnavailability;
}
