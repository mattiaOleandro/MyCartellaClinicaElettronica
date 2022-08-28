package it.angelo.MyCartellaClinicaElettronica.user.entities;

import lombok.Data;

import javax.persistence.*;

/**
 * this class describe a Role
 */
@Entity
@Table
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String description;
}
