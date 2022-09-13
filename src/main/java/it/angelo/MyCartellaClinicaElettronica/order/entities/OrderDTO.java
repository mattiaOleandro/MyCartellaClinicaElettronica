package it.angelo.MyCartellaClinicaElettronica.order.entities;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrderDTO {

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

    @ApiModelProperty(value = " ", example = " ")//TODO chiedere ad Angelo
    private Long restaurant;

    @ApiModelProperty(value = "Price of the order", example = "299.99")
    private double price;
}
