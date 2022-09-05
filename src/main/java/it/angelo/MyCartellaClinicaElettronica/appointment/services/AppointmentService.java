package it.angelo.MyCartellaClinicaElettronica.appointment.services;

import it.angelo.MyCartellaClinicaElettronica.appointment.entities.Appointment;
import it.angelo.MyCartellaClinicaElettronica.appointment.entities.AppointmentDTO;
import it.angelo.MyCartellaClinicaElettronica.appointment.entities.SchemeOfTheDay;
import it.angelo.MyCartellaClinicaElettronica.appointment.entities.TimeSlot;
import it.angelo.MyCartellaClinicaElettronica.appointment.repositories.AppointmentRepository;
import it.angelo.MyCartellaClinicaElettronica.appointment.repositories.SchemeOfTheDayRepository;
import it.angelo.MyCartellaClinicaElettronica.user.entities.User;
import it.angelo.MyCartellaClinicaElettronica.user.repositories.UserRepository;
import it.angelo.MyCartellaClinicaElettronica.user.utils.Roles;
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
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SchemeOfTheDayRepository schemeOfTheDayRepository;

    /**
     * save an appointment
     * @param appointmentInput is an appointmentDTO object
     * @return an appointment
     * @throws Exception a generic exception can be thrown
     */
    public Appointment save(AppointmentDTO appointmentInput)throws Exception{

        // rappresenta un utente autenticato, la gestione è demandata a JwtTokenFilter class
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Appointment appointment = null;
        /*
        if (TimeSlot.TIME_SLOT_18_00_19_00.equals(appointmentInput.getTimeSlot())){
            System.out.println("EQUALITY CONFIRMED");
        }*/

        List<Date> listOfDay = schemeOfTheDayRepository.findAllDate();
        System.out.println("LIST OF DAY");
        System.out.println(listOfDay);

        for (int i = 0; i < listOfDay.size(); i++) {
            Date date = listOfDay.get(i);
                if (LocalDate.from(appointmentInput.getAppointmentStart()).toString().equals(date.toString())) {
                    System.out.println("DATES ARE EQUALS ENTER INTO SWITCH!!!");
                    schemeOfTheDayRepository.findTimeSlotFromDate(date);
                    switch (appointmentInput.getTimeSlot()) {
                        case TIME_SLOT_08_00_09_00:
                            System.out.println("SLOT 1 - 08:00/09:00");
                            //schemeOfTheDayRepository.findTimeSlotFromDate(date);
                            System.out.println("Date from findTimeSlotFromDateBEFORE - " + date);
                            schemeOfTheDayRepository.updateTimeSlot1FromDate(date, Boolean.FALSE);
                            System.out.println("Date from findTimeSlotFromDateAFTER - " + date);
                            break;
                        case TIME_SLOT_09_00_10_00:
                            System.out.println("SLOT 2 - 09:00/10:00");
                            //schemeOfTheDayRepository.findTimeSlotFromDate(date);
                            System.out.println("Date from findTimeSlotFromDateBEFORE - " + date);
                            schemeOfTheDayRepository.updateTimeSlot2FromDate(date, Boolean.FALSE);
                            System.out.println("Date from findTimeSlotFromDateAFTER - " + date);
                            break;
                        case TIME_SLOT_10_00_11_00:
                            System.out.println("SLOT 3 - 10:00/11:00");
                            //schemeOfTheDayRepository.findTimeSlotFromDate(date);
                            System.out.println("Date from findTimeSlotFromDateBEFORE - " + date);
                            schemeOfTheDayRepository.updateTimeSlot3FromDate(date, Boolean.FALSE);
                            System.out.println("Date from findTimeSlotFromDateAFTER - " + date);
                            break;
                        case TIME_SLOT_11_00_12_00:
                            System.out.println("SLOT 4 - 11:00/12:00");
                            //schemeOfTheDayRepository.findTimeSlotFromDate(date);
                            System.out.println("Date from findTimeSlotFromDateBEFORE - " + date);
                            schemeOfTheDayRepository.updateTimeSlot4FromDate(date, Boolean.FALSE);
                            System.out.println("Date from findTimeSlotFromDateAFTER - " + date);
                            break;
                        case TIME_SLOT_15_00_16_00:
                            System.out.println("SLOT 5 - 15:00/16:00");
                            //schemeOfTheDayRepository.findTimeSlotFromDate(date);
                            System.out.println("Date from findTimeSlotFromDateBEFORE - " + date);
                            schemeOfTheDayRepository.updateTimeSlot5FromDate(date, Boolean.FALSE);
                            System.out.println("Date from findTimeSlotFromDateAFTER - " + date);
                            break;
                        case TIME_SLOT_16_00_17_00:
                            System.out.println("SLOT 6 - 16:00/17:00");
                            //schemeOfTheDayRepository.findTimeSlotFromDate(date);
                            System.out.println("Date from findTimeSlotFromDateBEFORE - " + date);
                            schemeOfTheDayRepository.updateTimeSlot6FromDate(date, Boolean.FALSE);
                            System.out.println("Date from findTimeSlotFromDateAFTER - " + date);
                            break;
                        case TIME_SLOT_17_00_18_00:
                            System.out.println("SLOT 7 - 17:00/18:00");
                            //schemeOfTheDayRepository.findTimeSlotFromDate(date);
                            System.out.println("Date from findTimeSlotFromDateBEFORE - " + date);
                            schemeOfTheDayRepository.updateTimeSlot7FromDate(date, Boolean.FALSE);
                            System.out.println("Date from findTimeSlotFromDateAFTER - " + date);
                            break;
                        case TIME_SLOT_18_00_19_00:
                            System.out.println("SLOT 8 - 18:00/19:00");
                            //schemeOfTheDayRepository.findTimeSlotFromDate(date);
                            System.out.println("Date from findTimeSlotFromDateBEFORE - " + date);
                            schemeOfTheDayRepository.updateTimeSlot8FromDate(date, Boolean.FALSE);
                            System.out.println("Date from findTimeSlotFromDateAFTER - " + date);
                            break;
                    }
                    break;
                }else {
                    System.out.println("DATES NOT ARE EQUALS!!!");
                    System.out.println("<- COMPARE DATES ->");
                    System.out.println("DATE FROM DATABASE: " + date);
                    System.out.println("DATE FROM FRONT-END: " + LocalDate.from(appointmentInput.getAppointmentStart()));
                    System.out.println("-------------------OK-OK---------------");

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
                    if(appointmentInput.getPatient() == null) throw new Exception("Patient not found");
                    Optional<User> patient = userRepository.findById(appointmentInput.getPatient());
                    if(!patient.isPresent() || !Roles.hasRole(patient.get(), Roles.PATIENT)) throw new Exception("Patient not found");
                    appointment.setPatient(patient.get());

                    SchemeOfTheDay schemeOfTheDay = new SchemeOfTheDay();
                    schemeOfTheDay.setTimeSlot8IsAvailable(false);
                    schemeOfTheDay.setSchemeOfTheDay(appointment.getAppointmentDate());
                    appointment.setSchemeOfTheDay(schemeOfTheDay);
                    schemeOfTheDayRepository.save(schemeOfTheDay);
                    System.out.println("SAVED");
                }
            }
        if (appointment != null) {
            return appointmentRepository.save(appointment);
        }
        return appointment;
    }


    public Appointment update(Long id, Appointment appointmentInput){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (appointmentInput == null)return null;
        appointmentInput.setId(id);
        appointmentInput.setUpdatedAt(LocalDateTime.now());
        appointmentInput.setUpdatedBy(user);
        return appointmentRepository.save(appointmentInput);
    }

    public boolean canEdit(Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        if(!appointment.isPresent())return false;
        return appointment.get().getCreatedBy().getId() == user.getId();
    }
}
