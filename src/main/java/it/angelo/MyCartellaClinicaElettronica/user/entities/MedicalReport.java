package it.angelo.MyCartellaClinicaElettronica.user.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.annotations.ApiModelProperty;
import it.angelo.MyCartellaClinicaElettronica.utils.entities.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;


// Referto medico
@Data
@Entity
@Table(name="MEDICAL_REPORT")
public class MedicalReport extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @ApiModelProperty(value = "The id of the medical report", example = "01")
    private Long id;

    @ApiModelProperty(value = "The anamnesis of the medical report", example = " ")//TODO completare documentazione con esempio
    private String anamnesis;

    @ApiModelProperty(value = "The diagnosis of the medical report", example = " ")//TODO completare documentazione con esempio
    private String diagnosis;

    @ApiModelProperty(value = "The therapy of the medical report", example = "Physical therapy")
    private String therapy;

    @ApiModelProperty(value = "The prognosis of the medical report", example = " ")//TODO completare documentazione con esempio
    private String prognosis;

    @ApiModelProperty(value = "The medical examination of the medical report", example = " ")//TODO completare documentazione con esempio
    private String medicalExamination;

    @OneToOne
    @ApiModelProperty(value = " ", example = " ")//TODO chiedere ad Angelo
    private User patient;

    @ManyToOne
    @ApiModelProperty(value = " ", example = " ")//TODO chiedere ad Angelo
    private User doctor;

    //proprietario della relazione
    //tanti referti(MedicalReport) possono essere contenuti dentro la cartella clinica(medicalRecord)
    @ManyToOne
    @JoinColumn(name = "medical_record_id")
    @JsonManagedReference //necessario per evitare Infinite recursion (StackOverflowError)
    @ApiModelProperty(value = " ", example = " ")//TODO chiedere ad Angelo
    private MedicalRecord medicalRecord; // lo trovo in MedicalRecord mappedBy

}
