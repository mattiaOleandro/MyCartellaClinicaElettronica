package it.angelo.MyCartellaClinicaElettronica.medicalArchive.services;

import it.angelo.MyCartellaClinicaElettronica.medicalArchive.entities.MedicalReportDTO;
import it.angelo.MyCartellaClinicaElettronica.medicalArchive.repositories.MedicalRecordRepository;
import it.angelo.MyCartellaClinicaElettronica.medicalArchive.repositories.MedicalReportRepository;
import it.angelo.MyCartellaClinicaElettronica.medicalArchive.entities.MedicalRecord;
import it.angelo.MyCartellaClinicaElettronica.medicalArchive.entities.MedicalReport;
import it.angelo.MyCartellaClinicaElettronica.user.entities.User;
import it.angelo.MyCartellaClinicaElettronica.user.repositories.UserRepository;
import it.angelo.MyCartellaClinicaElettronica.user.utils.Roles;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
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

        //check for patient
        if(medicalReportInput.getPatient() == null) throw new Exception("Patient not found");
        Optional<User> patient = userRepository.findById(medicalReportInput.getPatient());
        if(!patient.isPresent() || !Roles.hasRole(patient.get(), Roles.PATIENT)) throw new Exception("Patient not found");
        medicalReport.setPatient(patient.get());

        //check for medical Report
        if(medicalReportInput.getMedicalRecord() == null) throw new Exception("Medical Record not found");
        Optional<MedicalRecord> medicalRecord = medicalRecordRepository.findById(medicalReportInput.getMedicalRecord());
        if(medicalRecord.isEmpty()) throw new Exception("Medical Record is Empty");
        medicalRecord.ifPresent(medicalReport::setMedicalRecord);

        return medicalReportRepository.save(medicalReport);
    }

    public MedicalReport update (Long id, MedicalReport medicalReportInput) throws Exception{
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<MedicalReport> medicalReportToBeUpdated = medicalReportRepository.findById(id);

        medicalReportToBeUpdated.ifPresent(medicalReport -> medicalReport.setAnamnesis(medicalReportInput.getAnamnesis()));
        medicalReportToBeUpdated.ifPresent(medicalReport -> medicalReport.setMedicalRecord(medicalReportInput.getMedicalRecord()));
        medicalReportToBeUpdated.ifPresent(medicalReport -> medicalReport.setUpdatedAt(LocalDateTime.now()));
        medicalReportToBeUpdated.ifPresent(medicalReport -> medicalReport.setMedicalExamination(medicalReportInput.getMedicalExamination()));
        medicalReportToBeUpdated.ifPresent(medicalReport -> medicalReport.setUpdatedBy(user));
        medicalReportToBeUpdated.ifPresent(medicalReport -> medicalReport.setDiagnosis(medicalReportInput.getDiagnosis()));
        medicalReportToBeUpdated.ifPresent(medicalReport -> medicalReport.setTherapy(medicalReportInput.getTherapy()));
        medicalReportToBeUpdated.ifPresent(medicalReport -> medicalReport.setPrognosis(medicalReportInput.getPrognosis()));
        medicalReportToBeUpdated.ifPresent(medicalReport -> medicalReport.setDoctor(medicalReportInput.getDoctor()));
        medicalReportToBeUpdated.ifPresent(medicalReport -> medicalReport.setPatient(medicalReportInput.getPatient()));

        return medicalReportRepository.saveAndFlush(medicalReportToBeUpdated.get());
    }

    @SneakyThrows
    public MedicalReport patchAnamnesis(Long id, String anamnesis){
        Optional<MedicalReport> medicalReportTobeModified = medicalReportRepository.findById(id);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        medicalReportTobeModified.ifPresent(medicalReport -> medicalReport.setAnamnesis(anamnesis));
        medicalReportTobeModified.ifPresent(medicalReport -> medicalReport.setUpdatedAt(LocalDateTime.now()));
        medicalReportTobeModified.ifPresent(medicalReport -> medicalReport.setUpdatedBy(user));

        return medicalReportRepository.saveAndFlush(medicalReportTobeModified.get());
    }

    @SneakyThrows
    public MedicalReport patchPrognosis(Long id, String prognosis){
        Optional<MedicalReport> medicalReportTobeModified = medicalReportRepository.findById(id);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        medicalReportTobeModified.ifPresent(medicalReport -> medicalReport.setPrognosis(prognosis));
        medicalReportTobeModified.ifPresent(medicalReport -> medicalReport.setUpdatedAt(LocalDateTime.now()));
        medicalReportTobeModified.ifPresent(medicalReport -> medicalReport.setUpdatedBy(user));

        return medicalReportRepository.saveAndFlush(medicalReportTobeModified.get());
    }

    @SneakyThrows
    public MedicalReport patchTherapy(Long id, String therapy){
        Optional<MedicalReport> medicalReportTobeModified = medicalReportRepository.findById(id);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        medicalReportTobeModified.ifPresent(medicalReport -> medicalReport.setTherapy(therapy));
        medicalReportTobeModified.ifPresent(medicalReport -> medicalReport.setUpdatedAt(LocalDateTime.now()));
        medicalReportTobeModified.ifPresent(medicalReport -> medicalReport.setUpdatedBy(user));

        return medicalReportRepository.saveAndFlush(medicalReportTobeModified.get());
    }

    @SneakyThrows
    public MedicalReport patchDiagnosis(Long id, String diagnosis){
        Optional<MedicalReport> medicalReportTobeModified = medicalReportRepository.findById(id);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        medicalReportTobeModified.ifPresent(medicalReport -> medicalReport.setDiagnosis(diagnosis));
        medicalReportTobeModified.ifPresent(medicalReport -> medicalReport.setUpdatedAt(LocalDateTime.now()));
        medicalReportTobeModified.ifPresent(medicalReport -> medicalReport.setUpdatedBy(user));

        return medicalReportRepository.saveAndFlush(medicalReportTobeModified.get());
    }

    @SneakyThrows
    public MedicalReport patchMedExamination(Long id, String medExamination){
        Optional<MedicalReport> medicalReportTobeModified = medicalReportRepository.findById(id);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        medicalReportTobeModified.ifPresent(medicalReport -> medicalReport.setMedicalExamination(medExamination));
        medicalReportTobeModified.ifPresent(medicalReport -> medicalReport.setUpdatedAt(LocalDateTime.now()));
        medicalReportTobeModified.ifPresent(medicalReport -> medicalReport.setUpdatedBy(user));

        return medicalReportRepository.saveAndFlush(medicalReportTobeModified.get());
    }

    @SneakyThrows
    public ResponseEntity<MedicalReport> getMedicalReportResponseEntity(Long id, UsernamePasswordAuthenticationToken principal) {
        Optional <MedicalReport> medicalReport = medicalReportRepository.findById(id);
        if (!medicalReport.isPresent()) throw new RuntimeException("Medical Report not found, check the ID.");
        User user = (User) principal.getPrincipal();
        if(Roles.hasRole(user, Roles.SECRETARY) && medicalReport.get().getCreatedBy().getId() == user.getId()){
            return ResponseEntity.ok(medicalReport.get());

        }else if(Roles.hasRole(user, Roles.PATIENT) && medicalReport.get().getPatient().getId() == user.getId()) {
            return ResponseEntity.ok(medicalReport.get());

        }else if(Roles.hasRole(user, Roles.DOCTOR) && medicalReport.get().getDoctor().getId() == user.getId()){
            return ResponseEntity.ok(medicalReport.get());
        }
        return ResponseEntity.notFound().build();
    }

    @SneakyThrows
    public ResponseEntity<List<MedicalReport>> getListResponseEntity(UsernamePasswordAuthenticationToken principal) {
        User user = (User) principal.getPrincipal();

        if(Roles.hasRole(user, Roles.SECRETARY)){
            return ResponseEntity.ok(medicalReportRepository.findByCreatedBy(user));

        } else if (Roles.hasRole(user, Roles.PATIENT)) {
            return ResponseEntity.ok(medicalReportRepository.findByPatient(user));

        } else if (Roles.hasRole(user, Roles.DOCTOR)){
            return ResponseEntity.ok(medicalReportRepository.findByDoctor(user));
        }
        return null;
    }


}
