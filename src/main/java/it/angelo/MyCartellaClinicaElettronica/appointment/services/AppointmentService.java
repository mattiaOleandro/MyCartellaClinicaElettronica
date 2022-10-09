package it.angelo.MyCartellaClinicaElettronica.appointment.services;

import it.angelo.MyCartellaClinicaElettronica.appointment.entities.Appointment;
import it.angelo.MyCartellaClinicaElettronica.appointment.entities.AppointmentDTO;
import it.angelo.MyCartellaClinicaElettronica.appointment.entities.CalendarDay;
import it.angelo.MyCartellaClinicaElettronica.appointment.entities.TimeSlot;
import it.angelo.MyCartellaClinicaElettronica.appointment.repositories.AppointmentRepository;
import it.angelo.MyCartellaClinicaElettronica.appointment.repositories.CalendarDayRepository;
import it.angelo.MyCartellaClinicaElettronica.user.entities.User;
import it.angelo.MyCartellaClinicaElettronica.user.repositories.UserRepository;
import it.angelo.MyCartellaClinicaElettronica.user.utils.Roles;
import lombok.Data;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@ToString
@Service
@Data
public class AppointmentService {

    Logger logger = LoggerFactory.getLogger(AppointmentService.class);
    int lineGetter = new Exception().getStackTrace()[0].getLineNumber();

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CalendarDayRepository calendarDayRepository;

    /**
     * Questo metodo serve per creare un nuovo appuntamento e popolare la tabella appointment con i dati essenziali presi
     * in input da il parametro che gli passiamo.
     * @param appointmentInput consiste nel body della richiesta
     * @return appointment
     */
    private Appointment newAppointment(AppointmentDTO appointmentInput) throws Exception {
        Appointment appointment = new Appointment();
        logger.debug("New Appointment object instantiated");
        appointment.setCreatedAt(LocalDateTime.now());
        logger.debug("Set time: " + LocalDateTime.now() + " of creation of the appointment");
        appointment.setAddress(appointmentInput.getAddress());
        logger.debug("Set address: " + appointmentInput.getAddress() + "   of the appointment");
        appointment.setCity(appointmentInput.getCity());
        logger.debug("Set city: " + appointmentInput.getCity() + " of the appointment");
        appointment.setDescription(appointmentInput.getDescription());
        logger.debug("Set description: " + appointmentInput.getDescription() + " of the appointment");
        appointment.setState(appointmentInput.getState());
        logger.debug("Set state: " + appointmentInput.getState() + " of the appointment");
        appointment.setNumber(appointmentInput.getNumber());
        logger.debug("Set number: " + appointmentInput.getNumber() + " of the appointment");
        appointment.setZipCode(appointmentInput.getZipCode());
        logger.debug("Set zip-code: " + appointmentInput.getZipCode() + " of the appointment");
        appointment.setTimeSlot(appointmentInput.getTimeSlot());
        logger.debug("Set time-slot: " + appointmentInput.getTimeSlot() + " of the appointment");
        appointment.setAppointmentStart(appointmentInput.getAppointmentStart());
        logger.debug("Appointment start date and time: " + appointmentInput.getAppointmentStart() + " set");
        appointment.setAppointmentEnd(appointmentInput.getAppointmentEnd());
        logger.debug("Appointment end date and time: " + appointmentInput.getAppointmentEnd() + " set");
        appointment.setAppointmentDate(LocalDate.from(appointmentInput.getAppointmentStart()));
        logger.debug("Set date: " + LocalDate.from(appointmentInput.getAppointmentStart()) + " of the appointment");

        //check for patient
        if (appointmentInput.getPatient() == null) throw new Exception("Patient not found");
        Optional<User> patient = userRepository.findById(appointmentInput.getPatient());
        if (patient.isEmpty() || !Roles.hasRole(patient.get(), Roles.PATIENT))
            throw new Exception("Patient not found");
        appointment.setPatient(patient.get());
        logger.debug("Set patient: " + patient.get() + " of the appointment");

        logger.info("APPOINTMENT SAVED " +
                "DATE: " + LocalDate.from(appointmentInput.getAppointmentStart()) +
                ' ' + appointmentInput.getTimeSlot());
        return appointment;
    }

