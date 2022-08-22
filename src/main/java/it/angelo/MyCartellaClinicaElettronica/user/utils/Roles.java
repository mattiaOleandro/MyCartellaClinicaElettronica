package it.angelo.MyCartellaClinicaElettronica.user.utils;

import it.angelo.MyCartellaClinicaElettronica.user.entities.User;

public class Roles {

    public final static String REGISTERED = "REGISTERED";
    public final static String RESTAURANT = "RESTAURANT";
    public final static String RIDER ="RIDER";

    public final static String DOCTOR = "DOCTOR";
    public final static String SECRETARY = "SECRETARY";
    public final static String PATIENT ="PATIENT";

    public static boolean hasRole(User user, String roleInput){
        return user.getRoles().stream().filter(role -> role.getName().equals(roleInput)).count() != 0;
    }
}
