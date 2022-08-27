package it.angelo.MyCartellaClinicaElettronica.appointment.controllers;

import it.angelo.MyCartellaClinicaElettronica.appointment.entities.Appointment;
import it.angelo.MyCartellaClinicaElettronica.appointment.entities.AppointmentDTO;
import it.angelo.MyCartellaClinicaElettronica.appointment.entities.AppointmentStateEnum;
import it.angelo.MyCartellaClinicaElettronica.appointment.repositories.AppointmentRepository;
import it.angelo.MyCartellaClinicaElettronica.appointment.services.AppointmentService;
import it.angelo.MyCartellaClinicaElettronica.user.entities.User;
import it.angelo.MyCartellaClinicaElettronica.user.utils.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AppointmentService appointmentService;


    // create appointment
    @PostMapping
    @PreAuthorize("hasRole('ROLE_SECRETARY')") //solo un SEGRETARIO registrato può creare un appuntamento
    public ResponseEntity<Appointment> create(@RequestBody AppointmentDTO appointment) throws Exception{
        return ResponseEntity.ok(appointmentService.save(appointment));
    }

    // get single appointment
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

    // get single appointment
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

    // find all appointment by status (CREATED, ACCEPTED, IN_PROGRESS,COMPLETED)
    @GetMapping("/findAllByStatus")
    public List<Appointment> getAllAppointmentByStatus(@RequestParam AppointmentStateEnum appointmentStateEnum){
        return appointmentRepository.findAllByStatus(appointmentStateEnum);
    }

    // TODO: 24/08/2022 duplicate method "getAllAppointmentByStatus" for testing.
    // find all appointment by status (CREATED, ACCEPTED, IN_PROGRESS,COMPLETED)
    @GetMapping("/findAllByStatus2")
    public ResponseEntity<List<Appointment>> getAllAppointmentByStatus(@RequestParam AppointmentStateEnum appointmentStateEnum, Principal principal){

        User user = (User)((UsernamePasswordAuthenticationToken) principal).getPrincipal();

        if(Roles.hasRole(user, Roles.SECRETARY)){
            return ResponseEntity.ok(appointmentRepository.findAllByStatus(appointmentStateEnum));

        } else if (Roles.hasRole(user, Roles.PATIENT)) {
            return ResponseEntity.ok(appointmentRepository.findAllByStatus(appointmentStateEnum));

        } else if (Roles.hasRole(user, Roles.DOCTOR)){
            return ResponseEntity.ok(appointmentRepository.findAllByStatus(appointmentStateEnum));
        }
        return null;
    }

    //dovrebbe fare la stessa cosa di findByAppointmentDateBetween ma servendosi di una query nativa.
    //la troviamo in "AppointmentRepository". Il metodo da debug sembra funzionare ma la query no.
    //da rivedere
    @GetMapping("/findAppointmentByRangeDate")
    public List<Appointment> getAllAppointmentByRangeDate(@RequestParam("appointmentStart")
                                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                            LocalDateTime appointmentStart,
                                                            @RequestParam("appointmentEnd")
                                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                            LocalDateTime appointmentEnd){
        return appointmentRepository.findAppointmentByRangeDate(appointmentStart, appointmentEnd);
    }

    // implementazione di findAppointmentByRangeDate in "AppointmentRepository"
    @GetMapping("/findByAppointmentDateBetween")
    public List<Appointment> findByAppointmentDateBetween(@RequestParam("start")
                                                                @DateTimeFormat(pattern = "yyyy-MM-dd")
                                                                LocalDate start,
                                                          @RequestParam("end")
                                                                @DateTimeFormat(pattern = "yyyy-MM-dd")
                                                                LocalDate end){
        return appointmentRepository.findByAppointmentDateBetween(start, end);
    }
}