    /**
     * Questo metodo serve per creare un nuovo calendarDay e popolare la tabella calendarDay con i dati essenziali presi
     * in input da il parametro che gli passiamo.
     * @param appointmentInput consiste nel body della richiesta
     * @return calendarDay
     */
    private CalendarDay newCalendarDay(AppointmentDTO appointmentInput){
        CalendarDay calendarDay = new CalendarDay();
        logger.debug("New CalendarDay object instantiated");
        calendarDay.setAppointment(calendarDay.getAppointment());
        logger.debug("Set appointment: " + calendarDay.getAppointment() + " of the calendarDay");//da fix-are
        switch (appointmentInput.getTimeSlot()) {
            case TIME_SLOT_08_00_09_00 -> {
                calendarDay.setTimeSlot1IsAvailable(false);
                logger.debug("Set time slot 1 occupied in calendarDay");
            }
            case TIME_SLOT_09_00_10_00 -> {
                calendarDay.setTimeSlot2IsAvailable(false);
                logger.debug("Set time slot 2 occupied in calendarDay");
            }
            case TIME_SLOT_10_00_11_00 -> {
                calendarDay.setTimeSlot3IsAvailable(false);
                logger.debug("Set time slot 3 occupied in calendarDay");
            }
            case TIME_SLOT_11_00_12_00 -> {
                calendarDay.setTimeSlot4IsAvailable(false);
                logger.debug("Set time slot 4 occupied in calendarDay");
            }
            case TIME_SLOT_15_00_16_00 -> {
                calendarDay.setTimeSlot5IsAvailable(false);
                logger.debug("Set time slot 5 occupied in calendarDay");
            }
            case TIME_SLOT_16_00_17_00 -> {
                calendarDay.setTimeSlot6IsAvailable(false);
                logger.debug("Set time slot 6 occupied in calendarDay");
            }
            case TIME_SLOT_17_00_18_00 -> {
                calendarDay.setTimeSlot7IsAvailable(false);
                logger.debug("Set time slot 7 occupied in calendarDay");
            }
            case TIME_SLOT_18_00_19_00 -> {
                calendarDay.setTimeSlot8IsAvailable(false);
                logger.debug("Set time slot 8 occupied in calendarDay");
            }
        }
        logger.info("CALENDAR DAY SAVED " +
                "DATE: " + LocalDate.from(appointmentInput.getAppointmentStart()) +
                ' ' + appointmentInput.getTimeSlot());
        return calendarDay;
    }

