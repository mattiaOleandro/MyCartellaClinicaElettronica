package it.angelo.MyCartellaClinicaElettronica.appointment.repositories;

import it.angelo.MyCartellaClinicaElettronica.appointment.entities.CalendarDay;
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

    @Query(nativeQuery = true, value = "SELECT cd.* FROM calendar_day AS cd WHERE cd.id = :id")
    CalendarDay findAllById(Long id);

    @Query(nativeQuery = true, value = "SELECT cd.`doctor_id` FROM `calendar_doctor` AS cd")
    List<Long> findAllDoctorIdInCalendarDoctor();


    @Query(nativeQuery = true, value = "SELECT cd.`day` FROM `calendar_day` AS cd\n" +
            "WHERE cd.`day` = :day")
    List<Date> findAllDateByDate(LocalDate day);

    @Query(nativeQuery = true, value = "SELECT cd.`day` FROM `calendar_day` AS cd")
    List<Date> findAllDate();

    @Query(nativeQuery = true, value = "SELECT cd.`id` FROM `calendar_day` AS cd\n" +
            "WHERE cd.day = :day")
    Long findIdByDate(LocalDate day);

    @Query(nativeQuery = true,value = "SELECT cd.day FROM `calendar_day` AS cd\n" +
                                      "WHERE cd.id = :id")
    List<Date> findOneDate(@Param(value = "id") Long id);


    @Query(nativeQuery = true, value = "SELECT cd.calendar_id FROM calendar_doctor AS cd\n" +
            "WHERE cd.doctor_id = :id")
    List<Long> findCalendarIdFromCalendarDoctorByDoctorId(@Param(value = "id") Long id);

    @Query(nativeQuery = true, value = "SELECT cd.id FROM calendar_day AS cd\n" +
            "WHERE cd.day = :day")
    List<Long> findCalendarIdFromCalendarDayByDate(@Param(value = "day") Date day);

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
            "WHERE cd.id = :id")
    void updateTimeSlot8FromId(@Param(value = "time_slot8is_available") Boolean aBoolean,
                               @Param(value = "id") Long id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "UPDATE calendar_day AS cd\n" +
            "SET time_slot7is_available = :time_slot7is_available\n" +
            "WHERE cd.id = :id")
    void updateTimeSlot7FromId(@Param(value = "time_slot7is_available") Boolean aBoolean,
                               @Param(value = "id") Long id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "UPDATE calendar_day AS cd\n" +
            "SET time_slot6is_available = :time_slot6is_available\n" +
            "WHERE cd.id = :id")
    void updateTimeSlot6FromId(@Param(value = "time_slot6is_available") Boolean aBoolean,
                               @Param(value = "id") Long id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "UPDATE calendar_day AS cd\n" +
            "SET time_slot5is_available = :time_slot5is_available\n" +
            "WHERE cd.id = :id")
    void updateTimeSlot5FromId(@Param(value = "time_slot5is_available") Boolean aBoolean,
                               @Param(value = "id") Long id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "UPDATE calendar_day AS cd\n" +
            "SET time_slot4is_available = :time_slot4is_available\n" +
            "WHERE cd.id = :id")
    void updateTimeSlot4FromId(@Param(value = "time_slot4is_available") Boolean aBoolean,
                                 @Param(value = "id") Long id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "UPDATE calendar_day AS cd\n" +
            "SET time_slot3is_available = :time_slot3is_available\n" +
            "WHERE cd.id = :id")
    void updateTimeSlot3FromId(@Param(value = "time_slot3is_available") Boolean aBoolean,
                               @Param(value = "id") Long id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "UPDATE calendar_day AS cd\n" +
            "SET time_slot2is_available = :time_slot2is_available\n" +
            "WHERE cd.id = :id")
    void updateTimeSlot2FromId(@Param(value = "time_slot2is_available") Boolean aBoolean,
                               @Param(value = "id") Long id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "UPDATE calendar_day AS cd\n" +
            "SET time_slot1is_available = :time_slot1is_available\n" +
            "WHERE cd.id = :id")
    void updateTimeSlot1FromId(@Param(value = "time_slot1is_available") Boolean aBoolean,
                               @Param(value = "id") Long id);


    @Query(nativeQuery = true,value = "SELECT cd.`time_slot8is_available` FROM calendar_day AS cd \n" +
            "WHERE cd.`id` = :id")
    boolean findTimeSlot8FromId(@Param(value = "id") Long id);

    @Query(nativeQuery = true,value = "SELECT cd.`time_slot7is_available` FROM calendar_day AS cd \n" +
            "WHERE cd.`id` = :id")
    boolean findTimeSlot7FromId(@Param(value = "id") Long id);

    @Query(nativeQuery = true,value = "SELECT cd.`time_slot6is_available` FROM calendar_day AS cd \n" +
            "WHERE cd.`id` = :id")
    boolean findTimeSlot6FromId(@Param(value = "id") Long id);

    @Query(nativeQuery = true,value = "SELECT cd.`time_slot5is_available` FROM calendar_day AS cd \n" +
            "WHERE cd.`id` = :id")
    boolean findTimeSlot5FromId(@Param(value = "id") Long id);

    @Query(nativeQuery = true,value = "SELECT cd.`time_slot4is_available` FROM calendar_day AS cd \n" +
            "WHERE cd.`id` = :id")
    boolean findTimeSlot4FromId(@Param(value = "id") Long id);

    @Query(nativeQuery = true,value = "SELECT cd.`time_slot3is_available` FROM calendar_day AS cd \n" +
            "WHERE cd.`id` = :id")
    boolean findTimeSlot3FromId(@Param(value = "id") Long id);

    @Query(nativeQuery = true,value = "SELECT cd.`time_slot2is_available` FROM calendar_day AS cd \n" +
            "WHERE cd.`id` = :id")
    boolean findTimeSlot2FromId(@Param(value = "id") Long id);

    @Query(nativeQuery = true,value = "SELECT cd.`time_slot1is_available` FROM calendar_day AS cd \n" +
            "WHERE cd.`id` = :id")
    boolean findTimeSlot1FromId(@Param(value = "id") Long id);
}

