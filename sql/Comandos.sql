use db_chebet;

select * from tb_user;
select * from tb_team;
select * from tb_pilot;
select * from tb_preparer;
select * from tb_car;
select * from tb_championship;
select * from tb_transaction;
select * from tb_ranking where championship_id = 1;
select * from tb_race where championship_id = 1;
select * from tb_bet b
inner join tb_simple_victory sv on sv.id = b.simple_victory_id
where transaction_id = 64;

update tb_user set active = 0 where id = 1;

select sum(t.value)
FROM TB_Championship c
JOIN TB_Bet b on b.championship_id = c.id
JOIN TB_Simple_Victory sv on sv.id = b.simple_victory_id
JOIN TB_Transaction t ON b.transaction_id = t.id
WHERE c.id = 13;

update tb_user set active = 1 where id > 0;

delete from tb_transaction where id = 28;
delete from tb_race where id > 0;

delete from tb_bet where id > 0;

SELECT position FROM TB_Ranking WHERE championship_id = 16 AND pilot_id = 1;
update tb_championship set finished = false where id = 15;

SELECT
  SEC_TO_TIME(AVG(TIME_TO_SEC(total_time))) AS average_time
FROM (
  SELECT
    TIME_TO_SEC(TIMEDIFF(pilot1_time, '00:00:00')) AS total_time
  FROM
    tb_race
  WHERE
    pilot1_broke = false AND championship_id = 13

  UNION ALL

  SELECT
    TIME_TO_SEC(TIMEDIFF(pilot1_time, '00:00:00')) AS total_time
  FROM
    tb_race
  WHERE
    pilot2_broke = false AND championship_id = 13
) AS subquery;

describe tb_simple_victory;

update tb_race set pilot2_broke = false where id > 0;

update tb_championship set end_date = null where id > 5;
SELECT * FROM TB_Ranking where championship_id = 1 AND position = 1;

CALL SoftDeleteUser(1);
CALL GenerateRaceData(6);
CALL UpdateRanking(7);

SELECT * FROM TB_Championship where finished = true;

------------------------------------------
-- Carros com os pilotos e preparadores --
------------------------------------------
SELECT c.id, c.color, c.model, c.nickname, c.year, pi.name as pilot, pr.name as preparer FROM tb_car c
INNER JOIN tb_pilot pi ON pi.id = c.pilot_id
INNER JOIN tb_preparer pr ON pr.id = c.preparer_id;

------------------------------------------
--         Transações e usuários        --
------------------------------------------
SELECT CONCAT(u.first_name, ' ', u.last_name) AS name, 
u.cpf, 
u.email, 
date_format(t.datetime, '%d/%m/%Y - %h:%i:%s') as data, 
CASE 
    WHEN t.transaction_type = 0 THEN 'Depósito'
    WHEN t.transaction_type = 1 THEN 'Saque'
    WHEN t.transaction_type = 2 THEN 'Aposta'
    WHEN t.transaction_type = 3 THEN 'Pagamento de Aposta'
END AS tipo,
CONCAT('R$', t.value) as valor
FROM tb_user u
INNER JOIN tb_transaction t ON t.user_id = u.id
ORDER BY t.datetime;

------------------------------------------
--        Pilotos por campeonato        --
------------------------------------------
SELECT c.id, c.name, COUNT(*) as pilotos FROM tb_championship_pilots cp
INNER JOIN tb_championship c on cp.championship_id = c.id
GROUP BY c.id;

------------------------------------------
--          Ranking de Pilotos          --
------------------------------------------
SELECT c.id,
c.name as campeonato,
r.position as posicao,
p.name as nome,
p.id,
CASE
    WHEN r.pilot_id = ra.pilot1_id THEN ra.pilot1_time
    WHEN r.pilot_id = ra.pilot2_id THEN ra.pilot2_time
	ELSE NULL
END AS tempo
FROM tb_championship c
INNER JOIN tb_ranking r on c.id = r.championship_id
INNER JOIN tb_pilot p on p.id = r.pilot_id
INNER JOIN tb_race ra on ra.championship_id = c.id and ra.pilot1_id = p.id or ra.championship_id = c.id and ra.pilot2_id = p.id
ORDER BY r.championship_id, r.position;