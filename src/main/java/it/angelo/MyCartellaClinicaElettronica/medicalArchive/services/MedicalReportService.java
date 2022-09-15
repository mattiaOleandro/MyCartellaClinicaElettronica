package it.angelo.MyCartellaClinicaElettronica.medicalArchive.services;

import it.angelo.MyCartellaClinicaElettronica.medicalArchive.entities.MedicalReport;
import it.angelo.MyCartellaClinicaElettronica.medicalArchive.repositories.MedicalReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicalReportService {

    @Autowired
    private MedicalReportRepository medicalReportRepository;

    public MedicalReport create(MedicalReport medicalReport) {

        MedicalReport report = new MedicalReport();
        report.setAnamnesis(medicalReport.getAnamnesis());
        report.setDiagnosis(medicalReport.getDiagnosis());
        report.setDoctor(medicalReport.getDoctor());
        report.setPatient(medicalReport.getPatient());
        report.setPrognosis(medicalReport.getPrognosis());
        report.setFurtherDetails(medicalReport.getFurtherDetails());

        return medicalReportRepository.save(report);
    }

}
