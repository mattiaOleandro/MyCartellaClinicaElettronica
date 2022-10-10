package it.angelo.MyCartellaClinicaElettronica.appointment.services;

import it.angelo.MyCartellaClinicaElettronica.appointment.entities.AvailabilityDTO;
import it.angelo.MyCartellaClinicaElettronica.appointment.repositories.CalendarDayRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Data
public class AvailabilityService {

    @Autowired
    private CalendarDayRepository calendarDayRepository;

    private LocalDate endOfAvailability;

    private List<LocalDate> listOfAvailability;

    private List<LocalDate> listDateUnavailabilityOfCalendarDayByDoctor;

    private List<LocalDate> checkCalendarDayDateUnavailabilityByDoctor(Long doctorId) {

        List<LocalDate> listDatePossibleUnavailabilityOfCalendarDay = Arrays.asList(LocalDate.from((TemporalAccessor) calendarDayRepository.findAllDate()));
        for (int i = 0; i < listDatePossibleUnavailabilityOfCalendarDay.size(); i++) {
            LocalDate day = listDatePossibleUnavailabilityOfCalendarDay.get(i);
            List<LocalDate> listDateUnavailabilityOfCalendarDay = new ArrayList<>();
            if (!(calendarDayRepository.findTimeSlot1FromId(calendarDayRepository.findIdByDate(day))) &&
                    !(calendarDayRepository.findTimeSlot2FromId(calendarDayRepository.findIdByDate(day))) &&
                    !(calendarDayRepository.findTimeSlot3FromId(calendarDayRepository.findIdByDate(day))) &&
                    !(calendarDayRepository.findTimeSlot4FromId(calendarDayRepository.findIdByDate(day))) &&
                    !(calendarDayRepository.findTimeSlot5FromId(calendarDayRepository.findIdByDate(day))) &&
                    !(calendarDayRepository.findTimeSlot6FromId(calendarDayRepository.findIdByDate(day))) &&
                    !(calendarDayRepository.findTimeSlot7FromId(calendarDayRepository.findIdByDate(day))) &&
                    !(calendarDayRepository.findTimeSlot8FromId(calendarDayRepository.findIdByDate(day)))) {
                listDateUnavailabilityOfCalendarDay.add(day);
            }
            for (int j = 0; j < listDateUnavailabilityOfCalendarDay.size(); j++) {
                LocalDate dayByDoctor = listDatePossibleUnavailabilityOfCalendarDay.get(j);

                List<Long> calendarIdByCalendarDoctor = calendarDayRepository.findCalendarIdFromCalendarDoctorByDoctorId(doctorId);
                for (int k = 0; k < calendarIdByCalendarDoctor.size(); k++) {
                    Long calendarId = calendarIdByCalendarDoctor.get(k);
                    if (calendarDayRepository.findIdByDate(dayByDoctor).equals(calendarId)){
                        listDateUnavailabilityOfCalendarDayByDoctor.add(dayByDoctor);
                    }
                }
            }

        }
        return listDateUnavailabilityOfCalendarDayByDoctor;
    }

    public List<LocalDate> generateAvailabilityByDoctor(AvailabilityDTO availabilityDTO){

        endOfAvailability = availabilityDTO.getEndDate();
        List<LocalDate> listOfPossibleAvailability = (List<LocalDate>) LocalDate.now().datesUntil(endOfAvailability);
        List<LocalDate> listOfUnavailability = checkCalendarDayDateUnavailabilityByDoctor(availabilityDTO.getDoctorId());

        for (int i = 0; i < listOfPossibleAvailability.size(); i++) {
            LocalDate date = listOfPossibleAvailability.get(i);
            //unavailableDay
            if((!date.getDayOfWeek().name().equalsIgnoreCase("Sunday")) && !(date.getDayOfWeek().name().equalsIgnoreCase("Saturday"))){
                listOfAvailability.add(date);
            }

            for (int j = 0; j < listOfUnavailability.size(); j++) {
                LocalDate dateUnavailability = listOfUnavailability.get(j);

                if (listOfAvailability.contains(dateUnavailability)){
                    listOfAvailability.remove(dateUnavailability);
                }
            }
        }
        return listOfAvailability;
    }
}

