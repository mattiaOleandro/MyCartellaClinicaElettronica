package it.angelo.MyCartellaClinicaElettronica;

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

@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenFilter jwtTokenFilter;


    //non possiamo fare @Autowired di questo perchè non è un servizio(???), chiedere a Carlo
    //quindi il metodo va annotato con @Bean
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

 /*   @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }*/

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
                .antMatchers("/v2/api-docs",
                        "/swagger-resources/configuration/ui",
                        "/swagger-resources",
                        "/swagger-resources/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**").permitAll()
                /*.antMatchers("/admin/**").hasAnyRole("ROLE_"+ Roles.ADMIN, "ROLE_"+ Roles.OWNER, "ROLE_"+ Roles.SUPER_ADMIN)
                .antMatchers("/app/**").hasAnyRole("ROLE_"+ Roles.REGISTERED)*/
                /*.antMatchers("/blog/**").hasRole("ROLE:EDITOR")
                .antMatchers("/dev-tools/**").hasAnyAuthority("DEV_READ", "DEV_DELETE")
                .antMatchers("/dev-tools/-bis/**").hasAuthority("DO_DEV_TOOLS_READ")*/
                .anyRequest().authenticated();


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
