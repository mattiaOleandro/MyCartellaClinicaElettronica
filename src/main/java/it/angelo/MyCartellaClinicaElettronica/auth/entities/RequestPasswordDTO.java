package it.angelo.MyCartellaClinicaElettronica.auth.entities;

import lombok.Data;

/**
 * contain an email attribute necessary for reset password
 */
@Data
public class RequestPasswordDTO {
    private String email;
}

