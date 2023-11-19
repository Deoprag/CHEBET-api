use db_chebet;

select * from tb_user;
select * from tb_team;
select * from tb_pilot;
select * from tb_preparer;
select * from tb_car;

describe tb_car;

delete from tb_user where id = 2;

update tb_user set active = 1, role = 1 where id = 4;

------------------------------------------
-- Carros com os pilotos e preparadores --
------------------------------------------
SELECT c.id, c.color, c.model, c.nickname, c.year, pi.name as pilot, pr.name as preparer FROM tb_car c
INNER JOIN tb_pilot pi ON pi.id = c.pilot_id
INNER JOIN tb_preparer pr ON pr.id = c.preparer_id;