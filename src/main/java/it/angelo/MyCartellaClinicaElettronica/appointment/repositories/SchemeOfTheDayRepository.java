package it.angelo.MyCartellaClinicaElettronica.appointment.repositories;

import it.angelo.MyCartellaClinicaElettronica.appointment.entities.SchemeOfTheDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface SchemeOfTheDayRepository extends JpaRepository<SchemeOfTheDay,Long> {

    @Query(nativeQuery = true, value = "SELECT s.scheme_of_the_day FROM `scheme_of_the_day` AS s")
    List<Date> findAllDate();

    @Query(nativeQuery = true,value = "SELECT s.scheme_of_the_day FROM `scheme_of_the_day` AS s\n" +
                                      "WHERE s.id = :id;")
    List<Date> findOneDate(@Param(value = "id") Long id);

    @Query(nativeQuery = true,value = "SELECT sotd.scheme_of_the_day FROM scheme_of_the_day AS sotd\n" +
                                        "WHERE sotd.scheme_of_the_day = :scheme_of_the_day")
    List<Date> findTimeSlotFromDate(@Param(value = "scheme_of_the_day") Date scheme_of_the_day);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "UPDATE scheme_of_the_day AS sotd\n" +
            "SET time_slot8is_available = :time_slot8is_available\n" +
            "WHERE sotd.scheme_of_the_day = :scheme_of_the_day")
    void updateTimeSlot8FromDate(@Param(value = "scheme_of_the_day") Date scheme_of_the_day,
                                 @Param(value = "time_slot8is_available") Boolean aBoolean);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "UPDATE scheme_of_the_day AS sotd\n" +
            "SET time_slot7is_available = :time_slot7is_available\n" +
            "WHERE sotd.scheme_of_the_day = :scheme_of_the_day")
    void updateTimeSlot7FromDate(@Param(value = "scheme_of_the_day") Date scheme_of_the_day,
                                 @Param(value = "time_slot7is_available") Boolean aBoolean);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "UPDATE scheme_of_the_day AS sotd\n" +
            "SET time_slot6is_available = :time_slot6is_available\n" +
            "WHERE sotd.scheme_of_the_day = :scheme_of_the_day")
    void updateTimeSlot6FromDate(@Param(value = "scheme_of_the_day") Date scheme_of_the_day,
                                 @Param(value = "time_slot6is_available") Boolean aBoolean);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "UPDATE scheme_of_the_day AS sotd\n" +
            "SET time_slot5is_available = :time_slot5is_available\n" +
            "WHERE sotd.scheme_of_the_day = :scheme_of_the_day")
    void updateTimeSlot5FromDate(@Param(value = "scheme_of_the_day") Date scheme_of_the_day,
                                 @Param(value = "time_slot5is_available") Boolean aBoolean);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "UPDATE scheme_of_the_day AS sotd\n" +
            "SET time_slot4is_available = :time_slot4is_available\n" +
            "WHERE sotd.scheme_of_the_day = :scheme_of_the_day")
    void updateTimeSlot4FromDate(@Param(value = "scheme_of_the_day") Date scheme_of_the_day,
                                 @Param(value = "time_slot4is_available") Boolean aBoolean);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "UPDATE scheme_of_the_day AS sotd\n" +
            "SET time_slot3is_available = :time_slot3is_available\n" +
            "WHERE sotd.scheme_of_the_day = :scheme_of_the_day")
    void updateTimeSlot3FromDate(@Param(value = "scheme_of_the_day") Date scheme_of_the_day,
                                 @Param(value = "time_slot3is_available") Boolean aBoolean);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "UPDATE scheme_of_the_day AS sotd\n" +
            "SET time_slot2is_available = :time_slot2is_available\n" +
            "WHERE sotd.scheme_of_the_day = :scheme_of_the_day")
    void updateTimeSlot2FromDate(@Param(value = "scheme_of_the_day") Date scheme_of_the_day,
                                 @Param(value = "time_slot2is_available") Boolean aBoolean);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "UPDATE scheme_of_the_day AS sotd\n" +
            "SET time_slot1is_available = :time_slot1is_available\n" +
            "WHERE sotd.scheme_of_the_day = :scheme_of_the_day")
    void updateTimeSlot1FromDate(@Param(value = "scheme_of_the_day") Date scheme_of_the_day,
                                 @Param(value = "time_slot1is_available") Boolean aBoolean);
}

