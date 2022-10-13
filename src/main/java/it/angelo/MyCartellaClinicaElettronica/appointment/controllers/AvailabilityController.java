package it.angelo.MyCartellaClinicaElettronica.appointment.controllers;

import it.angelo.MyCartellaClinicaElettronica.appointment.entities.AvailabilityDTO;
import it.angelo.MyCartellaClinicaElettronica.appointment.exceptions.BodyErrorException;
import it.angelo.MyCartellaClinicaElettronica.appointment.exceptions.MethodErrorException;
import it.angelo.MyCartellaClinicaElettronica.appointment.exceptions.TimeSlotUnavailabilityException;
import it.angelo.MyCartellaClinicaElettronica.appointment.services.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/availability")
public class AvailabilityController {

    @Autowired
    private AvailabilityService availabilityService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_SECRETARY')")
    public ResponseEntity setUnavailability(@RequestBody AvailabilityDTO availabilityDTO){
        try {
            return ResponseEntity.ok(availabilityService.setUnavailabilityByDayAndDoctor(availabilityDTO));
        }catch (BodyErrorException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/timeslot")
    @PreAuthorize("hasRole('ROLE_SECRETARY')")
    public ResponseEntity setUnavailabilityTimeSlot(@RequestBody AvailabilityDTO availabilityDTO){
        try {
            return ResponseEntity.ok(availabilityService.setUnavailabilityTimeSlotByDayAndDoctor(availabilityDTO));
        }catch (BodyErrorException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        } catch (MethodErrorException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

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

    @GetMapping("/timeslot")
    public ResponseEntity checkAvailabilityTimeSlot(@RequestBody AvailabilityDTO availabilityDTO){
        try {
            return ResponseEntity.ok(availabilityService.checkAvailabilityTimeSlotByDayAndDoctor(availabilityDTO));
        }catch (BodyErrorException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        } catch (TimeSlotUnavailabilityException e) {
            return ResponseEntity.ok(e.getMessage());
        } catch (MethodErrorException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    //TODO modifica indisponibilità giorno

    //TODO modifica indisponibilità timeslot

    //TODO elimina indisponibilità
}
