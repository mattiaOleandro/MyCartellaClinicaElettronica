package it.angelo.MyCartellaClinicaElettronica.medicalArchive.repositories;

import it.angelo.MyCartellaClinicaElettronica.medicalArchive.entities.MedicalReport;
import it.angelo.MyCartellaClinicaElettronica.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalReportRepository extends JpaRepository<MedicalReport,Long> {

    List<MedicalReport> findByMedicalRecordId(Long postId);

    List<MedicalReport> findByCreatedBy(User user);

    List<MedicalReport> findByPatient(User user);

    List<MedicalReport> findByDoctor(User user);
}
