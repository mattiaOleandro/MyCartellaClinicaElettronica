package it.angelo.MyCartellaClinicaElettronica.order.entities;

import io.swagger.annotations.ApiModelProperty;
import it.angelo.MyCartellaClinicaElettronica.user.entities.User;
import it.angelo.MyCartellaClinicaElettronica.utils.entities.BaseEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @ApiModelProperty(value = "The id of the order", example = "01")
    private Long id;

    @ApiModelProperty(value = "Order description", example = "Fragile object handle with care")
    private String description;

    @ApiModelProperty(value = "Order address", example = "Via Roma, 234")
    private String address;

    @ApiModelProperty(value = "Telephone number for the order", example = "+393338427928")
    private String number;

    @ApiModelProperty(value = "City of order", example = "Milano")
    private String city;

    @ApiModelProperty(value = "Zip code of order", example = "20019")
    private String zipCode;

    @ApiModelProperty(value = "Order country", example = "Italy")
    private String state;

    @ApiModelProperty(value = "Progress status of the order", example = "CREATED")
    private OrderStateEnum status = OrderStateEnum.CREATED;

    @ManyToOne
    @ApiModelProperty(value = " ", example = " ")//TODO chiedere ad Angelo
    private User restaurant;

    @ManyToOne
    @ApiModelProperty(value = " ", example = " ")//TODO chiedere ad Angelo
    private User rider;

    @ApiModelProperty(value = "Price of the order", example = "299.99")
    private double price;

}
