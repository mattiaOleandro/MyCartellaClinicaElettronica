package it.angelo.MyCartellaClinicaElettronica.medicalArchive.entities;

import lombok.Data;

@Data
public class MedicalRecordDTO {

    private Long id;
    private String description;
    private String patientHistory;

    private Long patient;
}
