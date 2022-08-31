package it.angelo.MyCartellaClinicaElettronica.medicalArchive.repositories;

import it.angelo.MyCartellaClinicaElettronica.user.entities.MedicalReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalReportRepository extends JpaRepository<MedicalReport,Long> {
}
