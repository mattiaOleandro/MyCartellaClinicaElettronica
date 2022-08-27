package it.angelo.MyCartellaClinicaElettronica.auth.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

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

    @Column(unique = true,length = 16) //valutare omocodia
    private String fiscalCode;

    private String documentNumber;

}
