-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: db2_project
-- ------------------------------------------------------
-- Server version	8.0.27

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
-- Table structure for table `alert`
--

DROP TABLE IF EXISTS `alert`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alert` (
  `alertId` int NOT NULL AUTO_INCREMENT,
  `userId` int NOT NULL,
  `username` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `totalAmount_euro` float NOT NULL,
  `rejectionDate` date NOT NULL,
  `rejectionHour` time NOT NULL,
  PRIMARY KEY (`alertId`),
  KEY `userId_idx` (`userId`),
  CONSTRAINT `ALERT_userId` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alert`
--

LOCK TABLES `alert` WRITE;
/*!40000 ALTER TABLE `alert` DISABLE KEYS */;
/*!40000 ALTER TABLE `alert` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `employeeId` int NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`employeeId`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,'emp1@gmail.com','a');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fixed_internet`
--

DROP TABLE IF EXISTS `fixed_internet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fixed_internet` (
  `serviceId` int NOT NULL,
  `GBs` int NOT NULL,
  `extraGBFee_euro` float DEFAULT NULL,
  PRIMARY KEY (`serviceId`),
  CONSTRAINT `FIXED_INTERNET_serviceId` FOREIGN KEY (`serviceId`) REFERENCES `service` (`serviceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fixed_internet`
--

LOCK TABLES `fixed_internet` WRITE;
/*!40000 ALTER TABLE `fixed_internet` DISABLE KEYS */;
INSERT INTO `fixed_internet` VALUES (1,100,0.99),(5,500,0.5);
/*!40000 ALTER TABLE `fixed_internet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fixed_phone`
--

DROP TABLE IF EXISTS `fixed_phone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fixed_phone` (
  `serviceId` int NOT NULL,
  PRIMARY KEY (`serviceId`),
  CONSTRAINT `FIXED_PHONE_serviceId` FOREIGN KEY (`serviceId`) REFERENCES `service` (`serviceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fixed_phone`
--

LOCK TABLES `fixed_phone` WRITE;
/*!40000 ALTER TABLE `fixed_phone` DISABLE KEYS */;
INSERT INTO `fixed_phone` VALUES (2);
/*!40000 ALTER TABLE `fixed_phone` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mobile_internet`
--

DROP TABLE IF EXISTS `mobile_internet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mobile_internet` (
  `serviceId` int NOT NULL,
  `GBs` int NOT NULL,
  `extraGBFee_euro` float DEFAULT NULL,
  PRIMARY KEY (`serviceId`),
  CONSTRAINT `MOBILE_INTERNET_serviceId` FOREIGN KEY (`serviceId`) REFERENCES `service` (`serviceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mobile_internet`
--

LOCK TABLES `mobile_internet` WRITE;
/*!40000 ALTER TABLE `mobile_internet` DISABLE KEYS */;
INSERT INTO `mobile_internet` VALUES (3,50,1.99),(6,150,1.99);
/*!40000 ALTER TABLE `mobile_internet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mobile_phone`
--

DROP TABLE IF EXISTS `mobile_phone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mobile_phone` (
  `serviceId` int NOT NULL,
  `minutes` int NOT NULL,
  `SMSs` int NOT NULL,
  `extraMinFee_euro` float DEFAULT NULL,
  `extraSMSFee_euro` float DEFAULT NULL,
  PRIMARY KEY (`serviceId`),
  CONSTRAINT `MOBILE_PHONE_serviceId` FOREIGN KEY (`serviceId`) REFERENCES `service` (`serviceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mobile_phone`
--

LOCK TABLES `mobile_phone` WRITE;
/*!40000 ALTER TABLE `mobile_phone` DISABLE KEYS */;
INSERT INTO `mobile_phone` VALUES (4,1000,500,0.15,0.3),(7,100,100,0.1,0.25);
/*!40000 ALTER TABLE `mobile_phone` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mv_avg_amount_op_per_sp`
--

DROP TABLE IF EXISTS `mv_avg_amount_op_per_sp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mv_avg_amount_op_per_sp` (
  `servicePackageId` int NOT NULL,
  `avgAmountOptionalProducts` float NOT NULL,
  PRIMARY KEY (`servicePackageId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mv_avg_amount_op_per_sp`
--

LOCK TABLES `mv_avg_amount_op_per_sp` WRITE;
/*!40000 ALTER TABLE `mv_avg_amount_op_per_sp` DISABLE KEYS */;
INSERT INTO `mv_avg_amount_op_per_sp` VALUES (1,2),(2,1),(3,1.5);
/*!40000 ALTER TABLE `mv_avg_amount_op_per_sp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mv_total_purchases_per_op`
--

DROP TABLE IF EXISTS `mv_total_purchases_per_op`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mv_total_purchases_per_op` (
  `optionalProductId` int NOT NULL,
  `totalPurchases` int NOT NULL,
  PRIMARY KEY (`optionalProductId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mv_total_purchases_per_op`
--

LOCK TABLES `mv_total_purchases_per_op` WRITE;
/*!40000 ALTER TABLE `mv_total_purchases_per_op` DISABLE KEYS */;
INSERT INTO `mv_total_purchases_per_op` VALUES (2,3),(3,3),(5,2);
/*!40000 ALTER TABLE `mv_total_purchases_per_op` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mv_total_purchases_per_sp`
--

DROP TABLE IF EXISTS `mv_total_purchases_per_sp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mv_total_purchases_per_sp` (
  `servicePackageId` int NOT NULL,
  `totalPurchases` int NOT NULL,
  PRIMARY KEY (`servicePackageId`),
  CONSTRAINT `MV_TOTAL_PURCHASES_PER_SERVICE_PACKAGE_servicePackageId` FOREIGN KEY (`servicePackageId`) REFERENCES `service_package` (`servicePackageId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mv_total_purchases_per_sp`
--

LOCK TABLES `mv_total_purchases_per_sp` WRITE;
/*!40000 ALTER TABLE `mv_total_purchases_per_sp` DISABLE KEYS */;
INSERT INTO `mv_total_purchases_per_sp` VALUES (1,2),(2,1),(3,2);
/*!40000 ALTER TABLE `mv_total_purchases_per_sp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mv_total_purchases_per_sp_and_vp`
--

DROP TABLE IF EXISTS `mv_total_purchases_per_sp_and_vp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mv_total_purchases_per_sp_and_vp` (
  `servicePackageId` int NOT NULL,
  `validityPeriodId` int NOT NULL,
  `totalPurchases` int NOT NULL,
  PRIMARY KEY (`servicePackageId`,`validityPeriodId`),
  KEY `MV_TOTAL_PURCHASES_PER_SP_VP_validityPeriodId_idx` (`validityPeriodId`),
  CONSTRAINT `MV_TOTAL_PURCHASES_PER_SP_VP_servicePackageId` FOREIGN KEY (`servicePackageId`) REFERENCES `service_package` (`servicePackageId`),
  CONSTRAINT `MV_TOTAL_PURCHASES_PER_SP_VP_validityPeriodId` FOREIGN KEY (`validityPeriodId`) REFERENCES `validity_period` (`validityPeriodId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mv_total_purchases_per_sp_and_vp`
--

LOCK TABLES `mv_total_purchases_per_sp_and_vp` WRITE;
/*!40000 ALTER TABLE `mv_total_purchases_per_sp_and_vp` DISABLE KEYS */;
INSERT INTO `mv_total_purchases_per_sp_and_vp` VALUES (1,1,1),(1,3,1),(2,1,1),(3,3,2);
/*!40000 ALTER TABLE `mv_total_purchases_per_sp_and_vp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mv_total_value_per_sp`
--

DROP TABLE IF EXISTS `mv_total_value_per_sp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mv_total_value_per_sp` (
  `servicePackageId` int NOT NULL,
  `totalValue_euro` float NOT NULL,
  PRIMARY KEY (`servicePackageId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mv_total_value_per_sp`
--

LOCK TABLES `mv_total_value_per_sp` WRITE;
/*!40000 ALTER TABLE `mv_total_value_per_sp` DISABLE KEYS */;
INSERT INTO `mv_total_value_per_sp` VALUES (1,23.98),(2,13.99),(3,19.98);
/*!40000 ALTER TABLE `mv_total_value_per_sp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mv_total_value_per_sp_with_op`
--

DROP TABLE IF EXISTS `mv_total_value_per_sp_with_op`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mv_total_value_per_sp_with_op` (
  `servicePackageId` int NOT NULL,
  `totalValue_euro` float NOT NULL,
  PRIMARY KEY (`servicePackageId`),
  CONSTRAINT `MV_TOTAL_VALUE_PER_SP_WITH_OP_servicePackageId` FOREIGN KEY (`servicePackageId`) REFERENCES `service_package` (`servicePackageId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mv_total_value_per_sp_with_op`
--

LOCK TABLES `mv_total_value_per_sp_with_op` WRITE;
/*!40000 ALTER TABLE `mv_total_value_per_sp_with_op` DISABLE KEYS */;
INSERT INTO `mv_total_value_per_sp_with_op` VALUES (1,63.94),(2,29.98),(3,73.95);
/*!40000 ALTER TABLE `mv_total_value_per_sp_with_op` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `optional_product`
--

DROP TABLE IF EXISTS `optional_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `optional_product` (
  `optionalProductId` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `monthlyFee_euro` float NOT NULL,
  PRIMARY KEY (`optionalProductId`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `optional_product`
--

LOCK TABLES `optional_product` WRITE;
/*!40000 ALTER TABLE `optional_product` DISABLE KEYS */;
INSERT INTO `optional_product` VALUES (1,'Smartwatch',8.99),(2,'Smartphone',15.99),(3,'Modem',3.99),(4,'Earbuds',4.99),(5,'Smart TV',24.99);
/*!40000 ALTER TABLE `optional_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `orderId` int NOT NULL AUTO_INCREMENT,
  `creationDate` date NOT NULL,
  `creationHour` time NOT NULL,
  `totalCost_euro` float NOT NULL,
  `startDate` date NOT NULL,
  `userId` int NOT NULL,
  `servicePackageId` int NOT NULL,
  `valid` int NOT NULL DEFAULT '0',
  `validityPeriodId` int NOT NULL,
  `amountOptionalProducts` int NOT NULL,
  PRIMARY KEY (`orderId`),
  KEY `userId_idx` (`userId`),
  KEY `ORDER_servicePackageId_idx` (`servicePackageId`),
  KEY `ORDER_validityPeriodId_idx` (`validityPeriodId`),
  CONSTRAINT `ORDER_servicePackageId` FOREIGN KEY (`servicePackageId`) REFERENCES `service_package` (`servicePackageId`),
  CONSTRAINT `ORDER_userId` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`),
  CONSTRAINT `ORDER_validityPeriodId` FOREIGN KEY (`validityPeriodId`) REFERENCES `validity_period` (`validityPeriodId`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci KEY_BLOCK_SIZE=2;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (1,'2022-03-28','09:13:07',33.97,'2022-03-28',6,1,1,1,2),(2,'2022-03-28','09:13:20',29.98,'2022-03-28',6,2,1,1,1),(3,'2022-03-28','09:13:30',38.97,'2022-03-28',6,3,1,3,2),(4,'2022-03-28','09:13:54',29.97,'2022-03-28',6,1,1,3,2),(5,'2022-03-28','09:16:27',43.96,'2022-03-29',7,4,0,1,3),(6,'2022-03-28','09:16:48',27.98,'2022-03-29',7,1,0,2,1),(7,'2022-03-28','09:17:05',34.98,'2022-03-29',7,3,1,3,1);
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order__optional_product`
--

DROP TABLE IF EXISTS `order__optional_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order__optional_product` (
  `orderId` int NOT NULL,
  `optionalProductId` int NOT NULL,
  PRIMARY KEY (`orderId`,`optionalProductId`),
  KEY `ORDER-OPTIONAL_PRODUCT_optionalProductId_idx` (`optionalProductId`),
  CONSTRAINT `ORDER__OPTIONAL_PRODUCT_optionalProductId` FOREIGN KEY (`optionalProductId`) REFERENCES `optional_product` (`optionalProductId`),
  CONSTRAINT `ORDER__OPTIONAL_PRODUCT_orderId` FOREIGN KEY (`orderId`) REFERENCES `order` (`orderId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order__optional_product`
--

LOCK TABLES `order__optional_product` WRITE;
/*!40000 ALTER TABLE `order__optional_product` DISABLE KEYS */;
INSERT INTO `order__optional_product` VALUES (5,1),(1,2),(2,2),(4,2),(5,2),(6,2),(1,3),(3,3),(4,3),(5,4),(3,5),(7,5);
/*!40000 ALTER TABLE `order__optional_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service`
--

DROP TABLE IF EXISTS `service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service` (
  `serviceId` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`serviceId`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service`
--

LOCK TABLES `service` WRITE;
/*!40000 ALTER TABLE `service` DISABLE KEYS */;
INSERT INTO `service` VALUES (1),(2),(3),(4),(5),(6),(7);
/*!40000 ALTER TABLE `service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service_package`
--

DROP TABLE IF EXISTS `service_package`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service_package` (
  `servicePackageId` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`servicePackageId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service_package`
--

LOCK TABLES `service_package` WRITE;
/*!40000 ALTER TABLE `service_package` DISABLE KEYS */;
INSERT INTO `service_package` VALUES (1,'Internet'),(2,'Phone'),(3,'Fixed'),(4,'Mobile');
/*!40000 ALTER TABLE `service_package` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service_package__optional_product`
--

DROP TABLE IF EXISTS `service_package__optional_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service_package__optional_product` (
  `servicePackageId` int NOT NULL,
  `optionalProductId` int NOT NULL,
  PRIMARY KEY (`optionalProductId`,`servicePackageId`),
  KEY `SERVICE_PACKAGE-OPTIONAL_PRODUCT_servicePackageId_idx` (`servicePackageId`),
  KEY `SERVICE_PACKAGE-OPTIONAL_PRODUCT_optionalProductId_idx` (`optionalProductId`),
  CONSTRAINT `SERVICE_PACKAGE__OPTIONAL_PRODUCT_optionalProductId` FOREIGN KEY (`optionalProductId`) REFERENCES `optional_product` (`optionalProductId`),
  CONSTRAINT `SERVICE_PACKAGE__OPTIONAL_PRODUCT_servicePackageId` FOREIGN KEY (`servicePackageId`) REFERENCES `service_package` (`servicePackageId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service_package__optional_product`
--

LOCK TABLES `service_package__optional_product` WRITE;
/*!40000 ALTER TABLE `service_package__optional_product` DISABLE KEYS */;
INSERT INTO `service_package__optional_product` VALUES (1,2),(1,3),(2,2),(2,4),(3,3),(3,5),(4,1),(4,2),(4,4);
/*!40000 ALTER TABLE `service_package__optional_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service_package__service`
--

DROP TABLE IF EXISTS `service_package__service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service_package__service` (
  `servicePackageId` int NOT NULL,
  `serviceId` int NOT NULL,
  PRIMARY KEY (`servicePackageId`,`serviceId`),
  KEY `idService_idx` (`serviceId`),
  KEY `SERVICE_PACKAGE-SERVICE_servicePackageId_idx` (`servicePackageId`),
  CONSTRAINT `SERVICE_PACKAGE__SERVICE_serviceId` FOREIGN KEY (`serviceId`) REFERENCES `service` (`serviceId`),
  CONSTRAINT `SERVICE_PACKAGE__SERVICE_servicePackageId` FOREIGN KEY (`servicePackageId`) REFERENCES `service_package` (`servicePackageId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service_package__service`
--

LOCK TABLES `service_package__service` WRITE;
/*!40000 ALTER TABLE `service_package__service` DISABLE KEYS */;
INSERT INTO `service_package__service` VALUES (1,1),(3,1),(2,2),(3,2),(1,3),(4,3),(2,4),(4,4);
/*!40000 ALTER TABLE `service_package__service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service_package__validity_period`
--

DROP TABLE IF EXISTS `service_package__validity_period`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service_package__validity_period` (
  `servicePackageId` int NOT NULL,
  `validityPeriodId` int NOT NULL,
  PRIMARY KEY (`servicePackageId`,`validityPeriodId`),
  KEY `SERVICE_PACKAGE__VALIDITY_PERIOD_validityPeriodId_idx` (`validityPeriodId`),
  CONSTRAINT `SERVICE_PACKAGE__VALIDITY_PERIOD_servicePackageId` FOREIGN KEY (`servicePackageId`) REFERENCES `service_package` (`servicePackageId`),
  CONSTRAINT `SERVICE_PACKAGE__VALIDITY_PERIOD_validityPeriodId` FOREIGN KEY (`validityPeriodId`) REFERENCES `validity_period` (`validityPeriodId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service_package__validity_period`
--

LOCK TABLES `service_package__validity_period` WRITE;
/*!40000 ALTER TABLE `service_package__validity_period` DISABLE KEYS */;
INSERT INTO `service_package__validity_period` VALUES (1,1),(2,1),(4,1),(1,2),(2,2),(4,2),(1,3),(2,3),(3,3);
/*!40000 ALTER TABLE `service_package__validity_period` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `userId` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `insolvent` int NOT NULL DEFAULT '0',
  `numOfFailedPayments` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`userId`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (6,'nicolo','fontana.nicolo98@gmail.com','c',0,0),(7,'dodo','dodo@gmail.com','1',1,2);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `validity_period`
--

DROP TABLE IF EXISTS `validity_period`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `validity_period` (
  `validityPeriodId` int NOT NULL AUTO_INCREMENT,
  `monthsOfValidity` int NOT NULL,
  `monthlyFee_euro` float NOT NULL,
  PRIMARY KEY (`validityPeriodId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `validity_period`
--

LOCK TABLES `validity_period` WRITE;
/*!40000 ALTER TABLE `validity_period` DISABLE KEYS */;
INSERT INTO `validity_period` VALUES (1,12,13.99),(2,24,11.99),(3,36,9.99);
/*!40000 ALTER TABLE `validity_period` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-03-28  9:23:59
