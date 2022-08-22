package it.angelo.MyCartellaClinicaElettronica.order.entities;

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
    private Long id;

    private String description;

    private String address;
    private String number;
    private String city;
    private String zipCode;
    private String state;

    private OrderStateEnum status = OrderStateEnum.CREATED;

    @ManyToOne
    private User restaurant;

    @ManyToOne
    private User rider;

    private double price;

}
