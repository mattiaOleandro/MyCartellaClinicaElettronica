package it.angelo.MyCartellaClinicaElettronica.auth.entities;

import lombok.Data;

/**
 * handle restore password, contain 2 attribute
 * newPassword and resetPasswordCode
 * the user insert this parameter API located in PasswordRestoreController
 */
@Data
public class RestorePasswordDTO {

    private String newPassword;
    private String resetPasswordCode;
}
