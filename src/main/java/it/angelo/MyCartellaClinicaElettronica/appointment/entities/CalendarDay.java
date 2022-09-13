package it.angelo.MyCartellaClinicaElettronica.appointment.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@DynamicUpdate
@Table(name="CALENDAR_DAY")
public class CalendarDay {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private LocalDate day;

    private boolean timeSlot1IsAvailable = true;// dalle 8:00 alle 9:00
    private boolean timeSlot2IsAvailable = true;// dalle 9:00 alle 10:00
    private boolean timeSlot3IsAvailable = true;// dalle 10:00 alle 11:00
    private boolean timeSlot4IsAvailable = true;// dalle 11:00 alle 12:00

    private boolean timeSlot5IsAvailable = true;// dalle 15:00 alle 16:00
    private boolean timeSlot6IsAvailable = true;// dalle 16:00 alle 17:00
    private boolean timeSlot7IsAvailable = true;// dalle 17:00 alle 18:00
    private boolean timeSlot8IsAvailable = true;// dalle 18:00 alle 19:00


    @OneToMany(mappedBy = "calendarDay")
    @JsonBackReference //necessario per evitare Infinite recursion (StackOverflowError)
    @JsonIgnore
    private Set<Appointment> appointment;

}
