package it.angelo.MyCartellaClinicaElettronica.user.entities;

import it.angelo.MyCartellaClinicaElettronica.utils.entities.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name="`medical_record`")
public class MedicalRecord extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String description;
    private String patientHistory;

    private boolean isActive = true;

    @OneToOne
    private User patient;

    @ManyToOne
    private User doctor;
/*
    @OneToMany(mappedBy = "medical_record")
    private Set<MedicalReport> medicalReport;
*/


    // Facciamo il join della tabella MEDICAL_RECORD e della tabella MEDICAL_REPORT. Una cartella medica
    // può quindi avere uno o più referti al suo interno
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "MEDICAL_RECORD_MEDICAL_REPORT",
            joinColumns = {
                    @JoinColumn(name = "MEDICAL_RECORD_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "MEDICAL_REPORT_ID")
            })
    private Set<MedicalReport> medicalReport;

}
