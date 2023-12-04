use db_chebet;

select * from tb_user;
select * from tb_team;
select * from tb_pilot;
select * from tb_preparer;
select * from tb_car;
select * from tb_championship;
select * from tb_transaction;
select * from tb_championship_pilots;
select * from tb_ranking;
select * from tb_race;

delete from tb_transaction where id = 28;
delete from tb_race where id > 0;

delete from tb_ranking where id > 0;

update tb_user set active = 1, role = 1 where id = 3;
update tb_user set balance = 0 where id = 2;
describe tb_race;

update tb_championship set end_date = null where id > 5;

CALL SoftDeleteUser(1);
CALL GenerateRaceData(5);
CALL UpdateRanking(5);

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
ORDER BY u.id, t.datetime;

------------------------------------------
--        Pilotos por campeonato        --
------------------------------------------
SELECT c.id, c.name, COUNT(*) as pilotos FROM tb_championship_pilots cp
INNER JOIN tb_championship c on cp.championship_id = c.id
GROUP BY c.id;

------------------------------------------
--          Ranking de Pilotos          --
------------------------------------------
SELECT c.name as campeonato,
r.position as posicao,
p.name as nome, 
CASE
    WHEN r.pilot_id = ra.pilot1_id THEN ra.pilot1_time
    WHEN r.pilot_id = ra.pilot2_id THEN ra.pilot2_time
	ELSE NULL
END AS tempo
FROM tb_ranking r
INNER JOIN tb_pilot p on p.id = r.pilot_id
INNER JOIN tb_championship c on c.id = r.championship_id
INNER JOIN tb_race ra on ra.championship_id = c.id and ra.pilot1_id = p.id or ra.championship_id = c.id and ra.pilot2_id = p.id
ORDER BY r.championship_id, r.position;