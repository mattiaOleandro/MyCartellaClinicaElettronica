package it.angelo.MyCartellaClinicaElettronica.user.entities;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value = "The id of the role", example = "01")
    private Long id;

    @ApiModelProperty(value = "The name of the role", example = "REGISTERED")
    private String name;

    @ApiModelProperty(value = "The description of the role", example = "Registered")
    private String description;
}