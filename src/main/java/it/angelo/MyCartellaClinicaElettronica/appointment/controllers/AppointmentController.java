package it.angelo.MyCartellaClinicaElettronica.appointment.controllers;

import it.angelo.MyCartellaClinicaElettronica.appointment.entities.Appointment;
import it.angelo.MyCartellaClinicaElettronica.appointment.entities.AppointmentDTO;
import it.angelo.MyCartellaClinicaElettronica.appointment.repositories.AppointmentRepository;
import it.angelo.MyCartellaClinicaElettronica.appointment.services.AppointmentService;
import it.angelo.MyCartellaClinicaElettronica.user.entities.User;
import it.angelo.MyCartellaClinicaElettronica.user.utils.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_SECRETARY')") //solo un SEGRETARIO registrato può creare un appuntamento
    public ResponseEntity<Appointment> create(@RequestBody AppointmentDTO appointment) throws Exception{
        return ResponseEntity.ok(appointmentService.save(appointment));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getSingle(@PathVariable Long id, Principal principal){

        Optional<Appointment> appointment = appointmentRepository.findById(id);
        if (!appointment.isPresent())return ResponseEntity.notFound().build();

        User user = (User)((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        if(Roles.hasRole(user, Roles.SECRETARY) && appointment.get().getCreatedBy().getId() == user.getId()){
            return ResponseEntity.ok(appointment.get());

        }else if(Roles.hasRole(user, Roles.PATIENT) && appointment.get().getPatient().getId() == user.getId()) {
            return ResponseEntity.ok(appointment.get());

        }else if(Roles.hasRole(user, Roles.DOCTOR) && appointment.get().getDoctor().getId() == user.getId()){
            return ResponseEntity.ok(appointment.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Appointment>> getAll(Principal principal){
        User user = (User)((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        if(Roles.hasRole(user, Roles.SECRETARY)){
            return ResponseEntity.ok(appointmentRepository.findByCreatedBy(user));
        } else if (Roles.hasRole(user, Roles.PATIENT)) {
            return ResponseEntity.ok(appointmentRepository.findByPatient(user));
        } else if (Roles.hasRole(user, Roles.DOCTOR)){
            return ResponseEntity.ok(appointmentRepository.findByDoctor(user));
        }
        return null;
    }
}
