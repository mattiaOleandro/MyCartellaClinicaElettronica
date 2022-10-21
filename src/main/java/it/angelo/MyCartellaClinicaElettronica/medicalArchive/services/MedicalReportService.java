package it.angelo.MyCartellaClinicaElettronica.medicalArchive.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
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

import java.io.FileOutputStream;
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

    public void createPdf(Optional<MedicalReport> medicalReportToPrint){
        try {
            String filename = medicalReportToPrint.get().getPatient().getName() + " " + medicalReportToPrint.get().getPatient().getSurname() + ".pdf";
            String fileNameAndPath = "C:\\Users\\User\\Desktop\\"+filename;
            Document document = new Document();
            PdfWriter.getInstance(document,new FileOutputStream(fileNameAndPath));

            document.open();


            Paragraph paragraph0 = new Paragraph("Medical Report of : " + filename);
            document.add(paragraph0);

            document.add(new Paragraph(" "));

            PdfPTable table1 = new PdfPTable(1);
            table1.setHeaderRows(1);
            PdfPCell c1 = new PdfPCell(new Phrase("Anamnesis"));
            table1.addCell(c1);
            table1.addCell(medicalReportToPrint.get().getAnamnesis());
            document.add(table1);
            document.add(new Paragraph(" "));

            PdfPTable table2 = new PdfPTable(1);
            table2.setHeaderRows(1);
            PdfPCell c2 = new PdfPCell(new Phrase("Medical Examination"));
            table2.addCell(c2);
            table2.addCell(medicalReportToPrint.get().getMedicalExamination());
            document.add(table2);
            document.add(new Paragraph(" "));

            PdfPTable table3= new PdfPTable(1);
            table3.setHeaderRows(1);
            PdfPCell c3 = new PdfPCell(new Phrase("Diagnosis"));
            table3.addCell(c3);
            table3.addCell(medicalReportToPrint.get().getDiagnosis());
            document.add(table3);
            document.add(new Paragraph(" "));

            PdfPTable table4 = new PdfPTable(1);
            table4.setHeaderRows(1);
            PdfPCell c4 = new PdfPCell(new Phrase("Prognosis"));
            table4.addCell(c4);
            table4.addCell(medicalReportToPrint.get().getPrognosis());
            document.add(table4);
            document.add(new Paragraph(" "));

            PdfPTable table5 = new PdfPTable(1);
            table5.setHeaderRows(1);
            PdfPCell c5 = new PdfPCell(new Phrase("Therapy"));
            table5.addCell(c5);
            table5.addCell(medicalReportToPrint.get().getTherapy());
            document.add(table5);
            document.add(new Paragraph(" "));

            String doctorCompleteName = medicalReportToPrint.get().getDoctor().getName() + " " + medicalReportToPrint.get().getDoctor().getSurname();

            Paragraph paragraph1 = new Paragraph(
                    "Doctor : " + doctorCompleteName);
            document.add(paragraph1);

            Paragraph paragraph2 = new Paragraph("Emitted at : " + LocalDateTime.now());
            document.add(paragraph2);

            document.close();

        }catch (Exception e){
            System.out.println(e);
        }
    }
}
