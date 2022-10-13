package it.angelo.MyCartellaClinicaElettronica.appointment.services;

import it.angelo.MyCartellaClinicaElettronica.appointment.entities.AvailabilityDTO;
import it.angelo.MyCartellaClinicaElettronica.appointment.entities.CalendarDay;
import it.angelo.MyCartellaClinicaElettronica.appointment.entities.TimeSlot;
import it.angelo.MyCartellaClinicaElettronica.appointment.exceptions.BodyErrorException;
import it.angelo.MyCartellaClinicaElettronica.appointment.exceptions.MethodErrorException;
import it.angelo.MyCartellaClinicaElettronica.appointment.exceptions.TimeSlotUnavailabilityException;
import it.angelo.MyCartellaClinicaElettronica.appointment.repositories.CalendarDayRepository;
import it.angelo.MyCartellaClinicaElettronica.user.repositories.UserRepository;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Data
public class AvailabilityService {

    @Autowired
    private CalendarDayRepository calendarDayRepository;

    @Autowired
    private UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(AvailabilityService.class);
    int lineGetter = new Exception().getStackTrace()[0].getLineNumber();

    public List<LocalDate> generateAvailabilityByDoctor(AvailabilityDTO availabilityDTO) throws Exception, BodyErrorException {

        logger.debug(String.format("(generateAvailabilityByDoctor) method called at %s at line# %d",
                AvailabilityService.class , lineGetter));

        List<LocalDate> listOfAvailability = new ArrayList<>();

        List<LocalDate> listOfPossibleAvailability = LocalDate.now().datesUntil(availabilityDTO.getEndDate()).collect(Collectors.toList());

        boolean isDoctor = userRepository.getSingleUserIdRoleId(availabilityDTO.getDoctorId()) == 4;
        logger.debug("The user " + availabilityDTO.getDoctorId() + " is doctor? " + isDoctor);

        if (userRepository.findUserById(availabilityDTO.getDoctorId()) == null || !isDoctor){
            logger.debug("Stopped controller!");
            throw new BodyErrorException("There is a problem with the selected doctor: " + availabilityDTO.getDoctorId());
        }

        List<Date> listDateUnavailabilityOfCalendarDay = calendarDayRepository.findAllDateByTimeSlotNotFree();
        for (Date day : listDateUnavailabilityOfCalendarDay) {
            List<Long> calendarIdByCalendarDoctor = calendarDayRepository.findCalendarIdFromCalendarDoctorByDoctorId(availabilityDTO.getDoctorId());
            List<Long> calendarIdByCalendarDay = calendarDayRepository.findCalendarIdFromCalendarDayByDate(day);
            for (Long id1 : calendarIdByCalendarDoctor) {
                for (Long id2 : calendarIdByCalendarDay) {
                    if (id1.equals(id2)) {
                        LocalDate date = new java.sql.Date(day.getTime()).toLocalDate();
                        listOfPossibleAvailability.remove(date);
                    }
                }
            }
        }

        for (LocalDate date : listOfPossibleAvailability) {
            if (!(date.getDayOfWeek().name().equalsIgnoreCase("Sunday")) && !(date.getDayOfWeek().name().equalsIgnoreCase("Saturday"))) {
                listOfAvailability.add(date);
            }
        }
        return listOfAvailability;
    }

