package it.angelo.MyCartellaClinicaElettronica.user.services;

import it.angelo.MyCartellaClinicaElettronica.user.entities.User;
import it.angelo.MyCartellaClinicaElettronica.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    private UserRepository userRepository;

    public User pickDoctor() {
        Optional<User> doctor = userRepository.pickDoctor();
        if(doctor.isPresent()){
            return doctor.get();
        }else {
            //all rider are busy -> take the first available
            return userRepository.findAll(PageRequest.of(0,1)).toList().get(0);
        }
    }
}
