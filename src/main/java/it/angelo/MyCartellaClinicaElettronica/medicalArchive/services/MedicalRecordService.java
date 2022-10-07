package it.angelo.MyCartellaClinicaElettronica.medicalArchive.services;

import it.angelo.MyCartellaClinicaElettronica.medicalArchive.entities.MedicalRecordDTO;
import it.angelo.MyCartellaClinicaElettronica.medicalArchive.repositories.MedicalRecordRepository;
import it.angelo.MyCartellaClinicaElettronica.medicalArchive.entities.MedicalRecord;
import it.angelo.MyCartellaClinicaElettronica.user.entities.User;
import it.angelo.MyCartellaClinicaElettronica.user.repositories.UserRepository;
import it.angelo.MyCartellaClinicaElettronica.user.utils.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;



@Service
public class MedicalRecordService {

    @Autowired
    MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private UserRepository userRepository;


    public MedicalRecord save(MedicalRecordDTO medicalRecordInput)throws Exception{
        // rappresenta un utente autenticato, la gestione è demandata a JwtTokenFilter class
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setDoctor(user);
        medicalRecord.setDescription(medicalRecordInput.getDescription());
        medicalRecord.setPatientHistory(medicalRecordInput.getPatientHistory());
        medicalRecord.setCreatedBy(user);
        medicalRecord.setCreatedAt(LocalDateTime.now());

        //check for patient
        if(medicalRecordInput.getPatient() == null) throw new Exception("Patient not found");
        Optional<User> patient = userRepository.findById(medicalRecordInput.getPatient());
        if(!patient.isPresent() || !Roles.hasRole(patient.get(), Roles.PATIENT)) throw new Exception("Patient not found");

        medicalRecord.setPatient(patient.get());

        return medicalRecordRepository.save(medicalRecord);
    }

    public MedicalRecord update(Long id, MedicalRecord medicalRecordInput) throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<MedicalRecord>mdToBeUpdated = medicalRecordRepository.findById(id);
        mdToBeUpdated.ifPresent(record -> record.setDescription(medicalRecordInput.getDescription()));
        mdToBeUpdated.ifPresent(record -> record.setPatientHistory(medicalRecordInput.getPatientHistory()));
        mdToBeUpdated.ifPresent(record -> record.setUpdatedBy(medicalRecordInput.getUpdatedBy()));
        mdToBeUpdated.ifPresent(record -> record.setUpdatedAt(LocalDateTime.now()));

            return medicalRecordRepository.save(mdToBeUpdated.get());
    }

    public MedicalRecord patchDescription(Long id, String description){
        Optional<MedicalRecord> medicalRecordTobeModified = medicalRecordRepository.findById(id);
        medicalRecordTobeModified.ifPresent(record -> record.setDescription(description));
        return medicalRecordRepository.saveAndFlush(medicalRecordTobeModified.get());
    }

    public MedicalRecord patchPatientHistory(Long id, String patientHistory){
        Optional<MedicalRecord> medicalRecordTobeModified = medicalRecordRepository.findById(id);
        medicalRecordTobeModified.ifPresent(record -> record.setPatientHistory(patientHistory));
        return medicalRecordRepository.saveAndFlush(medicalRecordTobeModified.get());
    }

    //controlliamo se una Cartella clinica è modificabile dall'utente autenticato
    public boolean canEdit(Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<MedicalRecord> medicalRecord = medicalRecordRepository.findById(id);
        if(!medicalRecord.isPresent())return false;
        return medicalRecord.get().getCreatedBy().getId() == user.getId();
    }
}
