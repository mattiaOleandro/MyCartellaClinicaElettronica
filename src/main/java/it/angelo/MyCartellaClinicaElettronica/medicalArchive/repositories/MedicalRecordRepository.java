package it.angelo.MyCartellaClinicaElettronica.medicalArchive.repositories;

import it.angelo.MyCartellaClinicaElettronica.user.entities.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

    //Optional<MedicalReport> findByMedicalRecord();
}
