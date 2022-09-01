package it.angelo.MyCartellaClinicaElettronica.medicalArchive.services;

import it.angelo.MyCartellaClinicaElettronica.medicalArchive.entities.MedicalReportDTO;
import it.angelo.MyCartellaClinicaElettronica.medicalArchive.repositories.MedicalRecordRepository;
import it.angelo.MyCartellaClinicaElettronica.medicalArchive.repositories.MedicalReportRepository;
import it.angelo.MyCartellaClinicaElettronica.user.entities.MedicalRecord;
import it.angelo.MyCartellaClinicaElettronica.user.entities.MedicalReport;
import it.angelo.MyCartellaClinicaElettronica.user.entities.User;
import it.angelo.MyCartellaClinicaElettronica.user.repositories.UserRepository;
import it.angelo.MyCartellaClinicaElettronica.user.utils.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MedicalReportService {

    @Autowired
    MedicalReportRepository medicalReportRepository;

    @Autowired
    MedicalRecordRepository medicalRecordRepository;
    @Autowired
    private UserRepository userRepository;


    public MedicalReport save(MedicalReportDTO medicalReportInput)throws Exception{
        // rappresenta un utente autenticato, la gestione è demandata a JwtTokenFilter class
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // creo un nuovo medicalReport e assegno un valore ai suoi attributi
        MedicalReport medicalReport = new MedicalReport();
        medicalReport.setDoctor(user);
        medicalReport.setAnamnesis(medicalReportInput.getAnamnesis());
        medicalReport.setDiagnosis(medicalReportInput.getDiagnosis());
        medicalReport.setTherapy(medicalReportInput.getTherapy());
        medicalReport.setPrognosis(medicalReportInput.getPrognosis());
        medicalReport.setMedicalExamination(medicalReportInput.getMedicalExamination());

        medicalReport.setCreatedBy(user);
        medicalReport.setCreatedAt(LocalDateTime.now());
        Optional<MedicalRecord> medicalRecord2 = medicalRecordRepository.findById(medicalReportInput.getMedicalRecord());
        medicalReport.setMedicalRecord(medicalRecord2.get());

        //check for patient
        if(medicalReportInput.getPatient() == null) throw new Exception("Patient not found");
        Optional<User> patient = userRepository.findById(medicalReportInput.getPatient());
        if(!patient.isPresent() || !Roles.hasRole(patient.get(), Roles.PATIENT)) throw new Exception("Patient not found");

        medicalReport.setPatient(patient.get());
/*
        //check for medical Report
        if(medicalReportInput.getMedicalRecord() == null) throw new Exception("Medical Record not found");
        Optional<MedicalRecord> medicalRecord = medicalRecordRepository.findById(medicalReportInput.getMedicalRecord());
        if(!medicalRecord.isPresent()) throw new Exception("Medical Record not found");

        medicalReport.setMedicalRecord(medicalRecord.get());
*/
        return medicalReportRepository.save(medicalReport);
    }
}
