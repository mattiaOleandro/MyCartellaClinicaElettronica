SELECT HOUR(appointment_start), MINUTE(appointment_start) 
FROM appointment
WHERE (SELECT HOUR(appointment_start)) = 10;

SELECT * FROM `appointment` 
JOIN `user`
WHERE `user`.id = `doctor_id`;

-- SELEZIONO GLI APPUNTAMENTI DI UNO SPECIFICO MEDICO IN UN GIORNO SPECIFICO
SELECT * FROM `appointment` WHERE DAY(`appointment_start`) = 15 AND `doctor_Id` = 1;

-- SELEZIONO GLI APPUNTAMENTI ENTRO UN RANGE DI DATE 
SELECT a.appointment_start, u.surname AS 'Doctor', up.surname AS 'Patient', a.`description` FROM `appointment` AS a
JOIN `user` AS u ON u.id = a.doctor_id
JOIN `user` AS up ON up.id = a.patient_id
WHERE `appointment_start` BETWEEN '2022-10-14T09:30:00' AND '2022-10-17T10:30:00';

SELECT * FROM `medical_record`;

UPDATE `medical_record` 
SET `description` = 'descrizione della cartella clinica HEIDI 2afe',`patient_history` = 'Storia del paziente HEIDI afwa'
WHERE `medical_record`.id = 95;

SELECT s.id AS "id schema del giorno", s.scheme_of_the_day, a.id, a.time_slot, a.scheme_of_the_day_id FROM `scheme_of_the_day` AS s
JOIN `appointment` AS a ON a.scheme_of_the_day_id = s.id
WHERE s.id = 136;

-- findAllDate
SELECT cd.`day` FROM `calendar_day` AS cd;

-- findOneDate
SELECT cd.`day` FROM `calendar_day` AS cd
WHERE cd.id = 317;

-- findTimeSlotFromDate
SELECT cd.`day` FROM calendar_day AS cd
WHERE cd.`day` = '2022-09-18';

-- update calendar_day and set timeSlot to true where calendar_day is equal to date
UPDATE calendar_day AS cd
SET time_slot8is_available = FALSE
WHERE cd.`day` = '2022-09-17';

-- seleziona tutto dalla tabella calendar_day
SELECT * FROM calendar_day AS cd;

-- seleziona l'id dalla tabella calendar_day dove la colonna day è uguale alla data specificata
SELECT cd.id FROM calendar_day AS cd
WHERE cd.`day` = '2022-10-10';

-- set timeSLot
UPDATE appointment AS a
SET calendar_day_id = (SELECT cd.id FROM calendar_day AS cd
WHERE cd.`day` = '2022-12-25')
WHERE a.appointment_date = '2022-12-25' AND (SELECT HOUR(appointment_start)) = 18;

-- seleziona tutto da appointment dove a.appointment_date = date e a.time_slot = time_slot
SELECT * FROM appointment AS a
WHERE a.appointment_date = '2022-12-25' AND (SELECT HOUR(appointment_start)) = 18;


