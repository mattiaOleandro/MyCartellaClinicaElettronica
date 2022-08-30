package it.angelo.MyCartellaClinicaElettronica.user.utils;

import it.angelo.MyCartellaClinicaElettronica.user.entities.User;

public class Roles {

    public final static String REGISTERED = "REGISTERED";

    //da rimuovere---------------------------------------
    public final static String RESTAURANT = "RESTAURANT";
    public final static String RIDER = "RIDER";
    //---------------------------------------------------

    public final static String OWNER = "OWNER";
    public final static String SUPER_ADMIN = "SUPER_ADMIN";
    public final static String ADMIN = "ADMIN";
    public final static String DOCTOR = "DOCTOR";
    public final static String SECRETARY = "SECRETARY";
    public final static String PATIENT ="PATIENT";

    //verifica che l'utente sia registrato e abbia un ruolo
    public static boolean hasRole(User user, String roleInput){
        return user.getRoles().stream().filter(role -> role.getName().equals(roleInput)).count() != 0;// da approfondire
    }
}
