package it.angelo.MyCartellaClinicaElettronica.medicalArchive.controllers;

import it.angelo.MyCartellaClinicaElettronica.medicalArchive.entities.MedicalReportDTO;
import it.angelo.MyCartellaClinicaElettronica.medicalArchive.repositories.MedicalReportRepository;
import it.angelo.MyCartellaClinicaElettronica.medicalArchive.services.MedicalReportService;
import it.angelo.MyCartellaClinicaElettronica.medicalArchive.entities.MedicalReport;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/medical-report")
public class MedicalReportController {

    @Autowired
    MedicalReportRepository medicalReportRepository;

    @Autowired
    MedicalReportService medicalReportService;


    @GetMapping("/get1medicalReport/{id}")
    public ResponseEntity<MedicalReport> getOne(@PathVariable Long id, Principal principal)throws Exception{
        return medicalReportService.getMedicalReportResponseEntity(id, (UsernamePasswordAuthenticationToken) principal);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<MedicalReport>> getAll(Principal principal){
        return medicalReportService.getListResponseEntity((UsernamePasswordAuthenticationToken) principal);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_DOCTOR')") //solo un MEDICO può creare una referto
    public ResponseEntity<MedicalReport> create(@RequestBody MedicalReportDTO medicalReport) throws Exception{
        return ResponseEntity.ok(medicalReportService.save(medicalReport));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<MedicalReport> updateAll(@PathVariable Long id, @RequestBody MedicalReport medicalReport) throws Exception {
        return ResponseEntity.ok(medicalReportService.update(id,medicalReport));
    }

    @SneakyThrows
    @PatchMapping("/anamnesis/{id}")
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<MedicalReport> patchAnamnesis(@PathVariable Long id,@RequestBody String anamnesis){
        return ResponseEntity.ok(medicalReportService.patchAnamnesis(id,anamnesis));
    }

    @SneakyThrows
    @PatchMapping("/prognosis/{id}")
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<MedicalReport> patchPrognosis(@PathVariable Long id,@RequestBody String prognosis){
        return ResponseEntity.ok(medicalReportService.patchPrognosis(id,prognosis));
    }

    @SneakyThrows
    @PatchMapping("/therapy/{id}")
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<MedicalReport> patchTherapy(@PathVariable Long id,@RequestBody String therapy){
        return ResponseEntity.ok(medicalReportService.patchTherapy(id,therapy));
    }

    @SneakyThrows
    @PatchMapping("/diagnosis/{id}")
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<MedicalReport> patchDiagnosis(@PathVariable Long id,@RequestBody String diagnosis){
        return ResponseEntity.ok(medicalReportService.patchDiagnosis(id,diagnosis));
    }

    @SneakyThrows
    @PatchMapping("/medexamination/{id}")
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<MedicalReport> patchMedExamination(@PathVariable Long id,@RequestBody String medExamination){
        return ResponseEntity.ok(medicalReportService.patchMedExamination(id,medExamination));
    }

    @SneakyThrows
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete (@PathVariable Long id){
        medicalReportRepository.deleteById(id);
        return ResponseEntity.ok("Medical Report deleted!");
    }
}
