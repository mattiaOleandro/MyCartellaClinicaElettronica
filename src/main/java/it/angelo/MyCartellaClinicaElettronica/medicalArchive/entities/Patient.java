package it.angelo.MyCartellaClinicaElettronica.medicalArchive.entities;

import it.angelo.MyCartellaClinicaElettronica.utils.entities.BaseEntity;
import lombok.Data;
import org.springframework.security.core.userdetails.User;

import javax.persistence.*;

@Entity
@Table(name="patients")
@Data
public class Patient extends BaseEntity {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    @OneToOne
    @JoinColumn(name="medical_record_id")
    private MedicalRecord medicalRecord;

    private String name;
    private String surname;
    private String fiscalCode;

    // TODO other fields




}
