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
        logger.debug(String.format(" \'/save\' method called at %s at line# %d by %s",
                AppointmentService.class , lineGetter, appointmentInput.getNumber()));
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
        logger.error(String.format("if statement in \'/save\' method called at %s at line# %d by %s - Error : Patient not found.",
                AppointmentService.class, lineGetter, appointmentInput.getNumber()));
        Optional<User> patient = userRepository.findById(appointmentInput.getPatient());
        if(!patient.isPresent() || !Roles.hasRole(patient.get(), Roles.PATIENT)) throw new Exception("Patient not found");
        logger.error(String.format("if statement in \'/save\' method called at %s at line# %d by %s - Error : Patient not found.",
                AppointmentService.class, lineGetter, appointmentInput.getNumber()));

        appointment.setPatient(patient.get());

        return appointmentRepository.save(appointment);
    }

    public Appointment update(Long id, Appointment appointmentInput){
        logger.debug(String.format(" \'/update\' method called at %s at line# %d by %s",
                AppointmentService.class , lineGetter, appointmentInput.getNumber()));
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (appointmentInput == null)return null;
        logger.error(String.format("if statement in \'/save\' method called at %s at line# %d by %s - Error : appointmentInput is null.",
                AppointmentService.class, lineGetter, appointmentInput.getNumber()));
        appointmentInput.setId(id);
        appointmentInput.setUpdatedAt(LocalDateTime.now());
        appointmentInput.setUpdatedBy(user);
        return appointmentRepository.save(appointmentInput);
    }

    public boolean canEdit(Long id) {
        logger.debug(String.format(" \'/canEdit\' method called at %s at line# %d by ID %s",
                AppointmentService.class , lineGetter, id));
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        if(!appointment.isPresent())return false;
        logger.error(String.format("if statement in \'/canEdit\' method called at %s at line# %d - Error : appointment is not present.",
                AppointmentService.class, lineGetter));
        return appointment.get().getCreatedBy().getId() == user.getId();
    }
}
