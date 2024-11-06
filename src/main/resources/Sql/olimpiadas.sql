CREATE DATABASE IF NOT EXISTS `olimpiadas`;
USE `olimpiadas`;
DROP TABLE IF EXISTS `Deporte`;
CREATE TABLE `Deporte` (
  `id_deporte` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  PRIMARY KEY (`id_deporte`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
LOCK TABLES `Deporte` WRITE;

INSERT INTO `Deporte` VALUES
(1,'Futbol'),
(2,'Baloncesto'),
(3,'Boxeo');
DROP TABLE IF EXISTS `Deportista`;
CREATE TABLE `Deportista` (
  `id_deportista` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) NOT NULL,
  `sexo` enum('M','F') NOT NULL,
  `peso` int(11) DEFAULT NULL,
  `altura` int(11) DEFAULT NULL,
  `foto` blob DEFAULT NULL,
  PRIMARY KEY (`id_deportista`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
LOCK TABLES `Deportista` WRITE;
INSERT INTO `Deportista` VALUES
(1,'Alberto Gines','M',80,180,NULL),
(2,'Tadej Pogacar','M',60,170,NULL);
UNLOCK TABLES;
DROP TABLE IF EXISTS `Equipo`;
CREATE TABLE `Equipo` (
  `id_equipo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `iniciales` varchar(3) NOT NULL,
  PRIMARY KEY (`id_equipo`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
LOCK TABLES `Equipo` WRITE;
INSERT INTO `Equipo` VALUES
(1,'España','ESP'),
(2,'Francia','FRA');
UNLOCK TABLES;
DROP TABLE IF EXISTS `Evento`;
CREATE TABLE `Evento` (
 `id_evento` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) NOT NULL,
  `id_olimpiada` int(11) NOT NULL,
  `id_deporte` int(11) NOT NULL,
  PRIMARY KEY (`id_evento`),
  KEY `FK_Evento_Deporte` (`id_deporte`),
  KEY `FK_Evento_Olimpiada` (`id_olimpiada`),
  CONSTRAINT `FK_Evento_Deporte` FOREIGN KEY (`id_deporte`) REFERENCES `Deporte` (`id_deporte`),
  CONSTRAINT `FK_Evento_Olimpiada` FOREIGN KEY (`id_olimpiada`) REFERENCES `Olimpiada` (`id_olimpiada`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
LOCK TABLES `Evento` WRITE;
UNLOCK TABLES;
DROP TABLE IF EXISTS `Olimpiada`;
CREATE TABLE `Olimpiada` (
  `id_olimpiada` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(11) NOT NULL,
  `anio` smallint(6) NOT NULL,
  `temporada` enum('Summer','Winter') NOT NULL,
  `ciudad` varchar(50) NOT NULL,
  PRIMARY KEY (`id_olimpiada`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
LOCK TABLES `Olimpiada` WRITE;
INSERT INTO `Olimpiada` VALUES
(1,'2020 Summer',2020,'Summer','Tokio'),
(2,'2012 Summer',2012,'Summer','London'),
(3,'2016 Summer',2016,'Summer','Rio');
UNLOCK TABLES;
DROP TABLE IF EXISTS `Participacion`;
CREATE TABLE `Participacion` (
  `id_deportista` int(11) NOT NULL,
  `id_evento` int(11) NOT NULL,
  `id_equipo` int(11) NOT NULL,
  `edad` tinyint(4) DEFAULT NULL,
  `medalla` varchar(6) DEFAULT NULL,
  PRIMARY KEY (`id_deportista`,`id_evento`),
  KEY `FK_Participacion_Equipo` (`id_equipo`),
  KEY `FK_Participacion_Evento` (`id_evento`),
  CONSTRAINT `FK_Participacion_Deportista` FOREIGN KEY (`id_deportista`) REFERENCES `Deportista` (`id_deportista`),
  CONSTRAINT `FK_Participacion_Equipo` FOREIGN KEY (`id_equipo`) REFERENCES `Equipo` (`id_equipo`),
  CONSTRAINT `FK_Participacion_Evento` FOREIGN KEY (`id_evento`) REFERENCES `Evento` (`id_evento`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
LOCK TABLES `Participacion` WRITE;
UNLOCK TABLES;