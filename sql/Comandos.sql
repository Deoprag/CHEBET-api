use db_chebet;

select * from tb_user;
select * from tb_team;
select * from tb_pilot;
select * from tb_preparer;
select * from tb_car;
select * from tb_championship;
select * from tb_championship_pilots;

delete from tb_preparer where id = 11;

delete from tb_user where id = 2;

update tb_user set active = 1, role = 0 where id = 2;
update tb_user set balance = 0 where id = 1;

update tb_championship set end_date = null where id > 5;

CALL SoftDeleteUser(2);

------------------------------------------
-- Carros com os pilotos e preparadores --
------------------------------------------
SELECT c.id, c.color, c.model, c.nickname, c.year, pi.name as pilot, pr.name as preparer FROM tb_car c
INNER JOIN tb_pilot pi ON pi.id = c.pilot_id
INNER JOIN tb_preparer pr ON pr.id = c.preparer_id;