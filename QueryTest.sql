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
