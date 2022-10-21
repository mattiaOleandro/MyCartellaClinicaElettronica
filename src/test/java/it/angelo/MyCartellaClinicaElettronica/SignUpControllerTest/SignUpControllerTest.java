package it.angelo.MyCartellaClinicaElettronica.SignUpControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.angelo.MyCartellaClinicaElettronica.auth.controllers.LoginController;
import it.angelo.MyCartellaClinicaElettronica.auth.controllers.SignupController;
import it.angelo.MyCartellaClinicaElettronica.auth.entities.SignupDTO;
import it.angelo.MyCartellaClinicaElettronica.auth.entities.SignupDoctorDTO;
import it.angelo.MyCartellaClinicaElettronica.user.entities.EnumMedicalSpecializzation;
import it.angelo.MyCartellaClinicaElettronica.user.entities.EnumPlaceOfWork;
import it.angelo.MyCartellaClinicaElettronica.user.entities.Role;
import it.angelo.MyCartellaClinicaElettronica.user.utils.Roles;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static it.angelo.MyCartellaClinicaElettronica.user.entities.EnumMedicalSpecializzation.CARDIOLOGY;
import static it.angelo.MyCartellaClinicaElettronica.user.entities.EnumPlaceOfWork.MILANO;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class SignUpControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SignupController signupController;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void signup() throws Exception{
        SignupDTO signupDTO = new SignupDTO(
                "Davide","Fazio",
                "drumprog93@gmail.com",
                "mypassword","Via verde","City","12345456",
                "Italiano","Gela", LocalDate.parse("1993-05-03"),
                "fzadvd93e03d960w","adfasfg");

        //https://reflectoring.io/spring-boot-web-controller-test/
        mockMvc.perform(post("/auth/signup", 42L)
                        .contentType("application/json")
                        .param("signUpDTO", "true")
                        .content(objectMapper.writeValueAsString(signupDTO)))
                        .andDo(print())
                        .andExpect(status().isOk());
    }

    @Test
    public void signupDoctor() throws Exception{
        //Creiamo una copia dell'oggetto che verrà trasformata in Json dal mockMVC
        SignupDoctorDTO signupDoctorDTO = new SignupDoctorDTO(
                "Furio", "Camillo",
                "camillone@gmail.com", "password",
                "via bianca","Milano","123456789",
                "Italiana","Milano",LocalDate.parse("1970-09-30"),
                "af6a4fa654f654","ad888ad",
                "1",
                CARDIOLOGY,
                MILANO);


        //specifichiamo l'emulazione di un PostMapping con perform(post.()) e specifichiamo URL e PathVariables
        mockMvc.perform(post("/auth/signup/doctor/{role}", Roles.DOCTOR)
                //qui stiamo dicendo che vogliamo trasformare l'oggetto in json
                        .contentType("application/json")
                //scriviamo i parametri in ingresso del metodo originale
                        .param("signupDoctorDTO", "true")
                //Convertiamo tutto in stringa con ObjectMapper
                        .content(objectMapper.writeValueAsString(signupDoctorDTO))).
                andDo(print())
                //asseriamo che lo status debba essere 200 OK per sancire che il test sia di esito positivo
                .andExpect(status().isOk());
    }
}
