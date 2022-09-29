package it.angelo.MyCartellaClinicaElettronica.medicalArchive.repositories;

import it.angelo.MyCartellaClinicaElettronica.user.entities.MedicalReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicalReportRepository extends JpaRepository<MedicalReport,Long> {

    List<MedicalReport> findByMedicalRecordId(Long postId);
}
