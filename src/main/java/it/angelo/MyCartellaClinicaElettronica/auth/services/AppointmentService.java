package it.angelo.MyCartellaClinicaElettronica.auth.services;

import it.angelo.MyCartellaClinicaElettronica.appointment.entities.Appointment;
import it.angelo.MyCartellaClinicaElettronica.appointment.entities.AppointmentDTO;
import it.angelo.MyCartellaClinicaElettronica.appointment.repositories.AppointmentRepository;
import it.angelo.MyCartellaClinicaElettronica.auth.controllers.PasswordRestoreController;
import it.angelo.MyCartellaClinicaElettronica.auth.controllers.SignupController;
import it.angelo.MyCartellaClinicaElettronica.user.entities.User;
import it.angelo.MyCartellaClinicaElettronica.user.repositories.UserRepository;
import it.angelo.MyCartellaClinicaElettronica.user.utils.Roles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AppointmentService {

    Logger logger = LoggerFactory.getLogger(AppointmentService.class);
    int lineGetter = new Exception().getStackTrace()[0].getLineNumber();


    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * save an appointment
     * @param appointmentInput is an appointmentDTO object
     * @return an appointment
     * @throws Exception a generic exception can be thrown
     */
    public Appointment save(AppointmentDTO appointmentInput)throws Exception{
        // rappresenta un utente autenticato, la gestione è demandata a JwtTokenFilter class
        logger.info("Public method 'save' method called at "+ AppointmentService.class +" at line#" + lineGetter);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Appointment appointment = new Appointment();
        appointment.setCreatedAt(LocalDateTime.now());
        appointment.setCreatedBy(user);
        appointment.setAddress(appointmentInput.getAddress());
        appointment.setCity(appointmentInput.getCity());
        appointment.setDescription(appointmentInput.getDescription());
        appointment.setState(appointmentInput.getState());
        appointment.setNumber(appointmentInput.getNumber());
        appointment.setZipCode(appointmentInput.getZipCode());

        appointment.setAppointmentStart(appointmentInput.getAppointmentStart());
        appointment.setAppointmentEnd(appointmentInput.getAppointmentEnd());

        appointment.setAppointmentDate(LocalDate.from(appointmentInput.getAppointmentStart()));

        //check for patient
        if(appointmentInput.getPatient() == null) throw new Exception("Patient not found");
        logger.error("if statement from public Appointment 'save' inside :"+ AppointmentService.class +" at line#" +
                lineGetter + "- Error : 'Patient is null");
        Optional<User> patient = userRepository.findById(appointmentInput.getPatient());
        if(!patient.isPresent() || !Roles.hasRole(patient.get(), Roles.PATIENT)) throw new Exception("Patient not found");
        logger.error("if statement from public Appointment 'save' inside "+ AppointmentService.class +" at line#" +
                lineGetter + "- Error : 'Patient not found");

        appointment.setPatient(patient.get());

        return appointmentRepository.save(appointment);
    }

    public Appointment update(Long id, Appointment appointmentInput){
        logger.info("Public method 'update' method called at "+ AppointmentService.class +" at line#" + lineGetter);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (appointmentInput == null)return null;
        logger.error("if statement from public Appointment 'update' inside "+ AppointmentService.class +" at line#" +
                lineGetter + "- Error : 'appointmentInput is null");
        appointmentInput.setId(id);
        appointmentInput.setUpdatedAt(LocalDateTime.now());
        appointmentInput.setUpdatedBy(user);
        return appointmentRepository.save(appointmentInput);
    }

    public boolean canEdit(Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logger.info("Public method 'canEdit' method called at "+ AppointmentService.class +" at line#" + lineGetter);
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        if(!appointment.isPresent())return false;
        logger.error("if statement from public Appointment 'update' inside "+ AppointmentService.class +" at line#" +
                lineGetter + "- Error : 'appointmentInput is not present");
        return appointment.get().getCreatedBy().getId() == user.getId();
    }
}
