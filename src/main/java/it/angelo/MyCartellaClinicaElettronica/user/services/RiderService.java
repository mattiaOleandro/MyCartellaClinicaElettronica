package it.angelo.MyCartellaClinicaElettronica.user.services;

import it.angelo.MyCartellaClinicaElettronica.user.entities.User;
import it.angelo.MyCartellaClinicaElettronica.user.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RiderService {

    @Autowired
    private UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(RiderService.class);
    int lineGetter = new Exception().getStackTrace()[0].getLineNumber();

    public User pickRider() {
        logger.info("Public method 'pickRider' method called at "+ RiderService.class +" at line#" + lineGetter);
        Optional<User> rider = userRepository.pickRider();
        if(rider.isPresent()){
            return rider.get();
        }else {
            //all rider are busy -> take the first available
            return userRepository.findAll(PageRequest.of(0,1)).toList().get(0);
        }
    }
}