    /**
     * save an appointment
     *
     * @param appointmentInput is an appointmentDTO object
     * @return an appointment
     * @throws Exception a generic exception can be thrown
     */
    public Appointment save(AppointmentDTO appointmentInput) throws Exception {

        logger.debug(String.format("(save) method called at %s at line# %d by %s",
                AppointmentService.class , lineGetter, appointmentInput.getNumber()));

        // rappresenta un utente autenticato, la gestione è demandata a JwtTokenFilter class
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        LocalDate DateAppointment = LocalDate.from(appointmentInput.getAppointmentStart());

        if(DateAppointment.isBefore(LocalDate.now())){
            logger.debug("Stopped controller!");
            throw new Exception("The selected date: " + DateAppointment + "could not be entered");
        }

        boolean isDoctor = userRepository.getSingleUserIdRoleId(appointmentInput.getDoctor()) == 4;
        logger.debug("The user " + appointmentInput.getDoctor() + " is doctor? " + isDoctor);

        if (userRepository.findUserById(appointmentInput.getDoctor()) == null && !isDoctor){
            logger.debug("Stopped controller!");
            throw new Exception("There is a problem with the selected doctor: " + appointmentInput.getDoctor());
        }

        List<Date> listOfDay = calendarDayRepository.findAllDateByDate(LocalDate.from(appointmentInput.getAppointmentStart()));
        logger.debug("LIST OF DAY: " + listOfDay);
        logger.info("DAY: " + LocalDate.from(appointmentInput.getAppointmentStart()).toString());

        List<Long> listOfDoctorIdInCalendarDoctor = new ArrayList(new HashSet(calendarDayRepository.findAllDoctorIdInCalendarDoctor()));
        logger.debug("LIST OF DOCTOR ID: " + listOfDoctorIdInCalendarDoctor);

        List<Long> listOfUserIdRoleDoctor = userRepository.getAllUserIdByRoleId();
        logger.debug("LIST OF USER ID ROLE DOCTOR: " + listOfUserIdRoleDoctor);

        if(listOfDay.isEmpty()){
            logger.info("DATES " + LocalDate.from(appointmentInput.getAppointmentStart()) + " NOT EXSIST!");
            User doctor = userRepository.findUserById(appointmentInput.getDoctor());
            logger.debug("Select doctor");
            logger.info("CREATE NEW DATE APPOINTMENT IN DATE: " + LocalDate.from(appointmentInput.getAppointmentStart()));
            Appointment appointment = newAppointment(appointmentInput);
            appointment.setDoctor(doctor);
            logger.debug("Set doctor: " + doctor + " of the appointment");
            appointment.setCreatedBy(user);
            logger.debug("Set the user: " + user + " who created the appointment");
            logger.info("CREATE NEW CALENDAR DAY: " + LocalDate.from(appointmentInput.getAppointmentStart()));
            CalendarDay calendarDay = newCalendarDay(appointmentInput);
            calendarDay.setDay(appointment.getAppointmentDate());
            logger.debug("Set day: " + appointment.getAppointmentDate() + " of the calendarDay");
            appointment.setCalendarDay(calendarDay);
            logger.debug("Set calendarDay of the appointment");
            calendarDayRepository.save(calendarDay);
            logger.info("CALENADR DAY SAVED! ");
            appointmentRepository.save(appointment);
            logger.info("APPOINTMENT SAVED! ");
            calendarDayRepository.updateCalendarDoctor(calendarDay.getId(), doctor.getId());
            logger.info("CALENDAR DOCTOR SAVED! ");
            return appointment;
        }else{
            for (int i = 0; i < listOfDay.size(); i++) {
                Date date = listOfDay.get(i);
                logger.info("DATES ARE EQUALS: " + date);
                for (int y = 0; y < listOfUserIdRoleDoctor.size(); y++) {
                    Long userDoctorId = listOfUserIdRoleDoctor.get(y);
                    logger.debug("DOCTOR ID: " + userDoctorId);
                    for (int j = 0; j < listOfDoctorIdInCalendarDoctor.size(); j++) {
                        Long calendarDoctorId = listOfDoctorIdInCalendarDoctor.get(j);
                        logger.debug("CALENDAR DOCTOR ID: " + calendarDoctorId);
                        logger.debug("Doctor id: " + userDoctorId + " and Calendar Doctor id: " + calendarDoctorId + " is equals? " + calendarDoctorId.equals(userDoctorId));
                        if(appointmentInput.getDoctor().equals(calendarDoctorId) && calendarDoctorId.equals(userDoctorId)){
                            List<Long> calendarIdByCalendarDoctor = calendarDayRepository.findCalendarIdFromCalendarDoctorByDoctorId(appointmentInput.getDoctor());
                            logger.debug("LIST OF CALENDAR ID BY CALENDAR DOCTOR: " + calendarIdByCalendarDoctor);
                            List<Long> calendarIdByCalendarDay = calendarDayRepository.findCalendarIdFromCalendarDayByDate(date);
                            logger.debug("LIST OF CALENDAR ID BY CALENDAR DAY: " + calendarIdByCalendarDay);
                            for (Long id1 : calendarIdByCalendarDoctor) {
                                logger.debug("calendar_id by calendar_doctor = " + id1);
                                for (Long id2 : calendarIdByCalendarDay) {
                                    logger.debug("calendar_id by calendar_day = " + id2);
                                    logger.debug("calendar_id1: " + id1 + " and calendar_id2: " + id2 + " is equals? " + id1.equals(id2));
                                    if (id1.equals(id2)) {
                                        Long calendarId = id1;
                                        logger.info("CALENDAR_ID = " + calendarId);
                                        logger.info("CHECK SLOT");
                                        if (appointmentInput.getTimeSlot().equals(TimeSlot.TIME_SLOT_08_00_09_00) && !calendarDayRepository.findTimeSlot1FromId(calendarId)) {
                                            logger.info("SOLT 1 IS EMPTY");
                                            return null;
                                        } else if (appointmentInput.getTimeSlot().equals(TimeSlot.TIME_SLOT_09_00_10_00) && !calendarDayRepository.findTimeSlot2FromId(calendarId)) {
                                            logger.info("SOLT 2 IS EMPTY");
                                            return null;
                                        } else if (appointmentInput.getTimeSlot().equals(TimeSlot.TIME_SLOT_10_00_11_00) && !calendarDayRepository.findTimeSlot3FromId(calendarId)) {
                                            logger.info("SOLT 3 IS EMPTY");
                                            return null;
                                        } else if (appointmentInput.getTimeSlot().equals(TimeSlot.TIME_SLOT_11_00_12_00) && !calendarDayRepository.findTimeSlot4FromId(calendarId)) {
                                            logger.info("SOLT 4 IS EMPTY");
                                            return null;
                                        } else if (appointmentInput.getTimeSlot().equals(TimeSlot.TIME_SLOT_15_00_16_00) && !calendarDayRepository.findTimeSlot5FromId(calendarId)) {
                                            logger.info("SOLT 5 IS EMPTY");
                                            return null;
                                        } else if (appointmentInput.getTimeSlot().equals(TimeSlot.TIME_SLOT_16_00_17_00) && !calendarDayRepository.findTimeSlot6FromId(calendarId)) {
                                            logger.info("SOLT 6 IS EMPTY");
                                            return null;
                                        } else if (appointmentInput.getTimeSlot().equals(TimeSlot.TIME_SLOT_17_00_18_00) && !calendarDayRepository.findTimeSlot7FromId(calendarId)) {
                                            logger.info("SOLT 7 IS EMPTY");
                                            return null;
                                        } else if (appointmentInput.getTimeSlot().equals(TimeSlot.TIME_SLOT_18_00_19_00) && !calendarDayRepository.findTimeSlot8FromId(calendarId)) {
                                            logger.info("SOLT 8 IS EMPTY");
                                            return null;
                                        } else {
                                            logger.info("CHECK SLOT PASSED");
                                            logger.debug("DATES ARE EQUALS ENTER INTO SWITCH!!!");
                                            switch (appointmentInput.getTimeSlot()) {
                                                case TIME_SLOT_08_00_09_00 -> {
                                                    logger.info("SLOT 1 - 08:00/09:00 FROM DATE " + date + " IS AVIABLE");
                                                    calendarDayRepository.updateTimeSlot1FromId(Boolean.FALSE, calendarId);
                                                    logger.debug("Set time slot 1 occupied in calendarDay");
                                                }
                                                case TIME_SLOT_09_00_10_00 -> {
                                                    logger.info("SLOT 2 - 09:00/10:00 FROM DATE " + date + " IS AVIABLE");
                                                    calendarDayRepository.updateTimeSlot2FromId(Boolean.FALSE, calendarId);
                                                    logger.debug("Set time slot 2 occupied in calendarDay");
                                                }
                                                case TIME_SLOT_10_00_11_00 -> {
                                                    logger.info("SLOT 3 - 10:00/11:00 FROM DATE " + date + " IS AVIABLE");
                                                    calendarDayRepository.updateTimeSlot3FromId(Boolean.FALSE, calendarId);
                                                    logger.debug("Set time slot 3 occupied in calendarDay");
                                                }
                                                case TIME_SLOT_11_00_12_00 -> {
                                                    logger.info("SLOT 4 - 11:00/12:00 FROM DATE " + date + " IS AVIABLE");
                                                    calendarDayRepository.updateTimeSlot4FromId(Boolean.FALSE, calendarId);
                                                    logger.debug("Set time slot 4 occupied in calendarDay");
                                                }
                                                case TIME_SLOT_15_00_16_00 -> {
                                                    logger.info("SLOT 5 - 15:00/16:00 FROM DATE " + date + " IS AVIABLE");
                                                    calendarDayRepository.updateTimeSlot5FromId(Boolean.FALSE, calendarId);
                                                    logger.debug("Set time slot 5 occupied in calendarDay");
                                                }
                                                case TIME_SLOT_16_00_17_00 -> {
                                                    logger.info("SLOT 6 - 16:00/17:00 FROM DATE " + date + " IS AVIABLE");
                                                    calendarDayRepository.updateTimeSlot6FromId(Boolean.FALSE, calendarId);
                                                    logger.debug("Set time slot 6 occupied in calendarDay");
                                                }
                                                case TIME_SLOT_17_00_18_00 -> {
                                                    logger.info("SLOT 7 - 17:00/18:00 FROM DATE " + date + " IS AVIABLE");
                                                    calendarDayRepository.updateTimeSlot7FromId(Boolean.FALSE, calendarId);
                                                    logger.debug("Set time slot 7 occupied in calendarDay");
                                                }
                                                case TIME_SLOT_18_00_19_00 -> {
                                                    logger.info("SLOT 8 - 18:00/19:00 FROM DATE " + date + " IS AVIABLE");
                                                    calendarDayRepository.updateTimeSlot8FromId(Boolean.FALSE, calendarId);
                                                    logger.debug("Set time slot 8 occupied in calendarDay");
                                                }
                                            }
                                            logger.info("CREATE APPOINTMENT IN DATE: " + LocalDate.from(appointmentInput.getAppointmentStart()));
                                            Appointment appointment = newAppointment(appointmentInput);
                                            appointment.setCreatedBy(user);
                                            logger.debug("Set the user: " + user + " who created the appointment");
                                            appointment.setDoctor(userRepository.findUserById(appointmentInput.getDoctor()));
                                            logger.debug("Set doctor: " + userRepository.findUserById(appointmentInput.getDoctor()) + " of the appointment");
                                            appointment.setCalendarDay(calendarDayRepository.findAllById(calendarId));
                                            logger.debug("Set calendarDay of the appointment");
                                            appointmentRepository.save(appointment);
                                            logger.info("APPOINTMENT SAVED!");
                                            return appointment;
                                        }
                                    }
                                }
                            }
                        }else{
                            logger.debug("Doctor id and Calendar Doctor id is not equals");
                            if (listOfDay.size()-1 == i && listOfDoctorIdInCalendarDoctor.size()-1 == j && listOfUserIdRoleDoctor.size()-1 == y) {
                                logger.info("On the selected date the doctor has no appointments yet");
                                User doctor = userRepository.findUserById(appointmentInput.getDoctor());
                                logger.debug("Select doctor");
                                logger.info("CREATE NEW DATE APPOINTMENT IN DATE: " + LocalDate.from(appointmentInput.getAppointmentStart()));
                                Appointment appointment = newAppointment(appointmentInput);
                                appointment.setDoctor(doctor);
                                logger.debug("Set doctor: " + doctor + " of the appointment");
                                appointment.setCreatedBy(user);
                                logger.debug("Set the user: " + user + " who created the appointment");
                                logger.info("CREATE NEW CALENDAR DAY: " + LocalDate.from(appointmentInput.getAppointmentStart()));
                                CalendarDay calendarDay = newCalendarDay(appointmentInput);
                                calendarDay.setDay(appointment.getAppointmentDate());
                                logger.debug("Set day: " + appointment.getAppointmentDate() + " of the calendarDay");
                                appointment.setCalendarDay(calendarDay);
                                logger.debug("Set calendarDay of the appointment");
                                calendarDayRepository.save(calendarDay);
                                logger.info("CALENADR DAY SAVED! ");
                                appointmentRepository.save(appointment);
                                logger.info("APPOINTMENT SAVED! ");
                                calendarDayRepository.updateCalendarDoctor(calendarDay.getId(), doctor.getId());
                                logger.info("CALENDAR DOCTOR SAVED! ");
                                return appointment;
                            }
                        }
                    }
                }
            }
        }
        logger.debug("ERROR/BUG");
        return null;
    }


    public Appointment update(Long id, Appointment appointmentInput) {
        logger.debug(String.format("(update) method called at %s at line# %d by %s",
                AppointmentService.class , lineGetter, appointmentInput.getNumber()));
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (appointmentInput == null) return null;
        appointmentInput.setId(id);
        appointmentInput.setUpdatedAt(LocalDateTime.now());
        appointmentInput.setUpdatedBy(user);
        return appointmentRepository.save(appointmentInput);
    }

    public boolean canEdit(Long id) {
        logger.debug(String.format(" (canEdit) method called at %s at line# %d by ID %s",
                AppointmentService.class , lineGetter, id));
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        if (!appointment.isPresent()) return false;
        return appointment.get().getCreatedBy().getId() == user.getId();
    }
}

