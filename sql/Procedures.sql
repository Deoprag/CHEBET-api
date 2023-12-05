use db_chebet;
----------------------------------------------------------------------------------------------------------------------
-- PROCEDURE - UpdateUserBalance																					--
-- Atualiza o campo balance da tb_user																				--
-- DROP PROCEDURE UpdateUserBalance;																				--
----------------------------------------------------------------------------------------------------------------------
DELIMITER //
CREATE PROCEDURE UpdateUserBalance(IN user_id_param INT)
BEGIN
    DECLARE newBalance FLOAT;
    SET newBalance = (
        SELECT 
            COALESCE(SUM(CASE WHEN t.transaction_type IN (0, 3) THEN t.value ELSE 0 END), 0)
            - COALESCE(SUM(CASE WHEN t.transaction_type IN (1, 2) THEN t.value ELSE 0 END), 0)
        FROM tb_transaction t
        WHERE t.user_id = user_id_param
    );
    IF newBalance >= 0 THEN
        UPDATE tb_user
        SET balance = newBalance
        WHERE id = user_id_param;
    ELSE
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Saldo não pode ser negativo';
    END IF;
END;
// DELIMITER ;

----------------------------------------------------------------------------------------------------------------------
-- TRIGGER - tr_insert_transaction																					--
-- Quando algo é inserido na tb_transaction executa a procedure UpdateUserBalance									--
-- DROP TRIGGER tr_insert_transaction;																				--
----------------------------------------------------------------------------------------------------------------------
DELIMITER //
CREATE TRIGGER tr_insert_transaction
AFTER INSERT
ON tb_transaction FOR EACH ROW
BEGIN
    CALL UpdateUserBalance(NEW.user_id);
END;
// DELIMITER ;
DELIMITER //
CREATE TRIGGER tr_after_delete_transaction
AFTER DELETE
ON tb_transaction FOR EACH ROW
BEGIN
    CALL UpdateUserBalance(OLD.user_id);
END;
//
DELIMITER ;

----------------------------------------------------------------------------------------------------------------------
-- PROCEDURE - SoftDeleteUser																						--
-- Troca todos os campos da tb_user para valores padrão, removendo todos os dados do usuário, seguindo a LGPD		--
-- DROP PROCEDURE SoftDeleteUser;																					--
----------------------------------------------------------------------------------------------------------------------
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

----------------------------------------------------------------------------------------------------------------------
-- PROCEDURE - GenerateRaceData																						--
-- Gera as corridas referentes a um campeonato																		--
-- DROP PROCEDURE GenerateRaceData;																					--
----------------------------------------------------------------------------------------------------------------------
DELIMITER //
CREATE PROCEDURE GenerateRaceData(IN championship_id_param INT)
BEGIN
  DECLARE current_championship_id INT;
  DECLARE cur_championship_done BOOLEAN DEFAULT FALSE;
  DECLARE pilot1_id INT;
  DECLARE pilot2_id INT;

  -- Crie uma cursor para iterar pelos campeonatos
  DECLARE cur_championship CURSOR FOR SELECT id FROM tb_championship WHERE id = championship_id_param;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET cur_championship_done = TRUE;
  OPEN cur_championship;
  
  DELETE FROM tb_race WHERE championship_id = championship_id_param;

  -- Inicie a leitura dos campeonatos
  read_championship: LOOP
    FETCH cur_championship INTO current_championship_id;
    IF cur_championship_done THEN
      LEAVE read_championship;
    END IF;

    -- Crie uma tabela temporária para armazenar os pilotos associados ao campeonato
    CREATE TEMPORARY TABLE temp_championship_pilots AS
      SELECT pilots_id
      FROM tb_championship_pilots
      WHERE championship_id = current_championship_id;

    -- Inicializa as variáveis
    SET pilot1_id = 0;
    SET pilot2_id = 0;

    -- Insira dados na tb_race com base no campeonato atual
    WHILE (SELECT COUNT(*) FROM temp_championship_pilots) > 1 DO
      -- Seleciona dois pilotos diferentes aleatoriamente
      SELECT pilots_id INTO pilot1_id FROM temp_championship_pilots ORDER BY RAND() LIMIT 1;
      DELETE FROM temp_championship_pilots WHERE pilots_id = pilot1_id;

      SELECT pilots_id INTO pilot2_id FROM temp_championship_pilots ORDER BY RAND() LIMIT 1;
      DELETE FROM temp_championship_pilots WHERE pilots_id = pilot2_id;

      -- Insere os dados na tb_race
      INSERT INTO tb_race (race_number, pilot1_id, pilot2_id, championship_id, pilot1_broke, pilot2_broke)
      SELECT 
        COALESCE(MAX(r.race_number), 0) + 1,
        pilot1_id,
        pilot2_id,
        current_championship_id,
        false,
        false
      FROM tb_race r
      WHERE r.championship_id = current_championship_id;

    END WHILE;

    -- Drop da tabela temporária
    DROP TEMPORARY TABLE IF EXISTS temp_championship_pilots;

  END LOOP;

  CLOSE cur_championship;
  
