package it.angelo.MyCartellaClinicaElettronica.medicalArchive.controllers;

import it.angelo.MyCartellaClinicaElettronica.medicalArchive.entities.MedicalRecordDTO;
import it.angelo.MyCartellaClinicaElettronica.medicalArchive.entities.MedicalReportDTO;
import it.angelo.MyCartellaClinicaElettronica.medicalArchive.repositories.MedicalRecordRepository;
import it.angelo.MyCartellaClinicaElettronica.medicalArchive.repositories.MedicalReportRepository;
import it.angelo.MyCartellaClinicaElettronica.medicalArchive.services.MedicalRecordService;
import it.angelo.MyCartellaClinicaElettronica.medicalArchive.services.MedicalReportService;
import it.angelo.MyCartellaClinicaElettronica.user.entities.MedicalRecord;
import it.angelo.MyCartellaClinicaElettronica.user.entities.MedicalReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medical-report")
public class MedicalReportController {

    @Autowired
    MedicalReportRepository medicalReportRepository;

    @Autowired
    MedicalReportService medicalReportService;

    // create medical record
    @PostMapping
    @PreAuthorize("hasRole('ROLE_DOCTOR')") //solo un MEDICO può creare una referto
    public ResponseEntity<MedicalReport> create(@RequestBody MedicalReportDTO medicalReport) throws Exception{
        return ResponseEntity.ok(medicalReportService.save(medicalReport));
    }
}
