package it.angelo.MyCartellaClinicaElettronica.appointment.repositories;

import it.angelo.MyCartellaClinicaElettronica.appointment.entities.Appointment;
import it.angelo.MyCartellaClinicaElettronica.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByCreatedBy(User user);

    List<Appointment> findByPatient(User user);

    List<Appointment> findByDoctor(User user);


}

