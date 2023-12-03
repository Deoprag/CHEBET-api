-- PROCEDURE - UpdateUserBalance
-- Atualiza o campo balance da tb_user
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

-- TRIGGER - tr_insert_transaction
-- Quando algo é inserido na tb_transaction executa a procedure UpdateUserBalance
DELIMITER //
CREATE TRIGGER tr_insert_transaction
AFTER INSERT
ON tb_transaction FOR EACH ROW
BEGIN
    CALL UpdateUserBalance(NEW.user_id);
END;
// DELIMITER ;

-- PROCEDURE - SoftDeleteUser
-- Troca todos os campos da tb_user para valores padrão, removendo todos os dados do usuário, seguindo a LGPD
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