    public List<TimeSlot> checkAvailabilityTimeSlotByDayAndDoctor(AvailabilityDTO availabilityDTO) throws BodyErrorException, TimeSlotUnavailabilityException, MethodErrorException {

        List<Date> listOfDay = calendarDayRepository.findAllDateByDate(LocalDate.from(availabilityDTO.getDateCheckTimeSlot()));
        logger.debug("LIST OF DAY: " + listOfDay);
        logger.info("DAY: " + availabilityDTO.getDateCheckTimeSlot());

        List<Long> listOfDoctorIdInCalendarDoctor = new ArrayList(new HashSet(calendarDayRepository.findAllDoctorIdInCalendarDoctor()));
        logger.debug("LIST OF DOCTOR ID: " + listOfDoctorIdInCalendarDoctor);

        List<Long> listOfUserIdRoleDoctor = userRepository.getAllUserIdByRoleId();
        logger.debug("LIST OF USER ID ROLE DOCTOR: " + listOfUserIdRoleDoctor);

        List<TimeSlot> listTimeSlotAvailabilityStandard = Arrays.asList(
                TimeSlot.TIME_SLOT_08_00_09_00,
                TimeSlot.TIME_SLOT_09_00_10_00,
                TimeSlot.TIME_SLOT_10_00_11_00,
                TimeSlot.TIME_SLOT_11_00_12_00,
                TimeSlot.TIME_SLOT_15_00_16_00,
                TimeSlot.TIME_SLOT_16_00_17_00,
                TimeSlot.TIME_SLOT_17_00_18_00,
                TimeSlot.TIME_SLOT_18_00_19_00
        );

        boolean isDoctor = userRepository.getSingleUserIdRoleId(availabilityDTO.getDoctorId()) == 4;
        logger.debug("The user " + availabilityDTO.getDoctorId() + " is doctor? " + isDoctor);

        if (userRepository.findUserById(availabilityDTO.getDoctorId()) == null || !isDoctor){
            logger.debug("Stopped controller!");
            throw new BodyErrorException("There is a problem with the selected doctor: " + availabilityDTO.getDoctorId());
        }

        if(listOfDay.isEmpty()){
            return listTimeSlotAvailabilityStandard;
        }else{
            for (Date date : listOfDay) {
                for (Long userDoctorId : listOfUserIdRoleDoctor) {
                    logger.debug("DOCTOR ID: " + userDoctorId);
                    for (Object calendarDoctorId : listOfDoctorIdInCalendarDoctor) {//evitare i cicli estraendo solo da calendar_doctor tramite l'id passato in input
                        logger.debug("CALENDAR DOCTOR ID: " + calendarDoctorId);
                        logger.debug("Doctor id: " + userDoctorId + " and Calendar Doctor id: " + calendarDoctorId + " is equals? " + calendarDoctorId.equals(userDoctorId));
                        if (availabilityDTO.getDoctorId().equals(calendarDoctorId) && calendarDoctorId.equals(userDoctorId)) {
                            List<Long> calendarIdByCalendarDoctor = calendarDayRepository.findCalendarIdFromCalendarDoctorByDoctorId(availabilityDTO.getDoctorId());
                            logger.debug("LIST OF CALENDAR ID BY CALENDAR DOCTOR: " + calendarIdByCalendarDoctor);
                            List<Long> calendarIdByCalendarDay = calendarDayRepository.findCalendarIdFromCalendarDayByDate(date);
                            logger.debug("LIST OF CALENDAR ID BY CALENDAR DAY: " + calendarIdByCalendarDay);
                            for (Long id1 : calendarIdByCalendarDoctor) {
                                logger.debug("calendar_id by calendar_doctor = " + id1);
                                for (Long id2 : calendarIdByCalendarDay) {
                                    logger.debug("calendar_id by calendar_day = " + id2);
                                    logger.debug("calendar_id1: " + id1 + " and calendar_id2: " + id2 + " is equals? " + id1.equals(id2));
                                    if (id1.equals(id2)) {

                                        CalendarDay calendarDay = calendarDayRepository.findAllById(id1);
                                        logger.debug("New CalendarDay object instantiated");

                                        List<TimeSlot> listTimeSlotAvailability = new ArrayList<>();

                                        if (calendarDay.isTimeSlot1IsAvailable()) {
                                            listTimeSlotAvailability.add(TimeSlot.TIME_SLOT_08_00_09_00);
                                            logger.debug(TimeSlot.TIME_SLOT_08_00_09_00 + " add to availability list time-slot");
                                        }
                                        if (calendarDay.isTimeSlot2IsAvailable()) {
                                            listTimeSlotAvailability.add(TimeSlot.TIME_SLOT_09_00_10_00);
                                            logger.debug(TimeSlot.TIME_SLOT_09_00_10_00 + " add to availability list time-slot");
                                        }
                                        if (calendarDay.isTimeSlot3IsAvailable()) {
                                            listTimeSlotAvailability.add(TimeSlot.TIME_SLOT_10_00_11_00);
                                            logger.debug(TimeSlot.TIME_SLOT_10_00_11_00 + " add to availability list time-slot");
                                        }
                                        if (calendarDay.isTimeSlot4IsAvailable()) {
                                            listTimeSlotAvailability.add(TimeSlot.TIME_SLOT_11_00_12_00);
                                            logger.debug(TimeSlot.TIME_SLOT_11_00_12_00 + " add to availability list time-slot");
                                        }
                                        if (calendarDay.isTimeSlot5IsAvailable()) {
                                            listTimeSlotAvailability.add(TimeSlot.TIME_SLOT_15_00_16_00);
                                            logger.debug(TimeSlot.TIME_SLOT_15_00_16_00 + " add to availability list time-slot");
                                        }
                                        if (calendarDay.isTimeSlot6IsAvailable()) {
                                            listTimeSlotAvailability.add(TimeSlot.TIME_SLOT_16_00_17_00);
                                            logger.debug(TimeSlot.TIME_SLOT_16_00_17_00 + " add to availability list time-slot");
                                        }
                                        if (calendarDay.isTimeSlot7IsAvailable()) {
                                            listTimeSlotAvailability.add(TimeSlot.TIME_SLOT_17_00_18_00);
                                            logger.debug(TimeSlot.TIME_SLOT_17_00_18_00 + " add to availability list time-slot");
                                        }
                                        if (calendarDay.isTimeSlot8IsAvailable()) {
                                            listTimeSlotAvailability.add(TimeSlot.TIME_SLOT_18_00_19_00);
                                            logger.debug(TimeSlot.TIME_SLOT_18_00_19_00 + " add to availability list time-slot");
                                        }

                                        if (listTimeSlotAvailability.isEmpty()) {
                                            logger.debug("The listTimeSlotAvailability is empty");
                                            throw new TimeSlotUnavailabilityException("The day has all the timeslots unavailable");
                                        } else {
                                            return listTimeSlotAvailability;
                                        }
                                    }
                                }
                            }
                        } else {
                            logger.debug("Doctor id and Calendar Doctor id is not equals");
                        }
                    }
                }
            }
        }
        logger.debug("ERROR/BUG");
        throw new MethodErrorException("An error has occurred in the system");
    }

