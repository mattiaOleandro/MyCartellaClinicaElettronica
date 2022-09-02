package it.angelo.MyCartellaClinicaElettronica.appointment.controllers;

import it.angelo.MyCartellaClinicaElettronica.appointment.entities.Appointment;
import it.angelo.MyCartellaClinicaElettronica.appointment.repositories.AppointmentRepository;
import it.angelo.MyCartellaClinicaElettronica.auth.services.AppointmentStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/appointment/{id}/state")
public class AppointmentStateController {

    @Autowired
    private AppointmentStateService appointmentStateService;

    @Autowired
    private AppointmentRepository appointmentRepository;


    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @PutMapping("/accepted")
    public ResponseEntity accepted(@PathVariable long id) throws Exception{
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        if(!appointment.isPresent())return ResponseEntity.notFound().build();
        return ResponseEntity.ok(appointmentStateService.setAccept(appointment.get()));
    }

    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @PutMapping("/in-progress")
    public ResponseEntity inProgress(@PathVariable long id) throws Exception{
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        if(!appointment.isPresent())return ResponseEntity.notFound().build();
        return ResponseEntity.ok(appointmentStateService.setInProgress(appointment.get()));
    }

    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @PutMapping("/complete")
    public ResponseEntity complete(@PathVariable long id) throws Exception{
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        if(!appointment.isPresent())return ResponseEntity.notFound().build();
        return ResponseEntity.ok(appointmentStateService.setComplete(appointment.get()));
    }
}
