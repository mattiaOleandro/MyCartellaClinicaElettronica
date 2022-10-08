package it.angelo.MyCartellaClinicaElettronica.auth.entities;

import it.angelo.MyCartellaClinicaElettronica.user.entities.EnumMedicalSpecializzation;
import it.angelo.MyCartellaClinicaElettronica.user.entities.EnumPlaceOfWork;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Data
public class SignupDoctorDTO extends SignupDTO{

    private String badgeNumber;

    @Enumerated(EnumType.STRING)
    private EnumMedicalSpecializzation medicalSpecialization;

    @Enumerated(EnumType.STRING)
    private EnumPlaceOfWork placeOfWork;

    public SignupDoctorDTO() {
    }

    public SignupDoctorDTO(String name, String surname, String email, String password, String address, String city, String phone, String nationality, String placeOfBirth, LocalDate birthDate, String fiscalCode, String documentNumber, String badgeNumber, EnumMedicalSpecializzation medicalSpecialization, EnumPlaceOfWork placeOfWork) {
        super(name, surname, email, password, address, city, phone, nationality, placeOfBirth, birthDate, fiscalCode, documentNumber);
        this.badgeNumber = badgeNumber;
        this.medicalSpecialization = medicalSpecialization;
        this.placeOfWork = placeOfWork;
    }


}
