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

SELECT sotd.scheme_of_the_day FROM scheme_of_the_day AS sotd
WHERE sotd.scheme_of_the_day = '2022-10-17';

UPDATE scheme_of_the_day AS sotd
SET time_slot8is_available = TRUE
WHERE sotd.scheme_of_the_day = '2022-10-17';

SELECT * FROM scheme_of_the_day AS sotd;

SELECT sotd.id FROM scheme_of_the_day AS sotd
WHERE sotd.scheme_of_the_day = '2022-09-21';

UPDATE appointment AS a
SET scheme_of_the_day_id = (SELECT sotd.id FROM scheme_of_the_day AS sotd
WHERE sotd.scheme_of_the_day = '2022-09-26')
WHERE a.appointment_date = '2022-09-26';