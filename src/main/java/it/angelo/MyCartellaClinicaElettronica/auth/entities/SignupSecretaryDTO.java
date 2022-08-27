package it.angelo.MyCartellaClinicaElettronica.auth.entities;

import it.angelo.MyCartellaClinicaElettronica.user.entities.EnumPlaceOfWork;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class SignupSecretaryDTO extends SignupDTO{

    private String badgeNumber;

    @Enumerated(EnumType.STRING)
    private EnumPlaceOfWork placeOfWork;
}
