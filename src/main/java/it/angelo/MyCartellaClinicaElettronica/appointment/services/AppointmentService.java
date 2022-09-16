package it.angelo.MyCartellaClinicaElettronica.appointment.services;

import it.angelo.MyCartellaClinicaElettronica.appointment.entities.Appointment;
import it.angelo.MyCartellaClinicaElettronica.appointment.entities.AppointmentDTO;
import it.angelo.MyCartellaClinicaElettronica.appointment.entities.CalendarDay;
import it.angelo.MyCartellaClinicaElettronica.appointment.entities.TimeSlot;
import it.angelo.MyCartellaClinicaElettronica.appointment.repositories.AppointmentRepository;
import it.angelo.MyCartellaClinicaElettronica.appointment.repositories.CalendarDayRepository;
import it.angelo.MyCartellaClinicaElettronica.user.entities.User;
import it.angelo.MyCartellaClinicaElettronica.user.repositories.UserRepository;
import it.angelo.MyCartellaClinicaElettronica.user.utils.Roles;
import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@ToString
@Service
@Data
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CalendarDayRepository calendarDayRepository;

    private boolean free = false;

    /**
     * save an appointment
     *
     * @param appointmentInput is an appointmentDTO object
     * @return an appointment
     * @throws Exception a generic exception can be thrown
     */
    public Appointment save(AppointmentDTO appointmentInput) throws Exception {

        // rappresenta un utente autenticato, la gestione è demandata a JwtTokenFilter class
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Appointment appointment = null;
        int timeSlotSetting = 0;

        //Lista dei giorni presenti in tabella. Utilizziamo il metodo findByDate
        List<Date> listOfDay = calendarDayRepository.findAllDate();
        System.out.println("LIST OF DAY");
        System.out.println(listOfDay);
        System.out.println(LocalDate.from(appointmentInput.getAppointmentStart()).toString());

        //Scorriamo la lista dei giorni
        for (int i = 0; i < listOfDay.size(); i++) {
            Date date = listOfDay.get(i);
            //Se appointmentStart(che mi viene passato da AppointmentDTO) è uguale alla data presente in lista
            if (LocalDate.from(appointmentInput.getAppointmentStart()).toString().equals(date.toString())) {
                System.out.println("DATES ARE EQUALS!!!");
                System.out.println("<- COMPARE DATES ->");
                System.out.println("DATE FROM DATABASE: " + date);
                System.out.println("DATE FROM FRONT-END: " + LocalDate.from(appointmentInput.getAppointmentStart()));
                System.out.println("-------------------OK-OK---------------");
                System.out.println("CHECK SLOT");
                //Controlla se lo slot selezionato è fià occupato
                if(appointmentInput.getTimeSlot().equals(TimeSlot.TIME_SLOT_08_00_09_00) && !calendarDayRepository.findTimeSlot1FromDate(date)){
                    System.out.println("SOLT 1 IS EMPTY");
                    free = true;
                    break;
                }else if(appointmentInput.getTimeSlot().equals(TimeSlot.TIME_SLOT_09_00_10_00) && !calendarDayRepository.findTimeSlot2FromDate(date)){
                    System.out.println("SOLT 2 IS EMPTY");
                    free = true;
                    break;
                }else if(appointmentInput.getTimeSlot().equals(TimeSlot.TIME_SLOT_10_00_11_00) && !calendarDayRepository.findTimeSlot3FromDate(date)){
                    System.out.println("SOLT 3 IS EMPTY");
                    free = true;
                    break;
                }else if(appointmentInput.getTimeSlot().equals(TimeSlot.TIME_SLOT_11_00_12_00) && !calendarDayRepository.findTimeSlot4FromDate(date)){
                    System.out.println("SOLT 4 IS EMPTY");
                    free = true;
                    break;
                }else if(appointmentInput.getTimeSlot().equals(TimeSlot.TIME_SLOT_15_00_16_00) && !calendarDayRepository.findTimeSlot5FromDate(date)){
                    System.out.println("SOLT 5 IS EMPTY");
                    free = true;
                    break;
                }else if(appointmentInput.getTimeSlot().equals(TimeSlot.TIME_SLOT_16_00_17_00) && !calendarDayRepository.findTimeSlot6FromDate(date)){
                    System.out.println("SOLT 6 IS EMPTY");
                    free = true;
                    break;
                }else if(appointmentInput.getTimeSlot().equals(TimeSlot.TIME_SLOT_17_00_18_00) && !calendarDayRepository.findTimeSlot7FromDate(date)){
                    System.out.println("SOLT 7 IS EMPTY");
                    free = true;
                    break;
                }else if(appointmentInput.getTimeSlot().equals(TimeSlot.TIME_SLOT_18_00_19_00) && !calendarDayRepository.findTimeSlot8FromDate(date)){
                    System.out.println("SOLT 8 IS EMPTY");
                    free = true;
                    break;
                }else{
                    free = false;
                    System.out.println("DATES ARE EQUALS ENTER INTO SWITCH!!!");
                    //Estraggo il timeSlot tramite la data match-ata nel ciclo if
                    calendarDayRepository.findTimeSlotFromDate(date);
                    //verifico in quale casistica ricade il timeSlot estratto
                    switch (appointmentInput.getTimeSlot()) {
                        case TIME_SLOT_08_00_09_00:
                            System.out.println("SLOT 1 - 08:00/09:00");
                            System.out.println("Date from findTimeSlotFromDateBEFORE - " + date);
                            //setto il timeSlot su falso
                            calendarDayRepository.updateTimeSlot1FromDate(date, Boolean.FALSE);
                            System.out.println("Date from findTimeSlotFromDateAFTER - " + date);
                            timeSlotSetting = 1;
                            break;
                        case TIME_SLOT_09_00_10_00:
                            System.out.println("SLOT 2 - 09:00/10:00");
                            System.out.println("Date from findTimeSlotFromDateBEFORE - " + date);
                            calendarDayRepository.updateTimeSlot2FromDate(date, Boolean.FALSE);
                            System.out.println("Date from findTimeSlotFromDateAFTER - " + date);
                            timeSlotSetting = 2;
                            break;
                        case TIME_SLOT_10_00_11_00:
                            System.out.println("SLOT 3 - 10:00/11:00");
                            System.out.println("Date from findTimeSlotFromDateBEFORE - " + date);
                            calendarDayRepository.updateTimeSlot3FromDate(date, Boolean.FALSE);
                            System.out.println("Date from findTimeSlotFromDateAFTER - " + date);
                            timeSlotSetting = 3;
                            break;
                        case TIME_SLOT_11_00_12_00:
                            System.out.println("SLOT 4 - 11:00/12:00");
                            System.out.println("Date from findTimeSlotFromDateBEFORE - " + date);
                            calendarDayRepository.updateTimeSlot4FromDate(date, Boolean.FALSE);
                            System.out.println("Date from findTimeSlotFromDateAFTER - " + date);
                            timeSlotSetting = 4;
                            break;
                        case TIME_SLOT_15_00_16_00:
                            System.out.println("SLOT 5 - 15:00/16:00");
                            System.out.println("Date from findTimeSlotFromDateBEFORE - " + date);
                            calendarDayRepository.updateTimeSlot5FromDate(date, Boolean.FALSE);
                            System.out.println("Date from findTimeSlotFromDateAFTER - " + date);
                            timeSlotSetting = 5;
                            break;
                        case TIME_SLOT_16_00_17_00:
                            System.out.println("SLOT 6 - 16:00/17:00");
                            System.out.println("Date from findTimeSlotFromDateBEFORE - " + date);
                            calendarDayRepository.updateTimeSlot6FromDate(date, Boolean.FALSE);
                            System.out.println("Date from findTimeSlotFromDateAFTER - " + date);
                            timeSlotSetting = 6;
                            break;
                        case TIME_SLOT_17_00_18_00:
                            System.out.println("SLOT 7 - 17:00/18:00");
                            System.out.println("Date from findTimeSlotFromDateBEFORE - " + date);
                            calendarDayRepository.updateTimeSlot7FromDate(date, Boolean.FALSE);
                            System.out.println("Date from findTimeSlotFromDateAFTER - " + date);
                            timeSlotSetting = 7;
                            break;
                        case TIME_SLOT_18_00_19_00:
                            System.out.println("SLOT 8 - 18:00/19:00");
                            System.out.println("Date from findTimeSlotFromDateBEFORE - " + date);
                            calendarDayRepository.updateTimeSlot8FromDate(date, Boolean.FALSE);
                            System.out.println("Date from findTimeSlotFromDateAFTER - " + date);
                            timeSlotSetting = 8;
                            break;
                    }
                    //creo un nuovo appuntamento
                    appointment = new Appointment();
                    appointment.setCreatedAt(LocalDateTime.now());
                    appointment.setCreatedBy(user);
                    appointment.setAddress(appointmentInput.getAddress());
                    appointment.setCity(appointmentInput.getCity());
                    appointment.setDescription(appointmentInput.getDescription());
                    appointment.setState(appointmentInput.getState());
                    appointment.setNumber(appointmentInput.getNumber());
                    appointment.setZipCode(appointmentInput.getZipCode());
                    appointment.setTimeSlot(appointmentInput.getTimeSlot());

                    appointment.setAppointmentStart(appointmentInput.getAppointmentStart());
                    appointment.setAppointmentEnd(appointmentInput.getAppointmentEnd());

                    appointment.setAppointmentDate((LocalDate.from(appointmentInput.getAppointmentStart())));

                    //inserisce la chiave esterna(calendar_day_id) nella tabella appointment
                    appointment.setCalendarDay(calendarDayRepository.findAllByDay(LocalDate.from(appointmentInput.getAppointmentStart())));

                    //check for patient
                    if (appointmentInput.getPatient() == null) throw new Exception("Patient not found");
                    Optional<User> patient = userRepository.findById(appointmentInput.getPatient());
                    if (!patient.isPresent() || !Roles.hasRole(patient.get(), Roles.PATIENT))
                        throw new Exception("Patient not found");
                    appointment.setPatient(patient.get());

                    break;
                }
            }else{
                //altrimenti, se in calendar_day non troviamo corrispondenza della data passata da front-end
                System.out.println("DATES NOT ARE EQUALS!!!");
                System.out.println("<- COMPARE DATES ->");
                System.out.println("DATE FROM DATABASE: " + date);
                System.out.println("DATE FROM FRONT-END: " + LocalDate.from(appointmentInput.getAppointmentStart()));
                System.out.println("-------------------OK-OK---------------");

                //se abbiamo finito di scorrere la lista
                if (listOfDay.size()-1 == i) {

                    System.out.println("CREATE NEW DATE APPOINTMENT");
                    //creiamo un nuovo appuntamento
                    appointment = new Appointment();
                    appointment.setCreatedAt(LocalDateTime.now());
                    appointment.setCreatedBy(user);
                    appointment.setAddress(appointmentInput.getAddress());
                    appointment.setCity(appointmentInput.getCity());
                    appointment.setDescription(appointmentInput.getDescription());
                    appointment.setState(appointmentInput.getState());
                    appointment.setNumber(appointmentInput.getNumber());
                    appointment.setZipCode(appointmentInput.getZipCode());
                    appointment.setTimeSlot(appointmentInput.getTimeSlot());

                    appointment.setAppointmentStart(appointmentInput.getAppointmentStart());
                    appointment.setAppointmentEnd(appointmentInput.getAppointmentEnd());

                    appointment.setAppointmentDate(LocalDate.from(appointmentInput.getAppointmentStart()));

                    //check for patient
                    if (appointmentInput.getPatient() == null) throw new Exception("Patient not found");
                    Optional<User> patient = userRepository.findById(appointmentInput.getPatient());
                    if (!patient.isPresent() || !Roles.hasRole(patient.get(), Roles.PATIENT))
                        throw new Exception("Patient not found");
                    appointment.setPatient(patient.get());

                    CalendarDay calendarDay = new CalendarDay();
                    calendarDay.setDay(appointment.getAppointmentDate());
                    calendarDay.setAppointment(calendarDay.getAppointment());
                    appointment.setCalendarDay(calendarDay);

                    //Seleziona lo slot dato in ingresso occupato
                    switch (appointmentInput.getTimeSlot()) {
                        case TIME_SLOT_08_00_09_00:
                            calendarDay.setTimeSlot1IsAvailable(false);
                            break;
                        case TIME_SLOT_09_00_10_00:
                            calendarDay.setTimeSlot2IsAvailable(false);
                            break;
                        case TIME_SLOT_10_00_11_00:
                            calendarDay.setTimeSlot3IsAvailable(false);
                            break;
                        case TIME_SLOT_11_00_12_00:
                            calendarDay.setTimeSlot4IsAvailable(false);
                            break;
                        case TIME_SLOT_15_00_16_00:
                            calendarDay.setTimeSlot5IsAvailable(false);
                            break;
                        case TIME_SLOT_16_00_17_00:
                            calendarDay.setTimeSlot6IsAvailable(false);
                            break;
                        case TIME_SLOT_17_00_18_00:
                            calendarDay.setTimeSlot7IsAvailable(false);
                            break;
                        case TIME_SLOT_18_00_19_00:
                            calendarDay.setTimeSlot8IsAvailable(false);
                            break;
                    }
                    calendarDayRepository.save(calendarDay);
                    System.out.println("-------------------SAVED---------------\n" +
                            "APPOINTMENT SAVED!!!\n" +
                            "<-    DATE    ->\n" +
                            "DATE FROM SAVED: " + LocalDate.from(appointmentInput.getAppointmentStart()) + "\n" +
                            "SLOT FROM SAVED: " + appointmentInput.getTimeSlot() + "\n" +
                            "-------------------SAVED---------------" );
                }
            }
        }
        if (appointment != null) {
            return appointmentRepository.save(appointment);
        }
        return appointment;
    }


    public Appointment update(Long id, Appointment appointmentInput) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (appointmentInput == null) return null;
        appointmentInput.setId(id);
        appointmentInput.setUpdatedAt(LocalDateTime.now());
        appointmentInput.setUpdatedBy(user);
        return appointmentRepository.save(appointmentInput);
    }

    public boolean canEdit(Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        if (!appointment.isPresent()) return false;
        return appointment.get().getCreatedBy().getId() == user.getId();
    }
}