    public List<String> setUnavailabilityByDayAndDoctor(AvailabilityDTO availabilityDTO) throws BodyErrorException, MethodErrorException {

        List<String> s = new ArrayList<>();

        logger.debug(String.format("(setUnavailabilityByDayAndDoctor) method called at %s at line# %d",
                AvailabilityService.class , lineGetter));

        boolean isDoctor = userRepository.getSingleUserIdRoleId(availabilityDTO.getDoctorId()) == 4;
        logger.debug("The user " + availabilityDTO.getDoctorId() + " is doctor? " + isDoctor);

        if (userRepository.findUserById(availabilityDTO.getDoctorId()) == null || !isDoctor){
            logger.debug("Stopped controller!");
            throw new BodyErrorException("There is a problem with the selected doctor: " + availabilityDTO.getDoctorId());
        }

        List<Long> listOfDoctorIdInCalendarDoctor = new ArrayList(new HashSet(calendarDayRepository.findAllDoctorIdInCalendarDoctor()));
        logger.debug("LIST OF DOCTOR ID: " + listOfDoctorIdInCalendarDoctor);

        List<Long> listOfUserIdRoleDoctor = userRepository.getAllUserIdByRoleId();
        logger.debug("LIST OF USER ID ROLE DOCTOR: " + listOfUserIdRoleDoctor);

        for (int i = 0; i < availabilityDTO.getDateUnavailability().size(); i++) {
            LocalDate dateUnavailability = availabilityDTO.getDateUnavailability().get(i);

            List<Date> listOfDay = calendarDayRepository.findAllDateByDate(dateUnavailability);
            logger.debug("LIST OF DAY: " + listOfDay);
            logger.info("DAY: " + dateUnavailability);

            if (listOfDay.isEmpty()) {
                CalendarDay calendarDay = new CalendarDay();

                calendarDay.setDay(dateUnavailability);

                calendarDay.setTimeSlot1IsAvailable(false);
                calendarDay.setTimeSlot2IsAvailable(false);
                calendarDay.setTimeSlot3IsAvailable(false);
                calendarDay.setTimeSlot4IsAvailable(false);
                calendarDay.setTimeSlot5IsAvailable(false);
                calendarDay.setTimeSlot6IsAvailable(false);
                calendarDay.setTimeSlot7IsAvailable(false);
                calendarDay.setTimeSlot8IsAvailable(false);

                calendarDayRepository.save(calendarDay);

                calendarDayRepository.updateCalendarDoctor(calendarDay.getId(), availabilityDTO.getDoctorId());

                logger.info("CALENDAR DAY UNAVAILABILITY " +
                        "DATE: " + calendarDay.getDay());
                s.add("Dates: " + availabilityDTO.getDateUnavailability().get(i) + " set unavailability");
            }else {
                for (int y = 0; y < listOfDay.size(); y++) {
                    Date date = listOfDay.get(y);
                    logger.info("DATES ARE EQUALS: " + date);
                    for (int j = 0; j < listOfUserIdRoleDoctor.size(); j++) {
                        Long userDoctorId = listOfUserIdRoleDoctor.get(j);
                        logger.debug("DOCTOR ID: " + userDoctorId);
                        for (Long calendarDoctorId : listOfDoctorIdInCalendarDoctor) {
                            logger.debug("CALENDAR DOCTOR ID: " + calendarDoctorId);
                            logger.debug("Doctor id: " + userDoctorId + " and Calendar Doctor id: " + calendarDoctorId + " is equals? " + calendarDoctorId.equals(userDoctorId));
                            if (availabilityDTO.getDoctorId().equals(calendarDoctorId) && calendarDoctorId.equals(userDoctorId)) {
                                List<Long> calendarIdByCalendarDoctor = calendarDayRepository.findCalendarIdFromCalendarDoctorByDoctorId(availabilityDTO.getDoctorId());
                                logger.debug("LIST OF CALENDAR ID BY CALENDAR DOCTOR: " + calendarIdByCalendarDoctor);
                                List<Long> calendarIdByCalendarDay = calendarDayRepository.findCalendarIdFromCalendarDayByDate(date);
                                logger.debug("LIST OF CALENDAR ID BY CALENDAR DAY: " + calendarIdByCalendarDay);
                                for (int l = 0; l < calendarIdByCalendarDoctor.size(); l++) {
                                    Long id1 = calendarIdByCalendarDoctor.get(l);
                                    logger.debug("calendar_id by calendar_doctor = " + id1);
                                    for (Long id2 : calendarIdByCalendarDay) {
                                        logger.debug("calendar_id by calendar_day = " + id2);
                                        logger.debug("calendar_id1: " + id1 + " and calendar_id2: " + id2 + " is equals? " + id1.equals(id2));
                                        if (id1.equals(id2)) {
                                            logger.debug("On the selected date: " + availabilityDTO.getDateUnavailability().get(i) + " there are already booked appointments");
                                            s.add("On the selected date: " + availabilityDTO.getDateUnavailability().get(i) + " there are already booked appointments");
                                            j = listOfUserIdRoleDoctor.size();
                                            l = calendarIdByCalendarDoctor.size();
                                        } else {
                                            logger.debug("Doctor id and Calendar Doctor id is not equals");
                                            if (listOfDay.size() -1 == y && calendarIdByCalendarDoctor.size() - 1 == l) {
                                                j = listOfUserIdRoleDoctor.size();
                                                CalendarDay calendarDay = new CalendarDay();

                                                calendarDay.setDay(dateUnavailability);

                                                calendarDay.setTimeSlot1IsAvailable(false);
                                                calendarDay.setTimeSlot2IsAvailable(false);
                                                calendarDay.setTimeSlot3IsAvailable(false);
                                                calendarDay.setTimeSlot4IsAvailable(false);
                                                calendarDay.setTimeSlot5IsAvailable(false);
                                                calendarDay.setTimeSlot6IsAvailable(false);
                                                calendarDay.setTimeSlot7IsAvailable(false);
                                                calendarDay.setTimeSlot8IsAvailable(false);

                                                calendarDayRepository.save(calendarDay);

                                                calendarDayRepository.updateCalendarDoctor(calendarDay.getId(), availabilityDTO.getDoctorId());

                                                logger.info("CALENDAR DAY UNAVAILABILITY " +
                                                        "DATE: " + calendarDay.getDay());
                                                s.add("Dates: " + availabilityDTO.getDateUnavailability().get(i) + " set unavailability");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        HashSet converter = new HashSet(s);
        return converter.stream().toList();
    }

    public String setUnavailabilityTimeSlotByDayAndDoctor(AvailabilityDTO availabilityDTO) throws BodyErrorException, MethodErrorException {

        logger.debug(String.format("(setUnavailabilityByDayAndDoctor) method called at %s at line# %d",
                AvailabilityService.class , lineGetter));

        boolean isDoctor = userRepository.getSingleUserIdRoleId(availabilityDTO.getDoctorId()) == 4;
        logger.debug("The user " + availabilityDTO.getDoctorId() + " is doctor? " + isDoctor);

        if (userRepository.findUserById(availabilityDTO.getDoctorId()) == null || !isDoctor){
            logger.debug("Stopped controller!");
            throw new BodyErrorException("There is a problem with the selected doctor: " + availabilityDTO.getDoctorId());
        }

        List<Date> listOfDay = calendarDayRepository.findAllDateByDate(availabilityDTO.getDateUnavailabilityTimeSlot());
        logger.debug("LIST OF DAY: " + listOfDay);
        logger.info("DAY: " + availabilityDTO.getDateUnavailabilityTimeSlot());

        List<Long> listOfDoctorIdInCalendarDoctor = new ArrayList(new HashSet(calendarDayRepository.findAllDoctorIdInCalendarDoctor()));
        logger.debug("LIST OF DOCTOR ID: " + listOfDoctorIdInCalendarDoctor);

        List<Long> listOfUserIdRoleDoctor = userRepository.getAllUserIdByRoleId();
        logger.debug("LIST OF USER ID ROLE DOCTOR: " + listOfUserIdRoleDoctor);

        if (listOfDay.isEmpty()){

            CalendarDay calendarDay = new CalendarDay();

            calendarDay.setDay(availabilityDTO.getDateUnavailabilityTimeSlot());

            for (int i = 0; i < availabilityDTO.getListTimeSlots().size(); i++) {
                switch (availabilityDTO.getListTimeSlots().get(i)) {
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
            }

            calendarDayRepository.save(calendarDay);

            calendarDayRepository.updateCalendarDoctor(calendarDay.getId(), availabilityDTO.getDoctorId());

            logger.info("CALENDAR DAY UNAVAILABILITY " +
                    "DATE: " + calendarDay.getDay());

            return "Dates: " + availabilityDTO.getDateUnavailabilityTimeSlot() + " set " + availabilityDTO.getListTimeSlots() + " unavailability";
        }else{
            for (Date date : listOfDay) {
                logger.info("DATES ARE EQUALS: " + date);
                for (Long userDoctorId : listOfUserIdRoleDoctor) {
                    logger.debug("DOCTOR ID: " + userDoctorId);
                    for (Long calendarDoctorId : listOfDoctorIdInCalendarDoctor) {
                        logger.debug("CALENDAR DOCTOR ID: " + calendarDoctorId);
                        logger.debug("Doctor id: " + userDoctorId + " and Calendar Doctor id: " + calendarDoctorId + " is equals? " + calendarDoctorId.equals(userDoctorId));
                        if (availabilityDTO.getDoctorId().equals(calendarDoctorId) && calendarDoctorId.equals(userDoctorId)) {
                            List<Long> calendarIdByCalendarDoctor = calendarDayRepository.findCalendarIdFromCalendarDoctorByDoctorId(availabilityDTO.getDoctorId());
                            logger.debug("LIST OF CALENDAR ID BY CALENDAR DOCTOR: " + calendarIdByCalendarDoctor);
                            List<Long> calendarIdByCalendarDay = calendarDayRepository.findCalendarIdFromCalendarDayByDate(date);
                            logger.debug("LIST OF CALENDAR ID BY CALENDAR DAY: " + calendarIdByCalendarDay);
                            for (Long id1 : calendarIdByCalendarDoctor) {
                                logger.debug("calendar_id by calendar_doctor = " + id1);
                                for (Long id2 : calendarIdByCalendarDay) {
                                    logger.debug("calendar_id by calendar_day = " + id2);
                                    logger.debug("calendar_id1: " + id1 + " and calendar_id2: " + id2 + " is equals? " + id1.equals(id2));
                                    if (id1.equals(id2)) {
                                        for (int i = 0; i < availabilityDTO.getListTimeSlots().size(); i++) {
                                            switch (availabilityDTO.getListTimeSlots().get(i)) {
                                                case TIME_SLOT_08_00_09_00 -> {
                                                    calendarDayRepository.updateTimeSlot1FromId(Boolean.FALSE, id1);
                                                    logger.debug("Set time slot 1 occupied in calendarDay");
                                                }
                                                case TIME_SLOT_09_00_10_00 -> {
                                                    calendarDayRepository.updateTimeSlot2FromId(Boolean.FALSE, id1);
                                                    logger.debug("Set time slot 2 occupied in calendarDay");
                                                }
                                                case TIME_SLOT_10_00_11_00 -> {
                                                    calendarDayRepository.updateTimeSlot3FromId(Boolean.FALSE, id1);
                                                    logger.debug("Set time slot 3 occupied in calendarDay");
                                                }
                                                case TIME_SLOT_11_00_12_00 -> {
                                                    calendarDayRepository.updateTimeSlot4FromId(Boolean.FALSE, id1);
                                                    logger.debug("Set time slot 4 occupied in calendarDay");
                                                }
                                                case TIME_SLOT_15_00_16_00 -> {
                                                    calendarDayRepository.updateTimeSlot5FromId(Boolean.FALSE, id1);
                                                    logger.debug("Set time slot 5 occupied in calendarDay");
                                                }
                                                case TIME_SLOT_16_00_17_00 -> {
                                                    calendarDayRepository.updateTimeSlot6FromId(Boolean.FALSE, id1);
                                                    logger.debug("Set time slot 6 occupied in calendarDay");
                                                }
                                                case TIME_SLOT_17_00_18_00 -> {
                                                    calendarDayRepository.updateTimeSlot7FromId(Boolean.FALSE, id1);
                                                    logger.debug("Set time slot 7 occupied in calendarDay");
                                                }
                                                case TIME_SLOT_18_00_19_00 -> {
                                                    calendarDayRepository.updateTimeSlot8FromId(Boolean.FALSE, id1);
                                                    logger.debug("Set time slot 8 occupied in calendarDay");
                                                }
                                            }
                                            return "Dates: " + availabilityDTO.getDateUnavailabilityTimeSlot() + " set " + availabilityDTO.getListTimeSlots() + " unavailability";
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        logger.debug("ERROR/BUG");
        throw new MethodErrorException("An error has occurred in the system");
    }
}

