package it.angelo.MyCartellaClinicaElettronica.appointment.services;

import it.angelo.MyCartellaClinicaElettronica.appointment.entities.AvailabilityDTO;
import it.angelo.MyCartellaClinicaElettronica.appointment.repositories.CalendarDayRepository;
import lombok.Data;
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

    private LocalDate endOfAvailability;

    private List<LocalDate> listOfAvailability = new ArrayList<>();

    private List<LocalDate> listDateUnavailabilityOfCalendarDayByDoctor = new ArrayList<>();

    private List<LocalDate> checkCalendarDayDateUnavailabilityByDoctor(Long doctorId) {

        List<Date> listDatePossibleUnavailabilityOfCalendarDay = calendarDayRepository.findAllDate();
        for (int i = 0; i < listDatePossibleUnavailabilityOfCalendarDay.size(); i++) {
            Date day = listDatePossibleUnavailabilityOfCalendarDay.get(i);
            List<Date> listDateUnavailabilityOfCalendarDay = new ArrayList<>();
            List<Long> calendarIdByCalendarDoctor = calendarDayRepository.findCalendarIdFromCalendarDoctorByDoctorId(doctorId);
            //logger.debug("LIST OF CALENDAR ID BY CALENDAR DOCTOR: " + calendarIdByCalendarDoctor);
            List<Long> calendarIdByCalendarDay = calendarDayRepository.findCalendarIdFromCalendarDayByDate(day);
            //logger.debug("LIST OF CALENDAR ID BY CALENDAR DAY: " + calendarIdByCalendarDay);
            for (Long id1 : calendarIdByCalendarDoctor) {
                //logger.debug("calendar_id by calendar_doctor = " + id1);
                for (Long id2 : calendarIdByCalendarDay) {
                    //logger.debug("calendar_id by calendar_day = " + id2);
                    //logger.debug("calendar_id1: " + id1 + " and calendar_id2: " + id2 + " is equals? " + id1.equals(id2));
                    if (id1.equals(id2)) {
                        //logger.info("CALENDAR_ID = " + calendarId);
                        //Sistemare l'estrazione dell'id di calendarDay tramite anche il dottore, attualmente esistono piu date dello stesso tipo
                        if (!(calendarDayRepository.findTimeSlot1FromId(id1)) &&
                                !(calendarDayRepository.findTimeSlot2FromId(id1)) &&
                                !(calendarDayRepository.findTimeSlot3FromId(id1)) &&
                                !(calendarDayRepository.findTimeSlot4FromId(id1)) &&
                                !(calendarDayRepository.findTimeSlot5FromId(id1)) &&
                                !(calendarDayRepository.findTimeSlot6FromId(id1)) &&
                                !(calendarDayRepository.findTimeSlot7FromId(id1)) &&
                                !(calendarDayRepository.findTimeSlot8FromId(id1))) {
                            listDateUnavailabilityOfCalendarDay.add(day);
                        }
                        for (int j = 0; j < listDateUnavailabilityOfCalendarDay.size(); j++) {
                            Date dayByDoctor = listDatePossibleUnavailabilityOfCalendarDay.get(j);

                            List<Long> calendarIdByCalendarDoctorId = calendarDayRepository.findCalendarIdFromCalendarDoctorByDoctorId(doctorId);
                            for (int k = 0; k < calendarIdByCalendarDoctorId.size(); k++) {
                                Long calendarId = calendarIdByCalendarDoctorId.get(k);
                                if (calendarDayRepository.findCalendarIdFromCalendarDayByDate(dayByDoctor).equals(calendarId)){
                                    ZoneId defaultZoneId = ZoneId.systemDefault();
                                    Instant instant = dayByDoctor.toInstant();
                                    LocalDate dayByDoctorLocalDate = instant.atZone(defaultZoneId).toLocalDate();
                                    listDateUnavailabilityOfCalendarDayByDoctor.add(dayByDoctorLocalDate);
                                }
                            }
                        }
                    }
                }
            }
        }
        return listDateUnavailabilityOfCalendarDayByDoctor;
    }

    public List<LocalDate> generateAvailabilityByDoctor(AvailabilityDTO availabilityDTO){

        endOfAvailability = availabilityDTO.getEndDate();
        List<LocalDate> listOfPossibleAvailability = LocalDate.now().datesUntil(endOfAvailability).collect(Collectors.toList());
        List<LocalDate> listOfUnavailability = checkCalendarDayDateUnavailabilityByDoctor(availabilityDTO.getDoctorId());

        for (int i = 0; i < listOfPossibleAvailability.size(); i++) {
            LocalDate date = listOfPossibleAvailability.get(i);
            //unavailableDay
            if(!(date.getDayOfWeek().name().equalsIgnoreCase("Sunday")) && !(date.getDayOfWeek().name().equalsIgnoreCase("Saturday"))){
                listOfAvailability.add(date);

            }
        }

        for (int j = 0; j < listOfUnavailability.size(); j++) {
            LocalDate dateUnavailability = listOfUnavailability.get(j);

            if (listOfAvailability.contains(dateUnavailability)){
                listOfAvailability.remove(dateUnavailability);
            }
        }
        return listOfAvailability;
    }
}

