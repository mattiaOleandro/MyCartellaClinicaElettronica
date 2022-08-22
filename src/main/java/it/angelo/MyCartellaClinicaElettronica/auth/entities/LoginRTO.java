package it.angelo.MyCartellaClinicaElettronica.auth.entities;

import it.angelo.MyCartellaClinicaElettronica.user.entities.User;
import lombok.Data;

@Data
public class LoginRTO {

    private User user;
    private String JWT;
}
