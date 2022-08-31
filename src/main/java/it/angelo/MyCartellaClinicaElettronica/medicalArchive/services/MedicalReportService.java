package it.angelo.MyCartellaClinicaElettronica.medicalArchive.services;

import it.angelo.MyCartellaClinicaElettronica.medicalArchive.entities.MedicalRecordDTO;
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

        MedicalReport medicalReport = new MedicalReport();
        medicalReport.setDoctor(user);
        medicalReport.setAnamnesis(medicalReportInput.getAnamnesis());
        medicalReport.setDiagnosis(medicalReportInput.getDiagnosis());
        medicalReport.setTherapy(medicalReportInput.getTherapy());
        medicalReport.setPrognosis(medicalReportInput.getPrognosis());
        medicalReport.setMedicalExamination(medicalReportInput.getMedicalExamination());
        /*
        //assegniamo alla cartella medica  un set di referti
        Set<MedicalReport> medicalReports = new HashSet<>();
        Optional<MedicalReport> medicalReportmedicalRecord = medicalRecordRepository.findByMedicalRecord();
        if(!medicalReportmedicalRecord.isPresent()) throw new Exception("Cannot set medical Record");
        medicalReports.add(medicalReportmedicalRecord.get());
        medicalRecord.setMedicalReport(medicalReports);
        */
        medicalReport.setCreatedBy(user);
        medicalReport.setCreatedAt(LocalDateTime.now());

        //check for patient
        if(medicalReportInput.getPatient() == null) throw new Exception("Patient not found");
        Optional<User> patient = userRepository.findById(medicalReportInput.getPatient());
        if(!patient.isPresent() || !Roles.hasRole(patient.get(), Roles.PATIENT)) throw new Exception("Patient not found");

        medicalReport.setPatient(patient.get());

        return medicalReportRepository.save(medicalReport);
    }
}
