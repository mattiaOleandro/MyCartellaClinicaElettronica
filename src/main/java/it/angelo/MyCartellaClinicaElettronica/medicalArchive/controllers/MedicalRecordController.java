package it.angelo.MyCartellaClinicaElettronica.medicalArchive.controllers;

import it.angelo.MyCartellaClinicaElettronica.medicalArchive.entities.MedicalRecordDTO;
import it.angelo.MyCartellaClinicaElettronica.medicalArchive.repositories.MedicalRecordRepository;
import it.angelo.MyCartellaClinicaElettronica.medicalArchive.services.MedicalRecordService;
import it.angelo.MyCartellaClinicaElettronica.user.entities.MedicalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medical-record")
public class MedicalRecordController {

    @Autowired
    MedicalRecordRepository medicalRecordRepository;

    @Autowired
    MedicalRecordService medicalRecordService;

    // create medical record
    @PostMapping
    @PreAuthorize("hasRole('ROLE_DOCTOR')") //solo un MEDICO può creare una Cartella Clinica
    public ResponseEntity<MedicalRecord> create(@RequestBody MedicalRecordDTO medicalRecord) throws Exception{
        return ResponseEntity.ok(medicalRecordService.save(medicalRecord));
    }
}
