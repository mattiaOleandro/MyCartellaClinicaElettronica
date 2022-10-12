package it.angelo.MyCartellaClinicaElettronica.appointment.services;

import it.angelo.MyCartellaClinicaElettronica.appointment.entities.AvailabilityDTO;
import it.angelo.MyCartellaClinicaElettronica.appointment.entities.CalendarDay;
import it.angelo.MyCartellaClinicaElettronica.appointment.exceptions.BodyErrorException;
import it.angelo.MyCartellaClinicaElettronica.appointment.repositories.CalendarDayRepository;
import it.angelo.MyCartellaClinicaElettronica.user.repositories.UserRepository;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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
        for (int j = 0; j < listDateUnavailabilityOfCalendarDay.size(); j++) {
            Date day = listDateUnavailabilityOfCalendarDay.get(j);
            List<Long> calendarIdByCalendarDoctor = calendarDayRepository.findCalendarIdFromCalendarDoctorByDoctorId(availabilityDTO.getDoctorId());
            List<Long> calendarIdByCalendarDay = calendarDayRepository.findCalendarIdFromCalendarDayByDate(day);
            for (Long id1 : calendarIdByCalendarDoctor) {
                for (Long id2 : calendarIdByCalendarDay) {
                    if (id1.equals(id2)) {
                        LocalDate date = new java.sql.Date(day.getTime()).toLocalDate();
                        if (listOfPossibleAvailability.contains(date)){
                            listOfPossibleAvailability.remove(date);
                        }
                    }
                }
            }
        }

        for (int i = 0; i < listOfPossibleAvailability.size(); i++) {
            LocalDate date = listOfPossibleAvailability.get(i);
            if(!(date.getDayOfWeek().name().equalsIgnoreCase("Sunday")) && !(date.getDayOfWeek().name().equalsIgnoreCase("Saturday"))){
                listOfAvailability.add(date);
            }
        }
        return listOfAvailability;
    }

    public String setUnavailabilityByDayAndDoctor(AvailabilityDTO availabilityDTO) throws BodyErrorException {

        logger.debug(String.format("(setUnavailabilityByDayAndDoctor) method called at %s at line# %d",
                AvailabilityService.class , lineGetter));

        boolean isDoctor = userRepository.getSingleUserIdRoleId(availabilityDTO.getDoctorId()) == 4;
        logger.debug("The user " + availabilityDTO.getDoctorId() + " is doctor? " + isDoctor);

        if (userRepository.findUserById(availabilityDTO.getDoctorId()) == null || !isDoctor){
            logger.debug("Stopped controller!");
            throw new BodyErrorException("There is a problem with the selected doctor: " + availabilityDTO.getDoctorId());
        }

        for (int i = 0; i < availabilityDTO.getDateUnavailability().size(); i++) {
            LocalDate dateUnavailability = availabilityDTO.getDateUnavailability().get(i);

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

        }
        return "Dates: " + availabilityDTO.getDateUnavailability() + " set unavailability";
    }
}

