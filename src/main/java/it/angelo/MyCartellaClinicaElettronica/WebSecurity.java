package it.angelo.MyCartellaClinicaElettronica;

import it.angelo.MyCartellaClinicaElettronica.user.utils.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * general security configuration
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity( //abilita la sicurezza a livello di singolo metodo
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    //il nostro intento è accedere alla Classe BCryptPasswordEncoder(), non usiamo il classico @Autowired ma...
    //...annotiamo con @Bean e ritorniamo BCryptPasswordEncoder()
    //siamo in presenza di un password encoder
    //se cerchiamo su google "Java Spring encoder" ne troviamo diversi
    /**
     * PasswordEncoder class
     * @return new BCryptPasswordEncoder()
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * configure the security of all my app
     * @param http is a HttpSecurity object
     * @throws Exception can throw a generic exception
     */
    //disattiviamo il form di login di default di SpringBoot e configuriamo manualmente la sicurezza della ns app
    protected void configure(HttpSecurity http) throws  Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/h2-console/**").permitAll() //da eliminare in produzione
                //l'accesso alla root /admin è consentita solo ai ruoli tra parentesi
                //.antMatchers("/admin/**").hasAnyRole( "ROLE_"+ Roles.OWNER, "ROLE_" + Roles.SUPER_ADMIN, "ROLE_" + Roles.ADMIN)
                //l'accesso alla root /app è consentita agli utenti registrati
                //.antMatchers("/app/**").hasAnyRole( "ROLE_"+ Roles.REGISTERED)
                .antMatchers("/v2/api-docs", //dovrebbe servire per il libero accesso allo swagger ma non funziona :(
                        "/swagger-resources/configuration/ui",
                        "/swagger-resources",
                        "/swagger-resources/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**").permitAll();
                // questi ulteriori antmathers mi consentono di abilitare le root per secondo i ruoli
                // quindi possiamo proteggere API o gruppi di API da accessi indesiderati
                // hasRole rappresenta i ruoli
                // hasAutority rappresenta i permessi che ha ogni ruolo, mi dice quindi le cose che posso fare.
                /*.antMatchers("/admin/**").hasAnyRole("ROLE_"+ Roles.ADMIN, "ROLE_"+ Roles.OWNER, "ROLE_"+ Roles.SUPER_ADMIN)
                .antMatchers("/app/**").hasAnyRole("ROLE_"+ Roles.REGISTERED)*/
                /*.antMatchers("/blog/**").hasRole("ROLE_EDITOR")
                .antMatchers("/dev-tools/**").hasAnyAuthority("DEV_READ", "DEV_DELETE")
                .antMatchers("/dev-tools/-bis/**").hasAuthority("DO_DEV_TOOLS_READ")*/
        //--------------------------------------------------------------------------------------------------------------
                //.anyRequest().authenticated(); // ATTENZIONE!!! tutte le richieste in whitelist così sono libere! Da ricontrollare.
        //--------------------------------------------------------------------------------------------------------------

        http.csrf().disable();// disabilitiamo sistema di sicurezza(???) da approfondire
        http.headers().frameOptions().disable();// disabilitiamo sistema di sicurezza per frame(???) da approfondire

        // add JWT token filter
        // prima di tutte le chiamate API si inserisce questo filtro sull'autenticazione
        http.addFilterBefore(
                jwtTokenFilter,
                UsernamePasswordAuthenticationFilter.class
        );
    }
}
