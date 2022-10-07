package it.angelo.MyCartellaClinicaElettronica.medicalArchive.controllers;

import it.angelo.MyCartellaClinicaElettronica.medicalArchive.entities.MedicalRecordDTO;
import it.angelo.MyCartellaClinicaElettronica.medicalArchive.repositories.MedicalRecordRepository;
import it.angelo.MyCartellaClinicaElettronica.medicalArchive.services.MedicalRecordService;
import it.angelo.MyCartellaClinicaElettronica.medicalArchive.entities.MedicalRecord;
import it.angelo.MyCartellaClinicaElettronica.user.entities.User;
import it.angelo.MyCartellaClinicaElettronica.user.utils.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

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

    // get single medical record
    @GetMapping("/{id}")
    //@PostAuthorize("hasRole('"+Roles.ADMIN + "') OR returnObject.body == null OR returnObject.body.createdBy.id == authentication.principal.id")
    public ResponseEntity<MedicalRecord> getSingle(@PathVariable Long id, Principal principal){

        Optional<MedicalRecord> medicalRecord = medicalRecordRepository.findById(id);
        if (!medicalRecord.isPresent())return ResponseEntity.notFound().build();

        //creiamo un user con i riferimenti contenuti in Principal(UsernamePasswordAuthenticationToken)
        User user = (User)((UsernamePasswordAuthenticationToken) principal).getPrincipal();

        //un medico può vedere le cartelle cliniche, può farlo un segretario?Può farlo un paziente?
        // per adesso si, eventualmente da modificare.
        if(Roles.hasRole(user, Roles.SECRETARY) && medicalRecord.get().getCreatedBy().getId() == user.getId()){
            return ResponseEntity.ok(medicalRecord.get());

        }else if(Roles.hasRole(user, Roles.PATIENT) && medicalRecord.get().getPatient().getId() == user.getId()) {
            return ResponseEntity.ok(medicalRecord.get());

        }else if(Roles.hasRole(user, Roles.DOCTOR) && medicalRecord.get().getDoctor().getId() == user.getId()){
            return ResponseEntity.ok(medicalRecord.get());
        }
        return ResponseEntity.notFound().build();
    }

    // get all medical record
    @GetMapping
    public ResponseEntity<List<MedicalRecord>> getAll(Principal principal){

        User user = (User)((UsernamePasswordAuthenticationToken) principal).getPrincipal();

        //un medico può vedere le cartelle cliniche, può farlo un segretario?Può farlo un paziente?
        // per adesso si, eventualmente da modificare.
        if(Roles.hasRole(user, Roles.SECRETARY)){
            return ResponseEntity.ok(medicalRecordRepository.findByCreatedBy(user));

        } else if (Roles.hasRole(user, Roles.PATIENT)) {
            return ResponseEntity.ok(medicalRecordRepository.findByPatient(user));

        } else if (Roles.hasRole(user, Roles.DOCTOR)){
            return ResponseEntity.ok(medicalRecordRepository.findByDoctor(user));
        }
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalRecord> update(@RequestBody MedicalRecord medicalRecord,
                                                @PathVariable Long id) throws Exception {
        if(!medicalRecordService.canEdit(id)){
            return  ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(medicalRecordService.update(id, medicalRecord));
    }


    @PatchMapping("/patch/description/{id}")
    public ResponseEntity<?> patchDescription(@RequestBody String description, @PathVariable("id") Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        medicalRecordService.patchDescription(id,description);
        return ResponseEntity.ok("Description updated");
    }

    @PatchMapping("/patch/history/{id}")
    public ResponseEntity<?> patchPatientHistory(@RequestBody String patientHistory, @PathVariable("id") Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        medicalRecordService.patchDescription(id,patientHistory);
        return ResponseEntity.ok("Patient History updated");
    }


    //delete an appointment
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        if(!medicalRecordService.canEdit(id)){
            return  ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        medicalRecordRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
