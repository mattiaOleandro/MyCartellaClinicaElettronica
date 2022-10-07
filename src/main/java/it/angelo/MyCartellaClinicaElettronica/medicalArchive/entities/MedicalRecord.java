package it.angelo.MyCartellaClinicaElettronica.medicalArchive.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import it.angelo.MyCartellaClinicaElettronica.user.entities.User;
import it.angelo.MyCartellaClinicaElettronica.utils.entities.BaseEntity;
import lombok.Data;
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
    private Long id;
    private String description;
    private String patientHistory;

    private boolean isActive = true; //cancellazione logica

    @ManyToOne
    private User patient;

    @ManyToOne
    private User doctor;

    //set di referti medici
    //mappedBy deve contenere il nome dell'istanza del proprietario della relazione che è: medicalRecord
    @OneToMany(mappedBy = "medicalRecord")
    @JsonBackReference //necessario per evitare Infinite recursion (StackOverflowError)
    @JsonIgnore // per evitare "Failed to evaluate Jackson deserialization"
    private Set<MedicalReport> medicalReport;

}
