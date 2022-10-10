package it.angelo.MyCartellaClinicaElettronica.appointment.controllers;

import it.angelo.MyCartellaClinicaElettronica.appointment.entities.AvailabilityDTO;
import it.angelo.MyCartellaClinicaElettronica.appointment.services.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/availability")
public class AvailabilityController {

    @Autowired
    private AvailabilityService availabilityService;

    @GetMapping
    public List<LocalDate> checkAvailability(@RequestBody AvailabilityDTO availabilityDTO){
        return availabilityService.generateAvailabilityByDoctor(availabilityDTO);
    }

    //per rimuovere i giorni di ferie o di malattie creare gia la data in calendar day e mettere tutti gli slot occupati
}
