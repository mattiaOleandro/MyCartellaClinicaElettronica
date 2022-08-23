package it.angelo.MyCartellaClinicaElettronica.auth.entities;

import it.angelo.MyCartellaClinicaElettronica.user.entities.EnumPlaceOfWork;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class SignupSecretaryDTO {

    private String name;

    private String surname;

    private String email;

    private String password;

    private String badgeNumber;

    @Enumerated(EnumType.STRING)
    private EnumPlaceOfWork placeOfWork;
}
