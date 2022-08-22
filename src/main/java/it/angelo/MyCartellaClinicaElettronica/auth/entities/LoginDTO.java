package it.angelo.MyCartellaClinicaElettronica.auth.entities;

import lombok.Data;

@Data
public class LoginDTO {

    /** This is the user email */
    private String email;

    /** This is the password CLEAR */
    private String password;

}

