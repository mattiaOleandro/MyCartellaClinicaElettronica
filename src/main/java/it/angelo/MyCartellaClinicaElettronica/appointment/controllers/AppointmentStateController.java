package it.angelo.MyCartellaClinicaElettronica.appointment.controllers;

import it.angelo.MyCartellaClinicaElettronica.appointment.entities.Appointment;
import it.angelo.MyCartellaClinicaElettronica.appointment.repositories.AppointmentRepository;
import it.angelo.MyCartellaClinicaElettronica.auth.controllers.PasswordRestoreController;
import it.angelo.MyCartellaClinicaElettronica.auth.services.AppointmentStateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(AppointmentStateController.class);
    int lineGetter = new Exception().getStackTrace()[0].getLineNumber();


    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @PutMapping("/accepted")
    public ResponseEntity accepted(@PathVariable long id) throws Exception{
        logger.debug(String.format("@PutMapped \'/accepted\' method called at %s at line# %d by this ID %s",
                AppointmentStateController.class , lineGetter, id));
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        if(!appointment.isPresent())return ResponseEntity.notFound().build();
        return ResponseEntity.ok(appointmentStateService.setAccept(appointment.get()));
    }

    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @PutMapping("/in-progress")
    public ResponseEntity inProgress(@PathVariable long id) throws Exception{
        logger.debug(String.format("@PutMapped \'/inProgress\' method called at %s at line# %d by this ID %s",
                AppointmentStateController.class , lineGetter, id));
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        if(!appointment.isPresent())return ResponseEntity.notFound().build();
        return ResponseEntity.ok(appointmentStateService.setInProgress(appointment.get()));
    }

    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @PutMapping("/complete")
    public ResponseEntity complete(@PathVariable long id) throws Exception{
        logger.debug(String.format("@PutMapped \'/complete\' method called at %s at line# %d by this ID %s",
                AppointmentStateController.class , lineGetter, id));
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        if(!appointment.isPresent())return ResponseEntity.notFound().build();
        return ResponseEntity.ok(appointmentStateService.setComplete(appointment.get()));
    }
}
