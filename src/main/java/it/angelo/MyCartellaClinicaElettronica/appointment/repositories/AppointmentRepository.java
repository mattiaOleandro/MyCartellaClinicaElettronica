package it.angelo.MyCartellaClinicaElettronica.appointment.repositories;

import it.angelo.MyCartellaClinicaElettronica.appointment.entities.Appointment;
import it.angelo.MyCartellaClinicaElettronica.appointment.entities.AppointmentStateEnum;
import it.angelo.MyCartellaClinicaElettronica.appointment.entities.TimeSlot;
import it.angelo.MyCartellaClinicaElettronica.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * is  appointment repository
 */
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByCreatedBy(User user);

    List<Appointment> findByPatient(User user);

    List<Appointment> findByDoctor(User user);


    @Query("select a from Appointment a where a.status = :appointmentStateEnum")
    List<Appointment> findAllByStatus(@Param("appointmentStateEnum") AppointmentStateEnum appointmentStateEnum);

    /**
     * @deprecated Appointment
     * @param appointmentStart
     * @param appointmentEnd
     * @return
     */
    // findAppointmentByRangeDate una query nativa, non funziona.
    @Query(nativeQuery = true,
            value = "SELECT a.appointment_start, u.surname AS 'Doctor', up.surname AS 'Patient', a.`description` " +
                    "FROM `appointment` AS a " +
                    "JOIN `user` AS u ON u.id = a.doctor_id " +
                    "JOIN `user` AS up ON up.id = a.patient_id " +
                    "WHERE appointment_start BETWEEN :appointmentStart AND :appointmentEnd")
    List<Appointment> findAppointmentByRangeDate(@Param("appointmentStart") LocalDateTime appointmentStart,
                                                 @Param("appointmentEnd") LocalDateTime appointmentEnd);


    // cerca gli appuntamenti in un range di date, lo implementiamo in "Appointment Controller"
    List<Appointment> findByAppointmentDateBetween(@Param("start") LocalDate start, @Param("end")LocalDate end);


}

