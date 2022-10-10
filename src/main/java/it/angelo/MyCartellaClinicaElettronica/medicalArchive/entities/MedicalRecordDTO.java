package it.angelo.MyCartellaClinicaElettronica.medicalArchive.entities;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data

public class MedicalRecordDTO implements Serializable {

    private Long id;
    private String description;
    private String patientHistory;

    private Long patient;
    private Long medicalReport;

    @Override
    public String toString() {
        return "MedicalRecordDTO " +
                "id= " + id +
                ", description= " + description + " - " +
                ", patientHistory= " + patientHistory + " - " +
                ", patient= " + patient +
                ", medicalReport= " + medicalReport ;
    }
}
