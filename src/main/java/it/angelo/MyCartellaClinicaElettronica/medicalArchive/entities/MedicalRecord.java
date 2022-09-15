package it.angelo.MyCartellaClinicaElettronica.medicalArchive.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * medialRecord è la cartella clinica, relativa all'intera storia clinica del paziente
 */

@Entity
@Table
@Data
public class MedicalRecord {

    @Id
    @GeneratedValue
    private long id;


}
