package it.angelo.MyCartellaClinicaElettronica.auth.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

// contiene le informazioni che l'utente dovrà inserire in fase di registrazione

/**
 * This Class contains all attribute of user to transfer
 * is superclass of SignupDoctorDTO, SignupSecretaryDTO, SignupPatientDTO,
 */
@Data
@MappedSuperclass
public class SignupDTO {

    private String name;
    private String surname;
    private String email;
    private String password;

    private String address;
    private String city;
    private String phone;
    private String nationality;
    private String placeOfBirth;
    private LocalDate birthDate;

    /**
     * fiscalCode attribute must be unique and has a length equal to 16
     */
    @Column(unique = true,length = 16) //valutare omocodia
    private String fiscalCode;

    private String documentNumber;

    public SignupDTO() {
    }

    public SignupDTO(String name, String surname, String email, String password, String address, String city, String phone, String nationality, String placeOfBirth, LocalDate birthDate, String fiscalCode, String documentNumber) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.address = address;
        this.city = city;
        this.phone = phone;
        this.nationality = nationality;
        this.placeOfBirth = placeOfBirth;
        this.birthDate = birthDate;
        this.fiscalCode = fiscalCode;
        this.documentNumber = documentNumber;
    }
}
