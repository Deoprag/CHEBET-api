DROP DATABASE db_chebet;
CREATE DATABASE db_chebet;
USE db_chebet;

insert into tb_user (first_name, last_name, email, birth_date, cpf, gender, phone_number, password, active)
value
('Pedro',
'Rabelo',
'emailteste@gmail.com',
'2004-02-27',
'12345678910',
0,
'41999999999',
'akNVEOAIVowfjq9523ufwg7aafhAJGBEBiqr128512',
1);

insert into tb_team (name) 
values
('Equipe 01');

insert into tb_pilot (birth_date, name, nickname, team_id)
values
('2004-02-27', 'Pedro', 'Apelido', 1);

insert into tb_preparer (name, nickname)
values
('Pedro', 'Apelido');

insert into tb_championship (date_time, name)
values
(current_timestamp(), 'Campeonato 1');

insert into tb_transaction (datetime, transaction_type, value, user_id)
values
(current_timestamp(), 0, 500, 1);