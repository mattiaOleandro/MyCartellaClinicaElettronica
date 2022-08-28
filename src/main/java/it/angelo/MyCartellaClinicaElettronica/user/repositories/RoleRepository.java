package it.angelo.MyCartellaClinicaElettronica.user.repositories;

import it.angelo.MyCartellaClinicaElettronica.user.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * role interface, it will manage the roles in the database
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    //possiamo cercare i nomi attraverso i Ruoli
    Optional<Role> findByName(String name);
}
