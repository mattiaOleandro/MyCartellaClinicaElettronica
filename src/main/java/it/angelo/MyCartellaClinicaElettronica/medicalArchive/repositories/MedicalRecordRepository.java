package it.angelo.MyCartellaClinicaElettronica.medicalArchive.repositories;

import it.angelo.MyCartellaClinicaElettronica.appointment.entities.Appointment;
import it.angelo.MyCartellaClinicaElettronica.user.entities.MedicalRecord;
import it.angelo.MyCartellaClinicaElettronica.user.entities.MedicalReport;
import it.angelo.MyCartellaClinicaElettronica.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

    List<MedicalRecord> findByPatient(User user);
}
