package it.angelo.MyCartellaClinicaElettronica.medicalArchive.repositories;

import it.angelo.MyCartellaClinicaElettronica.medicalArchive.entities.MedicalRecord;
import it.angelo.MyCartellaClinicaElettronica.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {


    List<MedicalRecord> findByPatient(User user);

    List<MedicalRecord> findByCreatedBy(User user);

    List<MedicalRecord> findByDoctor(User user);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = " UPDATE `medical_record` \n" +
            "SET `description` = :description,`patient_history` = :patient_history\n" +
            "WHERE `medical_record`.id = :id")
    void updateWithQuery(@Param(value = "id") Long id,
                         @Param(value = "description") String description,
                         @Param(value = "patient_history") String patient_history);

}
