insert into users (username, password, name, surname, enabled, last_password_reset_date, role) values ('house.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Doctor', 'House', true, '2022-08-16 21:58:58.508-07', 'doctor');
insert into users (username, password, name, surname, enabled, last_password_reset_date, role) values ('drmd.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','DrMr', 'MrDr', true, '2022-08-16 21:58:58.508-07', 'doctor');
/*Nurses*/
insert into users (username, password, name, surname, enabled, last_password_reset_date, role) values ('nurse.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'nurse', 'nursic', true, '2022-08-16 21:58:58.508-07', 'nurse');
insert into users (username, password, name, surname, enabled, last_password_reset_date, role) values ('druga.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'druga', 'drugic', true, '2022-08-16 21:58:58.508-07', 'nurse');
/*Clinical Administrators*/
insert into users (username, password, name, surname, enabled, last_password_reset_date, role) values ('blanusa.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '12313131', '32131313', true, '2022-08-16 21:58:58.508-07', 'clinical_admin');
/*Clinical Center Administrator*/
insert into users (username, password, name, surname, enabled, last_password_reset_date, role) values ('dusan.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '12313131', '32131313', true, '2022-08-16 21:58:58.508-07', 'clinical_center_admin');
/*Patients*/
insert into users (username, password, name, surname, enabled, last_password_reset_date, role) values ('novi.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Zika', 'Haralampijevic', true, '2022-08-16 21:58:58.508-07', 'patient');
insert into users (username, password, name, surname, enabled, last_password_reset_date, role) values ('prihvaceni.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Prihvaceni', '32131313', true, '2022-08-16 21:58:58.508-07', 'patient');
insert into users (username, password, name, surname, enabled, last_password_reset_date, role) values ('peraperic.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Pera', 'Peric', true, '2022-08-16 21:58:58.508-07', 'patient');

insert into authority (name) values ('ROLE_DOCTOR');
insert into authority (name) values ('ROLE_NURSE');
insert into authority (name) values ('ROLE_CLINICAL_ADMIN');
insert into authority (name) values ('ROLE_CLINICAL_CENTER_ADMIN');
insert into authority (name) values ('ROLE_PATIENT');

insert into user_authority (user_id, authority_id) values (1, 1);
insert into user_authority (user_id, authority_id) values (2, 1);
insert into user_authority (user_id, authority_id) values (3, 2);
insert into user_authority (user_id, authority_id) values (4, 2);
insert into user_authority (user_id, authority_id) values (5, 3);
insert into user_authority (user_id, authority_id) values (6, 4);
insert into user_authority (user_id, authority_id) values (7, 5);
insert into user_authority (user_id, authority_id) values (8, 5);
insert into user_authority (user_id, authority_id) values (9, 5);

insert into clinic (name, adress, description, grade_sum, grade_number, longitude, latitude) values ('Urologija','blabla', 'opis', 0.0, 0, 19.821, 45.014);
insert into clinic (name, adress, description, grade_sum, grade_number, longitude, latitude) values ('Infektivna klinika','JNA', 'klinika za infektivne i tropske bolesti', 0.0, 0, 19.823, 45.25);

insert into doctor(name, surname, email, password, adress, city, state, phone, work_hour_start, work_hour_finish,grade_sum, grade_number ,clinic_id) values ('DrMr', 'MrDr','drmd.com', '123', 'NTP', 'Nju Nau', 'Makedonija', 333, 7, 15,0,0, 1);
insert into doctor(name, surname, email, password, adress, city, state, phone, work_hour_start, work_hour_finish, clinic_id,grade_sum, grade_number) values ('Doctor', 'House','house.com', '123', 'plainsborough', 'make', 'USA', 988, 7, 15, 2,0,0);

insert into diagnosis(name) values ('Hipertenzija');

insert into clinical_center_administrator (name, surname, email, password, adress, city, state, phone, validated) values ('12313131', '32131313', 'dusan.com', '123', '1232131321', '3213123123', '1231231231', 111222333, true);

insert into medical_room (operational, reserved, room_codename, room_number, date, clinic_id) values (true, false, 'Soba333', 15, '1999-05-05', 1);
insert into medical_room (operational, reserved, room_codename, room_number, date, clinic_id) values (true, true, 'Soba mala', 18, '2000-05-05', 1);
insert into medical_room (operational, reserved, room_codename, room_number, date, clinic_id) values (false, false, 'Laboratorija', 21, '2010-05-05', 1);

insert into patient (name, surname, email, password, adress, city, state, phone, lbo, validated, accepted) values ('Zika', 'Haralampijevic', 'novi.com', '123', 'Bulevar Oslobodjenja 11', 'Novi Sad', 'Serbia', 111222333, 111, false, false);
insert into patient (name, surname, email, password, adress, city, state, phone, lbo, validated, accepted) values ('Prihvaceni', '32131313', 'prihvaceni.com', '123', '1232131321', '3213123123', '1231231231', 111222333, 111, true, true);
insert into patient (name, surname, email, password, adress, city, state, phone, lbo, validated, accepted) values ('Pera', 'Peric', 'peraperic.com', '123', 'Zrenjanin', 'Zarka Zrenjanina', 'Serbia', 111222333, 111, false, false);

insert into nurse(name, surname, email, password, adress, city, state, phone) values ('nurse', 'nursic','nurse.com', '123', 'NTP', 'Nju Nau', 'Makedonija', 333);
insert into nurse(name, surname, email, password, adress, city, state, phone) values ('druga', 'drugic','druga.com', '123', 'NTP', 'Nju Nau', 'Makedonija', 333);

insert into prescription (name, validated) values ('Bromazepam',false);
insert into prescription (name, validated) values ('Fervex',true);
insert into prescription (name, validated) values ('Loperamid',false);

insert into examination_type(typename) values ('Ocni pregled');
insert into examination_type(typename) values ('Ginekoloski pregled');

insert into examination (date, price, type_id, clinic_id, doctor_id, start_time, end_time, operation, accepted, patient_id) values ('2020-02-01', 1500, 1, 1, 1, 420, 480, true, true, 1);
insert into examination (date, price, type_id, clinic_id, doctor_id, start_time, end_time, operation, accepted, patient_id) values ('2020-02-01', 1500, 1, 1, 1, 420, 480, true, true, 1);
insert into examination (date, price, type_id, clinic_id, doctor_id, start_time, end_time, operation, accepted, patient_id) values ('2020-02-01', 1500, 1, 1, 1, 420, 480, true, true, 1);
insert into examination (date, price, type_id, clinic_id, doctor_id, start_time, end_time, operation, accepted, patient_id) values ('2020-02-01', 1500, 1, 1, 1, 420, 480, true, true, 1);
insert into examination (date, price, type_id, clinic_id, doctor_id, start_time, end_time, operation, accepted, patient_id) values ('2020-02-01', 1500, 1, 1, 1, 420, 480, true, true, 1);
insert into examination (date, price, type_id, clinic_id, doctor_id, start_time, end_time, operation, accepted, patient_id) values ('2020-02-01', 1500, 1, 1, 1, 420, 480, true, true, 1);
insert into examination (date, price, type_id, clinic_id, doctor_id, start_time, end_time, operation, accepted, patient_id) values ('2020-02-01', 1500, 1, 1, 1, 420, 480, true, true, 1);
insert into examination (date, price, type_id, clinic_id, doctor_id, start_time, end_time, operation, accepted, patient_id) values ('2020-02-01', 1500, 1, 1, 1, 420, 480, true, true, 1);
insert into examination (date, price, type_id, clinic_id, doctor_id, start_time, end_time, operation, accepted, patient_id) values ('2020-02-01', 1500, 1, 1, 1, 420, 480, true, true, 1);
insert into examination (date, price, type_id, clinic_id, doctor_id, start_time, end_time, operation, accepted, patient_id) values ('2020-02-01', 1500, 1, 1, 1, 420, 480, true, true, 1);
insert into examination (date, price, type_id, clinic_id, doctor_id, start_time, end_time, operation, accepted, patient_id) values ('2020-02-01', 1500, 1, 1, 1, 420, 480, true, true, 1);
insert into examination (date, price, type_id, clinic_id, doctor_id, start_time, end_time, operation, accepted, patient_id) values ('2020-02-01', 1500, 1, 1, 1, 420, 480, true, true, 1);
insert into examination (date, price, type_id, clinic_id, doctor_id, start_time, end_time, operation, accepted, patient_id) values ('2020-02-01', 1500, 1, 1, 1, 420, 480, true, true, 1);
insert into examination (date, price, type_id, clinic_id, doctor_id, start_time, end_time, operation, accepted, patient_id) values ('2020-02-01', 1500, 1, 1, 1, 420, 480, true, true, 1);
insert into examination (date, price, type_id, clinic_id, doctor_id, start_time, end_time, operation, accepted, patient_id) values ('2020-02-01', 1500, 1, 1, 1, 420, 480, true, true, 1);
insert into examination (date, price, type_id, clinic_id, doctor_id, start_time, end_time, operation, accepted, patient_id) values ('2020-02-01', 1500, 1, 1, 1, 420, 480, true, true, 1);
insert into examination (date, price, type_id, clinic_id, doctor_id, start_time, end_time, operation, accepted, patient_id) values ('2020-02-01', 1500, 1, 1, 1, 420, 480, true, true, 1);
insert into examination (date, price, type_id, clinic_id, doctor_id, start_time, end_time, operation, accepted, patient_id) values ('2020-02-01', 1500, 1, 1, 1, 420, 480, true, true, 1);
insert into examination (date, price, type_id, clinic_id, doctor_id, start_time, end_time, operation, accepted, patient_id) values ('2020-02-01', 1500, 1, 1, 1, 420, 480, true, true, 1);
insert into examination (date, price, type_id, clinic_id, doctor_id, start_time, end_time, operation, accepted, patient_id) values ('2020-02-01', 1500, 1, 1, 1, 420, 480, true, true, 1);
insert into examination (date, price, type_id, clinic_id, doctor_id, start_time, end_time, operation, accepted, patient_id) values ('2020-02-01', 1500, 1, 1, 1, 420, 480, true, true, 1);
insert into examination (date, price, type_id, clinic_id, doctor_id, start_time, end_time, operation, accepted, patient_id) values ('2020-02-01', 1500, 1, 1, 1, 420, 480, true, true, 1);
insert into examination (date, price, type_id, clinic_id, doctor_id, start_time, end_time, operation, accepted, patient_id) values ('2020-02-01', 1500, 1, 1, 1, 420, 480, true, true, 1);
insert into examination (date, price, type_id, clinic_id, doctor_id, start_time, end_time, operation, accepted, patient_id) values ('2020-02-01', 1500, 1, 1, 1, 420, 480, true, true, 1);
insert into examination (date, price, type_id, clinic_id, doctor_id, start_time, end_time, operation, accepted, patient_id) values ('2020-02-01', 1500, 1, 1, 1, 420, 480, true, true, 1);
insert into examination (date, price, type_id, clinic_id, doctor_id, start_time, end_time, operation, accepted, patient_id) values ('2020-02-01', 1500, 1, 1, 1, 420, 480, true, true, 1);
insert into examination (date, price, type_id, clinic_id, doctor_id, start_time, end_time, operation, accepted, patient_id) values ('2020-02-01', 1500, 1, 1, 1, 420, 480, true, true, 1);
insert into examination (date, price, type_id, clinic_id, doctor_id, start_time, end_time, operation, accepted, patient_id) values ('2020-02-01', 1500, 1, 1, 1, 420, 480, true, true, 1);
insert into examination (date, price, type_id, clinic_id, doctor_id, start_time, end_time, operation, accepted, patient_id) values ('2020-02-01', 1500, 1, 1, 1, 420, 480, true, true, 1);
insert into examination (date, price, type_id, clinic_id, doctor_id, start_time, end_time, operation, accepted, patient_id) values ('2020-02-01', 1500, 1, 1, 1, 420, 480, true, true, 1);
insert into examination (date, price, type_id, clinic_id, doctor_id, start_time, end_time, operation, accepted, patient_id) values ('2020-02-01', 1500, 1, 1, 1, 420, 480, true, true, 1);
insert into examination (date, price, type_id, clinic_id, doctor_id, start_time, end_time, operation, accepted, patient_id) values ('2020-02-01', 1500, 1, 1, 1, 420, 480, true, true, 1);
insert into examination (date, price, type_id, clinic_id, doctor_id, start_time, end_time, operation, accepted, patient_id) values ('2020-02-01', 1500, 1, 1, 1, 420, 480, true, true, 1);
insert into examination (date, price, type_id, clinic_id, doctor_id, start_time, end_time, operation, accepted, patient_id) values ('2020-02-01', 1500, 1, 1, 1, 420, 480, true, true, 1);
insert into examination (date, price, type_id, clinic_id, doctor_id, start_time, end_time, operation, accepted) values ('2020-02-01', 1500, 1, 1, 1, 420, 480, true, true);
insert into examination (date, price, type_id, clinic_id, doctor_id, start_time, end_time, operation, accepted) values ('2020-02-03', 1500, 1, 1, 1, 420, 480, true, true);

insert into absence_request(startingdate, endingdate, doctor_id) values ('2019-05-05', '2019-09-09', 1);
insert into absence_request(startingdate, endingdate, doctor_id) values ('2000-05-05', '2010-09-09', 2);

insert into clinical_administrator (name, surname, email, password, adress, city, state, phone, validated, clinic_id) values ('12313131', '32131313', 'blanusa.com', '123', '1232131321', '3213123123', '1231231231', 111222333, true, 1);

insert into examination_report(text_report, diagnosis_id, prescription_id, patient_id) values ('IzvestajLekara', 1,  1, 1);
