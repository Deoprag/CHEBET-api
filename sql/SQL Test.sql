-- DROP DATABASE db_chebet;
CREATE DATABASE IF NOT EXISTS db_chebet;
USE db_chebet;

insert into tb_user (first_name, last_name, email, birth_date, cpf, gender, phone_number, password, role, active)
value
('Pedro',
'Rabelo',
'emailteste@gmail.com',
'2004-02-27',
'12345678910',
0,
'41999999999',
'123456',
0,
1);

insert into tb_team (name)
values
('Monsters'),
('Reptiles'),
('Vultures'),
('Redbull'),
('Drag Kings'),
('Nitro Chargers'),
('Speed Demons'),
('Rocket Racers'),
('Turbo Titans'),
('Adrenaline Rush');

INSERT INTO tb_pilot (birth_date, name, nickname, team_id, active)
VALUES
('1985-05-10', 'José Silva', 'Velozinho', 1, true),
('1990-12-15', 'Maria Santos', 'Turbinada', 2, true),
('1988-03-02', 'João Pereira', 'Fogueteiro', 3, true),
('1995-07-20', 'Ana Oliveira', 'Anita', 4, true),
('1982-09-08', 'Luiz Souza', 'Turbo Master', 5, true),
('1992-04-17', 'Cristina Lima', 'Raio X', 6, true),
('1987-11-12', 'Marcos Ferreira', 'Adrenalina', 7, true),
('1998-06-03', 'Sofia Costa', 'Relâmpago', 8, true),
('1991-01-29', 'Carlos Santos', 'Demolidor', 9, true),
('1986-10-07', 'Fernanda Rodrigues', 'Furia Nitro', 10, true),
('1993-09-14', 'Rafael Costa', 'Turbulento', 1, true),
('1986-06-25', 'Juliana Oliveira', 'Velocidade Pura', 2, true),
('1990-04-03', 'Antônio Santos', 'Flecha Dourada', 3, true),
('1988-02-11', 'Amanda Pereira', 'Turbocharged', 4, true),
('1994-07-06', 'Leonardo Silva', 'Velozão', 5, true),
('1985-12-19', 'Renata Souza', 'Tempestade Turbo', 6, true),
('1991-08-30', 'Eduardo Almeida', 'Meteoro', 7, true),
('1989-10-05', 'Camila Ribeiro', 'Ágil', 8, true),
('1996-03-22', 'Hugo Fonseca', 'Foguete Veloz', 9, true),
('1992-01-08', 'Larissa Martins', 'Velocista', 10, true);

insert into tb_preparer (name, nickname, team_id)
values
('Carlos Silva', 'Mestre das Modificações', 1),
('Fernanda Oliveira', 'Gênio da Preparação', 2),
('Ricardo Pereira', 'Magia no Motor', 3),
('Camilo Santos', 'Rei dos Ajustes', 4),
('Roberto Costa', 'Dr. Velocidade', 5),
('Julio Fernandes', 'Mestre das Turbinas', 6),
('Lucas Souza', 'Guru dos Motores', 7),
('Mariana Mendes', 'Mágico das Rodas', 8),
('João Pereira', 'Especialista em Acelerador', 9),
('Bruno Lima', 'Arrancada', 10);

insert into tb_car (color, model, nickname, year, pilot_id, preparer_id)
values
(0, 'Civic', 'Turbo Rocket', 2020, 1, 1),
(1, 'Mustang', 'Veloz Mustang', 2018, 2, 2),
(2, 'Golf GTI', 'Foguete Veloz', 2019, 3, 3),
(3, 'Corvette', 'Relâmpago Dourado', 2022, 4, 4),
(4, 'BMW M3', 'Turbocharged Beast', 2021, 5, 5),
(5, 'Audi R8', 'Raio X', 2020, 6, 6),
(6, 'Porsche 911', 'Adrenalina Suprema', 2019, 7, 7),
(7, 'Subaru WRX', 'Velocidade Extrema', 2022, 8, 8),
(8, 'Ford Focus RS', 'Demolidor Turbo', 2018, 9, 9),
(9, 'Chevrolet Camaro', 'Furia Nitro', 2021, 10, 10),
(5, 'Tesla Model S', 'EletroTurbo', 2022, 11, 1),
(6, 'Mazda RX-7', 'Rajada Rápida', 2020, 12, 2),
(7, 'Toyota Supra', 'Fúria Japonesa', 2019, 13, 3),
(8, 'Dodge Challenger', 'Trovoada Turbo', 2021, 14, 4),
(9, 'Mercedes-AMG GT', 'Turbo Elegante', 2020, 15, 5),
(0, 'Chevrolet Corvette Stingray', 'Vento Veloz', 2021, 16, 6),
(1, 'Nissan GT-R', 'Raios Rápidos', 2018, 17, 7),
(2, 'Ford Mustang Shelby GT500', 'Demolidor Mustang', 2019, 18, 8),
(3, 'Honda S2000', 'Foguete Japonês', 2022, 19, 9),
(4, 'Aston Martin Vantage', 'Velocidade Britânica', 2018, 20, 10);

insert into tb_championship (date_time, name)
values
(current_date(), 'Arrancada Suprema'),
(current_date(), 'Desafio das Pistas'),
(current_date(), 'Copa Turbo Aceleração'),
(current_date(), 'Grand Prix de Arrancada'),
(current_date(), 'Corrida Turbocharge'),
(current_date(), 'Chebet Oficial'),
(current_date(), 'Grande Corrida Turbo'),
(current_date(), 'Copa das Arrancadas Extremas'),
(current_date(), 'Campeonato Velozes e Furiosos'),
(current_date(), 'Armageddon 2023');

insert into tb_transaction (datetime, transaction_type, value, user_id)
values
(current_timestamp(), 0, 500, 1);