package it.angelo.MyCartellaClinicaElettronica.appointment.services;

import it.angelo.MyCartellaClinicaElettronica.appointment.entities.Appointment;
import it.angelo.MyCartellaClinicaElettronica.appointment.entities.AppointmentDTO;
import it.angelo.MyCartellaClinicaElettronica.appointment.repositories.AppointmentRepository;
import it.angelo.MyCartellaClinicaElettronica.user.entities.User;
import it.angelo.MyCartellaClinicaElettronica.user.repositories.UserRepository;
import it.angelo.MyCartellaClinicaElettronica.user.utils.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    public Appointment save(AppointmentDTO appointmentInput)throws Exception{
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


        //check for patient
        if(appointmentInput.getPatient() == null) throw new Exception("Patient not found");
        Optional<User> patient = userRepository.findById(appointmentInput.getPatient());
        if(!patient.isPresent() || !Roles.hasRole(patient.get(), Roles.PATIENT)) throw new Exception("Patient not found");

        appointment.setPatient(patient.get());

        return appointmentRepository.save(appointment);
    }

    public Appointment update(Long id, Appointment appointmentInput){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (appointmentInput == null)return null;
        appointmentInput.setId(id);
        appointmentInput.setUpdatedAt(LocalDateTime.now());
        appointmentInput.setUpdatedBy(user);
        return appointmentRepository.save(appointmentInput);
    }

    public boolean canEdit(Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        if(!appointment.isPresent())return false;
        return appointment.get().getCreatedBy().getId() == user.getId();
    }
}
