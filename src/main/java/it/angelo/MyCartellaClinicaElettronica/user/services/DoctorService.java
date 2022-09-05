package it.angelo.MyCartellaClinicaElettronica.user.services;

import it.angelo.MyCartellaClinicaElettronica.auth.services.SignupService;
import it.angelo.MyCartellaClinicaElettronica.user.entities.User;
import it.angelo.MyCartellaClinicaElettronica.user.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    private UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(DoctorService.class);
    int lineGetter = new Exception().getStackTrace()[0].getLineNumber();

    public User pickDoctor() {
        logger.info("Public method 'pickDoctor' method called at "+ DoctorService.class +" at line#" + lineGetter);
        Optional<User> doctor = userRepository.pickDoctor();
        if(doctor.isPresent()){
            return doctor.get();
        }else {
            //all rider are busy -> take the first available
            return userRepository.findAll(PageRequest.of(0,1)).toList().get(0);
        }
    }
}
