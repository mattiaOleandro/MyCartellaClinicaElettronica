package it.angelo.MyCartellaClinicaElettronica.user.controllers;

import it.angelo.MyCartellaClinicaElettronica.auth.entities.LoginRTO;
import it.angelo.MyCartellaClinicaElettronica.auth.services.LoginService;
import it.angelo.MyCartellaClinicaElettronica.user.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserController {

    @Autowired
    LoginService loginService;

    @GetMapping("/profile")
    public LoginRTO getProfile(Principal principal ){
        User user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        LoginRTO rto = new LoginRTO();
        rto.setUser(user);
        rto.setJWT(loginService.generateJWT(user));
        return rto;
    }
}
