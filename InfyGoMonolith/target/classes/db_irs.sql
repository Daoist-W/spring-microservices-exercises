drop database if exists db_irs;
create database db_irs;
use db_irs;

GRANT ALL PRIVILEGES ON infytel.* TO 'demo'@'localhost';

DROP TABLE IF EXISTS `user_details`;

CREATE TABLE `user_details` (
  `user_id` varchar(255) NOT NULL,
  `city` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

INSERT INTO `user_details` VALUES ('Greg','LA','greg@dmail.com','Greg K','Greg^InfyGo','8765421906');

DROP TABLE IF EXISTS `flight_details`;

CREATE TABLE `flight_details` (
  `flight_id` varchar(255) NOT NULL,
  `airlines` varchar(255) DEFAULT NULL,
  `arrival_time` varchar(255) DEFAULT NULL,
  `departure_time` varchar(255) DEFAULT NULL,
  `destination` varchar(255) DEFAULT NULL,
  `fare` double NOT NULL,
  `flight_available_date` date DEFAULT NULL,
  `seat_count` int(11) DEFAULT NULL,
  `source` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`flight_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

INSERT INTO `flight_details` VALUES ('F101','WingMeIn','19:00','20:00','California',40000,'2019-03-05',22,'Mumbai');
INSERT INTO `flight_details` VALUES ('F102','MagAirLines','20:00','21:00','LA',50000,'2019-03-06',30,'Delhi');

DROP TABLE IF EXISTS `ticket_details`;

CREATE TABLE `ticket_details` (
  `pnr` int(11) NOT NULL,
  `booking_date` date DEFAULT NULL,
  `departure_date` date DEFAULT NULL,
  `departure_time` varchar(255) DEFAULT NULL,
  `flight_id` varchar(255) DEFAULT NULL,
  `no_of_seats` int(11) NOT NULL,
  `total_fare` double NOT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`pnr`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

INSERT INTO `ticket_details` VALUES(1608294,'2019-03-05','2019-03-05','20:00','F101',1,40000,'Greg');

DROP TABLE IF EXISTS `passenger_details`;

CREATE TABLE `passenger_details` (
  `passenger_id` int(11) NOT NULL,
  `passenger_age` varchar(255) DEFAULT NULL,
  `passenger_gender` varchar(255) DEFAULT NULL,
  `passenger_name` varchar(255) DEFAULT NULL,
  `ticket_pnr` int(11) DEFAULT NULL,
  PRIMARY KEY (`passenger_id`),
  KEY `FKlcjup2fi5sgce4lvo0mwoou1e` (`ticket_pnr`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

INSERT INTO `passenger_details` VALUES(105,'30','Male','Bob',1608294);

DROP TABLE IF EXISTS `creditcard_details`;

CREATE TABLE `creditcard_details` (
  `card_number` varchar(255) NOT NULL,
  `apin` varchar(255) DEFAULT NULL,
  `card_holder_name` varchar(255) DEFAULT NULL,
  `cvv` varchar(255) DEFAULT NULL,
  `expiry_month` varchar(255) DEFAULT NULL,
  `expiry_year` varchar(255) DEFAULT NULL,
  `total_bill` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`card_number`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

INSERT INTO `creditcard_details` VALUES ('1234567891234567','123456','Bob','235','Jan','2020',40000);
