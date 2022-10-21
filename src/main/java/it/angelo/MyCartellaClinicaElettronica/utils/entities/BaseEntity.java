package it.angelo.MyCartellaClinicaElettronica.utils.entities;

import it.angelo.MyCartellaClinicaElettronica.user.entities.User;
import lombok.Data;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * is a base entity that contains attribute for Appointment class and other
 * it is mapped as a superclass
 */
@Data
@MappedSuperclass //aggiungerà tutti gli attributi di BaseEntity a ogni classe che la estende(Appointment ad esempio)
public class BaseEntity {

    // sapremo quando è stato creato l'appuntamento, quando aggiornato, il creatore o chi l'ha modificato per ultimo
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne
    private User createdBy;

    @ManyToOne
    private User updatedBy;
}
