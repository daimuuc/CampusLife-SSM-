/*
 Navicat Premium Data Transfer

 Source Server         : Ponmma
 Source Server Type    : MySQL
 Source Server Version : 80016
 Source Host           : localhost:3306
 Source Schema         : campus_life

 Target Server Type    : MySQL
 Target Server Version : 80016
 File Encoding         : 65001

 Date: 14/11/2019 21:27:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_area
-- ----------------------------
DROP TABLE IF EXISTS `tb_area`;
CREATE TABLE `tb_area` (
  `id` int(3) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `last_edit_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_area` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_cartinfo
-- ----------------------------
DROP TABLE IF EXISTS `tb_cartinfo`;
CREATE TABLE `tb_cartinfo` (
  `id` int(3) NOT NULL AUTO_INCREMENT,
  `person_info_id` int(3) NOT NULL,
  `product_info_id` int(3) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `product_info_id` (`product_info_id`),
  KEY `fk_person_info` (`person_info_id`),
  CONSTRAINT `fk_person_info` FOREIGN KEY (`person_info_id`) REFERENCES `tb_personinfo` (`id`),
  CONSTRAINT `fk_product` FOREIGN KEY (`product_info_id`) REFERENCES `tb_productinfo` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_headline
-- ----------------------------
DROP TABLE IF EXISTS `tb_headline`;
CREATE TABLE `tb_headline` (
  `id` int(3) NOT NULL AUTO_INCREMENT,
  `last_edit_time` datetime NOT NULL,
  `image_id` int(3) NOT NULL,
  `shop_info_id` int(3) NOT NULL,
  `status` int(3) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_headline_image` (`image_id`),
  KEY `fk_headline_shop` (`shop_info_id`),
  CONSTRAINT `fk_headline_image` FOREIGN KEY (`image_id`) REFERENCES `tb_single_imageinfo` (`id`),
  CONSTRAINT `fk_headline_shop` FOREIGN KEY (`shop_info_id`) REFERENCES `tb_shopinfo` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_mutiple_imageinfo
-- ----------------------------
DROP TABLE IF EXISTS `tb_mutiple_imageinfo`;
CREATE TABLE `tb_mutiple_imageinfo` (
  `id` int(3) NOT NULL AUTO_INCREMENT,
  `src` varchar(100) NOT NULL,
  `product_info_id` int(3) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_product_info` (`product_info_id`),
  CONSTRAINT `fk_product_info` FOREIGN KEY (`product_info_id`) REFERENCES `tb_productinfo` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_orderinfo
-- ----------------------------
DROP TABLE IF EXISTS `tb_orderinfo`;
CREATE TABLE `tb_orderinfo` (
  `id` int(3) NOT NULL AUTO_INCREMENT,
  `person_info_id` int(3) NOT NULL,
  `product_info_id` int(3) NOT NULL,
  `shop_info_id` int(3) NOT NULL,
  `status` int(3) NOT NULL,
  `last_edit_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_order_person` (`person_info_id`),
  KEY `fk_order_product` (`product_info_id`),
  KEY `fk_order_shop` (`shop_info_id`),
  CONSTRAINT `fk_order_person` FOREIGN KEY (`person_info_id`) REFERENCES `tb_personinfo` (`id`),
  CONSTRAINT `fk_order_product` FOREIGN KEY (`product_info_id`) REFERENCES `tb_productinfo` (`id`),
  CONSTRAINT `fk_order_shop` FOREIGN KEY (`shop_info_id`) REFERENCES `tb_shopinfo` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_personinfo
-- ----------------------------
DROP TABLE IF EXISTS `tb_personinfo`;
CREATE TABLE `tb_personinfo` (
  `id` int(3) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `password` varchar(50) NOT NULL,
  `role` int(3) NOT NULL,
  `image_id` int(3) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone` (`phone`,`role`),
  KEY `fk_personinfo` (`image_id`),
  CONSTRAINT `fk_personinfo` FOREIGN KEY (`image_id`) REFERENCES `tb_single_imageinfo` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_product_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_product_category`;
CREATE TABLE `tb_product_category` (
  `id` int(3) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `last_edit_time` datetime NOT NULL,
  `shop_info_id` int(3) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_shop_info` (`shop_info_id`),
  CONSTRAINT `fk_shop_info` FOREIGN KEY (`shop_info_id`) REFERENCES `tb_shopinfo` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_productinfo
-- ----------------------------
DROP TABLE IF EXISTS `tb_productinfo`;
CREATE TABLE `tb_productinfo` (
  `id` int(3) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `last_edit_time` datetime NOT NULL,
  `shop_info_id` int(3) NOT NULL,
  `des` varchar(100) NOT NULL,
  `image_id` int(3) NOT NULL,
  `normal_price` int(3) NOT NULL,
  `promotion_price` int(3) NOT NULL,
  `point` int(5) NOT NULL,
  `enable_status` int(3) NOT NULL,
  `product_category_id` int(3) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_shop_info_pi` (`shop_info_id`),
  KEY `fk_image_pi` (`image_id`),
  KEY `fk_product_category_pi` (`product_category_id`),
  CONSTRAINT `fk_image_pi` FOREIGN KEY (`image_id`) REFERENCES `tb_single_imageinfo` (`id`),
  CONSTRAINT `fk_product_category_pi` FOREIGN KEY (`product_category_id`) REFERENCES `tb_product_category` (`id`),
  CONSTRAINT `fk_shop_info_pi` FOREIGN KEY (`shop_info_id`) REFERENCES `tb_shopinfo` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_shop_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_shop_category`;
CREATE TABLE `tb_shop_category` (
  `id` int(3) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `last_edit_time` datetime NOT NULL,
  `image_id` int(3) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_shop_category` (`name`),
  KEY `fk_shop_category` (`image_id`),
  CONSTRAINT `fk_shop_category` FOREIGN KEY (`image_id`) REFERENCES `tb_single_imageinfo` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_shopinfo
-- ----------------------------
DROP TABLE IF EXISTS `tb_shopinfo`;
CREATE TABLE `tb_shopinfo` (
  `id` int(3) NOT NULL AUTO_INCREMENT,
  `des` varchar(100) NOT NULL,
  `addr` varchar(100) NOT NULL,
  `person_info_id` int(3) NOT NULL,
  `shop_category_id` int(3) NOT NULL,
  `area_info_id` int(3) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_personinfo_p` (`person_info_id`),
  KEY `fk_personinfo_s` (`shop_category_id`),
  KEY `fk_personinfo_a` (`area_info_id`),
  CONSTRAINT `fk_personinfo_a` FOREIGN KEY (`area_info_id`) REFERENCES `tb_area` (`id`),
  CONSTRAINT `fk_personinfo_p` FOREIGN KEY (`person_info_id`) REFERENCES `tb_personinfo` (`id`),
  CONSTRAINT `fk_personinfo_s` FOREIGN KEY (`shop_category_id`) REFERENCES `tb_shop_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_single_imageinfo
-- ----------------------------
DROP TABLE IF EXISTS `tb_single_imageinfo`;
CREATE TABLE `tb_single_imageinfo` (
  `id` int(3) NOT NULL AUTO_INCREMENT,
  `src` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
