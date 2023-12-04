-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: db_chebet
-- ------------------------------------------------------
-- Server version	8.0.35

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tb_average_time`
--

DROP TABLE IF EXISTS `tb_average_time`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_average_time` (
  `id` int NOT NULL AUTO_INCREMENT,
  `average_time_1` time(6) DEFAULT NULL,
  `average_time_2` time(6) DEFAULT NULL,
  `championship_id` int NOT NULL,
  `loser_id` int NOT NULL,
  `race_id` int NOT NULL,
  `winner_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhhiqe5w17hpiutkbgvt0xru26` (`championship_id`),
  KEY `FKrcj3qjkuasxjedcesshmrnoyw` (`loser_id`),
  KEY `FKdl1fer9thalsul6a746o64jyk` (`race_id`),
  KEY `FK9rp6kvw9no28mc8ysn2qrjfr1` (`winner_id`),
  CONSTRAINT `FK9rp6kvw9no28mc8ysn2qrjfr1` FOREIGN KEY (`winner_id`) REFERENCES `tb_pilot` (`id`),
  CONSTRAINT `FKdl1fer9thalsul6a746o64jyk` FOREIGN KEY (`race_id`) REFERENCES `tb_race` (`id`),
  CONSTRAINT `FKhhiqe5w17hpiutkbgvt0xru26` FOREIGN KEY (`championship_id`) REFERENCES `tb_championship` (`id`),
  CONSTRAINT `FKrcj3qjkuasxjedcesshmrnoyw` FOREIGN KEY (`loser_id`) REFERENCES `tb_pilot` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_average_time`
--

LOCK TABLES `tb_average_time` WRITE;
/*!40000 ALTER TABLE `tb_average_time` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_average_time` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_bet`
--

DROP TABLE IF EXISTS `tb_bet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_bet` (
  `id` int NOT NULL AUTO_INCREMENT,
  `bet_type` tinyint DEFAULT NULL,
  `championship_id` int NOT NULL,
  `transaction_id` int NOT NULL,
  `average_time_id` int NOT NULL,
  `broken_car_id` int NOT NULL,
  `head_to_head_id` int NOT NULL,
  `simple_position_id` int NOT NULL,
  `simple_victory_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8tkm5b8bhuehjil3bgvdkq990` (`championship_id`),
  KEY `FKcythdfxpo38qf20gdode4x4it` (`transaction_id`),
  KEY `FKhf9ivqib6f4j2askauh0n5rg3` (`average_time_id`),
  KEY `FKlxqy14kwcka7cie44j22apot8` (`broken_car_id`),
  KEY `FKgshtd3renjatvj9wvmgubh5q8` (`simple_position_id`),
  KEY `FKoepawuryomx3ufc7g153stkpu` (`simple_victory_id`),
  KEY `FKgqekby0lcjuuikrsy43ekn14s` (`head_to_head_id`),
  CONSTRAINT `FK8tkm5b8bhuehjil3bgvdkq990` FOREIGN KEY (`championship_id`) REFERENCES `tb_championship` (`id`),
  CONSTRAINT `FKcythdfxpo38qf20gdode4x4it` FOREIGN KEY (`transaction_id`) REFERENCES `tb_transaction` (`id`),
  CONSTRAINT `FKgqekby0lcjuuikrsy43ekn14s` FOREIGN KEY (`head_to_head_id`) REFERENCES `tb_head_to_head` (`id`),
  CONSTRAINT `FKgshtd3renjatvj9wvmgubh5q8` FOREIGN KEY (`simple_position_id`) REFERENCES `tb_simple_position` (`id`),
  CONSTRAINT `FKhf9ivqib6f4j2askauh0n5rg3` FOREIGN KEY (`average_time_id`) REFERENCES `tb_average_time` (`id`),
  CONSTRAINT `FKk9vpyjel9bcacp60ttmnxlikq` FOREIGN KEY (`head_to_head_id`) REFERENCES `tb_average_time` (`id`),
  CONSTRAINT `FKlxqy14kwcka7cie44j22apot8` FOREIGN KEY (`broken_car_id`) REFERENCES `tb_broken_car` (`id`),
  CONSTRAINT `FKoepawuryomx3ufc7g153stkpu` FOREIGN KEY (`simple_victory_id`) REFERENCES `tb_simple_victory` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_bet`
--

LOCK TABLES `tb_bet` WRITE;
/*!40000 ALTER TABLE `tb_bet` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_bet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_broken_car`
--

DROP TABLE IF EXISTS `tb_broken_car`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_broken_car` (
  `id` int NOT NULL AUTO_INCREMENT,
  `championship_id` int NOT NULL,
  `pilot_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKlxigd2cfhl0svfm1jmy2eep6g` (`championship_id`),
  KEY `FKjgbdvadti9471l21bjfntx15j` (`pilot_id`),
  CONSTRAINT `FKjgbdvadti9471l21bjfntx15j` FOREIGN KEY (`pilot_id`) REFERENCES `tb_pilot` (`id`),
  CONSTRAINT `FKlxigd2cfhl0svfm1jmy2eep6g` FOREIGN KEY (`championship_id`) REFERENCES `tb_championship` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_broken_car`
--

LOCK TABLES `tb_broken_car` WRITE;
/*!40000 ALTER TABLE `tb_broken_car` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_broken_car` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_car`
--

DROP TABLE IF EXISTS `tb_car`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_car` (
  `id` int NOT NULL AUTO_INCREMENT,
  `color` tinyint NOT NULL,
  `model` varchar(150) NOT NULL,
  `nickname` varchar(50) NOT NULL,
  `year` smallint NOT NULL,
  `pilot_id` int NOT NULL,
  `preparer_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_qkgs6qn280ky36d1vbi6qt42l` (`pilot_id`),
  KEY `FKgx8g0n4kouxp1qelctt9mbw3p` (`preparer_id`),
  CONSTRAINT `FK7sg2e1i3pefvw184j23n3q9va` FOREIGN KEY (`pilot_id`) REFERENCES `tb_pilot` (`id`),
  CONSTRAINT `FKgx8g0n4kouxp1qelctt9mbw3p` FOREIGN KEY (`preparer_id`) REFERENCES `tb_preparer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_car`
--

LOCK TABLES `tb_car` WRITE;
/*!40000 ALTER TABLE `tb_car` DISABLE KEYS */;
INSERT INTO `tb_car` VALUES (1,0,'Civic','Turbo Rocket',2020,1,1),(2,1,'Mustang','Veloz Mustang',2018,2,2),(3,2,'Golf GTI','Foguete Veloz',2019,3,3),(4,3,'Corvette','Relâmpago Dourado',2022,4,4),(5,4,'BMW M3','Turbocharged Beast',2021,5,5),(6,5,'Audi R8','Raio X',2020,6,6),(7,6,'Porsche 911','Adrenalina Suprema',2019,7,7),(8,7,'Subaru WRX','Velocidade Extrema',2022,8,8),(9,8,'Ford Focus RS','Demolidor Turbo',2018,9,9),(10,9,'Chevrolet Camaro','Furia Nitro',2021,10,10),(11,5,'Tesla Model S','EletroTurbo',2022,11,1),(12,6,'Mazda RX-7','Rajada Rápida',2020,12,2),(13,7,'Toyota Supra','Fúria Japonesa',2019,13,3),(14,8,'Dodge Challenger','Trovoada Turbo',2021,14,4),(15,9,'Mercedes-AMG GT','Turbo Elegante',2020,15,5),(16,0,'Chevrolet Corvette Stingray','Vento Veloz',2021,16,6),(17,1,'Nissan GT-R','Raios Rápidos',2018,17,7),(18,2,'Ford Mustang Shelby GT500','Demolidor Mustang',2019,18,8),(19,3,'Honda S2000','Foguete Japonês',2022,19,9),(20,4,'Aston Martin Vantage','Velocidade Britânica',2018,20,10);
/*!40000 ALTER TABLE `tb_car` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_championship`
--

DROP TABLE IF EXISTS `tb_championship`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_championship` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date_time` datetime(6) DEFAULT NULL,
  `end_date_time` datetime(6) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `finished` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_or8pnnlhwv2ugurkmwi2lwv1g` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_championship`
--

LOCK TABLES `tb_championship` WRITE;
/*!40000 ALTER TABLE `tb_championship` DISABLE KEYS */;
INSERT INTO `tb_championship` VALUES (1,'2023-12-03 11:19:40.000000','2023-12-03 13:19:40.000000','Arrancada Suprema',_binary ''),(2,'2023-12-03 02:19:40.000000','2023-12-03 08:19:40.000000','Desafio das Pistas',_binary ''),(3,'2023-12-02 23:19:40.000000','2023-12-04 23:19:40.000000','Copa Turbo Aceleração',_binary ''),(4,'2023-12-02 20:19:40.000000','2023-12-04 20:19:40.000000','Grand Prix de Arrancada',_binary '\0'),(5,'2023-11-26 17:19:40.000000','2023-12-01 17:19:40.000000','Corrida Turbocharge',_binary ''),(6,'2023-11-25 20:19:40.000000','2023-11-30 20:19:40.000000','Chebet Oficial',_binary '\0'),(7,'2023-11-25 20:19:40.000000','2023-11-30 20:19:40.000000','Grande Corrida Turbo',_binary '\0'),(8,'2023-11-25 20:19:40.000000','2023-11-30 20:19:40.000000','Copa das Arrancadas Extremas',_binary '\0'),(9,'2023-11-25 20:19:40.000000','2023-11-30 20:19:40.000000','Campeonato Velozes e Furiosos',_binary '\0'),(10,'2023-11-25 20:19:40.000000','2023-11-30 20:19:40.000000','Armageddon 2023',_binary '\0');
/*!40000 ALTER TABLE `tb_championship` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_championship_pilots`
--

DROP TABLE IF EXISTS `tb_championship_pilots`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_championship_pilots` (
  `championship_id` int NOT NULL,
  `pilots_id` int NOT NULL,
  KEY `FK89booy08q4uu7pq5w582w54o7` (`pilots_id`),
  KEY `FKmkwjbhffoccsmomioral2nqgd` (`championship_id`),
  CONSTRAINT `FK89booy08q4uu7pq5w582w54o7` FOREIGN KEY (`pilots_id`) REFERENCES `tb_pilot` (`id`),
  CONSTRAINT `FKmkwjbhffoccsmomioral2nqgd` FOREIGN KEY (`championship_id`) REFERENCES `tb_championship` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_championship_pilots`
--

LOCK TABLES `tb_championship_pilots` WRITE;
/*!40000 ALTER TABLE `tb_championship_pilots` DISABLE KEYS */;
INSERT INTO `tb_championship_pilots` VALUES (6,2),(6,3),(6,1),(6,4),(6,15),(6,12),(6,13),(6,23),(6,21),(6,22),(6,24),(6,29),(6,27),(6,28),(6,30),(7,1),(7,2),(7,5),(7,7),(7,8),(7,18),(7,15),(7,17),(7,22),(7,24),(7,20),(7,27),(7,28),(7,29),(7,30),(7,34),(7,33),(8,3),(8,2),(8,9),(8,7),(8,10),(8,8),(8,16),(8,17),(8,15),(8,19),(8,22),(8,24),(8,30),(8,27),(8,39),(8,38),(8,46),(8,43),(8,45),(8,48),(8,50),(9,4),(9,9),(9,10),(9,11),(9,12),(9,17),(9,15),(9,22),(9,21),(9,30),(9,29),(9,34),(9,32),(10,1),(10,2),(10,3),(10,5),(10,6),(10,7),(10,13),(10,11),(10,12),(10,15),(10,17),(10,24),(10,22),(10,26),(10,28),(10,32),(10,31),(1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),(1,10),(1,11),(1,12),(5,8),(5,15),(5,18),(5,17),(5,16),(5,23),(5,21),(5,20),(5,22),(5,30),(5,29),(5,32),(5,33),(5,41),(5,39),(5,38),(5,40),(5,48),(5,46),(5,3),(2,1),(2,2),(2,3),(2,4),(2,5),(2,6),(2,7),(2,8),(2,9),(2,10),(3,11),(3,12),(3,13),(3,14),(3,15),(3,16),(3,17),(3,18),(3,19),(3,20),(4,11),(4,12),(4,13),(4,14),(4,15),(4,16),(4,17),(4,18),(4,19),(4,20);
/*!40000 ALTER TABLE `tb_championship_pilots` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_head_to_head`
--

DROP TABLE IF EXISTS `tb_head_to_head`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_head_to_head` (
  `id` int NOT NULL AUTO_INCREMENT,
  `championship_id` int NOT NULL,
  `loser_id` int NOT NULL,
  `race_id` int NOT NULL,
  `winner_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKso4ep4mv9g7jd4vasyo8kv6vt` (`championship_id`),
  KEY `FKsau3lx64v013i1tgdhdj8n5fy` (`loser_id`),
  KEY `FKllwl7935g134cvpd7hwicw1h9` (`race_id`),
  KEY `FK543d7gwhehaxox59r92b55qp1` (`winner_id`),
  CONSTRAINT `FK543d7gwhehaxox59r92b55qp1` FOREIGN KEY (`winner_id`) REFERENCES `tb_pilot` (`id`),
  CONSTRAINT `FKllwl7935g134cvpd7hwicw1h9` FOREIGN KEY (`race_id`) REFERENCES `tb_race` (`id`),
  CONSTRAINT `FKsau3lx64v013i1tgdhdj8n5fy` FOREIGN KEY (`loser_id`) REFERENCES `tb_pilot` (`id`),
  CONSTRAINT `FKso4ep4mv9g7jd4vasyo8kv6vt` FOREIGN KEY (`championship_id`) REFERENCES `tb_championship` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_head_to_head`
--

LOCK TABLES `tb_head_to_head` WRITE;
/*!40000 ALTER TABLE `tb_head_to_head` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_head_to_head` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_pilot`
--

DROP TABLE IF EXISTS `tb_pilot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_pilot` (
  `id` int NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL DEFAULT b'0',
  `birth_date` date NOT NULL,
  `name` varchar(150) NOT NULL,
  `nickname` varchar(50) NOT NULL,
  `team_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_qernnamwwr98anx8p5979sr7e` (`nickname`),
  KEY `FKrlsceqxcd98mhoshja9muc2od` (`team_id`),
  CONSTRAINT `FKrlsceqxcd98mhoshja9muc2od` FOREIGN KEY (`team_id`) REFERENCES `tb_team` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_pilot`
--

LOCK TABLES `tb_pilot` WRITE;
/*!40000 ALTER TABLE `tb_pilot` DISABLE KEYS */;
INSERT INTO `tb_pilot` VALUES (1,_binary '','1985-05-10','José Silva','Velozinho',1),(2,_binary '','1990-12-15','Maria Santos','Turbinada',2),(3,_binary '','1988-03-02','João Pereira','Fogueteiro',3),(4,_binary '','1995-07-20','Ana Oliveira','Anita',4),(5,_binary '','1982-09-08','Luiz Souza','Turbo Master',5),(6,_binary '','1992-04-17','Cristina Lima','Raio X',6),(7,_binary '','1987-11-12','Marcos Ferreira','Adrenalina',7),(8,_binary '','1998-06-03','Sofia Costa','Relâmpago',8),(9,_binary '','1991-01-29','Carlos Santos','Demolidor',9),(10,_binary '','1986-10-07','Fernanda Rodrigues','Furia Nitro',10),(11,_binary '','1993-09-14','Rafael Costa','Turbulento',1),(12,_binary '','1986-06-25','Juliana Oliveira','Velocidade Pura',2),(13,_binary '','1990-04-03','Antônio Santos','Flecha Dourada',3),(14,_binary '','1988-02-11','Amanda Pereira','Turbocharged',4),(15,_binary '','1994-07-06','Leonardo Silva','Velozão',5),(16,_binary '','1985-12-19','Renata Souza','Tempestade Turbo',6),(17,_binary '','1991-08-30','Eduardo Almeida','Meteoro',7),(18,_binary '','1989-10-05','Camila Ribeiro','Ágil',8),(19,_binary '','1996-03-22','Hugo Fonseca','Foguete Veloz',9),(20,_binary '','1992-01-08','Larissa Martins','Velocista',10),(21,_binary '','1987-02-28','Pedro Lima','Propulsor',1),(22,_binary '','1993-11-11','Mariana Costa','Turbo Girl',2),(23,_binary '','1984-08-15','André Oliveira','Relâmpago Azul',3),(24,_binary '','1997-07-25','Carolina Santos','Furacão',4),(25,_binary '','1990-05-01','Rodrigo Pereira','Turbo Blast',5),(26,_binary '','1986-12-10','Isabela Souza','Rajada Veloz',6),(27,_binary '','1995-09-05','Gabriel Ferreira','Sprint Master',7),(28,_binary '','1998-04-02','Tatiane Costa','Velociraptor',8),(29,_binary '','1989-03-18','Lucas Rodrigues','Rápido e Furioso',9),(30,_binary '','1994-01-14','Patrícia Almeida','Turbo Princess',10),(31,_binary '','1983-06-20','Fernando Ribeiro','Vortex',1),(32,_binary '','1991-04-08','Aline Fonseca','Ventania Veloz',2),(33,_binary '','1987-12-03','Gustavo Martins','Fórmula Veloz',3),(34,_binary '','1996-10-28','Laura Lima','Turbo Luminoso',4),(35,_binary '','1985-03-16','José Oliveira','Furia Turbo',5),(36,_binary '','1993-09-22','Mariana Pereira','Turbocharge',6),(37,_binary '','1988-05-11','Thiago Silva','Ciclone',7),(38,_binary '','1992-02-07','Julia Costa','Velocidade Relâmpago',8),(39,_binary '','1986-11-24','Ricardo Souza','Explosão Veloz',9),(40,_binary '','1990-08-19','Natalia Almeida','Turbo Star',10),(41,_binary '','1989-07-04','Diego Ribeiro','Relâmpago Verde',1),(42,_binary '','1997-01-30','Camila Fonseca','Furiosa',2),(43,_binary '','1984-09-14','Eduardo Lima','Tornado',3),(44,_binary '','1995-08-08','Larissa Silva','Velocidade Extrema',4),(45,_binary '','1991-06-26','Renato Pereira','Turbo Nitro',5),(46,_binary '','1986-04-13','Vanessa Souza','Rajada Turbo',6),(47,_binary '','1993-03-01','Felipe Costa','Sprint Turbo',7),(48,_binary '','1988-12-17','Tatiane Ribeiro','Veloz e Furioso',8),(49,_binary '','1992-10-02','Fernando Martins','Turbo Vortex',9),(50,_binary '','1985-01-09','Carolina Almeida','Ventania Turbo',10);
/*!40000 ALTER TABLE `tb_pilot` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_preparer`
--

DROP TABLE IF EXISTS `tb_preparer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_preparer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(150) NOT NULL,
  `nickname` varchar(50) NOT NULL,
  `team_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKr7ffk0ajrqvwl31lh3oa1xlla` (`team_id`),
  CONSTRAINT `FKr7ffk0ajrqvwl31lh3oa1xlla` FOREIGN KEY (`team_id`) REFERENCES `tb_team` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_preparer`
--

LOCK TABLES `tb_preparer` WRITE;
/*!40000 ALTER TABLE `tb_preparer` DISABLE KEYS */;
INSERT INTO `tb_preparer` VALUES (1,'Carlos Silva','Mestre das Modificações',1),(2,'Fernanda Oliveira','Gênio da Preparação',2),(3,'Ricardo Pereira','Magia no Motor',3),(4,'Camilo Santos','Rei dos Ajustes',4),(5,'Roberto Costa','Dr. Velocidade',5),(6,'Julio Fernandes','Mestre das Turbinas',6),(7,'Lucas Souza','Guru dos Motores',7),(8,'Mariana Mendes','Mágico das Rodas',8),(9,'João Pereira','Especialista em Acelerador',9),(10,'Bruno Lima','Arrancada',10);
/*!40000 ALTER TABLE `tb_preparer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_race`
--

DROP TABLE IF EXISTS `tb_race`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_race` (
  `id` int NOT NULL AUTO_INCREMENT,
  `pilot1_time` time(6) DEFAULT NULL,
  `pilot2_time` time(6) DEFAULT NULL,
  `race_number` int NOT NULL,
  `championship_id` int NOT NULL,
  `pilot1_id` int NOT NULL,
  `pilot2_id` int NOT NULL,
  `pilot1_broke` bit(1) DEFAULT NULL,
  `pilot2_broke` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKf0ii8n7asfmkoi3hlvwrmbos6` (`championship_id`),
  KEY `FKsibwxgv6wbatwlcigfah1d5y9` (`pilot1_id`),
  KEY `FKe0ri49xfdpy40pnwnfwha3mis` (`pilot2_id`),
  CONSTRAINT `FKe0ri49xfdpy40pnwnfwha3mis` FOREIGN KEY (`pilot2_id`) REFERENCES `tb_pilot` (`id`),
  CONSTRAINT `FKf0ii8n7asfmkoi3hlvwrmbos6` FOREIGN KEY (`championship_id`) REFERENCES `tb_championship` (`id`),
  CONSTRAINT `FKsibwxgv6wbatwlcigfah1d5y9` FOREIGN KEY (`pilot1_id`) REFERENCES `tb_pilot` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=151 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_race`
--

LOCK TABLES `tb_race` WRITE;
/*!40000 ALTER TABLE `tb_race` DISABLE KEYS */;
INSERT INTO `tb_race` VALUES (103,'00:00:54.161000','00:00:26.156000',1,5,15,17,_binary '\0',_binary '\0'),(104,'00:00:56.941000','00:00:26.145000',2,5,33,41,_binary '\0',_binary '\0'),(105,'00:00:56.151000','00:00:51.651000',3,5,46,8,_binary '\0',_binary '\0'),(106,'00:00:26.156000','00:00:51.654000',4,5,23,20,_binary '\0',_binary '\0'),(107,'00:00:26.315000','00:00:52.615000',5,5,30,29,_binary '\0',_binary '\0'),(108,'00:00:45.612000','00:00:35.465000',6,5,39,32,_binary '\0',_binary '\0'),(109,'00:00:26.159000','00:09:59.999000',7,5,22,38,_binary '\0',_binary ''),(110,'00:00:26.156000','00:00:54.654000',8,5,40,18,_binary '\0',_binary '\0'),(111,'00:09:59.999000','00:09:59.999000',9,5,21,48,_binary '',_binary ''),(112,'00:09:59.999000','00:00:26.351000',10,5,3,16,_binary '',_binary '\0'),(118,'00:00:26.551000','00:00:51.613000',1,3,13,15,_binary '\0',_binary '\0'),(119,'00:00:23.415000','00:00:35.646000',2,3,11,19,_binary '\0',_binary '\0'),(120,'00:00:26.546000','00:00:56.456000',3,3,18,17,_binary '\0',_binary '\0'),(121,'00:09:59.999000','00:00:34.564000',4,3,16,12,_binary '',_binary '\0'),(122,'00:00:26.546000','00:00:45.565000',5,3,14,20,_binary '\0',_binary '\0'),(129,'00:00:26.546000','00:00:46.512000',1,2,5,2,_binary '\0',_binary '\0'),(130,'00:00:32.165000','00:00:45.561000',2,2,7,3,_binary '\0',_binary '\0'),(131,'00:00:30.546000','00:00:51.613000',3,2,4,6,_binary '\0',_binary '\0'),(132,'00:01:02.651000','00:00:56.545000',4,2,9,1,_binary '\0',_binary '\0'),(133,'00:01:25.654000','00:09:59.999000',5,2,10,8,_binary '\0',_binary ''),(140,'00:00:51.196000','00:00:20.616000',1,1,12,3,_binary '\0',_binary '\0'),(141,'00:00:59.592000','00:00:15.654000',2,1,2,4,_binary '\0',_binary '\0'),(142,'00:00:26.549000','00:00:59.454000',3,1,8,5,_binary '\0',_binary '\0'),(143,'00:00:15.915000','00:00:56.451000',4,1,10,11,_binary '\0',_binary '\0'),(144,'00:09:59.999000','00:00:23.654000',5,1,7,1,_binary '',_binary '\0'),(145,'00:00:26.595000','00:09:59.999000',6,1,6,9,_binary '\0',_binary ''),(146,NULL,NULL,1,4,12,13,_binary '\0',_binary '\0'),(147,NULL,NULL,2,4,19,15,_binary '\0',_binary '\0'),(148,NULL,NULL,3,4,11,16,_binary '\0',_binary '\0'),(149,NULL,NULL,4,4,18,20,_binary '\0',_binary '\0'),(150,NULL,NULL,5,4,17,14,_binary '\0',_binary '\0');
/*!40000 ALTER TABLE `tb_race` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_ranking`
--

DROP TABLE IF EXISTS `tb_ranking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_ranking` (
  `id` int NOT NULL AUTO_INCREMENT,
  `position` smallint NOT NULL,
  `championship_id` int NOT NULL,
  `pilot_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK539w8l6wa844uh0043les6lll` (`championship_id`,`position`),
  KEY `FKgk776oasbxm96r3y8gspes7k7` (`pilot_id`),
  CONSTRAINT `FKbtge1gm9bo28ogfctbetynd29` FOREIGN KEY (`championship_id`) REFERENCES `tb_championship` (`id`),
  CONSTRAINT `FKgk776oasbxm96r3y8gspes7k7` FOREIGN KEY (`pilot_id`) REFERENCES `tb_pilot` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=542 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_ranking`
--

LOCK TABLES `tb_ranking` WRITE;
/*!40000 ALTER TABLE `tb_ranking` DISABLE KEYS */;
INSERT INTO `tb_ranking` VALUES (469,1,1,4),(470,2,1,10),(471,3,1,3),(472,4,1,1),(473,5,1,8),(474,6,1,6),(475,7,1,12),(476,8,1,11),(477,9,1,5),(478,10,1,2),(479,11,1,7),(480,12,1,9),(481,1,5,41),(482,2,5,17),(483,3,5,23),(484,4,5,40),(485,5,5,22),(486,6,5,30),(487,7,5,16),(488,8,5,32),(489,9,5,39),(490,10,5,8),(491,11,5,20),(492,12,5,29),(493,13,5,15),(494,14,5,18),(495,15,5,46),(496,16,5,33),(497,17,5,3),(498,18,5,21),(499,19,5,38),(500,20,5,48),(512,1,2,5),(513,2,2,4),(514,3,2,7),(515,4,2,3),(516,5,2,2),(517,6,2,6),(518,7,2,1),(519,8,2,9),(520,9,2,10),(521,10,2,8),(527,1,3,11),(528,2,3,14),(529,3,3,18),(530,4,3,13),(531,5,3,12),(532,6,3,19),(533,7,3,20),(534,8,3,15),(535,9,3,17),(536,10,3,16);
/*!40000 ALTER TABLE `tb_ranking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_simple_position`
--

DROP TABLE IF EXISTS `tb_simple_position`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_simple_position` (
  `id` int NOT NULL AUTO_INCREMENT,
  `position` smallint NOT NULL,
  `championship_id` int NOT NULL,
  `pilot_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK79ui6npg21ygsk1l0p2y54lwf` (`championship_id`),
  KEY `FKs7flt5hf8eic61wdu2liglk69` (`pilot_id`),
  CONSTRAINT `FK79ui6npg21ygsk1l0p2y54lwf` FOREIGN KEY (`championship_id`) REFERENCES `tb_championship` (`id`),
  CONSTRAINT `FKs7flt5hf8eic61wdu2liglk69` FOREIGN KEY (`pilot_id`) REFERENCES `tb_pilot` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_simple_position`
--

LOCK TABLES `tb_simple_position` WRITE;
/*!40000 ALTER TABLE `tb_simple_position` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_simple_position` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_simple_victory`
--

DROP TABLE IF EXISTS `tb_simple_victory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_simple_victory` (
  `id` int NOT NULL AUTO_INCREMENT,
  `championship_id` int NOT NULL,
  `pilot_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfabxcslobhxhnxj2mqg5cer1p` (`championship_id`),
  KEY `FKskhcs4fi3fm20agw5ajt591j3` (`pilot_id`),
  CONSTRAINT `FKfabxcslobhxhnxj2mqg5cer1p` FOREIGN KEY (`championship_id`) REFERENCES `tb_championship` (`id`),
  CONSTRAINT `FKskhcs4fi3fm20agw5ajt591j3` FOREIGN KEY (`pilot_id`) REFERENCES `tb_pilot` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_simple_victory`
--

LOCK TABLES `tb_simple_victory` WRITE;
/*!40000 ALTER TABLE `tb_simple_victory` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_simple_victory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_team`
--

DROP TABLE IF EXISTS `tb_team`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_team` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_team`
--

LOCK TABLES `tb_team` WRITE;
/*!40000 ALTER TABLE `tb_team` DISABLE KEYS */;
INSERT INTO `tb_team` VALUES (1,'Monsters'),(2,'Reptiles'),(3,'Vultures'),(4,'Redbull'),(5,'Drag Kings'),(6,'Nitro Chargers'),(7,'Speed Demons'),(8,'Rocket Racers'),(9,'Turbo Titans'),(10,'Adrenaline Rush');
/*!40000 ALTER TABLE `tb_team` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_transaction`
--

DROP TABLE IF EXISTS `tb_transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_transaction` (
  `id` int NOT NULL AUTO_INCREMENT,
  `datetime` datetime(6) NOT NULL,
  `transaction_type` tinyint NOT NULL,
  `value` float NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7f3wtdpf61kpwpm1p7ygh2ccf` (`user_id`),
  CONSTRAINT `FK7f3wtdpf61kpwpm1p7ygh2ccf` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_transaction`
--

LOCK TABLES `tb_transaction` WRITE;
/*!40000 ALTER TABLE `tb_transaction` DISABLE KEYS */;
INSERT INTO `tb_transaction` VALUES (1,'2023-12-02 17:19:40.000000',0,104.5,1),(2,'2023-12-02 17:19:40.000000',0,104.5,1),(3,'2023-12-02 17:19:40.000000',0,104.5,1),(4,'2023-12-02 17:19:40.000000',1,33,1),(5,'2023-12-02 17:19:40.000000',0,45,1),(6,'2023-12-02 17:21:55.427042',0,120,1),(7,'2023-12-02 17:22:47.545994',0,120,1),(8,'2023-12-02 17:22:48.453012',0,120,1),(9,'2023-12-02 17:22:48.981046',0,120,1),(10,'2023-12-02 17:22:51.613504',0,120,1),(11,'2023-12-02 17:22:51.756494',0,120,1),(12,'2023-12-02 17:22:51.907493',0,120,1),(13,'2023-12-02 17:22:52.054310',0,120,1),(14,'2023-12-02 17:22:52.364006',0,120,1),(15,'2023-12-02 17:25:50.430030',0,120,1),(16,'2023-12-02 17:31:08.076824',0,1,1),(17,'2023-12-02 17:31:34.338403',0,1,1),(18,'2023-12-02 17:31:38.948038',0,1,1),(19,'2023-12-02 17:36:09.086907',0,1,1),(20,'2023-12-02 17:36:45.499052',0,1,1),(21,'2023-12-02 17:38:07.131821',0,1,1),(22,'2023-12-02 17:38:26.411897',0,1000,1),(23,'2023-12-02 17:40:17.077293',1,125,1),(24,'2023-12-02 17:40:27.050385',1,406.5,1),(25,'2023-12-02 17:40:55.762519',1,2000,1),(26,'2023-12-02 17:41:11.530636',0,651,1),(27,'2023-12-02 17:41:49.514630',1,651,1),(29,'2023-12-02 17:55:34.563553',0,1,3),(30,'2023-12-02 17:55:41.456731',0,100,3),(31,'2023-12-02 17:55:47.289374',0,145,3),(32,'2023-12-02 17:55:51.961904',0,14,3),(33,'2023-12-02 17:55:56.970477',0,984,3),(34,'2023-12-02 17:56:03.450567',0,222,3),(35,'2023-12-02 17:56:13.882961',1,66,3),(36,'2023-12-02 17:56:20.416483',1,12,3),(37,'2023-12-02 17:56:25.488901',1,99,3),(38,'2023-12-02 19:23:06.713513',1,1289,3),(39,'2023-12-02 19:24:44.162455',0,120,3),(40,'2023-12-02 19:24:49.337159',0,110,3),(41,'2023-12-02 19:24:55.499076',0,160,3),(42,'2023-12-02 19:27:09.610587',0,125,3);
/*!40000 ALTER TABLE `tb_transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_user`
--

DROP TABLE IF EXISTS `tb_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `balance` decimal(10,2) DEFAULT NULL,
  `birth_date` date NOT NULL,
  `cpf` varchar(11) NOT NULL,
  `email` varchar(50) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `gender` tinyint NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone_number` varchar(11) NOT NULL,
  `role` tinyint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_869sa3rebuf3nm0d4jwxdtouk` (`cpf`),
  UNIQUE KEY `UK_4vih17mube9j7cqyjlfbcrk4m` (`email`),
  UNIQUE KEY `UK_qi5yr54j76lu2meatpwefocym` (`phone_number`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_user`
--

LOCK TABLES `tb_user` WRITE;
/*!40000 ALTER TABLE `tb_user` DISABLE KEYS */;
INSERT INTO `tb_user` VALUES (1,_binary '\0',0.00,'1900-01-01','deleted_1','deleted_1','deleted_1',2,'deleted_1','deleted_1','deleted_1',2),(2,_binary '',0.00,'2004-02-27','14848428683','pdroeofiarabelo@gmail.com','Pedro',0,'Rabelo','$2a$10$YV1aVK1lKNVeQ0eBschXr.Qs9OBrWvWez9Sv36TiXvmwsLR/KjYr.','41999299999',0),(3,_binary '',515.00,'2004-02-27','14848328683','pdroesofiarabelo@gmail.com','Pedro',0,'Rabelo','$2a$10$YV1aVK1lKNVeQ0eBschXr.Qs9OBrWvWez9Sv36TiXvmwsLR/KjYr.','41999999999',1);
/*!40000 ALTER TABLE `tb_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-03 21:28:31
