package it.angelo.MyCartellaClinicaElettronica.appointment.controllers;

import it.angelo.MyCartellaClinicaElettronica.appointment.entities.AvailabilityDTO;
import it.angelo.MyCartellaClinicaElettronica.appointment.exceptions.BodyErrorException;
import it.angelo.MyCartellaClinicaElettronica.appointment.services.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity checkAvailability(@RequestBody AvailabilityDTO availabilityDTO){
        try {
            return ResponseEntity.ok(availabilityService.generateAvailabilityByDoctor(availabilityDTO));
        }catch (BodyErrorException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    //per rimuovere i giorni di ferie o di malattie creare gia la data in calendar day e mettere tutti gli slot occupati
}
