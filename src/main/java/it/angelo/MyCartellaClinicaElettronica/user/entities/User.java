package it.angelo.MyCartellaClinicaElettronica.user.entities;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name="`user`")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @ApiModelProperty(value = "The id of the user", example = "01")
    private Long id;

    @ApiModelProperty(value = "The name of the user", example = "Mario")
    private String name;

    @ApiModelProperty(value = "The surname of the user", example = "Rossi")
    private String surname;

    @ApiModelProperty(value = "User activation status", example = "1")
    private boolean isActive = false;

    @Column(length = 36)
    @ApiModelProperty(value = "User activation code", example = "c28810b6-55rc-4a8b-bd9c-2f3d07i1919b")
    private String activationCode;

    @Column(length = 36)
    @ApiModelProperty(value = " ", example = " ")//TODO chiedere ad Angelo
    private String passwordResetCode;

    @Column(unique = true)
    @ApiModelProperty(value = "User email", example = "mariorossi@gamil.com")
    private String email;

    @ApiModelProperty(value = "Password User", example = "Password123")
    private String password;

    // https://jwt.io/ allows you to decode, verify and generate JWT.
    @ApiModelProperty(value = " ", example = " ")//TODO chiedere ad Angelo
    private LocalDateTime jwtCreatedOn;

    // Facciamo il join della tabella user e della tabella USER_ROLES. Un'utente può quindi avere uno o più ruoli
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLES",
            joinColumns = {
                    @JoinColumn(name = "USER_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "ROLE_ID")
            })
    @ApiModelProperty(value = " ", example = " ")//TODO chiedere ad Angelo
    private Set<Role> roles;

//-----------------------------------------------------------------------------------------------------------
    @ApiModelProperty(value = "User address", example = "Via Roma, 234")
    private String address;

    @ApiModelProperty(value = "City of the user", example = "Milano")
    private String city;

    @ApiModelProperty(value = "Telephone number of the user", example = "+393338427928")
    private String phone;

    @ApiModelProperty(value = "Nationality of the user", example = "Italy")
    private String nationality;

    @ApiModelProperty(value = "Place of birth of the user", example = "Roma")
    private String placeOfBirth;

    @ApiModelProperty(value = "Date of birth of the user", example = "1994/02/11")
    private LocalDate birthDate;

    @Column(unique = true,length = 16) //valutare omocodia
    @ApiModelProperty(value = "Fiscal code of the user", example = "YVNXMU82B63B142O")
    private String fiscalCode;

    @ApiModelProperty(value = "Number of the issued document", example = "CA00000AA")
    private String documentNumber;
//-----------------------------------------------------------------------------------------------------------
    @ApiModelProperty(value = "User serial number", example = "BJA353PRO")
    private String badgeNumber;

    @Enumerated(EnumType.STRING)
    @ApiModelProperty(value = "Medical specialization of the user", example = "CARDIOLOGY")
    private EnumMedicalSpecializzation medicalSpecialization;

    @Enumerated(EnumType.STRING)
    @ApiModelProperty(value = "Workplace of the user", example = "MILANO")
    private EnumPlaceOfWork placeOfWork;

    @ApiModelProperty(value = "Medical pathology of the user", example = "Diabete")
    private String medicalPathology;
}