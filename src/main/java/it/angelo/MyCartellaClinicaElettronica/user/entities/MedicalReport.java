package it.angelo.MyCartellaClinicaElettronica.user.entities;

import it.angelo.MyCartellaClinicaElettronica.utils.entities.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table//(name="`medical_report`")
public class MedicalReport extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String anamnesis;
    private String diagnosis;
    private String therapy;
    private String prognosis;
    private String medicalExamination;

    @OneToOne
    private User patient;

    @ManyToOne
    private User doctor;
/*
    @ManyToOne
    @JoinColumn(name = "medical_record_id")
    private MedicalReport medicalReport;

 */
}
