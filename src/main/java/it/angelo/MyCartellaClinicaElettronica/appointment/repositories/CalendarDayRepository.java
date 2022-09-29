package it.angelo.MyCartellaClinicaElettronica.appointment.repositories;

import it.angelo.MyCartellaClinicaElettronica.appointment.entities.CalendarDay;
import it.angelo.MyCartellaClinicaElettronica.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface CalendarDayRepository extends JpaRepository<CalendarDay,Long> {

    CalendarDay findAllById(Long id);

    @Query(nativeQuery = true, value = "SELECT cd.`doctor_id` FROM `calendar_doctor` AS cd")
    List<Long> findAllDoctorId();


    @Query(nativeQuery = true, value = "SELECT cd.`day` FROM `calendar_day` AS cd")
    List<Date> findAllDate();

    @Query(nativeQuery = true,value = "SELECT cd.day FROM `calendar_day` AS cd\n" +
                                      "WHERE cd.id = :id")
    List<Date> findOneDate(@Param(value = "id") Long id);

    //cerca il "timeSlot"(fascia oraria) per data
    @Query(nativeQuery = true,value = "SELECT cd.`day` FROM calendar_day AS cd\n" +
                                      "WHERE cd.`day` = :day")
    List<Date> findTimeSlotFromDate(@Param(value = "day") Date day);

    /*
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "UPDATE calendar_day AS cd\n" +
            "SET doctor_id = :doctorId\n" +
            "WHERE cd.day = :day")
    void updateDoctorIdFromData(@Param(value = "day") Date day,
                                @Param(value = "id") Long id);
    */

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO calendar_doctor (`calendar_id`, `doctor_id`) VALUES (:calendarId, :doctorId);")
    void updateCalendarDoctor(@Param(value = "calendarId") Long calendarId,
                              @Param(value = "doctorId") Long doctorId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "UPDATE calendar_day AS cd\n" +
            "SET time_slot8is_available = :time_slot8is_available\n" +
            "WHERE cd.day = :day AND calendar_doctor.doctor_id = :doctorID")
    void updateTimeSlot8FromDate(@Param(value = "day") Date day,
                                 @Param(value = "time_slot8is_available") Boolean aBoolean,
                                 @Param(value = "doctorID") Long doctorId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "UPDATE calendar_day AS cd\n" +
            "SET time_slot7is_available = :time_slot7is_available\n" +
            "WHERE cd.day = :day AND calendar_doctor.doctor_id = :doctorID")
    void updateTimeSlot7FromDate(@Param(value = "day") Date day,
                                 @Param(value = "time_slot7is_available") Boolean aBoolean,
                                 @Param(value = "doctorID") Long doctorId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "UPDATE calendar_day AS cd\n" +
            "SET time_slot6is_available = :time_slot6is_available\n" +
            "WHERE cd.day = :day AND calendar_doctor.doctor_id = :doctorID")
    void updateTimeSlot6FromDate(@Param(value = "day") Date day,
                                 @Param(value = "time_slot6is_available") Boolean aBoolean,
                                 @Param(value = "doctorID") Long doctorId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "UPDATE calendar_day AS cd\n" +
            "SET time_slot5is_available = :time_slot5is_available\n" +
            "WHERE cd.day = :day AND calendar_doctor.doctor_id = :doctorID")
    void updateTimeSlot5FromDate(@Param(value = "day") Date day,
                                 @Param(value = "time_slot5is_available") Boolean aBoolean,
                                 @Param(value = "doctorID") Long doctorId);

    @Query(nativeQuery = true, value = "SELECT cd.calendar_id FROM calendar_doctor AS cd\n" +
            "WHERE cd.doctor_id = :id")
    Long findCalendarIdFromCalendarDoctorByDoctorId(@Param(value = "id") Long id);

    //UPDATE appointment AS a
    //SET calendar_day_id = (SELECT cd.id FROM calendar_day AS cd
    //WHERE cd.day = '2022-12-25')
    //WHERE a.appointment_date = '2022-12-25' AND (SELECT HOUR(appointment_start)) = 18;

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "UPDATE calendar_day AS cd\n" +
            "SET time_slot4is_available = :time_slot4is_available\n" +
            "WHERE cd.id = :id")
    void updateTimeSlot4FromDate(@Param(value = "time_slot4is_available") Boolean aBoolean,
                                 @Param(value = "id") Long id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "UPDATE calendar_day AS cd\n" +
            "SET time_slot3is_available = :time_slot3is_available\n" +
            "WHERE cd.day = :day AND calendar_doctor.doctor_id = :doctorID")
    void updateTimeSlot3FromDate(@Param(value = "day") Date day,
                                 @Param(value = "time_slot3is_available") Boolean aBoolean,
                                 @Param(value = "doctorID") Long doctorId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "UPDATE calendar_day AS cd\n" +
            "SET time_slot2is_available = :time_slot2is_available\n" +
            "WHERE cd.day = :day AND calendar_doctor.doctor_id = :doctorID")
    void updateTimeSlot2FromDate(@Param(value = "day") Date day,
                                 @Param(value = "time_slot2is_available") Boolean aBoolean,
                                 @Param(value = "doctorID") Long doctorId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "UPDATE calendar_day AS cd\n" +
            "SET time_slot1is_available = :time_slot1is_available\n" +
            "WHERE cd.day = :day AND calendar_doctor.doctor_id = :doctorID")
    void updateTimeSlot1FromDate(@Param(value = "day") Date day,
                                 @Param(value = "time_slot1is_available") Boolean aBoolean,
                                 @Param(value = "doctorID") Long doctorId);


    @Query(nativeQuery = true,value = "SELECT cd.`time_slot8is_available` FROM calendar_day AS cd \n" +
            "WHERE cd.`day` = :day")
    boolean findTimeSlot8FromDate(@Param(value = "day") Date day);

    @Query(nativeQuery = true,value = "SELECT cd.`time_slot7is_available` FROM calendar_day AS cd \n" +
            "WHERE cd.`day` = :day")
    boolean findTimeSlot7FromDate(@Param(value = "day") Date day);

    @Query(nativeQuery = true,value = "SELECT cd.`time_slot6is_available` FROM calendar_day AS cd \n" +
            "WHERE cd.`day` = :day")
    boolean findTimeSlot6FromDate(@Param(value = "day") Date day);

    @Query(nativeQuery = true,value = "SELECT cd.`time_slot5is_available` FROM calendar_day AS cd \n" +
            "WHERE cd.`day` = :day")
    boolean findTimeSlot5FromDate(@Param(value = "day") Date day);

    @Query(nativeQuery = true,value = "SELECT cd.`time_slot4is_available` FROM calendar_day AS cd \n" +
            "WHERE cd.`id` = :id")
    boolean findTimeSlot4FromDate(@Param(value = "id") Long id);

    @Query(nativeQuery = true,value = "SELECT cd.`time_slot3is_available` FROM calendar_day AS cd \n" +
            "WHERE cd.`day` = :day")
    boolean findTimeSlot3FromDate(@Param(value = "day") Date day);

    @Query(nativeQuery = true,value = "SELECT cd.`time_slot2is_available` FROM calendar_day AS cd \n" +
            "WHERE cd.`day` = :day")
    boolean findTimeSlot2FromDate(@Param(value = "day") Date day);

    @Query(nativeQuery = true,value = "SELECT cd.`time_slot1is_available` FROM calendar_day AS cd\n" +
            "WHERE cd.`day` = :day")
    boolean findTimeSlot1FromDate(@Param(value = "day") Date day);
}

