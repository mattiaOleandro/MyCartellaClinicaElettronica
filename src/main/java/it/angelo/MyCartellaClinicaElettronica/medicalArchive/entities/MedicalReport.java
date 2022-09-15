package it.angelo.MyCartellaClinicaElettronica.medicalArchive.entities;

import it.angelo.MyCartellaClinicaElettronica.user.entities.User;
import lombok.Data;

import javax.persistence.*;

/*
 * Medical Report è il nostro referto medico, relativo alla singola visita medica
 */

@Entity
@Table
@Data
public class MedicalReport {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private User patient;

    @ManyToOne
    private User doctor;

    @Column(nullable = false, length = 4000)
    private String anamnesis;

    @Column(nullable = false, length = 4000)
    private String diagnosis;

    @Column(nullable = false, length = 4000)
    private String prognosis;

    @Column(length = 4000)
    private String furtherDetails;

    @OneToMany
    private MedicalRecord medicalRecord;
}
