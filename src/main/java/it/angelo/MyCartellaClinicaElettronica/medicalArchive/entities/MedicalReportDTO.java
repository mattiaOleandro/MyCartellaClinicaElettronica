package it.angelo.MyCartellaClinicaElettronica.medicalArchive.entities;

import lombok.Data;

@Data
public class MedicalReportDTO {

    private String anamnesis;
    private String diagnosis;
    private String therapy;
    private String prognosis;
    private String medicalExamination;

    private Long patient;

    private Long medicalRecord;
}
