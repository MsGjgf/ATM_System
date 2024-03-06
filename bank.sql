/*
 Navicat Premium Data Transfer

 Source Server         : MySQL8.0
 Source Server Type    : MySQL
 Source Server Version : 80033
 Source Host           : localhost:3306
 Source Schema         : bank

 Target Server Type    : MySQL
 Target Server Version : 80033
 File Encoding         : 65001

 Date: 06/03/2024 17:27:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admininfo
-- ----------------------------
DROP TABLE IF EXISTS `admininfo`;
CREATE TABLE `admininfo`  (
  `admin` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `adminPwd` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '123456',
  PRIMARY KEY (`admin`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admininfo
-- ----------------------------
INSERT INTO `admininfo` VALUES ('admin', '123456');

-- ----------------------------
-- Table structure for cardinfo
-- ----------------------------
DROP TABLE IF EXISTS `cardinfo`;
CREATE TABLE `cardinfo`  (
  `cardID` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `status` tinyint NOT NULL DEFAULT 1,
  `savingName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `openDate` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `balance` decimal(10, 2) NOT NULL,
  `pwd` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '000000',
  `personID` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`cardID`) USING BTREE,
  INDEX `savingName`(`savingName` ASC) USING BTREE,
  INDEX `personID`(`personID` ASC) USING BTREE,
  CONSTRAINT `cardinfo_ibfk_1` FOREIGN KEY (`savingName`) REFERENCES `deposit` (`savingName`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `cardinfo_ibfk_2` FOREIGN KEY (`personID`) REFERENCES `userinfo` (`personID`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `cardinfo_chk_1` CHECK (`balance` >= 0),
  CONSTRAINT `CK_CARDID` CHECK (regexp_like(`cardID`,_utf8mb4'^62228888[0-9]{4}$')),
  CONSTRAINT `CK_PWD` CHECK (regexp_like(`pwd`,_utf8mb4'^[0-9]{6}$'))
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cardinfo
-- ----------------------------
INSERT INTO `cardinfo` VALUES ('622288882328', 1, '定期', '2023-06-21 06:27:13', 0.01, '000000', '450722202312345678');
INSERT INTO `cardinfo` VALUES ('622288884967', 1, '定期', '2023-06-21 14:28:43', 0.00, '000000', '450722202312345678');
INSERT INTO `cardinfo` VALUES ('622288888259', 0, '定期', '2023-06-21 06:54:12', 0.00, '000000', '450722202312345678');

-- ----------------------------
-- Table structure for deposit
-- ----------------------------
DROP TABLE IF EXISTS `deposit`;
CREATE TABLE `deposit`  (
  `savingID` int NOT NULL AUTO_INCREMENT,
  `savingName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`savingID`) USING BTREE,
  UNIQUE INDEX `savingName`(`savingName` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of deposit
-- ----------------------------
INSERT INTO `deposit` VALUES (4, '定期');

-- ----------------------------
-- Table structure for tradeinfo
-- ----------------------------
DROP TABLE IF EXISTS `tradeinfo`;
CREATE TABLE `tradeinfo`  (
  `transDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `cardID` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `transType` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `transMoney` decimal(10, 2) NOT NULL,
  INDEX `cardID`(`cardID` ASC) USING BTREE,
  CONSTRAINT `tradeinfo_ibfk_1` FOREIGN KEY (`cardID`) REFERENCES `cardinfo` (`cardID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `CK_TRANSTYPE` CHECK (`transtype` in (_utf8mb4'支出',_utf8mb4'存入')),
  CONSTRAINT `tradeinfo_chk_1` CHECK (`transMoney` > 0)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tradeinfo
-- ----------------------------
INSERT INTO `tradeinfo` VALUES ('2023-06-21 07:29:29', '622288882328', '存入', 100.00);
INSERT INTO `tradeinfo` VALUES ('2023-06-21 07:32:15', '622288882328', '存入', 10000000.00);
INSERT INTO `tradeinfo` VALUES ('2023-06-21 07:33:26', '622288882328', '存入', 9999999.00);
INSERT INTO `tradeinfo` VALUES ('2023-06-21 07:36:55', '622288882328', '支出', 100.00);
INSERT INTO `tradeinfo` VALUES ('2023-06-21 07:37:12', '622288882328', '支出', 2000.00);
INSERT INTO `tradeinfo` VALUES ('2023-06-21 07:37:32', '622288882328', '支出', 50000.00);
INSERT INTO `tradeinfo` VALUES ('2023-06-21 07:37:38', '622288882328', '支出', 100000.00);
INSERT INTO `tradeinfo` VALUES ('2023-06-21 07:42:02', '622288882328', '存入', 100000.00);
INSERT INTO `tradeinfo` VALUES ('2023-06-30 02:55:28', '622288882328', '支出', 100000.00);
INSERT INTO `tradeinfo` VALUES ('2023-07-10 16:43:51', '622288882328', '支出', 0.01);
INSERT INTO `tradeinfo` VALUES ('2023-07-10 16:44:08', '622288882328', '存入', 0.01);
INSERT INTO `tradeinfo` VALUES ('2024-03-06 17:23:12', '622288882328', '存入', 50.00);
INSERT INTO `tradeinfo` VALUES ('2024-03-06 17:23:23', '622288882328', '支出', 50.00);

-- ----------------------------
-- Table structure for userinfo
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo`  (
  `personID` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `customerName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `telephone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`personID`) USING BTREE,
  CONSTRAINT `CK_PERSONID` CHECK (regexp_like(`personid`,_utf8mb4'^[0-9]{17}[0-9,x]$')),
  CONSTRAINT `CK_TELEPHONE` CHECK (regexp_like(`telephone`,_utf8mb4'^1[3,4,5,7,8][0-9]{8}$|^[0-9]{3,4}[0-9]{8}$'))
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of userinfo
-- ----------------------------
INSERT INTO `userinfo` VALUES ('450722202312345678', '张三', '13456789100', '桂林信息科技学院');

-- ----------------------------
-- Procedure structure for cardid
-- ----------------------------
DROP PROCEDURE IF EXISTS `cardid`;
delimiter ;;
CREATE PROCEDURE `cardid`(in savingName VARCHAR(20),in pwd VARCHAR(6),in personID VARCHAR(18),out cardID VARCHAR(12))
BEGIN
	SET cardID = CONCAT('6222','8888',FLOOR(RAND()*(9999-1000)+1000));
	INSERT INTO cardinfo VALUES(cardID,1,savingName,NOW(),0,pwd,personID);
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table deposit
-- ----------------------------
DROP TRIGGER IF EXISTS `savingid_update`;
delimiter ;;
CREATE TRIGGER `savingid_update` AFTER UPDATE ON `deposit` FOR EACH ROW BEGIN
	UPDATE cardinfo set savingName = new.savingName WHERE savingName = old.savingName;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
