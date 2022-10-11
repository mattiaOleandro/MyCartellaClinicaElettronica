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

    public List<LocalDate> generateAvailabilityByDoctor(AvailabilityDTO availabilityDTO){

        List<LocalDate> listOfAvailability = new ArrayList<>();

        endOfAvailability = availabilityDTO.getEndDate();

        List<LocalDate> listOfPossibleAvailability = LocalDate.now().datesUntil(endOfAvailability).collect(Collectors.toList());

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

//    public List<LocalDate> generateAvailabilityByDoctor(AvailabilityDTO availabilityDTO){
//
//        List<LocalDate> listOfAvailability = new ArrayList<>();
//
//        endOfAvailability = availabilityDTO.getEndDate();
//        List<LocalDate> listOfPossibleAvailability = LocalDate.now().datesUntil(endOfAvailability).collect(Collectors.toList());
//
//        for (int i = 0; i < listOfPossibleAvailability.size(); i++) {
//            LocalDate date = listOfPossibleAvailability.get(i);
//            //unavailableDay
//            if(!(date.getDayOfWeek().name().equalsIgnoreCase("Sunday")) && !(date.getDayOfWeek().name().equalsIgnoreCase("Saturday"))){
//                listOfAvailability.add(date);
//
//            }
//        }
//
//        List<Date> listDateUnavailabilityOfCalendarDay = calendarDayRepository.findAllDateByTimeSlotNotFree();
//        List<Long> calendarIdByCalendarDoctor = calendarDayRepository.findCalendarIdFromCalendarDoctorByDoctorId(availabilityDTO.getDoctorId());
//        for (int i = 0; i < listDateUnavailabilityOfCalendarDay.size(); i++) {
//            Date day = listDateUnavailabilityOfCalendarDay.get(i);
//            List<Long> calendarIdByCalendarDay = calendarDayRepository.findCalendarIdFromCalendarDayByDate(day);
//            for (Long id1 : calendarIdByCalendarDoctor) {
//                for (Long id2 : calendarIdByCalendarDay) {
//                    if (id1.equals(id2)) {
//                        if (listOfAvailability.contains(day)){
//                            listOfAvailability.remove(day);
//                        }
//                    }
//                }
//            }
//        }
//        return listOfAvailability;
//    }



//    private List<LocalDate> checkCalendarDayDateUnavailabilityByDoctor(Long doctorId) {
//
//        List<LocalDate> listDateUnavailabilityOfCalendarDayByDoctor = new ArrayList<>();
//
//        List<Date> listDatePossibleUnavailabilityOfCalendarDay = calendarDayRepository.findAllDate();
//        for (int i = 0; i < listDatePossibleUnavailabilityOfCalendarDay.size(); i++) {
//            Date day = listDatePossibleUnavailabilityOfCalendarDay.get(i);
//            List<Date> listDateUnavailabilityOfCalendarDay = new ArrayList<>();
//            List<Long> calendarIdByCalendarDoctor = calendarDayRepository.findCalendarIdFromCalendarDoctorByDoctorId(doctorId);
//            List<Long> calendarIdByCalendarDay = calendarDayRepository.findCalendarIdFromCalendarDayByDate(day);
//            for (Long id1 : calendarIdByCalendarDoctor) {
//                for (Long id2 : calendarIdByCalendarDay) {
//                    if (id1.equals(id2)) {
//                        if ((calendarDayRepository.findTimeSlot1FromId(id1)) &&
//                                (calendarDayRepository.findTimeSlot2FromId(id1)) &&
//                                (calendarDayRepository.findTimeSlot3FromId(id1)) &&
//                                (calendarDayRepository.findTimeSlot4FromId(id1)) &&
//                                (calendarDayRepository.findTimeSlot5FromId(id1)) &&
//                                (calendarDayRepository.findTimeSlot6FromId(id1)) &&
//                                (calendarDayRepository.findTimeSlot7FromId(id1)) &&
//                                (calendarDayRepository.findTimeSlot8FromId(id1))) {
//                            listDateUnavailabilityOfCalendarDay.add(day);
//                            ZoneId defaultZoneId = ZoneId.systemDefault();
//                            Instant instant = day.toInstant();
//                            LocalDate dayByDoctorLocalDate = instant.atZone(defaultZoneId).toLocalDate();
//                            listDateUnavailabilityOfCalendarDayByDoctor.add(dayByDoctorLocalDate);
//                        }
////                        for (int j = 0; j < listDateUnavailabilityOfCalendarDay.size(); j++) {
////                            Date dayByDoctor = listDatePossibleUnavailabilityOfCalendarDay.get(j);
////
////                            List<Long> calendarIdByCalendarDoctorId = calendarDayRepository.findCalendarIdFromCalendarDoctorByDoctorId(doctorId);
////                            for (int k = 0; k < calendarIdByCalendarDoctorId.size(); k++) {
////                                Long calendarId = calendarIdByCalendarDoctorId.get(k);
////                                if (calendarDayRepository.findCalendarIdFromCalendarDayByDate(dayByDoctor).equals(calendarId)){
////                                    ZoneId defaultZoneId = ZoneId.systemDefault();
////                                    Instant instant = dayByDoctor.toInstant();
////                                    LocalDate dayByDoctorLocalDate = instant.atZone(defaultZoneId).toLocalDate();
////                                    listDateUnavailabilityOfCalendarDayByDoctor.add(dayByDoctorLocalDate);
////                                }
////                            }
////                        }
//                    }
//                }
//            }
//        }
//        return listDateUnavailabilityOfCalendarDayByDoctor;
//    }
//
//    //estraggo dal db in base all id del dottore tutte le date con gli slot gia occupati.
//    //l'estrazione partira' dalla data odierna, quindi findAllDateByDoctorIdAndTimeSlotNotFree
//    private List<LocalDate> dataUnavailabilityByDoctor(Long doctorId){
//        List<LocalDate> listDateUnavailabilityOfCalendarDayByDoctor = new ArrayList<>();
//
//        List<Date> listDateUnavailabilityOfCalendarDay = calendarDayRepository.findAllDateByTimeSlotNotFree();
//        List<Long> calendarIdByCalendarDoctor = calendarDayRepository.findCalendarIdFromCalendarDoctorByDoctorId(doctorId);
//        for (int i = 0; i < listDateUnavailabilityOfCalendarDay.size(); i++) {
//            Date day = listDateUnavailabilityOfCalendarDay.get(i);
//            List<Long> calendarIdByCalendarDay = calendarDayRepository.findCalendarIdFromCalendarDayByDate(day);
//            for (Long id1 : calendarIdByCalendarDoctor) {
//                for (Long id2 : calendarIdByCalendarDay) {
//                    if (id1.equals(id2)) {
//                        ZoneId defaultZoneId = ZoneId.systemDefault();
//                        Instant instant = day.toInstant();
//                        LocalDate dayByDoctorLocalDate = instant.atZone(defaultZoneId).toLocalDate();
//                        listDateUnavailabilityOfCalendarDayByDoctor.add(dayByDoctorLocalDate);
//                    }
//                }
//            }
//        }
//        return listDateUnavailabilityOfCalendarDayByDoctor;
//    }
//
//    public List<LocalDate> generateAvailabilityByDoctor(AvailabilityDTO availabilityDTO){
//
//        List<LocalDate> listOfAvailability = new ArrayList<>();
//
//        endOfAvailability = availabilityDTO.getEndDate();
//        List<LocalDate> listOfPossibleAvailability = LocalDate.now().datesUntil(endOfAvailability).collect(Collectors.toList());
//        List<LocalDate> listOfUnavailability = checkCalendarDayDateUnavailabilityByDoctor(availabilityDTO.getDoctorId());
//
//        for (int i = 0; i < listOfPossibleAvailability.size(); i++) {
//            LocalDate date = listOfPossibleAvailability.get(i);
//            //unavailableDay
//            if(!(date.getDayOfWeek().name().equalsIgnoreCase("Sunday")) && !(date.getDayOfWeek().name().equalsIgnoreCase("Saturday"))){
//                listOfAvailability.add(date);
//
//            }
//        }
//
//        for (int j = 0; j < listOfUnavailability.size(); j++) {
//            LocalDate dateUnavailability = listOfUnavailability.get(j);
//
//            if (listOfAvailability.contains(dateUnavailability)){
//                listOfAvailability.remove(dateUnavailability);
//            }
//        }
//        return listOfAvailability;
//    }


}

