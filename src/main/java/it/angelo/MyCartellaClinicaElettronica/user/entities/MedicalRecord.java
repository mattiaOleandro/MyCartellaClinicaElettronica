package it.angelo.MyCartellaClinicaElettronica.user.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import it.angelo.MyCartellaClinicaElettronica.utils.entities.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@DynamicUpdate
@Table(name="MEDICAL_RECORD")
public class MedicalRecord extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @ApiModelProperty(value = "The id of the medical record", example = "01")
    private Long id;

    @ApiModelProperty(value = "The description of the medical report", example = " ")//TODO completare documentazione con esempio
    private String description;

    @ApiModelProperty(value = "The patient history of the medical report", example = " ")//TODO completare documentazione con esempio
    private String patientHistory;

    @ApiModelProperty(value = "Medical record activation status", example = "1")
    private boolean isActive = true;

    @ManyToOne
    @ApiModelProperty(value = " ", example = " ")//TODO chiedere ad Angelo
    private User patient;

    @ManyToOne
    @ApiModelProperty(value = " ", example = " ")//TODO chiedere ad Angelo
    private User doctor;

    //set di referti medici
    //mappedBy deve contenere il nome dell'istanza del proprietario della relazione che è: medicalRecord
    @OneToMany(mappedBy = "medicalRecord")
    @JsonBackReference //necessario per evitare Infinite recursion (StackOverflowError)
    @JsonIgnore // per evitare "Failed to evaluate Jackson deserialization"
    @ApiModelProperty(value = "Set medical report in medical record", example = " ")//TODO completare documentazione con esempio
    private Set<MedicalReport> medicalReport;

}
