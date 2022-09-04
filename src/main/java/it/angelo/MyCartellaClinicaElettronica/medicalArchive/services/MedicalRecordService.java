package it.angelo.MyCartellaClinicaElettronica.medicalArchive.services;

import it.angelo.MyCartellaClinicaElettronica.medicalArchive.entities.MedicalRecordDTO;
import it.angelo.MyCartellaClinicaElettronica.medicalArchive.repositories.MedicalRecordRepository;
import it.angelo.MyCartellaClinicaElettronica.user.entities.MedicalRecord;
import it.angelo.MyCartellaClinicaElettronica.user.entities.User;
import it.angelo.MyCartellaClinicaElettronica.user.repositories.UserRepository;
import it.angelo.MyCartellaClinicaElettronica.user.utils.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

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

    //non funziona come dovrebbe, aggiorna tutto l'oggetto quindi cancella tutti i dati nelle colonne
    //che non vengono settati sotto
    public MedicalRecord update(Long id, MedicalRecord medicalRecordInput) throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (medicalRecordInput == null)return null;
                medicalRecordInput.setId(id);
                medicalRecordInput.setUpdatedAt(LocalDateTime.now());
                medicalRecordInput.setUpdatedBy(user);

            return medicalRecordRepository.save(medicalRecordInput);
    }

    public MedicalRecord update2(Long id, String description, String patient_history, MedicalRecord medicalRecordUpdated){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        medicalRecordUpdated.setId(id);
        medicalRecordUpdated.setUpdatedAt(LocalDateTime.now());
        medicalRecordUpdated.setUpdatedBy(user);
        medicalRecordRepository.updateWithQuery(id, description, patient_history);

        return medicalRecordUpdated;
    }



    //controlliamo se una Cartella clinica è modificabile dall'utente autenticato
    public boolean canEdit(Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<MedicalRecord> medicalRecord = medicalRecordRepository.findById(id);
        if(!medicalRecord.isPresent())return false;
        return medicalRecord.get().getCreatedBy().getId() == user.getId();
    }
}
