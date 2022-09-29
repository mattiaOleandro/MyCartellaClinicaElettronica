package it.angelo.MyCartellaClinicaElettronica.appointment.services;

import it.angelo.MyCartellaClinicaElettronica.appointment.entities.Appointment;
import it.angelo.MyCartellaClinicaElettronica.appointment.entities.AppointmentStateEnum;
import it.angelo.MyCartellaClinicaElettronica.appointment.repositories.AppointmentRepository;
import it.angelo.MyCartellaClinicaElettronica.user.entities.User;
import it.angelo.MyCartellaClinicaElettronica.user.services.DoctorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AppointmentStateService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorService doctorService;

    Logger logger = LoggerFactory.getLogger(AppointmentStateService.class);
    int lineGetter = new Exception().getStackTrace()[0].getLineNumber();

    public Appointment setAccept(Appointment appointment) throws Exception{

        logger.debug(String.format("\'/setAccept\' method called at %s at line# %d by %s",
                AppointmentStateService.class , lineGetter, appointment.getNumber()));

        if(appointment == null) throw new NullPointerException();


        if(appointment.getStatus() != AppointmentStateEnum.CREATED) throw new Exception("Cannot edit appointment");

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(appointment.getPatient().getId() != user.getId()) throw new Exception("This is not your appointment");

        // Additional actions (!)

        //Doctor selection, the first available doctor will be assigned to appointment
        User doctor = doctorService.pickDoctor();
        appointment.setDoctor(doctor);

        //go ahead one step in state machine
        appointment.setStatus(AppointmentStateEnum.ACCEPTED);
        appointment.setUpdatedAt(LocalDateTime.now());
        appointment.setUpdatedBy(user);
        return appointmentRepository.save(appointment);
    }

    public Appointment setInProgress(Appointment appointment) throws Exception{
        logger.debug(String.format("\'/setInProgress\' method called at %s at line# %d by %s",
                AppointmentStateService.class , lineGetter, appointment.getNumber()));

        if(appointment == null) throw new NullPointerException();

        if(appointment.getStatus() != AppointmentStateEnum.ACCEPTED) throw new Exception("Cannot edit appointment");

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(appointment.getDoctor().getId() != user.getId()) throw new Exception("This is not your appointment");

        //go ahead one step
        appointment.setStatus(AppointmentStateEnum.IN_PROGRESS);
        appointment.setUpdatedAt(LocalDateTime.now());
        appointment.setUpdatedBy(user);
        return appointmentRepository.save(appointment);
    }

    public Appointment setComplete(Appointment appointment) throws Exception{
        logger.debug(String.format("\'/setComplete\' method called at %s at line# %d by %s",
                AppointmentStateService.class , lineGetter, appointment.getNumber()));
        if(appointment == null) throw new NullPointerException();

        if(appointment.getStatus() != AppointmentStateEnum.IN_PROGRESS) throw new Exception("Cannot edit appointment");

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(appointment.getDoctor().getId() != user.getId()) throw new Exception("This is not your appointment");

        //go ahead one step
        appointment.setStatus(AppointmentStateEnum.COMPLETED);
        appointment.setUpdatedAt(LocalDateTime.now());
        appointment.setUpdatedBy(user);
        return appointmentRepository.save(appointment);
    }
}
