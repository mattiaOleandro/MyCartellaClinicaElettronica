package it.angelo.MyCartellaClinicaElettronica.user.entities;

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
    private Long id;
    private String name;
    private String surname;

    private boolean isActive = false;

    @Column(length = 36)
    private String activationCode;

    @Column(length = 36)
    private String passwordResetCode;

    @Column(unique = true)
    private String email;
    private String password;

    // https://jwt.io/
    private LocalDateTime jwtCreatedOn;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLES",
            joinColumns = {
                    @JoinColumn(name = "USER_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "ROLE_ID")
            })
    private Set<Role> roles;
//-----------------------------------------------------------------------------------------------------------
    private String address;
    private String city;
    private String phone;
    private String nationality;
    private String placeOfBirth;
    private LocalDate birthDate;

    @Column(unique = true,length = 16) //valutare omocodia
    private String fiscalCode;

    private String documentNumber;

//-----------------------------------------------------------------------------------------------------------
    private String badgeNumber;

    @Enumerated(EnumType.STRING)
    private EnumMedicalSpecializzation medicalSpecialization;

    @Enumerated(EnumType.STRING)
    private EnumPlaceOfWork placeOfWork;

    private String medicalPathology;
}