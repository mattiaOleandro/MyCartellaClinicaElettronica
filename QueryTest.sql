SELECT * FROM `appointment` 
JOIN `user`
WHERE `user`.id = `doctor_id`;

-- SELEZIONO GLI APPUNTAMENTI DI UNO SPECIFICO MEDICO IN UN GIORNO SPECIFICO
SELECT * FROM `appointment` WHERE DAY(`appointment_start`) = 15 AND `doctor_Id` = 1;

-- SELEZIONO GLI APPUNTAMENTI ENTRO UN RANGE DI DATE 
SELECT a.appointment_start, u.surname AS 'Doctor', up.surname AS 'Patient', a.`description` FROM `appointment` AS a
JOIN `user` AS u ON u.id = a.doctor_id
JOIN `user` AS up ON up.id = a.patient_id
WHERE `appointment_start` BETWEEN '2022-10-14T09:30:00' AND '2022-10-16T10:30:00';