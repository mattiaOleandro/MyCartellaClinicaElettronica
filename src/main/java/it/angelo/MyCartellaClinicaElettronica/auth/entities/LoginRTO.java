package it.angelo.MyCartellaClinicaElettronica.auth.entities;

import it.angelo.MyCartellaClinicaElettronica.user.entities.User;
import lombok.Data;

/**
 * contains 2 attribute: user and JWT.
 * they are returned to the user upon login
 */
@Data
public class LoginRTO {

    /** Object user */
    private User user;

    /** String that contains a JWT token */
    private String JWT;
}
