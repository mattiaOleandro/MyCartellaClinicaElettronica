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

    @Query(nativeQuery = true, value = "SELECT cd.`day` FROM `calendar_day` AS cd")
    List<Date> findAllDate();

    @Query(nativeQuery = true,value = "SELECT cd.day FROM `calendar_day` AS cd\n" +
                                      "WHERE cd.id = :id")
    List<Date> findOneDate(@Param(value = "id") Long id);

    //cerca il "timeSlot"(fascia oraria) per data
    @Query(nativeQuery = true,value = "SELECT cd.`day` FROM calendar_day AS cd\n" +
                                      "WHERE cd.`day` = :day")
    List<Date> findTimeSlotFromDate(@Param(value = "day") Date day);

    //aggiorna
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "UPDATE appointment AS a\n" +
            "SET calendar_day_id = (SELECT cd.id FROM calendar_day AS cd\n" +
            "WHERE cd.`day` = :day)\n" +
            "WHERE a.appointment_date = :day AND (SELECT HOUR(appointment_start)) = :hour")
    void updateCalendarDayIdByDate(@Param(value = "day") Date day,
                                   @Param(value = "hour") int hour);


    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "UPDATE calendar_day AS cd\n" +
            "SET time_slot8is_available = :time_slot8is_available\n" +
            "WHERE cd.day = :day")
    void updateTimeSlot8FromDate(@Param(value = "day") Date day,
                                 @Param(value = "time_slot8is_available") Boolean aBoolean);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "UPDATE calendar_day AS cd\n" +
            "SET time_slot7is_available = :time_slot7is_available\n" +
            "WHERE cd.day = :day")
    void updateTimeSlot7FromDate(@Param(value = "day") Date day,
                                 @Param(value = "time_slot7is_available") Boolean aBoolean);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "UPDATE calendar_day AS cd\n" +
            "SET time_slot6is_available = :time_slot6is_available\n" +
            "WHERE cd.day = :day")
    void updateTimeSlot6FromDate(@Param(value = "day") Date day,
                                 @Param(value = "time_slot6is_available") Boolean aBoolean);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "UPDATE calendar_day AS cd\n" +
            "SET time_slot5is_available = :time_slot5is_available\n" +
            "WHERE cd.day = :day")
    void updateTimeSlot5FromDate(@Param(value = "day") Date day,
                                 @Param(value = "time_slot5is_available") Boolean aBoolean);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "UPDATE calendar_day AS cd\n" +
            "SET time_slot4is_available = :time_slot4is_available\n" +
            "WHERE cd.day = :day")
    void updateTimeSlot4FromDate(@Param(value = "day") Date day,
                                 @Param(value = "time_slot4is_available") Boolean aBoolean);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "UPDATE calendar_day AS cd\n" +
            "SET time_slot3is_available = :time_slot3is_available\n" +
            "WHERE cd.day = :day")
    void updateTimeSlot3FromDate(@Param(value = "day") Date day,
                                 @Param(value = "time_slot3is_available") Boolean aBoolean);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "UPDATE calendar_day AS cd\n" +
            "SET time_slot2is_available = :time_slot2is_available\n" +
            "WHERE cd.day = :day")
    void updateTimeSlot2FromDate(@Param(value = "day") Date day,
                                 @Param(value = "time_slot2is_available") Boolean aBoolean);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "UPDATE calendar_day AS cd\n" +
            "SET time_slot1is_available = :time_slot1is_available\n" +
            "WHERE cd.day = :day")
    void updateTimeSlot1FromDate(@Param(value = "day") Date day,
                                 @Param(value = "time_slot1is_available") Boolean aBoolean);

}

