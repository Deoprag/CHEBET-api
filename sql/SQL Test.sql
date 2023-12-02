-- DROP DATABASE db_chebet;
CREATE DATABASE IF NOT EXISTS db_chebet;
USE db_chebet;

DELIMITER //
CREATE PROCEDURE UpdateUserBalance(IN user_id_param INT)
BEGIN
    DECLARE newBalance FLOAT;

    -- Cálculo do novo saldo
    SET newBalance = (
        SELECT 
            COALESCE(SUM(CASE WHEN t.transaction_type IN (0, 3) THEN t.value ELSE 0 END), 0)
            - COALESCE(SUM(CASE WHEN t.transaction_type IN (1, 2) THEN t.value ELSE 0 END), 0)
        FROM tb_transaction t
        WHERE t.user_id = user_id_param
    );

    -- Verificar se o novo saldo é maior ou igual a 0
    IF newBalance >= 0 THEN
        -- Atualizar o campo balance apenas se o novo saldo não for negativo
        UPDATE tb_user
        SET balance = newBalance
        WHERE id = user_id_param;
    ELSE
        -- Se o novo saldo for negativo, você pode tomar alguma ação ou não fazer nada
        -- Neste exemplo, apenas um aviso é exibido
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Saldo não pode ser negativo';
    END IF;
END;
// DELIMITER ;

DELIMITER //
CREATE TRIGGER tr_after_insert_tb_transaction
AFTER INSERT
ON tb_transaction FOR EACH ROW
BEGIN
    CALL UpdateUserBalance(NEW.user_id);
END;
// DELIMITER ;

DELIMITER //
CREATE PROCEDURE SoftDeleteUser(
    IN p_user_id INT
)
BEGIN
    IF (SELECT balance FROM tb_user WHERE id = p_user_id) = 0 THEN
        UPDATE tb_user
        SET 
            first_name = CONCAT('deleted_', id),
            last_name = CONCAT('deleted_', id),
            email = CONCAT('deleted_', id),
            role = 2, 
            birth_date = '1900-01-01',
            cpf = CONCAT('deleted_', id),
            gender = 2, 
            phone_number = CONCAT('deleted_', id),
            password = CONCAT('deleted_', id),
            active = 0
        WHERE id = p_user_id;
    ELSE
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'A operação não pode ser realizada porque o saldo não é igual a 0.';
    END IF;
END 
// DELIMITER ;

insert into tb_user (first_name, last_name, email, birth_date, cpf, gender, phone_number, password, role, active, balance)
value
('Pedro',
'Rabelo',
'pdroesofiarabelo@gmail.com',
'2004-02-27',
'14848328683',
0,
'41999999999',
'$2a$10$YV1aVK1lKNVeQ0eBschXr.Qs9OBrWvWez9Sv36TiXvmwsLR/KjYr.',
0,
1,
0);

