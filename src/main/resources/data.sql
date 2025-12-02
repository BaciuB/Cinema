INSERT INTO theatres (id, name, city) VALUES
                                          ('T1', 'Cinema City Iulius', 'Cluj-Napoca'),
                                          ('T2', 'Cinema Victoria', 'Cluj-Napoca'),
                                          ('T3', 'Cinema City Vivo', 'Cluj-Napoca'),
                                          ('T4', 'Cinema Arta', 'Cluj-Napoca'),
                                          ('T5', 'Cinema Florin Piersic', 'Cluj-Napoca'),
                                          ('T6', 'Cinema City AFI', 'București'),
                                          ('T7', 'Cinema Palace', 'Oradea'),
                                          ('T8', 'Cinema City Timișoara', 'Timișoara'),
                                          ('T9', 'Cinema One', 'Brașov'),
                                          ('T10', 'Cinema City Mega Mall', 'București');


INSERT INTO halls (id, name, theatre_id, capacity) VALUES
                                                       ('H1', 'Sala 1', 'T1', 50),
                                                       ('H2', 'Sala 2', 'T1', 40),
                                                       ('H3', 'Sala A', 'T2', 30),
                                                       ('H4', 'Sala B', 'T2', 20),
                                                       ('H5', 'Sala IMAX', 'T3', 120),
                                                       ('H6', 'Sala VIP', 'T3', 40),
                                                       ('H7', 'Sala Studio', 'T4', 35),
                                                       ('H8', 'Sala Mare', 'T5', 80),
                                                       ('H9', 'Sala Premium', 'T6', 100),
                                                       ('H10', 'Sala Family', 'T7', 60);


INSERT INTO movies (id, title, duration_minutes, release_date) VALUES
                                                                   ('M1', 'Inception', 148, '2010-07-16'),
                                                                   ('M2', 'Interstellar', 169, '2014-11-07'),
                                                                   ('M3', 'The Dark Knight', 152, '2008-07-18'),
                                                                   ('M4', 'Dune: Part Two', 165, '2024-03-01'),
                                                                   ('M5', 'Oppenheimer', 180, '2023-07-21'),
                                                                   ('M6', 'Avengers: Endgame', 181, '2019-04-26'),
                                                                   ('M7', 'Gladiator', 155, '2000-05-05'),
                                                                   ('M8', 'The Matrix', 136, '1999-03-31'),
                                                                   ('M9', 'Spider-Man: No Way Home', 148, '2021-12-17'),
                                                                   ('M10', 'Tenet', 150, '2020-08-26');

INSERT INTO customers (id, name, email) VALUES
                                            (1, 'Andrei Pop', 'andrei.pop@gmail.com'),
                                            (2, 'Maria Ionescu', 'maria.ionescu@gmail.com'),
                                            (3, 'Alex Toma', 'alex.toma@gmail.com'),
                                            (4, 'Bianca Pop', 'bianca.pop@gmail.com'),
                                            (5, 'George Matei', 'george.matei@gmail.com'),
                                            (6, 'Ioana Vlad', 'ioana.vlad@gmail.com'),
                                            (7, 'Radu Roman', 'radu.roman@gmail.com'),
                                            (8, 'Cristina Luca', 'cristina.luca@gmail.com'),
                                            (9, 'Darius Pavel', 'darius.pavel@gmail.com'),
                                            (10, 'Elena Marin', 'elena.marin@gmail.com');


INSERT INTO seats (id, hall_id, number_row, number_column) VALUES
                                                               ('S1',  'H1', 1, 1),
                                                               ('S2',  'H1', 1, 2),
                                                               ('S3',  'H1', 1, 3),
                                                               ('S4',  'H1', 2, 1),
                                                               ('S5',  'H1', 2, 2),
                                                               ('S6',  'H2', 1, 1),
                                                               ('S7',  'H2', 1, 2),
                                                               ('S8',  'H2', 2, 1),
                                                               ('S9',  'H3', 1, 1),
                                                               ('S10', 'H4', 1, 1);


INSERT INTO screenings (id, hall_id, movie_id, date_time) VALUES
                                                              ('SCR1',  'H1', 'M1', '2025-01-10'),
                                                              ('SCR2',  'H1', 'M2', '2025-01-11'),
                                                              ('SCR3',  'H1', 'M3', '2025-01-12'),
                                                              ('SCR4',  'H2', 'M1', '2025-01-13'),
                                                              ('SCR5',  'H2', 'M4', '2025-01-14'),
                                                              ('SCR6',  'H3', 'M2', '2025-01-15'),
                                                              ('SCR7',  'H3', 'M3', '2025-01-16'),
                                                              ('SCR8',  'H4', 'M1', '2025-01-17'),
                                                              ('SCR9',  'H4', 'M4', '2025-01-18'),
                                                              ('SCR10', 'H4', 'M5', '2025-01-19');


INSERT INTO staff (id, name, salary) VALUES
                                         ('ST1',  'Robert Security',        2500),
                                         ('ST2',  'Mihai Cleaner',          2300),
                                         ('ST3',  'Laura Usher',            2100),
                                         ('ST4',  'Ovidiu Projectionist',   3000),
                                         ('ST5',  'Alex Sound Operator',    3200),
                                         ('ST6',  'Daria Projectionist',    2900),
                                         ('ST7',  'Tudor Sound Operator',   3100),
                                         ('ST8',  'Silvia Cleaner',         2200),
                                         ('ST9',  'Rares Security',         2400),
                                         ('ST10', 'Bianca Usher',           2150);



INSERT INTO support_staff (id, role) VALUES
                                         ('ST1',  'SECURITY'),
                                         ('ST2',  'CLEANING'),
                                         ('ST3',  'USHER'),
                                         ('ST8',  'CLEANING'),
                                         ('ST9',  'SECURITY'),
                                         ('ST10', 'USHER');



INSERT INTO technical_operators (id, specialization) VALUES
  ('ST4', 'PROJECTION'),
  ('ST5', 'SOUND'),
  ('ST6', 'PROJECTION'),
  ('ST7', 'SOUND');



INSERT INTO tickets (id, screening_id, customer_id, seat_id, price) VALUES
  ('TCK1',  'SCR1',  1,  'S1',  15.0),
  ('TCK2',  'SCR2',  2,  'S2',  16.0),
  ('TCK3',  'SCR3',  3,  'S3',  17.0),
  ('TCK4',  'SCR4',  4,  'S4',  14.0),
  ('TCK5',  'SCR5',  5,  'S5',  18.0),
  ('TCK6',  'SCR6',  6,  'S6',  13.0),
  ('TCK7',  'SCR7',  7,  'S7',  16.5),
  ('TCK8',  'SCR8',  8,  'S8',  14.5),
  ('TCK9',  'SCR9',  9,  'S9',  12.0),
  ('TCK10', 'SCR10', 10, 'S10', 19.0);


INSERT INTO staff_assignments (id, screening_id, staff_id) VALUES
  ('A1',  'SCR1',  'ST1'),
  ('A2',  'SCR2',  'ST2'),
  ('A3',  'SCR3',  'ST3'),
  ('A4',  'SCR4',  'ST4'),
  ('A5',  'SCR5',  'ST5'),
  ('A6',  'SCR6',  'ST6'),
  ('A7',  'SCR7',  'ST7'),
  ('A8',  'SCR8',  'ST8'),
  ('A9',  'SCR9',  'ST9'),
  ('A10', 'SCR10', 'ST10');
