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
    private LocalDate dateUnavailabilityTimeSlot;
    private List<TimeSlot> listTimeSlots;

    //DATA PER CONTROLLARE LA DISPONIBILITA' DEI TIMESLOT

    private LocalDate dateCheckTimeSlot;
}
