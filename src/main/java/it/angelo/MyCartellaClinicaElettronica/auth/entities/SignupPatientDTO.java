package it.angelo.MyCartellaClinicaElettronica.auth.entities;

import lombok.Data;

@Data
public class SignupPatientDTO {

    private String name;

    private String surname;

    private String email;

    private String password;

    private String medicalPathology;

}
