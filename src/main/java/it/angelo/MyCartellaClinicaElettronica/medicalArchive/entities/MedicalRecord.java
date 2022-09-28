package it.angelo.MyCartellaClinicaElettronica.medicalArchive.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

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

    @OneToOne
    @JoinColumn(name="patient_id")
    private Patient patient;

    // in kilograms
    private float weight;
    // in centimeters
    private int height;

    /*
    Qui inseriamo informazioni imporanti sulla storia sanitaria del paziente.
    Ci sono tanti modi di farlo:
    - le inseriamo semplicemente qui dentro come variabili
    - creiamo degli oggetti: oggetto StoriaClinicaGenerale, oggetto DatiAssicurativi...
     */

    // vaccination può essere una lista di stringhe; può essere una lista di enum;
    // può essere una lista di oggetti di tipo Vaccine -> questa sarebbe la migliore
    // così ci posso mettere dentro informazioni sulla marca, sulla versione specifica, il prezzo
    private List<String> vaccinations;
    // se invece che una lista di stringhe metto una lista di Hillness molto meglio,
    // perché l'oggetto Malattia può contenere tante informazioni utili che uso anche altrove
    private List<String> previousHillness;







}
