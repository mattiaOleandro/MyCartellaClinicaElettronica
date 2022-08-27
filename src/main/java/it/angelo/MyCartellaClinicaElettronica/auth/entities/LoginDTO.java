package it.angelo.MyCartellaClinicaElettronica.auth.entities;

import lombok.Data;

/**
 * contains 2 attribute: email and password, mandatory for login
 */
@Data
public class LoginDTO {

    /** This is the user email */
    private String email;

    /** This is the password CLEAR */
    private String password;

}

