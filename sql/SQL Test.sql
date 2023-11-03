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

insert into tb_pilot (birth_date, name, nickname, team_id)
values
('1985-05-10', 'José Silva', 'Velozinho', 1),
('1990-12-15', 'Maria Santos', 'Turbinada', 2),
('1988-03-02', 'João Pereira', 'Fogueteiro', 3),
('1995-07-20', 'Ana Oliveira', 'Anita', 4),
('1982-09-08', 'Luiz Souza', 'Turbo Master', 5),
('1992-04-17', 'Cristina Lima', 'Raio X', 6),
('1987-11-12', 'Marcos Ferreira', 'Adrenalina', 7),
('1998-06-03', 'Sofia Costa', 'Relâmpago', 8),
('1991-01-29', 'Carlos Santos', 'Demolidor', 9),
('1986-10-07', 'Fernanda Rodrigues', 'Furia Nitro', 10),
('1993-09-14', 'Rafael Costa', 'Turbulento', 1),
('1986-06-25', 'Juliana Oliveira', 'Velocidade Pura', 2),
('1990-04-03', 'Antônio Santos', 'Flecha Dourada', 3),
('1988-02-11', 'Amanda Pereira', 'Turbocharged', 4),
('1994-07-06', 'Leonardo Silva', 'Velozão', 5),
('1985-12-19', 'Renata Souza', 'Tempestade Turbo', 6),
('1991-08-30', 'Eduardo Almeida', 'Meteoro', 7),
('1989-10-05', 'Camila Ribeiro', 'Ágil', 8),
('1996-03-22', 'Hugo Fonseca', 'Foguete Veloz', 9),
('1992-01-08', 'Larissa Martins', 'Velocista', 10);


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

insert into tb_championship (date_time, name)
values
(current_timestamp(), 'Arrancada Suprema'),
(current_timestamp(), 'Desafio das Pistas'),
(current_timestamp(), 'Copa Turbo Aceleração'),
(current_timestamp(), 'Grand Prix de Arrancada'),
(current_timestamp(), 'Corrida Turbocharge'),
(current_timestamp(), 'Chebet Oficial'),
(current_timestamp(), 'Grande Corrida Turbo'),
(current_timestamp(), 'Copa das Arrancadas Extremas'),
(current_timestamp(), 'Campeonato Velozes e Furiosos'),
(current_timestamp(), 'Armageddon 2023');
;

insert into tb_transaction (datetime, transaction_type, value, user_id)
values
(current_timestamp(), 0, 500, 1);