insert into tb_user (first_name, last_name, email, birth_date, cpf, gender, phone_number, password, role, active, balance)
value
('Pedro',
'Rabelo',
'pdroeofiarabelo@gmail.com',
'2004-02-27',
'14848428683',
0,
'41999299999',
'$2a$10$YV1aVK1lKNVeQ0eBschXr.Qs9OBrWvWez9Sv36TiXvmwsLR/KjYr.',
0,
1,
0);

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
('1992-01-08', 'Larissa Martins', 'Velocista', 10, true),
('1987-02-28', 'Pedro Lima', 'Propulsor', 1, true),
('1993-11-11', 'Mariana Costa', 'Turbo Girl', 2, true),
('1984-08-15', 'André Oliveira', 'Relâmpago Azul', 3, true),
('1997-07-25', 'Carolina Santos', 'Furacão', 4, true),
('1990-05-01', 'Rodrigo Pereira', 'Turbo Blast', 5, true),
('1986-12-10', 'Isabela Souza', 'Rajada Veloz', 6, true),
('1995-09-05', 'Gabriel Ferreira', 'Sprint Master', 7, true),
('1998-04-02', 'Tatiane Costa', 'Velociraptor', 8, true),
('1989-03-18', 'Lucas Rodrigues', 'Rápido e Furioso', 9, true),
('1994-01-14', 'Patrícia Almeida', 'Turbo Princess', 10, true),
('1983-06-20', 'Fernando Ribeiro', 'Vortex', 1, true),
('1991-04-08', 'Aline Fonseca', 'Ventania Veloz', 2, true),
('1987-12-03', 'Gustavo Martins', 'Fórmula Veloz', 3, true),
('1996-10-28', 'Laura Lima', 'Turbo Luminoso', 4, true),
('1985-03-16', 'José Oliveira', 'Furia Turbo', 5, true),
('1993-09-22', 'Mariana Pereira', 'Turbocharge', 6, true),
('1988-05-11', 'Thiago Silva', 'Ciclone', 7, true),
('1992-02-07', 'Julia Costa', 'Velocidade Relâmpago', 8, true),
('1986-11-24', 'Ricardo Souza', 'Explosão Veloz', 9, true),
('1990-08-19', 'Natalia Almeida', 'Turbo Star', 10, true),
('1989-07-04', 'Diego Ribeiro', 'Relâmpago Verde', 1, true),
('1997-01-30', 'Camila Fonseca', 'Furiosa', 2, true),
('1984-09-14', 'Eduardo Lima', 'Tornado', 3, true),
('1995-08-08', 'Larissa Silva', 'Velocidade Extrema', 4, true),
('1991-06-26', 'Renato Pereira', 'Turbo Nitro', 5, true),
('1986-04-13', 'Vanessa Souza', 'Rajada Turbo', 6, true),
('1993-03-01', 'Felipe Costa', 'Sprint Turbo', 7, true),
('1988-12-17', 'Tatiane Ribeiro', 'Veloz e Furioso', 8, true),
('1992-10-02', 'Fernando Martins', 'Turbo Vortex', 9, true),
('1985-01-09', 'Carolina Almeida', 'Ventania Turbo', 10, true);

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

insert into tb_championship (date_time, end_date_time, name)
values
(current_timestamp(), CURRENT_TIMESTAMP() + INTERVAL 2 HOUR , 'Arrancada Suprema'),
(current_timestamp(), CURRENT_TIMESTAMP() + INTERVAL 6 HOUR , 'Desafio das Pistas'),
(current_timestamp(), CURRENT_TIMESTAMP() + INTERVAL 2 DAY , 'Copa Turbo Aceleração'),
(current_timestamp(), CURRENT_TIMESTAMP() + INTERVAL 2 DAY, 'Grand Prix de Arrancada'),
(current_timestamp() - INTERVAL 7 DAY, CURRENT_TIMESTAMP() - INTERVAL 2 DAY,  'Corrida Turbocharge'),
(current_timestamp(), null, 'Chebet Oficial'),
(current_timestamp(), null, 'Grande Corrida Turbo'),
(current_timestamp(), null, 'Copa das Arrancadas Extremas'),
(current_timestamp(), null, 'Campeonato Velozes e Furiosos'),
(current_timestamp(), null, 'Armageddon 2023');

insert into tb_championship_pilots (championship_id, pilots_id)
values
(1,1),
(1,2),
(1,3),
(1,4),
(1,5),
(1,6),
(1,7),
(1,8),
(1,9),
(1,10),
(2,1),
(2,2),
(2,3),
(2,4),
(2,5),
(2,6),
(2,7),
(2,8),
(2,9),
(2,10),
(3,11),
(3,12),
(3,13),
(3,14),
(3,15),
(3,16),
(3,17),
(3,18),
(3,19),
(3,20),
(4,11),
(4,12),
(4,13),
(4,14),
(4,15),
(4,16),
(4,17),
(4,18),
(4,19),
(4,20);

insert into tb_transaction (datetime, transaction_type, value, user_id)
values
(current_timestamp(), 0, 104.5, 1),
(current_timestamp(), 0, 104.5, 1),
(current_timestamp(), 0, 104.5, 1),
(current_timestamp(), 1, 33, 1),
(current_timestamp(), 0, 45, 1);