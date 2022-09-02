package it.angelo.MyCartellaClinicaElettronica.auth.services;

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

        logger.info("Public method 'setAccept' method called at "+ AppointmentService.class +" at line#" + lineGetter);

        if(appointment == null) throw new NullPointerException();
        logger.error("if statement from public AppointmentStateService 'setAccept' inside :"+
                AppointmentStateService.class +" at line#" +
                lineGetter + "- Error : appointment is null");


        if(appointment.getStatus() != AppointmentStateEnum.CREATED) throw new Exception("Cannot edit appointment");
        logger.error("if statement from public AppointmentStateService 'setAccept' inside :"+
                AppointmentService.class +" at line#" +
                lineGetter + "- Error : Cannot edit appointment");

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(appointment.getPatient().getId() != user.getId()) throw new Exception("This is not your appointment");
        logger.error("if statement from public AppointmentStateService 'setAccept' inside :" +
                AppointmentService.class +" at line#" +
                lineGetter + "- Error : This is not your appointment");
        // Additional actions (!)

        //Doctor selection
        User doctor = doctorService.pickDoctor();
        appointment.setDoctor(doctor);

        //go ahead one step
        appointment.setStatus(AppointmentStateEnum.ACCEPTED);
        appointment.setUpdatedAt(LocalDateTime.now());
        appointment.setUpdatedBy(user);
        return appointmentRepository.save(appointment);
    }

    public Appointment setInProgress(Appointment appointment) throws Exception{
        logger.info("Public method 'setInProgress' method called at "+ AppointmentService.class +" at line#" + lineGetter);

        if(appointment == null) throw new NullPointerException();
        logger.error("if statement from public AppointmentStateService 'setInProgress' inside :"+
                AppointmentStateService.class +" at line#" +
                lineGetter + "- Error : appointment is null");

        if(appointment.getStatus() != AppointmentStateEnum.ACCEPTED) throw new Exception("Cannot edit appointment");
        logger.error("if statement from public AppointmentStateService 'setInProgress' inside :"+
                AppointmentStateService.class +" at line#" +
                lineGetter + "- Error : Cannot edit appointment");

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(appointment.getDoctor().getId() != user.getId()) throw new Exception("This is not your appointment");
        logger.error("if statement from public AppointmentStateService 'setInProgress' inside :"+
                AppointmentStateService.class +" at line#" +
                lineGetter + "- Error : This is not your appointment");

        //go ahead one step
        appointment.setStatus(AppointmentStateEnum.IN_PROGRESS);
        appointment.setUpdatedAt(LocalDateTime.now());
        appointment.setUpdatedBy(user);
        return appointmentRepository.save(appointment);
    }

    public Appointment setComplete(Appointment appointment) throws Exception{
        logger.info("Public method 'setInProgress' method called at "+ AppointmentService.class +" at line#" + lineGetter);
        if(appointment == null) throw new NullPointerException();
        logger.error("if statement from public AppointmentStateService 'setComplete' inside :"+
                AppointmentStateService.class +" at line#" +
                lineGetter + "- Error : appointment is null");

        if(appointment.getStatus() != AppointmentStateEnum.IN_PROGRESS) throw new Exception("Cannot edit appointment");
        logger.error("if statement from public AppointmentStateService 'setComplete' inside :"+
                AppointmentStateService.class +" at line#" +
                lineGetter + "- Error : Cannot edit appointment");


        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(appointment.getDoctor().getId() != user.getId()) throw new Exception("This is not your appointment");
        logger.error("if statement from public AppointmentStateService 'setComplete' inside :"+
                AppointmentStateService.class +" at line#" +
                lineGetter + "- Error : This is not your appointment");

        //go ahead one step
        appointment.setStatus(AppointmentStateEnum.COMPLETED);
        appointment.setUpdatedAt(LocalDateTime.now());
        appointment.setUpdatedBy(user);
        return appointmentRepository.save(appointment);
    }
}
