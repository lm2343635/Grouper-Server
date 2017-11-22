SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for grouper_group
-- ----------------------------
DROP TABLE IF EXISTS `grouper_group`;
CREATE TABLE `grouper_group` (
  `gid` varchar(255) NOT NULL,
  `createDate` bigint(20) NOT NULL,
  `gname` varchar(255) NOT NULL,
  `id` varchar(255) NOT NULL,
  `intervalTime` int(11) DEFAULT NULL,
  `masterkey` varchar(255) NOT NULL,
  `members` int(11) NOT NULL,
  `servers` int(11) DEFAULT NULL,
  `threshold` int(11) DEFAULT NULL,
  `owner` varchar(255) DEFAULT NULL,
  `createAt` bigint(20) NOT NULL,
  `safe` int(11) DEFAULT NULL,
  PRIMARY KEY (`gid`),
  UNIQUE KEY `UK_sqgv8vcr0kl71p8djh6097wtx` (`owner`),
  CONSTRAINT `FK_sqgv8vcr0kl71p8djh6097wtx` FOREIGN KEY (`owner`) REFERENCES `grouper_user` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;;

-- ----------------------------
-- Table structure for grouper_transfer
-- ----------------------------
DROP TABLE IF EXISTS `grouper_transfer`;
CREATE TABLE `grouper_transfer` (
  `tid` varchar(255) NOT NULL,
  `messageId` varchar(255) NOT NULL,
  `savetime` bigint(20) NOT NULL,
  `share` text NOT NULL,
  `receiver` varchar(255) DEFAULT NULL,
  `sender` varchar(255) NOT NULL,
  PRIMARY KEY (`tid`),
  UNIQUE KEY `UK_jnbdrp5qscigk564dvo06n2m7` (`messageId`),
  KEY `FK_rnhekq1i8o0t8359nprnkf74u` (`receiver`),
  KEY `FK_3fsniemjeiet55sinwrhi82pp` (`sender`),
  CONSTRAINT `FK_3fsniemjeiet55sinwrhi82pp` FOREIGN KEY (`sender`) REFERENCES `grouper_user` (`uid`),
  CONSTRAINT `FK_rnhekq1i8o0t8359nprnkf74u` FOREIGN KEY (`receiver`) REFERENCES `grouper_user` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;;

-- ----------------------------
-- Table structure for grouper_user
-- ----------------------------
DROP TABLE IF EXISTS `grouper_user`;
CREATE TABLE `grouper_user` (
  `uid` varchar(255) NOT NULL,
  `accesskey` varchar(255) NOT NULL,
  `deviceToken` varchar(255) DEFAULT NULL,
  `node` varchar(255) NOT NULL,
  `gid` varchar(255) NOT NULL,
  PRIMARY KEY (`uid`),
  KEY `FK_qweh08t9n5kmaj6yqsw1a3n17` (`gid`),
  CONSTRAINT `FK_qweh08t9n5kmaj6yqsw1a3n17` FOREIGN KEY (`gid`) REFERENCES `grouper_group` (`gid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;;

SET FOREIGN_KEY_CHECKS = 1;