END 
// DELIMITER ;

----------------------------------------------------------------------------------------------------------------------
-- PROCEDURE - UpdateRanking																						--
-- Gera o ranking de pilotos baseado no tempo das corridas															--
-- DROP PROCEDURE UpdateRanking;																					--
----------------------------------------------------------------------------------------------------------------------
DELIMITER //

CREATE PROCEDURE UpdateRanking(IN championship_id_param INT)
BEGIN
  -- Limpar a tabela de ranking para o campeonato específico
  DELETE FROM tb_ranking WHERE championship_id = championship_id_param;

  -- Lógica para gerar o ranking
  INSERT INTO tb_ranking (championship_id, pilot_id, position)
  SELECT
    championship_id,
    pilot_id,
    RANK() OVER (PARTITION BY championship_id ORDER BY race_time, pilot_id) AS position
  FROM (
    SELECT
      r.championship_id,
      r.pilot1_id AS pilot_id,
      COALESCE(r.pilot1_time, '23:59:59.999') AS race_time
    FROM
      tb_race r
    WHERE
      r.championship_id = championship_id_param
      
    UNION ALL

    SELECT
      r.championship_id,
      r.pilot2_id AS pilot_id,
      COALESCE(r.pilot2_time, '23:59:59.999') AS race_time
    FROM
      tb_race r
    WHERE
      r.championship_id = championship_id_param
      
  ) AS subquery;
END 
// DELIMITER ;

DROP PROCEDURE GetAverageTime;
DELIMITER //

CREATE PROCEDURE GetAverageTime(IN championship_id_param INT)
BEGIN
  DECLARE total_seconds_avg INT;

  CREATE TEMPORARY TABLE IF NOT EXISTS temp_times (
    total_seconds INT
  );

  -- Calcula o tempo médio para pilot1
  INSERT INTO temp_times
  SELECT COALESCE(SUM(TIME_TO_SEC(TIMEDIFF(pilot1_time, '00:00:00'))), 0) AS total_seconds
  FROM tb_race
  WHERE pilot1_broke = false AND championship_id = championship_id_param;

  -- Calcula o tempo médio para pilot2
  INSERT INTO temp_times
  SELECT COALESCE(SUM(TIME_TO_SEC(TIMEDIFF(pilot2_time, '00:00:00'))), 0) AS total_seconds
  FROM tb_race
  WHERE pilot2_broke = false AND championship_id = championship_id_param;

  -- Calcula a média total
  SELECT COALESCE(AVG(total_seconds), 0)
  INTO total_seconds_avg
  FROM temp_times;

  -- Retorna o resultado
  SELECT SEC_TO_TIME(total_seconds_avg) AS average_time;

  -- Limpa a tabela temporária
  DROP TEMPORARY TABLE IF EXISTS temp_times;
END //

DELIMITER ;