package it.angelo.MyCartellaClinicaElettronica.appointment.controllers;

import it.angelo.MyCartellaClinicaElettronica.appointment.entities.Appointment;
import it.angelo.MyCartellaClinicaElettronica.appointment.entities.AppointmentDTO;
import it.angelo.MyCartellaClinicaElettronica.appointment.entities.AppointmentStateEnum;
import it.angelo.MyCartellaClinicaElettronica.appointment.repositories.AppointmentRepository;
import it.angelo.MyCartellaClinicaElettronica.appointment.services.AppointmentService;
import it.angelo.MyCartellaClinicaElettronica.user.entities.User;
import it.angelo.MyCartellaClinicaElettronica.user.utils.Roles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/appointment")
//@PreAuthorize("hasRole('"+ Roles.REGISTERED + "') OR hasRole('"+Roles.ADMIN+"')")
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AppointmentService appointmentService;

    Logger logger = LoggerFactory.getLogger(AppointmentController.class);
    int lineGetter = new Exception().getStackTrace()[0].getLineNumber();


    // create appointment
    @PostMapping
    @PreAuthorize("hasRole('ROLE_SECRETARY')") //solo un SEGRETARIO registrato può creare un appuntamento
    public HttpEntity<? extends Object> create(@RequestBody AppointmentDTO appointment) throws Exception{
        Appointment a = appointmentService.save(appointment);
        if (appointmentService.isFree()){
            return new ResponseEntity<String>(
                    "The slot is busy",
                    HttpStatus.BAD_REQUEST);
        }else {
            return ResponseEntity.ok(a);
        }
    }

    // get single appointment
    @GetMapping("/{id}")
    //@PostAuthorize("hasRole('"+Roles.ADMIN + "') OR returnObject.body == null OR returnObject.body.createdBy.id == authentication.principal.id")
    public ResponseEntity<Appointment> getSingle(@PathVariable Long id, Principal principal){
        logger.debug(String.format("@GetMapped \'/getSingle\' method called at %s at line# %d by this ID %s",
                AppointmentController.class , lineGetter, id));
        try{
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

    }catch (Exception e){
            logger.error(String.format("@GetMapped \'/getSingle\' method called at %s at line# %d by ID %s - Error : %s",
                    AppointmentController.class, lineGetter, id.toString() , e.getMessage()));
        }
        return ResponseEntity.notFound().build();
    }

    // get all appointment
    @GetMapping
    public ResponseEntity<List<Appointment>> getAll(Principal principal){
        logger.debug(String.format("@GetMapped \'/getAll\' method called at %s at line# %d by %s",
                AppointmentController.class , lineGetter, principal.getName()));

        User user = (User)((UsernamePasswordAuthenticationToken) principal).getPrincipal();

        try{
            if(Roles.hasRole(user, Roles.SECRETARY)){
            return ResponseEntity.ok(appointmentRepository.findByCreatedBy(user));

            } else if (Roles.hasRole(user, Roles.PATIENT)) {
            return ResponseEntity.ok(appointmentRepository.findByPatient(user));

            } else if (Roles.hasRole(user, Roles.DOCTOR)){

            }
        }catch (Exception e){
            logger.error(String.format("if statement in \'/getAll\' method called at %s at line# %d by %s - Error : %s",
                    AppointmentController.class, lineGetter, principal.getName() , e.getMessage()));
        }
        return ResponseEntity.ok(appointmentRepository.findByDoctor(user));
    }

    // find all appointment by status (CREATED, ACCEPTED, IN_PROGRESS,COMPLETED)
    @GetMapping("/findAllByStatus")
    public List<Appointment> getAllAppointmentByStatus(@RequestParam AppointmentStateEnum appointmentStateEnum){
        logger.debug(String.format("@GetMapped \'/getAllAppointmentByStatus\' method called at %s at line# %d by %s",
                AppointmentController.class , lineGetter, appointmentStateEnum.name()));
        return appointmentRepository.findAllByStatus(appointmentStateEnum);
    }

    // TODO: 24/08/2022 duplicate method "getAllAppointmentByStatus" for testing.
    // find all appointment by status (CREATED, ACCEPTED, IN_PROGRESS,COMPLETED)
    @GetMapping("/findAllByStatus2")
    public ResponseEntity<List<Appointment>> getAllAppointmentByStatus(@RequestParam AppointmentStateEnum appointmentStateEnum, Principal principal){

        logger.debug(String.format("@GetMapped \'/getAllAppointmentByStatus\' method called at %s at line# %d - Date: %s",
                AppointmentController.class , lineGetter, principal.getName()));
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
        logger.debug(String.format("@GetMapped \'/getAllAppointmentByRangeDate\' method called at %s at line# %d - Date: %s",
                AppointmentController.class , lineGetter, appointmentStart.toString()));
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
        logger.debug(String.format("@GetMapped \'/findByAppointmentDateBetween\' method called at %s at line# %d - Date: %s",
                AppointmentController.class , lineGetter, start.toString()));
        return appointmentRepository.findByAppointmentDateBetween(start, end);
    }

    //edit an appointment
    @PutMapping("/{id}")
    public ResponseEntity<Appointment> update(@RequestBody Appointment appointment, @PathVariable Long id){
        logger.debug(String.format("@PutMapped \'/findByAppointmentDateBetween\' method called at %s at line# %d - ID: %s",
                AppointmentController.class , lineGetter, id.toString()));
        if(!appointmentService.canEdit(id)){
            return  ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(appointmentService.update(id, appointment));
    }

    //delete an appointment
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        logger.debug(String.format("@DeleteMapped \'/delete\' method called at %s at line# %d - ID: %s",
                AppointmentController.class , lineGetter, id.toString()));
        if(!appointmentService.canEdit(id)){
            return  ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        appointmentRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
