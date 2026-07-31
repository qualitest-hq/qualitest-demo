-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: qualitest-demo
-- ------------------------------------------------------
-- Server version	8.0.46

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `account_id` bigint NOT NULL AUTO_INCREMENT COMMENT '账号ID',
  `nick_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '昵称',
  `mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '手机号',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '密码',
  `balance` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '账户余额',
  `gender` tinyint DEFAULT '2' COMMENT '性别（0男 1女 2未知）',
  `register_source` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'app' COMMENT '注册来源',
  `status` tinyint DEFAULT '0' COMMENT '账号状态（0正常 1停用）',
  `last_login_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '最后登录IP',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `del_flag` tinyint DEFAULT '0' COMMENT '删除标志（0未删除 1已删除）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`account_id`),
  KEY `idx_mobile` (`mobile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户账号';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `account_address`
--

DROP TABLE IF EXISTS `account_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account_address` (
  `address_id` bigint NOT NULL AUTO_INCREMENT COMMENT '地址ID',
  `account_id` bigint DEFAULT '0' COMMENT '账号ID',
  `receiver_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '收货人姓名',
  `receiver_phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '收货人手机',
  `province` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '省',
  `city` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '市',
  `district` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '区/县',
  `detail_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '详细地址',
  `postal_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '邮政编码',
  `is_default` tinyint DEFAULT '0' COMMENT '是否默认（0否 1是）',
  `del_flag` tinyint DEFAULT '0' COMMENT '删除标志（0未删除 1已删除）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`address_id`),
  KEY `idx_account_id` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='账号收货地址';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_address`
--

LOCK TABLES `account_address` WRITE;
/*!40000 ALTER TABLE `account_address` DISABLE KEYS */;
/*!40000 ALTER TABLE `account_address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `account_balance_record`
--

DROP TABLE IF EXISTS `account_balance_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account_balance_record` (
  `balance_record_id` bigint NOT NULL COMMENT '流水ID',
  `account_id` bigint NOT NULL DEFAULT '0' COMMENT '账号ID',
  `record_type` tinyint NOT NULL DEFAULT '0' COMMENT '流水类型（1充值 2赠送 3订单支付 4订单退款）',
  `change_type` tinyint NOT NULL DEFAULT '0' COMMENT '变动方向（1收入 2支出）',
  `change_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '变动金额（正数）',
  `balance_before` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '变动前余额',
  `balance_after` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '变动后余额',
  `biz_id` bigint NOT NULL DEFAULT '0' COMMENT '关联业务ID',
  `biz_no` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '业务单号',
  `del_flag` tinyint DEFAULT '0' COMMENT '删除标志（0未删除 1已删除）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`balance_record_id`),
  KEY `idx_account_id` (`account_id`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='账号余额流水';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_balance_record`
--

LOCK TABLES `account_balance_record` WRITE;
/*!40000 ALTER TABLE `account_balance_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `account_balance_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `account_coupon`
--

DROP TABLE IF EXISTS `account_coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account_coupon` (
  `account_coupon_id` bigint NOT NULL AUTO_INCREMENT COMMENT '账号优惠券ID',
  `coupon_id` bigint DEFAULT '0' COMMENT '优惠券ID',
  `account_id` bigint DEFAULT '0' COMMENT '账号ID',
  `coupon_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '券名称',
  `threshold_amount` decimal(10,2) DEFAULT '0.00' COMMENT '满减门槛',
  `discount_amount` decimal(10,2) DEFAULT '0.00' COMMENT '满减金额',
  `valid_start_time` datetime DEFAULT NULL COMMENT '有效开始',
  `valid_end_time` datetime DEFAULT NULL COMMENT '有效结束',
  `coupon_status` tinyint DEFAULT '0' COMMENT '状态（0未使用 1已使用 2已过期）',
  `receive_time` datetime DEFAULT NULL COMMENT '领取时间',
  `use_time` datetime DEFAULT NULL COMMENT '使用时间',
  `order_id` bigint DEFAULT '0' COMMENT '使用订单ID',
  `del_flag` tinyint DEFAULT '0' COMMENT '删除标志（0未删除 1已删除）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`account_coupon_id`),
  UNIQUE KEY `uk_account_coupon` (`account_id`,`coupon_id`),
  KEY `idx_coupon_id` (`coupon_id`),
  KEY `idx_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='账号优惠券';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_coupon`
--

LOCK TABLES `account_coupon` WRITE;
/*!40000 ALTER TABLE `account_coupon` DISABLE KEYS */;
/*!40000 ALTER TABLE `account_coupon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coupon`
--

DROP TABLE IF EXISTS `coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coupon` (
  `coupon_id` bigint NOT NULL AUTO_INCREMENT COMMENT '优惠券ID',
  `coupon_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '优惠券名称',
  `threshold_amount` decimal(10,2) DEFAULT '0.00' COMMENT '满金额',
  `discount_amount` decimal(10,2) DEFAULT '0.00' COMMENT '减金额',
  `total_count` int DEFAULT '0' COMMENT '发放总量',
  `receive_count` int DEFAULT '0' COMMENT '已领取数量',
  `used_count` int DEFAULT '0' COMMENT '已使用数量',
  `valid_start_time` datetime DEFAULT NULL COMMENT '有效开始时间',
  `valid_end_time` datetime DEFAULT NULL COMMENT '有效结束时间',
  `status` tinyint DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `del_flag` tinyint DEFAULT '0' COMMENT '删除标志（0未删除 1已删除）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`coupon_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='优惠券';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coupon`
--

LOCK TABLES `coupon` WRITE;
/*!40000 ALTER TABLE `coupon` DISABLE KEYS */;
/*!40000 ALTER TABLE `coupon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gen_table`
--

DROP TABLE IF EXISTS `gen_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gen_table` (
  `table_id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '表名称',
  `table_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '表描述',
  `sub_table_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '关联子表的表名',
  `sub_table_fk_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '子表关联的外键名',
  `class_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '实体类名称',
  `tpl_category` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'crud' COMMENT '使用的模板（crud单表操作 tree树表操作）',
  `tpl_web_type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '前端模板类型（element-ui模版 element-plus模版）',
  `package_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '生成包路径',
  `module_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '生成模块名',
  `business_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '生成业务名',
  `function_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '生成功能名',
  `function_author` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '生成功能作者',
  `form_col_num` int DEFAULT '1' COMMENT '表单布局（单列 双列 三列）',
  `gen_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '生成代码方式（0zip压缩包 1自定义路径）',
  `gen_path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '/' COMMENT '生成路径（不填默认项目路径）',
  `options` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '其它生成选项',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`table_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='代码生成业务表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gen_table`
--

LOCK TABLES `gen_table` WRITE;
/*!40000 ALTER TABLE `gen_table` DISABLE KEYS */;
INSERT INTO `gen_table` VALUES (2,'account','用户账号',NULL,NULL,'Account','crud','element-plus','com.demo.account','account','account','用户账号','demo',1,'0','/','{\"genView\":\"0\",\"parentMenuId\":2000}','admin','2026-06-20 18:35:09','','2026-06-20 21:14:07',NULL),(3,'account_address','账号收货地址',NULL,NULL,'AccountAddress','crud','element-plus','com.demo.mall','mall','accountAddress','账号收货地址','demo',1,'0','/','{\"genView\":\"0\",\"parentMenuId\":2000}','admin','2026-06-20 18:35:09','','2026-06-20 21:13:54',NULL),(4,'mall_cart','商城购物车',NULL,NULL,'MallCart','crud','element-plus','com.demo.mall','mall','mallCart','商城购物车','demo',1,'0','/','{\"genView\":\"0\",\"parentMenuId\":2001}','admin','2026-06-20 18:35:09','','2026-06-20 21:13:46',NULL),(5,'mall_category','商城商品分类',NULL,NULL,'MallCategory','crud','element-plus','com.demo.mall','mall','mallCategory','商城商品分类','demo',1,'0','/','{\"genView\":\"0\",\"parentMenuId\":2001}','admin','2026-06-20 18:35:09','','2026-06-20 21:13:37',NULL),(6,'mall_order','商城订单',NULL,NULL,'MallOrder','crud','element-plus','com.demo.mall','mall','mallOrder','商城订单','demo',1,'0','/','{\"genView\":\"0\",\"parentMenuId\":2001}','admin','2026-06-20 18:35:09','','2026-06-20 21:13:27',NULL),(7,'mall_order_item','商城订单明细',NULL,NULL,'MallOrderItem','crud','element-plus','com.demo.mall','mall','mallOrderItem','商城订单明细','demo',1,'0','/','{\"genView\":\"0\",\"parentMenuId\":2001}','admin','2026-06-20 18:35:09','','2026-06-20 21:13:17',NULL),(8,'mall_order_refund','商城-退款单',NULL,NULL,'MallOrderRefund','crud','element-plus','com.demo.mall','mall','mallOrderRefund','商城退款单','demo',1,'0','/','{\"genView\":\"0\",\"parentMenuId\":2001}','admin','2026-06-20 18:35:10','','2026-06-20 21:14:33',NULL),(9,'mall_order_refund_item','商城-退款明细',NULL,NULL,'MallOrderRefundItem','crud','element-plus','com.demo.mall','mall','mallOrderRefundItem','商城退款明细','demo',1,'0','/','{\"genView\":\"0\",\"parentMenuId\":2001}','admin','2026-06-20 18:35:10','','2026-06-20 21:14:28',NULL),(10,'mall_product','商城商品SPU',NULL,NULL,'MallProduct','crud','element-plus','com.demo.mall','mall','mallProduct','商城商品SPU','demo',1,'0','/','{\"genView\":\"0\",\"parentMenuId\":2001}','admin','2026-06-20 18:35:10','','2026-06-20 21:14:20',NULL),(11,'mall_product_sku','商城商品SKU',NULL,NULL,'MallProductSku','crud','element-plus','com.demo.mall','mall','mallProductSku','商城商品SKU','demo',1,'0','/','{\"genView\":\"0\",\"parentMenuId\":2001}','admin','2026-06-20 18:35:10','','2026-06-20 21:14:14',NULL),(12,'account_coupon','账号优惠券',NULL,NULL,'AccountCoupon','crud','element-plus','com.demo.mall','mall','accountCoupon','账号优惠券','demo',1,'0','/','{\"genView\":\"0\",\"parentMenuId\":2000}','admin','2026-06-20 18:35:19','','2026-06-20 21:14:45',NULL),(13,'coupon','优惠券',NULL,NULL,'Coupon','crud','element-plus','com.demo.mall','mall','coupon','优惠券','demo',1,'0','/','{\"genView\":\"0\",\"parentMenuId\":2001}','admin','2026-06-20 18:35:19','','2026-06-20 21:14:39',NULL);
/*!40000 ALTER TABLE `gen_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gen_table_column`
--

DROP TABLE IF EXISTS `gen_table_column`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gen_table_column` (
  `column_id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_id` bigint DEFAULT NULL COMMENT '归属表编号',
  `column_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '列名称',
  `column_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '列描述',
  `column_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '列类型',
  `java_type` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'JAVA字段名',
  `is_pk` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否主键（1是）',
  `is_increment` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否自增（1是）',
  `is_required` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否必填（1是）',
  `is_insert` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否为插入字段（1是）',
  `is_edit` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否编辑字段（1是）',
  `is_list` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否列表字段（1是）',
  `is_query` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否查询字段（1是）',
  `query_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
  `html_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  `dict_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '字典类型',
  `sort` int DEFAULT NULL COMMENT '排序',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`column_id`)
) ENGINE=InnoDB AUTO_INCREMENT=201 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='代码生成业务表字段';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gen_table_column`
--

LOCK TABLES `gen_table_column` WRITE;
/*!40000 ALTER TABLE `gen_table_column` DISABLE KEYS */;
INSERT INTO `gen_table_column` VALUES (21,2,'account_id','账号ID','bigint','Long','accountId','1','1','0','1',NULL,NULL,NULL,'EQ','input','',1,'admin','2026-06-20 18:35:09','','2026-06-20 21:14:07'),(23,2,'nick_name','昵称','varchar(64)','String','nickName','0','0','0','1','1','1','1','LIKE','input','',2,'admin','2026-06-20 18:35:09','','2026-06-20 21:14:07'),(24,2,'mobile','手机号','varchar(11)','String','mobile','0','0','0','1','1','1','1','LIKE','input','',3,'admin','2026-06-20 18:35:09','','2026-06-20 21:14:07'),(25,2,'password','密码','varchar(100)','String','password','0','0','0','1','1','1','0','EQ','input','',4,'admin','2026-06-20 18:35:09','','2026-06-20 21:14:07'),(26,2,'gender','性别（0男 1女 2未知）','tinyint','Integer','gender','0','0','0','1','1','1','1','EQ','input','',5,'admin','2026-06-20 18:35:09','','2026-06-20 21:14:07'),(27,2,'register_source','注册来源','varchar(32)','String','registerSource','0','0','0','1','1','1','1','EQ','input','',6,'admin','2026-06-20 18:35:09','','2026-06-20 21:14:07'),(28,2,'status','账号状态（0正常 1停用）','tinyint','Integer','status','0','0','0','1','1','1','1','EQ','radio','',7,'admin','2026-06-20 18:35:09','','2026-06-20 21:14:07'),(29,2,'last_login_ip','最后登录IP','varchar(128)','String','lastLoginIp','0','0','0','1','1','1','0','EQ','input','',8,'admin','2026-06-20 18:35:09','','2026-06-20 21:14:07'),(30,2,'last_login_time','最后登录时间','datetime','Date','lastLoginTime','0','0','0','1','1','1','0','EQ','datetime','',9,'admin','2026-06-20 18:35:09','','2026-06-20 21:14:07'),(31,2,'del_flag','删除标志（0未删除 1已删除）','tinyint','Integer','delFlag','0','0','0',NULL,NULL,NULL,NULL,'EQ','input','',10,'admin','2026-06-20 18:35:09','','2026-06-20 21:14:07'),(32,2,'create_time','创建时间','datetime','Date','createTime','0','0','0','1',NULL,NULL,NULL,'EQ','datetime','',11,'admin','2026-06-20 18:35:09','','2026-06-20 21:14:07'),(33,2,'update_time','更新时间','datetime','Date','updateTime','0','0','0','1','1',NULL,NULL,'EQ','datetime','',12,'admin','2026-06-20 18:35:09','','2026-06-20 21:14:07'),(34,2,'remark','备注','varchar(500)','String','remark','0','0','0','1','1','1',NULL,'EQ','textarea','',13,'admin','2026-06-20 18:35:09','','2026-06-20 21:14:07'),(35,3,'address_id','地址ID','bigint','Long','addressId','1','1','0','1',NULL,NULL,NULL,'EQ','input','',1,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:54'),(36,3,'account_id','账号ID','bigint','Long','accountId','0','0','0','1','1','1','1','EQ','input','',2,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:54'),(37,3,'receiver_name','收货人姓名','varchar(32)','String','receiverName','0','0','0','1','1','1','1','LIKE','input','',3,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:54'),(38,3,'receiver_phone','收货人手机','varchar(11)','String','receiverPhone','0','0','0','1','1','1','1','LIKE','input','',4,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:54'),(39,3,'province','省','varchar(32)','String','province','0','0','0','1','1','1','0','EQ','input','',5,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:54'),(40,3,'city','市','varchar(32)','String','city','0','0','0','1','1','1','0','EQ','input','',6,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:54'),(41,3,'district','区/县','varchar(32)','String','district','0','0','0','1','1','1','0','EQ','input','',7,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:54'),(42,3,'detail_address','详细地址','varchar(255)','String','detailAddress','0','0','0','1','1','1','0','EQ','input','',8,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:54'),(43,3,'postal_code','邮政编码','varchar(10)','String','postalCode','0','0','0','1','1','1','0','EQ','input','',9,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:54'),(44,3,'is_default','是否默认（0否 1是）','tinyint','Integer','isDefault','0','0','0','1','1','1','1','EQ','input','',10,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:54'),(45,3,'del_flag','删除标志（0未删除 1已删除）','tinyint','Integer','delFlag','0','0','0',NULL,NULL,NULL,NULL,'EQ','input','',11,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:54'),(46,3,'create_time','创建时间','datetime','Date','createTime','0','0','0','1',NULL,NULL,NULL,'EQ','datetime','',12,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:54'),(47,3,'update_time','更新时间','datetime','Date','updateTime','0','0','0','1','1',NULL,NULL,'EQ','datetime','',13,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:54'),(48,3,'remark','备注','varchar(500)','String','remark','0','0','0','1','1','1',NULL,'EQ','textarea','',14,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:54'),(49,4,'cart_id','购物车ID','bigint','Long','cartId','1','0','0','1',NULL,NULL,NULL,'EQ','input','',1,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:46'),(50,4,'account_id','账号ID','bigint','Long','accountId','0','0','0','1','1','1','1','EQ','input','',2,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:46'),(51,4,'product_id','商品ID','bigint','Long','productId','0','0','0','1','1','1','1','EQ','input','',3,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:46'),(52,4,'sku_id','SKU ID','bigint','Long','skuId','0','0','0','1','1','1','1','EQ','input','',4,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:46'),(53,4,'quantity','数量','int','Integer','quantity','0','0','0','1','1','1','0','EQ','input','',5,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:46'),(55,4,'del_flag','删除标志（0未删除 1已删除）','tinyint','Integer','delFlag','0','0','0',NULL,NULL,NULL,NULL,'EQ','input','',6,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:47'),(56,4,'create_time','创建时间','datetime','Date','createTime','0','0','0','1',NULL,NULL,NULL,'EQ','datetime','',7,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:47'),(57,4,'update_time','更新时间','datetime','Date','updateTime','0','0','0','1','1',NULL,NULL,'EQ','datetime','',8,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:47'),(58,5,'category_id','分类ID','bigint','Long','categoryId','1','0','0','1',NULL,NULL,NULL,'EQ','input','',1,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:37'),(59,5,'category_name','分类名称','varchar(64)','String','categoryName','0','0','0','1','1','1','1','LIKE','input','',2,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:37'),(61,5,'order_num','显示顺序','int','Integer','orderNum','0','0','0','1','1','1','0','EQ','input','',3,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:37'),(62,5,'status','状态（0正常 1停用）','tinyint','Integer','status','0','0','0','1','1','1','1','EQ','radio','',4,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:37'),(63,5,'del_flag','删除标志（0未删除 1已删除）','tinyint','Integer','delFlag','0','0','0',NULL,NULL,NULL,NULL,'EQ','input','',5,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:37'),(64,5,'create_time','创建时间','datetime','Date','createTime','0','0','0','1',NULL,NULL,NULL,'EQ','datetime','',6,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:37'),(65,5,'update_time','更新时间','datetime','Date','updateTime','0','0','0','1','1',NULL,NULL,'EQ','datetime','',7,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:37'),(66,5,'remark','备注','varchar(500)','String','remark','0','0','0','1','1','1',NULL,'EQ','textarea','',8,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:37'),(67,6,'order_id','订单ID','bigint','Long','orderId','1','0','0','1',NULL,NULL,NULL,'EQ','input','',1,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:27'),(68,6,'order_no','订单编号','varchar(32)','String','orderNo','0','0','0','1','1','1','1','EQ','input','',2,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:27'),(69,6,'account_id','账号ID','bigint','Long','accountId','0','0','0','1','1','1','1','EQ','input','',3,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:27'),(70,6,'order_status','订单状态（0待付款 1待发货 2待收货 3已完成 4已取消 5退款中）','tinyint','Long','orderStatus','0','0','0','1','1','1','1','EQ','radio','',4,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:27'),(71,6,'total_amount','商品总金额','decimal(10,2)','BigDecimal','totalAmount','0','0','0','1','1','1','0','EQ','input','',5,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:27'),(72,6,'discount_amount','优惠金额','decimal(10,2)','BigDecimal','discountAmount','0','0','0','1','1','1','0','EQ','input','',6,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:27'),(73,6,'coupon_id','优惠券ID','bigint','Long','couponId','0','0','0','1','1','1','1','EQ','input','',7,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:27'),(74,6,'coupon_name','券名称','varchar(64)','String','couponName','0','0','0','1','1','1','0','LIKE','input','',8,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:27'),(75,6,'coupon_threshold_amount','满减门槛','decimal(10,2)','BigDecimal','couponThresholdAmount','0','0','0','1','1','1','0','EQ','input','',9,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:27'),(76,6,'coupon_discount_amount','满减面额','decimal(10,2)','BigDecimal','couponDiscountAmount','0','0','0','1','1','1','0','EQ','input','',10,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:27'),(77,6,'coupon_amount','实际抵扣金额','decimal(10,2)','BigDecimal','couponAmount','0','0','0','1','1','1','0','EQ','input','',11,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:27'),(78,6,'freight_amount','运费','decimal(10,2)','BigDecimal','freightAmount','0','0','0','1','1','1','0','EQ','input','',12,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:27'),(79,6,'pay_amount','应付金额','decimal(10,2)','BigDecimal','payAmount','0','0','0','1','1','1','0','EQ','input','',13,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:27'),(80,6,'account_coupon_id','使用的账号优惠券ID','bigint','Long','accountCouponId','0','0','0','1','1','1','1','EQ','input','',14,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:27'),(81,6,'receiver_name','收货人姓名','varchar(32)','String','receiverName','0','0','0','1','1','1','1','LIKE','input','',15,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:27'),(82,6,'receiver_phone','收货人手机','varchar(11)','String','receiverPhone','0','0','0','1','1','1','1','LIKE','input','',16,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:28'),(83,6,'receiver_address','收货地址','varchar(500)','String','receiverAddress','0','0','0','1','1','1','0','EQ','textarea','',17,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:28'),(84,6,'buyer_remark','买家留言','varchar(255)','String','buyerRemark','0','0','0','1','1','1','0','EQ','input','',18,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:28'),(85,6,'cancel_reason','取消原因','varchar(255)','String','cancelReason','0','0','0','1','1','1','0','EQ','input','',19,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:28'),(86,6,'delivery_time','发货时间','datetime','Date','deliveryTime','0','0','0','1','1','1','0','EQ','datetime','',20,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:28'),(87,6,'receive_time','收货时间','datetime','Date','receiveTime','0','0','0','1','1','1','0','EQ','datetime','',21,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:28'),(88,6,'finish_time','完成时间','datetime','Date','finishTime','0','0','0','1','1','1','0','EQ','datetime','',22,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:28'),(89,6,'payment_no','支付流水号','varchar(32)','String','paymentNo','0','0','0','1','1','1','0','EQ','input','',23,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:28'),(90,6,'pay_type','支付方式（0未选 1微信 2支付宝 3余额）','tinyint','Integer','payType','0','0','0','1','1','1','1','EQ','select','',24,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:28'),(91,6,'pay_status','支付状态（0待支付 1已支付 2支付失败 3已关闭）','tinyint','Integer','payStatus','0','0','0','1','1','1','1','EQ','radio','',25,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:28'),(92,6,'transaction_no','第三方交易号','varchar(64)','String','transactionNo','0','0','0','1','1','1','0','EQ','input','',26,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:28'),(93,6,'pay_time','支付时间','datetime','Date','payTime','0','0','0','1','1','1','0','EQ','datetime','',27,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:28'),(94,6,'refund_amount','累计已退金额','decimal(10,2)','BigDecimal','refundAmount','0','0','0','1','1','1','0','EQ','input','',28,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:28'),(95,6,'refund_status','退款状态（0无退款 1部分退款 2全额退款 3退款处理中）','tinyint','Integer','refundStatus','0','0','0','1','1','1','1','EQ','radio','',29,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:28'),(96,6,'refund_count','退款次数','int','Integer','refundCount','0','0','0','1','1','1','0','EQ','input','',30,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:28'),(97,6,'del_flag','删除标志（0未删除 1已删除）','tinyint','Integer','delFlag','0','0','0',NULL,NULL,NULL,NULL,'EQ','input','',31,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:28'),(98,6,'create_time','创建时间','datetime','Date','createTime','0','0','0','1',NULL,NULL,NULL,'EQ','datetime','',32,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:28'),(99,6,'update_time','更新时间','datetime','Date','updateTime','0','0','0','1','1',NULL,NULL,'EQ','datetime','',33,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:28'),(100,6,'remark','备注','varchar(500)','String','remark','0','0','0','1','1','1',NULL,'EQ','textarea','',34,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:28'),(101,7,'order_item_id','订单明细ID','bigint','Long','orderItemId','1','0','0','1',NULL,NULL,NULL,'EQ','input','',1,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:17'),(102,7,'order_id','订单ID','bigint','Long','orderId','0','0','0','1','1','1','1','EQ','input','',2,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:17'),(103,7,'product_id','商品ID','bigint','Long','productId','0','0','0','1','1','1','1','EQ','input','',3,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:17'),(104,7,'sku_id','SKU ID','bigint','Long','skuId','0','0','0','1','1','1','1','EQ','input','',4,'admin','2026-06-20 18:35:09','','2026-06-20 21:13:17'),(105,7,'product_name','商品名称','varchar(128)','String','productName','0','0','0','1','1','1','1','LIKE','input','',5,'admin','2026-06-20 18:35:10','','2026-06-20 21:13:17'),(106,7,'sku_name','SKU名称','varchar(128)','String','skuName','0','0','0','1','1','1','1','LIKE','input','',6,'admin','2026-06-20 18:35:10','','2026-06-20 21:13:17'),(108,7,'sale_price','成交单价','decimal(10,2)','BigDecimal','salePrice','0','0','0','1','1','1','0','EQ','input','',7,'admin','2026-06-20 18:35:10','','2026-06-20 21:13:17'),(109,7,'quantity','购买数量','int','Integer','quantity','0','0','0','1','1','1','0','EQ','input','',8,'admin','2026-06-20 18:35:10','','2026-06-20 21:13:17'),(110,7,'total_amount','小计金额','decimal(10,2)','BigDecimal','totalAmount','0','0','0','1','1','1','0','EQ','input','',9,'admin','2026-06-20 18:35:10','','2026-06-20 21:13:17'),(111,7,'refunded_quantity','累计已退数量','int','Integer','refundedQuantity','0','0','0','1','1','1','0','EQ','input','',10,'admin','2026-06-20 18:35:10','','2026-06-20 21:13:17'),(112,7,'refunded_amount','累计已退金额','decimal(10,2)','BigDecimal','refundedAmount','0','0','0','1','1','1','0','EQ','input','',11,'admin','2026-06-20 18:35:10','','2026-06-20 21:13:17'),(113,7,'del_flag','删除标志（0未删除 1已删除）','tinyint','Integer','delFlag','0','0','0',NULL,NULL,NULL,NULL,'EQ','input','',12,'admin','2026-06-20 18:35:10','','2026-06-20 21:13:17'),(114,7,'create_time','创建时间','datetime','Date','createTime','0','0','0','1',NULL,NULL,NULL,'EQ','datetime','',13,'admin','2026-06-20 18:35:10','','2026-06-20 21:13:17'),(115,7,'update_time','更新时间','datetime','Date','updateTime','0','0','0','1','1',NULL,NULL,'EQ','datetime','',14,'admin','2026-06-20 18:35:10','','2026-06-20 21:13:17'),(116,8,'refund_id','退款单ID','bigint','Long','refundId','1','0','0','1',NULL,NULL,NULL,'EQ','input','',1,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:33'),(117,8,'refund_no','退款单号','varchar(32)','String','refundNo','0','0','0','1','1','1','1','EQ','input','',2,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:33'),(118,8,'order_id','订单ID','bigint','Long','orderId','0','0','0','1','1','1','1','EQ','input','',3,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:33'),(119,8,'account_id','账号ID','bigint','Long','accountId','0','0','0','1','1','1','1','EQ','input','',4,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:33'),(120,8,'refund_type','退款类型（1仅退款 2退货退款）','tinyint','Integer','refundType','0','0','0','1','1','1','1','EQ','select','',5,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:33'),(121,8,'refund_amount','本次退款金额','decimal(10,2)','BigDecimal','refundAmount','0','0','0','1','1','1','0','EQ','input','',6,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:33'),(122,8,'refund_status','退款状态（0待审核 1已通过 2已拒绝 3退款中 4已完成 5已取消）','tinyint','Integer','refundStatus','0','0','0','1','1','1','1','EQ','radio','',7,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:33'),(123,8,'refund_reason','退款原因','varchar(255)','String','refundReason','0','0','0','1','1','1','0','EQ','input','',8,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:33'),(124,8,'transaction_no','退款流水号','varchar(64)','String','transactionNo','0','0','0','1','1','1','0','EQ','input','',9,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:33'),(125,8,'apply_time','申请时间','datetime','Date','applyTime','0','0','0','1','1','1','0','EQ','datetime','',10,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:33'),(126,8,'refund_time','退款完成时间','datetime','Date','refundTime','0','0','0','1','1','1','0','EQ','datetime','',11,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:33'),(127,8,'handle_time','处理时间','datetime','Date','handleTime','0','0','0','1','1','1','0','EQ','datetime','',12,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:33'),(129,8,'handle_remark','处理备注','varchar(500)','String','handleRemark','0','0','0','1','1','1','0','EQ','textarea','',13,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:33'),(130,8,'del_flag','删除标志（0未删除 1已删除）','tinyint','Integer','delFlag','0','0','0',NULL,NULL,NULL,NULL,'EQ','input','',14,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:33'),(131,8,'create_time','创建时间','datetime','Date','createTime','0','0','0','1',NULL,NULL,NULL,'EQ','datetime','',15,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:33'),(132,8,'update_time','更新时间','datetime','Date','updateTime','0','0','0','1','1',NULL,NULL,'EQ','datetime','',16,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:34'),(133,8,'remark','备注','varchar(500)','String','remark','0','0','0','1','1','1',NULL,'EQ','textarea','',17,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:34'),(134,9,'refund_item_id','退款明细ID','bigint','Long','refundItemId','1','0','0','1',NULL,NULL,NULL,'EQ','input','',1,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:28'),(135,9,'refund_id','退款单ID','bigint','Long','refundId','0','0','0','1','1','1','1','EQ','input','',2,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:28'),(136,9,'order_id','订单ID','bigint','Long','orderId','0','0','0','1','1','1','1','EQ','input','',3,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:28'),(137,9,'order_item_id','订单明细ID','bigint','Long','orderItemId','0','0','0','1','1','1','1','EQ','input','',4,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:28'),(138,9,'refund_quantity','本次退款数量','int','Integer','refundQuantity','0','0','0','1','1','1','0','EQ','input','',5,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:28'),(139,9,'refund_amount','本次退款金额','decimal(10,2)','BigDecimal','refundAmount','0','0','0','1','1','1','0','EQ','input','',6,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:28'),(140,9,'del_flag','删除标志（0未删除 1已删除）','tinyint','Integer','delFlag','0','0','0',NULL,NULL,NULL,NULL,'EQ','input','',7,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:28'),(141,9,'create_time','创建时间','datetime','Date','createTime','0','0','0','1',NULL,NULL,NULL,'EQ','datetime','',8,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:28'),(142,9,'update_time','更新时间','datetime','Date','updateTime','0','0','0','1','1',NULL,NULL,'EQ','datetime','',9,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:28'),(143,10,'product_id','商品ID','bigint','Long','productId','1','0','0','1',NULL,NULL,NULL,'EQ','input','',1,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:20'),(144,10,'category_id','分类ID','bigint','Long','categoryId','0','0','0','1','1','1','1','EQ','input','',2,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:20'),(145,10,'product_name','商品名称','varchar(128)','String','productName','0','0','0','1','1','1','1','LIKE','input','',3,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:20'),(149,10,'unit_name','计量单位','varchar(16)','String','unitName','0','0','0','1','1','1','0','LIKE','input','',4,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:20'),(150,10,'market_price','市场价','decimal(10,2)','BigDecimal','marketPrice','0','0','0','1','1','1','0','EQ','input','',5,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:20'),(151,10,'sale_price','销售价','decimal(10,2)','BigDecimal','salePrice','0','0','0','1','1','1','0','EQ','input','',6,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:20'),(152,10,'stock_total','总库存','int','Integer','stockTotal','0','0','0','1','1','1','0','EQ','input','',7,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:20'),(153,10,'sales_count','累计销量','int','Integer','salesCount','0','0','0','1','1','1','0','EQ','input','',8,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:20'),(154,10,'shelf_status','上架状态（0下架 1上架）','tinyint','Integer','shelfStatus','0','0','0','1','1','1','1','EQ','radio','',9,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:20'),(155,10,'sort_num','排序','int','Integer','sortNum','0','0','0','1','1','1','0','EQ','input','',10,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:20'),(156,10,'del_flag','删除标志（0未删除 1已删除）','tinyint','Integer','delFlag','0','0','0',NULL,NULL,NULL,NULL,'EQ','input','',11,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:20'),(157,10,'create_time','创建时间','datetime','Date','createTime','0','0','0','1',NULL,NULL,NULL,'EQ','datetime','',12,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:20'),(158,10,'update_time','更新时间','datetime','Date','updateTime','0','0','0','1','1',NULL,NULL,'EQ','datetime','',13,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:20'),(159,10,'remark','备注','varchar(500)','String','remark','0','0','0','1','1','1',NULL,'EQ','textarea','',14,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:20'),(160,11,'sku_id','SKU ID','bigint','Long','skuId','1','0','0','1',NULL,NULL,NULL,'EQ','input','',1,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:14'),(161,11,'product_id','商品ID','bigint','Long','productId','0','0','0','1','1','1','1','EQ','input','',2,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:14'),(162,11,'sku_name','SKU名称','varchar(128)','String','skuName','0','0','0','1','1','1','1','LIKE','input','',3,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:14'),(163,11,'market_price','市场价','decimal(10,2)','BigDecimal','marketPrice','0','0','0','1','1','1','0','EQ','input','',4,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:14'),(164,11,'sale_price','销售价','decimal(10,2)','BigDecimal','salePrice','0','0','0','1','1','1','0','EQ','input','',5,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:14'),(165,11,'stock','库存','int','Integer','stock','0','0','0','1','1','1','0','EQ','input','',6,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:14'),(166,11,'sales_count','销量','int','Integer','salesCount','0','0','0','1','1','1','0','EQ','input','',7,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:14'),(167,11,'status','状态（0正常 1停用）','tinyint','Integer','status','0','0','0','1','1','1','0','EQ','radio','',8,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:14'),(168,11,'del_flag','删除标志（0未删除 1已删除）','tinyint','Integer','delFlag','0','0','0',NULL,NULL,NULL,NULL,'EQ','input','',9,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:14'),(169,11,'create_time','创建时间','datetime','Date','createTime','0','0','0','1',NULL,NULL,NULL,'EQ','datetime','',10,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:14'),(170,11,'update_time','更新时间','datetime','Date','updateTime','0','0','0','1','1',NULL,NULL,'EQ','datetime','',11,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:14'),(171,11,'remark','备注','varchar(500)','String','remark','0','0','0','1','1','1',NULL,'EQ','textarea','',12,'admin','2026-06-20 18:35:10','','2026-06-20 21:14:14'),(172,12,'account_coupon_id','账号优惠券ID','bigint','Long','accountCouponId','1','1','0','1',NULL,NULL,NULL,'EQ','input','',1,'admin','2026-06-20 18:35:19','','2026-06-20 21:14:45'),(173,12,'coupon_id','优惠券ID','bigint','Long','couponId','0','0','0','1','1','1','1','EQ','input','',2,'admin','2026-06-20 18:35:19','','2026-06-20 21:14:45'),(174,12,'account_id','账号ID','bigint','Long','accountId','0','0','0','1','1','1','1','EQ','input','',3,'admin','2026-06-20 18:35:19','','2026-06-20 21:14:45'),(175,12,'coupon_name','券名称','varchar(64)','String','couponName','0','0','0','1','1','1','1','LIKE','input','',4,'admin','2026-06-20 18:35:19','','2026-06-20 21:14:45'),(176,12,'threshold_amount','满减门槛','decimal(10,2)','BigDecimal','thresholdAmount','0','0','0','1','1','1','0','EQ','input','',5,'admin','2026-06-20 18:35:19','','2026-06-20 21:14:45'),(177,12,'discount_amount','满减金额','decimal(10,2)','BigDecimal','discountAmount','0','0','0','1','1','1','0','EQ','input','',6,'admin','2026-06-20 18:35:19','','2026-06-20 21:14:45'),(178,12,'valid_start_time','有效开始','datetime','Date','validStartTime','0','0','0','1','1','1','0','EQ','datetime','',7,'admin','2026-06-20 18:35:19','','2026-06-20 21:14:45'),(179,12,'valid_end_time','有效结束','datetime','Date','validEndTime','0','0','0','1','1','1','0','EQ','datetime','',8,'admin','2026-06-20 18:35:19','','2026-06-20 21:14:45'),(180,12,'coupon_status','状态（0未使用 1已使用 2已过期）','tinyint','Integer','couponStatus','0','0','0','1','1','1','1','EQ','radio','',9,'admin','2026-06-20 18:35:19','','2026-06-20 21:14:45'),(181,12,'receive_time','领取时间','datetime','Date','receiveTime','0','0','0','1','1','1','0','EQ','datetime','',10,'admin','2026-06-20 18:35:19','','2026-06-20 21:14:45'),(182,12,'use_time','使用时间','datetime','Date','useTime','0','0','0','1','1','1','0','EQ','datetime','',11,'admin','2026-06-20 18:35:19','','2026-06-20 21:14:45'),(183,12,'order_id','使用订单ID','bigint','Long','orderId','0','0','0','1','1','1','0','EQ','input','',12,'admin','2026-06-20 18:35:19','','2026-06-20 21:14:45'),(184,12,'del_flag','删除标志（0未删除 1已删除）','tinyint','Integer','delFlag','0','0','0',NULL,NULL,NULL,NULL,'EQ','input','',13,'admin','2026-06-20 18:35:19','','2026-06-20 21:14:45'),(185,12,'create_time','创建时间','datetime','Date','createTime','0','0','0','1',NULL,NULL,NULL,'EQ','datetime','',14,'admin','2026-06-20 18:35:19','','2026-06-20 21:14:45'),(186,12,'update_time','更新时间','datetime','Date','updateTime','0','0','0','1','1',NULL,NULL,'EQ','datetime','',15,'admin','2026-06-20 18:35:19','','2026-06-20 21:14:45'),(187,13,'coupon_id','优惠券ID','bigint','Long','couponId','1','1','0','1',NULL,NULL,NULL,'EQ','input','',1,'admin','2026-06-20 18:35:19','','2026-06-20 21:14:39'),(188,13,'coupon_name','优惠券名称','varchar(64)','String','couponName','0','0','0','1','1','1','1','LIKE','input','',2,'admin','2026-06-20 18:35:19','','2026-06-20 21:14:39'),(189,13,'threshold_amount','满金额','decimal(10,2)','BigDecimal','thresholdAmount','0','0','0','1','1','1','0','EQ','input','',3,'admin','2026-06-20 18:35:19','','2026-06-20 21:14:39'),(190,13,'discount_amount','减金额','decimal(10,2)','BigDecimal','discountAmount','0','0','0','1','1','1','0','EQ','input','',4,'admin','2026-06-20 18:35:19','','2026-06-20 21:14:39'),(191,13,'total_count','发放总量','int','Integer','totalCount','0','0','0','1','1','1','0','EQ','input','',5,'admin','2026-06-20 18:35:19','','2026-06-20 21:14:39'),(192,13,'receive_count','已领取数量','int','Integer','receiveCount','0','0','0','1','1','1','0','EQ','input','',6,'admin','2026-06-20 18:35:19','','2026-06-20 21:14:39'),(193,13,'used_count','已使用数量','int','Integer','usedCount','0','0','0','1','1','1','0','EQ','input','',7,'admin','2026-06-20 18:35:19','','2026-06-20 21:14:39'),(194,13,'valid_start_time','有效开始时间','datetime','Date','validStartTime','0','0','0','1','1','1','0','EQ','datetime','',8,'admin','2026-06-20 18:35:19','','2026-06-20 21:14:39'),(195,13,'valid_end_time','有效结束时间','datetime','Date','validEndTime','0','0','0','1','1','1','0','EQ','datetime','',9,'admin','2026-06-20 18:35:19','','2026-06-20 21:14:39'),(196,13,'status','状态（0正常 1停用）','tinyint','Integer','status','0','0','0','1','1','1','1','EQ','radio','',10,'admin','2026-06-20 18:35:19','','2026-06-20 21:14:39'),(197,13,'del_flag','删除标志（0未删除 1已删除）','tinyint','Integer','delFlag','0','0','0',NULL,NULL,NULL,NULL,'EQ','input','',11,'admin','2026-06-20 18:35:19','','2026-06-20 21:14:39'),(198,13,'create_time','创建时间','datetime','Date','createTime','0','0','0','1',NULL,NULL,NULL,'EQ','datetime','',12,'admin','2026-06-20 18:35:19','','2026-06-20 21:14:39'),(199,13,'update_time','更新时间','datetime','Date','updateTime','0','0','0','1','1',NULL,NULL,'EQ','datetime','',13,'admin','2026-06-20 18:35:19','','2026-06-20 21:14:39'),(200,13,'remark','备注','varchar(500)','String','remark','0','0','0','1','1','1',NULL,'EQ','textarea','',14,'admin','2026-06-20 18:35:19','','2026-06-20 21:14:39');
/*!40000 ALTER TABLE `gen_table_column` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mall_cart`
--

DROP TABLE IF EXISTS `mall_cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mall_cart` (
  `cart_id` bigint NOT NULL COMMENT '购物车ID',
  `account_id` bigint DEFAULT '0' COMMENT '账号ID',
  `product_id` bigint DEFAULT '0' COMMENT '商品ID',
  `sku_id` bigint DEFAULT '0' COMMENT 'SKU ID',
  `quantity` int DEFAULT '0' COMMENT '数量',
  `del_flag` tinyint DEFAULT '0' COMMENT '删除标志（0未删除 1已删除）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`cart_id`),
  KEY `idx_account_id` (`account_id`),
  KEY `idx_sku_id` (`sku_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='商城购物车';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mall_cart`
--

LOCK TABLES `mall_cart` WRITE;
/*!40000 ALTER TABLE `mall_cart` DISABLE KEYS */;
/*!40000 ALTER TABLE `mall_cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mall_category`
--

DROP TABLE IF EXISTS `mall_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mall_category` (
  `category_id` bigint NOT NULL COMMENT '分类ID',
  `category_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '分类名称',
  `order_num` int DEFAULT '0' COMMENT '显示顺序',
  `status` tinyint DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `del_flag` tinyint DEFAULT '0' COMMENT '删除标志（0未删除 1已删除）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='商城商品分类';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mall_category`
--

LOCK TABLES `mall_category` WRITE;
/*!40000 ALTER TABLE `mall_category` DISABLE KEYS */;
/*!40000 ALTER TABLE `mall_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mall_order`
--

DROP TABLE IF EXISTS `mall_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mall_order` (
  `order_id` bigint NOT NULL COMMENT '订单ID',
  `order_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '订单编号',
  `account_id` bigint DEFAULT '0' COMMENT '账号ID',
  `order_status` tinyint DEFAULT '0' COMMENT '订单状态（0待付款 1待发货 2待收货 3已完成 4已取消 5退款中）',
  `total_amount` decimal(10,2) DEFAULT '0.00' COMMENT '商品总金额',
  `discount_amount` decimal(10,2) DEFAULT '0.00' COMMENT '优惠金额',
  `coupon_id` bigint DEFAULT '0' COMMENT '优惠券ID',
  `coupon_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '券名称',
  `coupon_threshold_amount` decimal(10,2) DEFAULT '0.00' COMMENT '满减门槛',
  `coupon_discount_amount` decimal(10,2) DEFAULT '0.00' COMMENT '满减面额',
  `coupon_amount` decimal(10,2) DEFAULT '0.00' COMMENT '实际抵扣金额',
  `freight_amount` decimal(10,2) DEFAULT '0.00' COMMENT '运费',
  `pay_amount` decimal(10,2) DEFAULT '0.00' COMMENT '应付金额',
  `account_coupon_id` bigint DEFAULT '0' COMMENT '使用的账号优惠券ID',
  `receiver_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '收货人姓名',
  `receiver_phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '收货人手机',
  `receiver_address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '收货地址',
  `buyer_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '买家留言',
  `cancel_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '取消原因',
  `delivery_time` datetime DEFAULT NULL COMMENT '发货时间',
  `receive_time` datetime DEFAULT NULL COMMENT '收货时间',
  `finish_time` datetime DEFAULT NULL COMMENT '完成时间',
  `payment_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '支付流水号',
  `pay_type` tinyint DEFAULT '0' COMMENT '支付方式（0未选 1微信 2支付宝 3余额）',
  `pay_status` tinyint DEFAULT '0' COMMENT '支付状态（0待支付 1已支付 2支付失败 3已关闭）',
  `transaction_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '第三方交易号',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `refund_amount` decimal(10,2) DEFAULT '0.00' COMMENT '累计已退金额',
  `refund_status` tinyint DEFAULT '0' COMMENT '退款状态（0无退款 1部分退款 2全额退款 3退款处理中）',
  `refund_count` int DEFAULT '0' COMMENT '退款次数',
  `del_flag` tinyint DEFAULT '0' COMMENT '删除标志（0未删除 1已删除）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`order_id`),
  UNIQUE KEY `uk_order_no` (`order_no`),
  KEY `idx_account_id` (`account_id`),
  KEY `idx_order_status` (`order_status`),
  KEY `idx_pay_status` (`pay_status`),
  KEY `idx_refund_status` (`refund_status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='商城订单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mall_order`
--

LOCK TABLES `mall_order` WRITE;
/*!40000 ALTER TABLE `mall_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `mall_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mall_order_item`
--

DROP TABLE IF EXISTS `mall_order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mall_order_item` (
  `order_item_id` bigint NOT NULL COMMENT '订单明细ID',
  `order_id` bigint DEFAULT '0' COMMENT '订单ID',
  `product_id` bigint DEFAULT '0' COMMENT '商品ID',
  `sku_id` bigint DEFAULT '0' COMMENT 'SKU ID',
  `product_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '商品名称',
  `sku_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT 'SKU名称',
  `sale_price` decimal(10,2) DEFAULT '0.00' COMMENT '成交单价',
  `quantity` int DEFAULT '0' COMMENT '购买数量',
  `total_amount` decimal(10,2) DEFAULT '0.00' COMMENT '小计金额',
  `refunded_quantity` int DEFAULT '0' COMMENT '累计已退数量',
  `refunded_amount` decimal(10,2) DEFAULT '0.00' COMMENT '累计已退金额',
  `del_flag` tinyint DEFAULT '0' COMMENT '删除标志（0未删除 1已删除）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`order_item_id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_product_id` (`product_id`),
  KEY `idx_sku_id` (`sku_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='商城订单明细';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mall_order_item`
--

LOCK TABLES `mall_order_item` WRITE;
/*!40000 ALTER TABLE `mall_order_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `mall_order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mall_order_refund`
--

DROP TABLE IF EXISTS `mall_order_refund`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mall_order_refund` (
  `refund_id` bigint NOT NULL COMMENT '退款单ID',
  `refund_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '退款单号',
  `order_id` bigint DEFAULT '0' COMMENT '订单ID',
  `account_id` bigint DEFAULT '0' COMMENT '账号ID',
  `refund_type` tinyint DEFAULT '1' COMMENT '退款类型（1仅退款 2退货退款）',
  `refund_amount` decimal(10,2) DEFAULT '0.00' COMMENT '本次退款金额',
  `refund_status` tinyint DEFAULT '0' COMMENT '退款状态（0待审核 1已通过 2已拒绝 3退款中 4已完成 5已取消）',
  `refund_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '退款原因',
  `transaction_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '退款流水号',
  `apply_time` datetime DEFAULT NULL COMMENT '申请时间',
  `refund_time` datetime DEFAULT NULL COMMENT '退款完成时间',
  `handle_time` datetime DEFAULT NULL COMMENT '处理时间',
  `handle_remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '处理备注',
  `del_flag` tinyint DEFAULT '0' COMMENT '删除标志（0未删除 1已删除）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`refund_id`),
  UNIQUE KEY `uk_refund_no` (`refund_no`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_account_id` (`account_id`),
  KEY `idx_refund_status` (`refund_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='商城退款单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mall_order_refund`
--

LOCK TABLES `mall_order_refund` WRITE;
/*!40000 ALTER TABLE `mall_order_refund` DISABLE KEYS */;
/*!40000 ALTER TABLE `mall_order_refund` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mall_order_refund_item`
--

DROP TABLE IF EXISTS `mall_order_refund_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mall_order_refund_item` (
  `refund_item_id` bigint NOT NULL COMMENT '退款明细ID',
  `refund_id` bigint DEFAULT '0' COMMENT '退款单ID',
  `order_id` bigint DEFAULT '0' COMMENT '订单ID',
  `order_item_id` bigint DEFAULT '0' COMMENT '订单明细ID',
  `refund_quantity` int DEFAULT '0' COMMENT '本次退款数量',
  `refund_amount` decimal(10,2) DEFAULT '0.00' COMMENT '本次退款金额',
  `del_flag` tinyint DEFAULT '0' COMMENT '删除标志（0未删除 1已删除）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`refund_item_id`),
  KEY `idx_refund_id` (`refund_id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_order_item_id` (`order_item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='商城退款明细';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mall_order_refund_item`
--

LOCK TABLES `mall_order_refund_item` WRITE;
/*!40000 ALTER TABLE `mall_order_refund_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `mall_order_refund_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mall_product`
--

DROP TABLE IF EXISTS `mall_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mall_product` (
  `product_id` bigint NOT NULL COMMENT '商品ID',
  `category_id` bigint DEFAULT '0' COMMENT '分类ID',
  `product_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '商品名称',
  `unit_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '件' COMMENT '计量单位',
  `market_price` decimal(10,2) DEFAULT '0.00' COMMENT '市场价',
  `sale_price` decimal(10,2) DEFAULT '0.00' COMMENT '销售价',
  `cover_image` varchar(512) DEFAULT NULL COMMENT '封面图URL',
  `detail_images` varchar(2000) DEFAULT NULL COMMENT '详情图URL，逗号分隔',
  `video_url` varchar(512) DEFAULT NULL COMMENT '视频URL',
  `freight_amount` decimal(10,2) DEFAULT '0.00' COMMENT '固定运费',
  `stock_total` int DEFAULT '0' COMMENT '总库存',
  `sales_count` int DEFAULT '0' COMMENT '累计销量',
  `shelf_status` tinyint DEFAULT '0' COMMENT '上架状态（0下架 1上架）',
  `sort_num` int DEFAULT '0' COMMENT '排序',
  `del_flag` tinyint DEFAULT '0' COMMENT '删除标志（0未删除 1已删除）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`product_id`),
  KEY `idx_category_id` (`category_id`),
  KEY `idx_shelf_status` (`shelf_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='商城商品SPU';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mall_product`
--

LOCK TABLES `mall_product` WRITE;
/*!40000 ALTER TABLE `mall_product` DISABLE KEYS */;
/*!40000 ALTER TABLE `mall_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mall_product_sku`
--

DROP TABLE IF EXISTS `mall_product_sku`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mall_product_sku` (
  `sku_id` bigint NOT NULL COMMENT 'SKU ID',
  `product_id` bigint DEFAULT '0' COMMENT '商品ID',
  `sku_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT 'SKU名称',
  `market_price` decimal(10,2) DEFAULT '0.00' COMMENT '市场价',
  `sale_price` decimal(10,2) DEFAULT '0.00' COMMENT '销售价',
  `stock` int DEFAULT '0' COMMENT '库存',
  `sales_count` int DEFAULT '0' COMMENT '销量',
  `status` tinyint DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `del_flag` tinyint DEFAULT '0' COMMENT '删除标志（0未删除 1已删除）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`sku_id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='商城商品SKU';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mall_product_sku`
--

LOCK TABLES `mall_product_sku` WRITE;
/*!40000 ALTER TABLE `mall_product_sku` DISABLE KEYS */;
/*!40000 ALTER TABLE `mall_product_sku` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_blob_triggers`
--

DROP TABLE IF EXISTS `qrtz_blob_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_blob_triggers` (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `blob_data` blob COMMENT '存放持久化Trigger对象',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='Blob类型的触发器表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_blob_triggers`
--

LOCK TABLES `qrtz_blob_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_blob_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_blob_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_calendars`
--

DROP TABLE IF EXISTS `qrtz_calendars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_calendars` (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `calendar_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '日历名称',
  `calendar` blob NOT NULL COMMENT '存放持久化calendar对象',
  PRIMARY KEY (`sched_name`,`calendar_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='日历信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_calendars`
--

LOCK TABLES `qrtz_calendars` WRITE;
/*!40000 ALTER TABLE `qrtz_calendars` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_calendars` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_cron_triggers`
--

DROP TABLE IF EXISTS `qrtz_cron_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_cron_triggers` (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `cron_expression` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'cron表达式',
  `time_zone_id` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '时区',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='Cron类型的触发器表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_cron_triggers`
--

LOCK TABLES `qrtz_cron_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_cron_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_cron_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_fired_triggers`
--

DROP TABLE IF EXISTS `qrtz_fired_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_fired_triggers` (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `entry_id` varchar(95) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度器实例id',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `instance_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度器实例名',
  `fired_time` bigint NOT NULL COMMENT '触发的时间',
  `sched_time` bigint NOT NULL COMMENT '定时器制定的时间',
  `priority` int NOT NULL COMMENT '优先级',
  `state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态',
  `job_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '任务名称',
  `job_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '任务组名',
  `is_nonconcurrent` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否并发',
  `requests_recovery` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否接受恢复执行',
  PRIMARY KEY (`sched_name`,`entry_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='已触发的触发器表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_fired_triggers`
--

LOCK TABLES `qrtz_fired_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_fired_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_fired_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_job_details`
--

DROP TABLE IF EXISTS `qrtz_job_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_job_details` (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `job_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务名称',
  `job_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务组名',
  `description` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '相关介绍',
  `job_class_name` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '执行任务类名称',
  `is_durable` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否持久化',
  `is_nonconcurrent` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否并发',
  `is_update_data` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否更新数据',
  `requests_recovery` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否接受恢复执行',
  `job_data` blob COMMENT '存放持久化job对象',
  PRIMARY KEY (`sched_name`,`job_name`,`job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='任务详细信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_job_details`
--

LOCK TABLES `qrtz_job_details` WRITE;
/*!40000 ALTER TABLE `qrtz_job_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_job_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_locks`
--

DROP TABLE IF EXISTS `qrtz_locks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_locks` (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `lock_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '悲观锁名称',
  PRIMARY KEY (`sched_name`,`lock_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='存储的悲观锁信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_locks`
--

LOCK TABLES `qrtz_locks` WRITE;
/*!40000 ALTER TABLE `qrtz_locks` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_locks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_paused_trigger_grps`
--

DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  PRIMARY KEY (`sched_name`,`trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='暂停的触发器表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_paused_trigger_grps`
--

LOCK TABLES `qrtz_paused_trigger_grps` WRITE;
/*!40000 ALTER TABLE `qrtz_paused_trigger_grps` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_paused_trigger_grps` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_scheduler_state`
--

DROP TABLE IF EXISTS `qrtz_scheduler_state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_scheduler_state` (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `instance_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '实例名称',
  `last_checkin_time` bigint NOT NULL COMMENT '上次检查时间',
  `checkin_interval` bigint NOT NULL COMMENT '检查间隔时间',
  PRIMARY KEY (`sched_name`,`instance_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='调度器状态表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_scheduler_state`
--

LOCK TABLES `qrtz_scheduler_state` WRITE;
/*!40000 ALTER TABLE `qrtz_scheduler_state` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_scheduler_state` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_simple_triggers`
--

DROP TABLE IF EXISTS `qrtz_simple_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_simple_triggers` (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `repeat_count` bigint NOT NULL COMMENT '重复的次数统计',
  `repeat_interval` bigint NOT NULL COMMENT '重复的间隔时间',
  `times_triggered` bigint NOT NULL COMMENT '已经触发的次数',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='简单触发器的信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_simple_triggers`
--

LOCK TABLES `qrtz_simple_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_simple_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_simple_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_simprop_triggers`
--

DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_simprop_triggers` (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `str_prop_1` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'String类型的trigger的第一个参数',
  `str_prop_2` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'String类型的trigger的第二个参数',
  `str_prop_3` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'String类型的trigger的第三个参数',
  `int_prop_1` int DEFAULT NULL COMMENT 'int类型的trigger的第一个参数',
  `int_prop_2` int DEFAULT NULL COMMENT 'int类型的trigger的第二个参数',
  `long_prop_1` bigint DEFAULT NULL COMMENT 'long类型的trigger的第一个参数',
  `long_prop_2` bigint DEFAULT NULL COMMENT 'long类型的trigger的第二个参数',
  `dec_prop_1` decimal(13,4) DEFAULT NULL COMMENT 'decimal类型的trigger的第一个参数',
  `dec_prop_2` decimal(13,4) DEFAULT NULL COMMENT 'decimal类型的trigger的第二个参数',
  `bool_prop_1` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'Boolean类型的trigger的第一个参数',
  `bool_prop_2` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'Boolean类型的trigger的第二个参数',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='同步机制的行锁表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_simprop_triggers`
--

LOCK TABLES `qrtz_simprop_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_simprop_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_simprop_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_triggers`
--

DROP TABLE IF EXISTS `qrtz_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_triggers` (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '触发器的名字',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '触发器所属组的名字',
  `job_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_job_details表job_name的外键',
  `job_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_job_details表job_group的外键',
  `description` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '相关介绍',
  `next_fire_time` bigint DEFAULT NULL COMMENT '上一次触发时间（毫秒）',
  `prev_fire_time` bigint DEFAULT NULL COMMENT '下一次触发时间（默认为-1表示不触发）',
  `priority` int DEFAULT NULL COMMENT '优先级',
  `trigger_state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '触发器状态',
  `trigger_type` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '触发器的类型',
  `start_time` bigint NOT NULL COMMENT '开始时间',
  `end_time` bigint DEFAULT NULL COMMENT '结束时间',
  `calendar_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '日程表名称',
  `misfire_instr` smallint DEFAULT NULL COMMENT '补偿执行的策略',
  `job_data` blob COMMENT '存放持久化job对象',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  KEY `sched_name` (`sched_name`,`job_name`,`job_group`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `job_name`, `job_group`) REFERENCES `qrtz_job_details` (`sched_name`, `job_name`, `job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='触发器详细信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_triggers`
--

LOCK TABLES `qrtz_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_config`
--

DROP TABLE IF EXISTS `sys_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_config` (
  `config_id` int NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '参数键值',
  `config_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`config_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='参数配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_config`
--

LOCK TABLES `sys_config` WRITE;
/*!40000 ALTER TABLE `sys_config` DISABLE KEYS */;
INSERT INTO `sys_config` VALUES (1,'主框架页-默认皮肤样式名称','sys.index.skinName','skin-blue','Y','admin','2026-06-20 09:21:56','',NULL,'蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow'),(2,'用户管理-账号初始密码','sys.user.initPassword','123456','Y','admin','2026-06-20 09:21:56','',NULL,'初始化密码 123456'),(3,'主框架页-侧边栏主题','sys.index.sideTheme','theme-light','Y','admin','2026-06-20 09:21:56','admin','2026-06-20 10:03:53','深色主题theme-dark，浅色主题theme-light'),(4,'账号自助-验证码开关','sys.account.captchaEnabled','false','Y','admin','2026-06-20 09:21:56','admin','2026-06-20 09:58:53','是否开启验证码功能（true开启，false关闭）'),(5,'账号自助-是否开启用户注册功能','sys.account.registerUser','false','Y','admin','2026-06-20 09:21:56','',NULL,'是否开启注册用户功能（true开启，false关闭）'),(6,'用户登录-黑名单列表','sys.login.blackIPList','','Y','admin','2026-06-20 09:21:56','',NULL,'设置登录IP黑名单限制，多个匹配项以;分隔，支持匹配（*通配、网段）'),(7,'用户管理-初始密码修改策略','sys.account.initPasswordModify','1','Y','admin','2026-06-20 09:21:56','',NULL,'0：初始密码修改策略关闭，没有任何提示，1：提醒用户，如果未修改初始密码，则在登录时就会提醒修改密码对话框'),(8,'用户管理-账号密码更新周期','sys.account.passwordValidateDays','0','Y','admin','2026-06-20 09:21:56','',NULL,'密码更新周期（填写数字，数据初始化值为0不限制，若修改必须为大于0小于365的正整数），如果超过这个周期登录系统时，则在登录时就会提醒修改密码对话框'),(9,'用户管理-密码字符范围','sys.account.chrtype','0','Y','admin','2026-06-20 09:21:56','',NULL,'默认任意字符范围，0任意（密码可以输入任意字符），1数字（密码只能为0-9数字），2英文字母（密码只能为a-z和A-Z字母），3字母和数字（密码必须包含字母，数字）,4字母数字和特殊字符（目前支持的特殊字符包括：~!@#$%^&*()-=_+）');
/*!40000 ALTER TABLE `sys_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dept`
--

DROP TABLE IF EXISTS `sys_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dept` (
  `dept_id` bigint NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` bigint DEFAULT '0' COMMENT '父部门id',
  `ancestors` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '部门名称',
  `order_num` int DEFAULT '0' COMMENT '显示顺序',
  `leader` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邮箱',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=200 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='部门表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dept`
--

LOCK TABLES `sys_dept` WRITE;
/*!40000 ALTER TABLE `sys_dept` DISABLE KEYS */;
INSERT INTO `sys_dept` VALUES (100,0,'0','质衡科技',0,'质衡','15888888888','ry@qq.com','0','0','admin','2026-06-20 09:21:55','',NULL),(101,100,'0,100','深圳总公司',1,'质衡','15888888888','ry@qq.com','0','0','admin','2026-06-20 09:21:55','',NULL),(102,100,'0,100','长沙分公司',2,'质衡','15888888888','ry@qq.com','0','0','admin','2026-06-20 09:21:55','',NULL),(103,101,'0,100,101','研发部门',1,'质衡','15888888888','ry@qq.com','0','0','admin','2026-06-20 09:21:55','',NULL),(104,101,'0,100,101','市场部门',2,'质衡','15888888888','ry@qq.com','0','0','admin','2026-06-20 09:21:55','',NULL),(105,101,'0,100,101','测试部门',3,'质衡','15888888888','ry@qq.com','0','0','admin','2026-06-20 09:21:55','',NULL),(106,101,'0,100,101','财务部门',4,'质衡','15888888888','ry@qq.com','0','0','admin','2026-06-20 09:21:55','',NULL),(107,101,'0,100,101','运维部门',5,'质衡','15888888888','ry@qq.com','0','0','admin','2026-06-20 09:21:55','',NULL),(108,102,'0,100,102','市场部门',1,'质衡','15888888888','ry@qq.com','0','0','admin','2026-06-20 09:21:55','',NULL),(109,102,'0,100,102','财务部门',2,'质衡','15888888888','ry@qq.com','0','0','admin','2026-06-20 09:21:55','',NULL);
/*!40000 ALTER TABLE `sys_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict_data`
--

DROP TABLE IF EXISTS `sys_dict_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dict_data` (
  `dict_code` bigint NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_sort` int DEFAULT '0' COMMENT '字典排序',
  `dict_label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='字典数据表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict_data`
--

LOCK TABLES `sys_dict_data` WRITE;
/*!40000 ALTER TABLE `sys_dict_data` DISABLE KEYS */;
INSERT INTO `sys_dict_data` VALUES (1,1,'男','0','sys_user_sex','','','Y','0','admin','2026-06-20 09:21:56','',NULL,'性别男'),(2,2,'女','1','sys_user_sex','','','N','0','admin','2026-06-20 09:21:56','',NULL,'性别女'),(3,3,'未知','2','sys_user_sex','','','N','0','admin','2026-06-20 09:21:56','',NULL,'性别未知'),(4,1,'显示','0','sys_show_hide','','primary','Y','0','admin','2026-06-20 09:21:56','',NULL,'显示菜单'),(5,2,'隐藏','1','sys_show_hide','','danger','N','0','admin','2026-06-20 09:21:56','',NULL,'隐藏菜单'),(6,1,'正常','0','sys_normal_disable','','primary','Y','0','admin','2026-06-20 09:21:56','',NULL,'正常状态'),(7,2,'停用','1','sys_normal_disable','','danger','N','0','admin','2026-06-20 09:21:56','',NULL,'停用状态'),(8,1,'正常','0','sys_job_status','','primary','Y','0','admin','2026-06-20 09:21:56','',NULL,'正常状态'),(9,2,'暂停','1','sys_job_status','','danger','N','0','admin','2026-06-20 09:21:56','',NULL,'停用状态'),(10,1,'默认','DEFAULT','sys_job_group','','','Y','0','admin','2026-06-20 09:21:56','',NULL,'默认分组'),(11,2,'系统','SYSTEM','sys_job_group','','','N','0','admin','2026-06-20 09:21:56','',NULL,'系统分组'),(12,1,'是','Y','sys_yes_no','','primary','Y','0','admin','2026-06-20 09:21:56','',NULL,'系统默认是'),(13,2,'否','N','sys_yes_no','','danger','N','0','admin','2026-06-20 09:21:56','',NULL,'系统默认否'),(14,1,'通知','1','sys_notice_type','','warning','Y','0','admin','2026-06-20 09:21:56','',NULL,'通知'),(15,2,'公告','2','sys_notice_type','','success','N','0','admin','2026-06-20 09:21:56','',NULL,'公告'),(16,1,'正常','0','sys_notice_status','','primary','Y','0','admin','2026-06-20 09:21:56','',NULL,'正常状态'),(17,2,'关闭','1','sys_notice_status','','danger','N','0','admin','2026-06-20 09:21:56','',NULL,'关闭状态'),(18,99,'其他','0','sys_oper_type','','info','N','0','admin','2026-06-20 09:21:56','',NULL,'其他操作'),(19,1,'新增','1','sys_oper_type','','info','N','0','admin','2026-06-20 09:21:56','',NULL,'新增操作'),(20,2,'修改','2','sys_oper_type','','info','N','0','admin','2026-06-20 09:21:56','',NULL,'修改操作'),(21,3,'删除','3','sys_oper_type','','danger','N','0','admin','2026-06-20 09:21:56','',NULL,'删除操作'),(22,4,'授权','4','sys_oper_type','','primary','N','0','admin','2026-06-20 09:21:56','',NULL,'授权操作'),(23,5,'导出','5','sys_oper_type','','warning','N','0','admin','2026-06-20 09:21:56','',NULL,'导出操作'),(24,6,'导入','6','sys_oper_type','','warning','N','0','admin','2026-06-20 09:21:56','',NULL,'导入操作'),(25,7,'强退','7','sys_oper_type','','danger','N','0','admin','2026-06-20 09:21:56','',NULL,'强退操作'),(26,8,'生成代码','8','sys_oper_type','','warning','N','0','admin','2026-06-20 09:21:56','',NULL,'生成操作'),(27,9,'清空数据','9','sys_oper_type','','danger','N','0','admin','2026-06-20 09:21:56','',NULL,'清空操作'),(28,1,'成功','0','sys_common_status','','primary','N','0','admin','2026-06-20 09:21:56','',NULL,'正常状态'),(29,2,'失败','1','sys_common_status','','danger','N','0','admin','2026-06-20 09:21:56','',NULL,'停用状态');
/*!40000 ALTER TABLE `sys_dict_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict_type`
--

DROP TABLE IF EXISTS `sys_dict_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dict_type` (
  `dict_id` bigint NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '字典类型',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`),
  UNIQUE KEY `dict_type` (`dict_type`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='字典类型表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict_type`
--

LOCK TABLES `sys_dict_type` WRITE;
/*!40000 ALTER TABLE `sys_dict_type` DISABLE KEYS */;
INSERT INTO `sys_dict_type` VALUES (1,'用户性别','sys_user_sex','0','admin','2026-06-20 09:21:56','',NULL,'用户性别列表'),(2,'菜单状态','sys_show_hide','0','admin','2026-06-20 09:21:56','',NULL,'菜单状态列表'),(3,'系统开关','sys_normal_disable','0','admin','2026-06-20 09:21:56','',NULL,'系统开关列表'),(4,'任务状态','sys_job_status','0','admin','2026-06-20 09:21:56','',NULL,'任务状态列表'),(5,'任务分组','sys_job_group','0','admin','2026-06-20 09:21:56','',NULL,'任务分组列表'),(6,'系统是否','sys_yes_no','0','admin','2026-06-20 09:21:56','',NULL,'系统是否列表'),(7,'通知类型','sys_notice_type','0','admin','2026-06-20 09:21:56','',NULL,'通知类型列表'),(8,'通知状态','sys_notice_status','0','admin','2026-06-20 09:21:56','',NULL,'通知状态列表'),(9,'操作类型','sys_oper_type','0','admin','2026-06-20 09:21:56','',NULL,'操作类型列表'),(10,'系统状态','sys_common_status','0','admin','2026-06-20 09:21:56','',NULL,'登录状态列表');
/*!40000 ALTER TABLE `sys_dict_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_job`
--

DROP TABLE IF EXISTS `sys_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_job` (
  `job_id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `job_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调用目标字符串',
  `cron_expression` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT 'cron执行表达式',
  `misfire_policy` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  `concurrent` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '状态（0正常 1暂停）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '备注信息',
  PRIMARY KEY (`job_id`,`job_name`,`job_group`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='定时任务调度表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_job`
--

LOCK TABLES `sys_job` WRITE;
/*!40000 ALTER TABLE `sys_job` DISABLE KEYS */;
INSERT INTO `sys_job` VALUES (1,'系统默认（无参）','DEFAULT','ryTask.ryNoParams','0/10 * * * * ?','3','1','1','admin','2026-06-20 09:21:56','',NULL,''),(2,'系统默认（有参）','DEFAULT','ryTask.ryParams(\'ry\')','0/15 * * * * ?','3','1','1','admin','2026-06-20 09:21:56','',NULL,''),(3,'系统默认（多参）','DEFAULT','ryTask.ryMultipleParams(\'ry\', true, 2000L, 316.50D, 100)','0/20 * * * * ?','3','1','1','admin','2026-06-20 09:21:56','',NULL,'');
/*!40000 ALTER TABLE `sys_job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_job_log`
--

DROP TABLE IF EXISTS `sys_job_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_job_log` (
  `job_log_id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
  `job_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调用目标字符串',
  `job_message` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '日志信息',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
  `exception_info` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '异常信息',
  `start_time` datetime DEFAULT NULL COMMENT '执行开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '执行结束时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='定时任务调度日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_job_log`
--

LOCK TABLES `sys_job_log` WRITE;
/*!40000 ALTER TABLE `sys_job_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_job_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_logininfor`
--

DROP TABLE IF EXISTS `sys_logininfor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_logininfor` (
  `info_id` bigint NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '用户账号',
  `ipaddr` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '操作系统',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '提示消息',
  `login_time` datetime DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`info_id`),
  KEY `idx_sys_logininfor_s` (`status`),
  KEY `idx_sys_logininfor_lt` (`login_time`)
) ENGINE=InnoDB AUTO_INCREMENT=115 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统访问记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_logininfor`
--

LOCK TABLES `sys_logininfor` WRITE;
/*!40000 ALTER TABLE `sys_logininfor` DISABLE KEYS */;
INSERT INTO `sys_logininfor` VALUES (100,'admin','127.0.0.1','内网IP','Edge 139','Windows >=10','0','登录成功','2026-06-20 09:58:01'),(101,'admin','127.0.0.1','内网IP','Chrome 149','Windows10','0','登录成功','2026-06-20 10:03:59'),(102,'admin','127.0.0.1','内网IP','Chrome 149','Windows10','0','登录成功','2026-06-20 10:30:26'),(103,'18142393977','127.0.0.1','内网IP','Chrome 149','Windows10','1','用户不存在/密码错误','2026-06-20 10:30:33'),(104,'admin','127.0.0.1','内网IP','Chrome 149','Windows10','1','用户不存在/密码错误','2026-06-20 10:31:02'),(105,'admin','127.0.0.1','内网IP','Chrome 149','Windows10','1','用户不存在/密码错误','2026-06-20 10:31:17'),(106,'admin','127.0.0.1','内网IP','Chrome 149','Windows10','1','用户不存在/密码错误','2026-06-20 10:31:22'),(107,'admin','127.0.0.1','内网IP','Chrome 149','Windows10','0','退出成功','2026-06-20 10:31:28'),(108,'admin','127.0.0.1','内网IP','Chrome 149','Windows10','0','登录成功','2026-06-20 10:31:43'),(109,'admin','127.0.0.1','内网IP','Chrome 149','Windows10','0','登录成功','2026-06-20 16:05:12'),(110,'admin','127.0.0.1','内网IP','Chrome 149','Windows10','0','登录成功','2026-06-20 18:31:45'),(111,'admin','127.0.0.1','内网IP','Chrome 149','Windows10','0','登录成功','2026-06-20 20:56:12'),(112,'admin','127.0.0.1','内网IP','Chrome 149','Windows10','0','登录成功','2026-06-20 21:51:57'),(113,'admin','127.0.0.1','内网IP','Chrome 149','Windows10','0','登录成功','2026-06-25 08:59:22'),(114,'admin','127.0.0.1','内网IP','Chrome 149','Windows10','0','登录成功','2026-06-25 12:07:27');
/*!40000 ALTER TABLE `sys_logininfor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_menu` (
  `menu_id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
  `parent_id` bigint DEFAULT '0' COMMENT '父菜单ID',
  `order_num` int DEFAULT '0' COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '组件路径',
  `query` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '路由参数',
  `route_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '路由名称',
  `is_frame` int DEFAULT '1' COMMENT '是否为外链（0是 1否）',
  `is_cache` int DEFAULT '0' COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2221 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='菜单权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` VALUES (1,'系统管理',0,21,'system',NULL,'','',1,0,'M','0','0','','system','admin','2026-06-20 09:21:55','',NULL,'系统管理目录'),(2,'系统监控',0,22,'monitor',NULL,'','',1,0,'M','0','0','','monitor','admin','2026-06-20 09:21:55','',NULL,'系统监控目录'),(3,'系统工具',0,23,'tool',NULL,'','',1,0,'M','0','0','','tool','admin','2026-06-20 09:21:55','',NULL,'系统工具目录'),(4,'质衡 Demo',0,24,'http://localhost:8081',NULL,'','',0,0,'M','0','0','','guide','admin','2026-06-20 09:21:55','',NULL,'质衡 Demo地址'),(100,'用户管理',1,1,'user','system/user/index','','',1,0,'C','0','0','system:user:list','user','admin','2026-06-20 09:21:55','',NULL,'用户管理菜单'),(101,'角色管理',1,2,'role','system/role/index','','',1,0,'C','0','0','system:role:list','peoples','admin','2026-06-20 09:21:55','',NULL,'角色管理菜单'),(102,'菜单管理',1,3,'menu','system/menu/index','','',1,0,'C','0','0','system:menu:list','tree-table','admin','2026-06-20 09:21:55','',NULL,'菜单管理菜单'),(103,'部门管理',1,4,'dept','system/dept/index','','',1,0,'C','0','0','system:dept:list','tree','admin','2026-06-20 09:21:55','',NULL,'部门管理菜单'),(104,'岗位管理',1,5,'post','system/post/index','','',1,0,'C','0','0','system:post:list','post','admin','2026-06-20 09:21:55','',NULL,'岗位管理菜单'),(105,'字典管理',1,6,'dict','system/dict/index','','',1,0,'C','0','0','system:dict:list','dict','admin','2026-06-20 09:21:55','',NULL,'字典管理菜单'),(106,'参数设置',1,7,'config','system/config/index','','',1,0,'C','0','0','system:config:list','edit','admin','2026-06-20 09:21:55','',NULL,'参数设置菜单'),(107,'通知公告',1,8,'notice','system/notice/index','','',1,0,'C','0','0','system:notice:list','message','admin','2026-06-20 09:21:55','',NULL,'通知公告菜单'),(108,'日志管理',1,9,'log','','','',1,0,'M','0','0','','log','admin','2026-06-20 09:21:55','',NULL,'日志管理菜单'),(109,'在线用户',2,1,'online','monitor/online/index','','',1,0,'C','0','0','monitor:online:list','online','admin','2026-06-20 09:21:55','',NULL,'在线用户菜单'),(110,'定时任务',2,2,'job','monitor/job/index','','',1,0,'C','0','0','monitor:job:list','job','admin','2026-06-20 09:21:55','',NULL,'定时任务菜单'),(111,'数据监控',2,3,'druid','monitor/druid/index','','',1,0,'C','0','0','monitor:druid:list','druid','admin','2026-06-20 09:21:55','',NULL,'数据监控菜单'),(112,'服务监控',2,4,'server','monitor/server/index','','',1,0,'C','0','0','monitor:server:list','server','admin','2026-06-20 09:21:55','',NULL,'服务监控菜单'),(113,'缓存监控',2,5,'cache','monitor/cache/index','','',1,0,'C','0','0','monitor:cache:list','redis','admin','2026-06-20 09:21:55','',NULL,'缓存监控菜单'),(114,'缓存列表',2,6,'cacheList','monitor/cache/list','','',1,0,'C','0','0','monitor:cache:list','redis-list','admin','2026-06-20 09:21:55','',NULL,'缓存列表菜单'),(115,'表单构建',3,1,'build','tool/build/index','','',1,0,'C','0','0','tool:build:list','build','admin','2026-06-20 09:21:55','',NULL,'表单构建菜单'),(116,'代码生成',3,2,'gen','tool/gen/index','','',1,0,'C','0','0','tool:gen:list','code','admin','2026-06-20 09:21:55','',NULL,'代码生成菜单'),(117,'系统接口',3,3,'swagger','tool/swagger/index','','',1,0,'C','0','0','tool:swagger:list','swagger','admin','2026-06-20 09:21:55','',NULL,'系统接口菜单'),(500,'操作日志',108,1,'operlog','monitor/operlog/index','','',1,0,'C','0','0','monitor:operlog:list','form','admin','2026-06-20 09:21:55','',NULL,'操作日志菜单'),(501,'登录日志',108,2,'logininfor','monitor/logininfor/index','','',1,0,'C','0','0','monitor:logininfor:list','logininfor','admin','2026-06-20 09:21:55','',NULL,'登录日志菜单'),(1000,'用户查询',100,1,'','','','',1,0,'F','0','0','system:user:query','#','admin','2026-06-20 09:21:55','',NULL,''),(1001,'用户新增',100,2,'','','','',1,0,'F','0','0','system:user:add','#','admin','2026-06-20 09:21:55','',NULL,''),(1002,'用户修改',100,3,'','','','',1,0,'F','0','0','system:user:edit','#','admin','2026-06-20 09:21:55','',NULL,''),(1003,'用户删除',100,4,'','','','',1,0,'F','0','0','system:user:remove','#','admin','2026-06-20 09:21:55','',NULL,''),(1004,'用户导出',100,5,'','','','',1,0,'F','0','0','system:user:export','#','admin','2026-06-20 09:21:55','',NULL,''),(1005,'用户导入',100,6,'','','','',1,0,'F','0','0','system:user:import','#','admin','2026-06-20 09:21:55','',NULL,''),(1006,'重置密码',100,7,'','','','',1,0,'F','0','0','system:user:resetPwd','#','admin','2026-06-20 09:21:55','',NULL,''),(1007,'角色查询',101,1,'','','','',1,0,'F','0','0','system:role:query','#','admin','2026-06-20 09:21:55','',NULL,''),(1008,'角色新增',101,2,'','','','',1,0,'F','0','0','system:role:add','#','admin','2026-06-20 09:21:55','',NULL,''),(1009,'角色修改',101,3,'','','','',1,0,'F','0','0','system:role:edit','#','admin','2026-06-20 09:21:55','',NULL,''),(1010,'角色删除',101,4,'','','','',1,0,'F','0','0','system:role:remove','#','admin','2026-06-20 09:21:55','',NULL,''),(1011,'角色导出',101,5,'','','','',1,0,'F','0','0','system:role:export','#','admin','2026-06-20 09:21:55','',NULL,''),(1012,'菜单查询',102,1,'','','','',1,0,'F','0','0','system:menu:query','#','admin','2026-06-20 09:21:55','',NULL,''),(1013,'菜单新增',102,2,'','','','',1,0,'F','0','0','system:menu:add','#','admin','2026-06-20 09:21:55','',NULL,''),(1014,'菜单修改',102,3,'','','','',1,0,'F','0','0','system:menu:edit','#','admin','2026-06-20 09:21:55','',NULL,''),(1015,'菜单删除',102,4,'','','','',1,0,'F','0','0','system:menu:remove','#','admin','2026-06-20 09:21:55','',NULL,''),(1016,'部门查询',103,1,'','','','',1,0,'F','0','0','system:dept:query','#','admin','2026-06-20 09:21:55','',NULL,''),(1017,'部门新增',103,2,'','','','',1,0,'F','0','0','system:dept:add','#','admin','2026-06-20 09:21:55','',NULL,''),(1018,'部门修改',103,3,'','','','',1,0,'F','0','0','system:dept:edit','#','admin','2026-06-20 09:21:55','',NULL,''),(1019,'部门删除',103,4,'','','','',1,0,'F','0','0','system:dept:remove','#','admin','2026-06-20 09:21:55','',NULL,''),(1020,'岗位查询',104,1,'','','','',1,0,'F','0','0','system:post:query','#','admin','2026-06-20 09:21:55','',NULL,''),(1021,'岗位新增',104,2,'','','','',1,0,'F','0','0','system:post:add','#','admin','2026-06-20 09:21:55','',NULL,''),(1022,'岗位修改',104,3,'','','','',1,0,'F','0','0','system:post:edit','#','admin','2026-06-20 09:21:55','',NULL,''),(1023,'岗位删除',104,4,'','','','',1,0,'F','0','0','system:post:remove','#','admin','2026-06-20 09:21:55','',NULL,''),(1024,'岗位导出',104,5,'','','','',1,0,'F','0','0','system:post:export','#','admin','2026-06-20 09:21:55','',NULL,''),(1025,'字典查询',105,1,'#','','','',1,0,'F','0','0','system:dict:query','#','admin','2026-06-20 09:21:55','',NULL,''),(1026,'字典新增',105,2,'#','','','',1,0,'F','0','0','system:dict:add','#','admin','2026-06-20 09:21:55','',NULL,''),(1027,'字典修改',105,3,'#','','','',1,0,'F','0','0','system:dict:edit','#','admin','2026-06-20 09:21:55','',NULL,''),(1028,'字典删除',105,4,'#','','','',1,0,'F','0','0','system:dict:remove','#','admin','2026-06-20 09:21:55','',NULL,''),(1029,'字典导出',105,5,'#','','','',1,0,'F','0','0','system:dict:export','#','admin','2026-06-20 09:21:55','',NULL,''),(1030,'参数查询',106,1,'#','','','',1,0,'F','0','0','system:config:query','#','admin','2026-06-20 09:21:55','',NULL,''),(1031,'参数新增',106,2,'#','','','',1,0,'F','0','0','system:config:add','#','admin','2026-06-20 09:21:55','',NULL,''),(1032,'参数修改',106,3,'#','','','',1,0,'F','0','0','system:config:edit','#','admin','2026-06-20 09:21:55','',NULL,''),(1033,'参数删除',106,4,'#','','','',1,0,'F','0','0','system:config:remove','#','admin','2026-06-20 09:21:55','',NULL,''),(1034,'参数导出',106,5,'#','','','',1,0,'F','0','0','system:config:export','#','admin','2026-06-20 09:21:55','',NULL,''),(1035,'公告查询',107,1,'#','','','',1,0,'F','0','0','system:notice:query','#','admin','2026-06-20 09:21:55','',NULL,''),(1036,'公告新增',107,2,'#','','','',1,0,'F','0','0','system:notice:add','#','admin','2026-06-20 09:21:55','',NULL,''),(1037,'公告修改',107,3,'#','','','',1,0,'F','0','0','system:notice:edit','#','admin','2026-06-20 09:21:55','',NULL,''),(1038,'公告删除',107,4,'#','','','',1,0,'F','0','0','system:notice:remove','#','admin','2026-06-20 09:21:55','',NULL,''),(1039,'操作查询',500,1,'#','','','',1,0,'F','0','0','monitor:operlog:query','#','admin','2026-06-20 09:21:55','',NULL,''),(1040,'操作删除',500,2,'#','','','',1,0,'F','0','0','monitor:operlog:remove','#','admin','2026-06-20 09:21:55','',NULL,''),(1041,'日志导出',500,3,'#','','','',1,0,'F','0','0','monitor:operlog:export','#','admin','2026-06-20 09:21:55','',NULL,''),(1042,'登录查询',501,1,'#','','','',1,0,'F','0','0','monitor:logininfor:query','#','admin','2026-06-20 09:21:55','',NULL,''),(1043,'登录删除',501,2,'#','','','',1,0,'F','0','0','monitor:logininfor:remove','#','admin','2026-06-20 09:21:55','',NULL,''),(1044,'日志导出',501,3,'#','','','',1,0,'F','0','0','monitor:logininfor:export','#','admin','2026-06-20 09:21:55','',NULL,''),(1045,'账户解锁',501,4,'#','','','',1,0,'F','0','0','monitor:logininfor:unlock','#','admin','2026-06-20 09:21:55','',NULL,''),(1046,'在线查询',109,1,'#','','','',1,0,'F','0','0','monitor:online:query','#','admin','2026-06-20 09:21:55','',NULL,''),(1047,'批量强退',109,2,'#','','','',1,0,'F','0','0','monitor:online:batchLogout','#','admin','2026-06-20 09:21:55','',NULL,''),(1048,'单条强退',109,3,'#','','','',1,0,'F','0','0','monitor:online:forceLogout','#','admin','2026-06-20 09:21:55','',NULL,''),(1049,'任务查询',110,1,'#','','','',1,0,'F','0','0','monitor:job:query','#','admin','2026-06-20 09:21:55','',NULL,''),(1050,'任务新增',110,2,'#','','','',1,0,'F','0','0','monitor:job:add','#','admin','2026-06-20 09:21:55','',NULL,''),(1051,'任务修改',110,3,'#','','','',1,0,'F','0','0','monitor:job:edit','#','admin','2026-06-20 09:21:55','',NULL,''),(1052,'任务删除',110,4,'#','','','',1,0,'F','0','0','monitor:job:remove','#','admin','2026-06-20 09:21:55','',NULL,''),(1053,'状态修改',110,5,'#','','','',1,0,'F','0','0','monitor:job:changeStatus','#','admin','2026-06-20 09:21:55','',NULL,''),(1054,'任务导出',110,6,'#','','','',1,0,'F','0','0','monitor:job:export','#','admin','2026-06-20 09:21:55','',NULL,''),(1055,'生成查询',116,1,'#','','','',1,0,'F','0','0','tool:gen:query','#','admin','2026-06-20 09:21:55','',NULL,''),(1056,'生成修改',116,2,'#','','','',1,0,'F','0','0','tool:gen:edit','#','admin','2026-06-20 09:21:55','',NULL,''),(1057,'生成删除',116,3,'#','','','',1,0,'F','0','0','tool:gen:remove','#','admin','2026-06-20 09:21:55','',NULL,''),(1058,'导入代码',116,4,'#','','','',1,0,'F','0','0','tool:gen:import','#','admin','2026-06-20 09:21:55','',NULL,''),(1059,'预览代码',116,5,'#','','','',1,0,'F','0','0','tool:gen:preview','#','admin','2026-06-20 09:21:55','',NULL,''),(1060,'生成代码',116,6,'#','','','',1,0,'F','0','0','tool:gen:code','#','admin','2026-06-20 09:21:55','',NULL,''),(2000,'用户管理',0,1,'accountManages',NULL,NULL,'',1,0,'M','0','0',NULL,'user','admin','2026-06-20 21:01:53','',NULL,''),(2001,'商城管理',0,2,'mallManages',NULL,NULL,'',1,0,'M','0','0',NULL,'shopping','admin','2026-06-20 21:02:28','',NULL,''),(2215,'测试场景',3,4,'scenario','tool/scenario/index','','',1,0,'C','0','0','tool:scenario:list','example','admin','2026-06-25 09:05:36','',NULL,'接口测试场景数据准备'),(2216,'场景加载',2215,1,'','','','',1,0,'F','0','0','tool:scenario:load','#','admin','2026-06-25 09:05:36','',NULL,''),(2217,'基线重置',2215,2,'','','','',1,0,'F','0','0','tool:scenario:reset','#','admin','2026-06-25 09:05:36','',NULL,''),(2218,'余额流水',2000,3,'accountBalanceRecord','account/accountBalanceRecord/index','','',1,0,'C','0','0','account:accountBalanceRecord:list','money','admin','2026-06-28 19:23:38','',NULL,'账号余额流水'),(2219,'余额流水导出',2218,1,'','','','',1,0,'F','0','0','account:accountBalanceRecord:export','#','admin','2026-06-28 19:23:38','',NULL,''),(2220,'余额流水查询',2218,2,'','','','',1,0,'F','0','0','account:accountBalanceRecord:query','#','admin','2026-06-28 19:23:38','',NULL,'');
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_notice`
--

DROP TABLE IF EXISTS `sys_notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_notice` (
  `notice_id` int NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `notice_title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公告标题',
  `notice_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公告类型（1通知 2公告）',
  `notice_content` longblob COMMENT '公告内容',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='通知公告表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_notice`
--

LOCK TABLES `sys_notice` WRITE;
/*!40000 ALTER TABLE `sys_notice` DISABLE KEYS */;
INSERT INTO `sys_notice` VALUES (1,'温馨提醒：2018-07-01 质衡 Demo 新版本发布啦','2',_binary '新版本内容','0','admin','2026-06-20 09:21:56','',NULL,'管理员'),(2,'维护通知：2018-07-01 质衡 Demo 系统凌晨维护','1',_binary '维护内容','0','admin','2026-06-20 09:21:56','',NULL,'管理员'),(3,'质衡 Demo 介绍','1',_binary '<p><span style=\"color: rgb(230, 0, 0);\">项目介绍</span></p><p><font color=\"#333333\">Qualitest Demo开源项目是为企业用户定制的后台脚手架框架，为企业打造的一站式解决方案，降低企业开发成本，提升开发效率。主要包括用户管理、角色管理、部门管理、菜单管理、参数管理、字典管理、</font><span style=\"color: rgb(51, 51, 51);\">岗位管理</span><span style=\"color: rgb(51, 51, 51);\">、定时任务</span><span style=\"color: rgb(51, 51, 51);\">、</span><span style=\"color: rgb(51, 51, 51);\">服务监控、登录日志、操作日志、代码生成等功能。其中，还支持多数据源、数据权限、国际化、Redis缓存、Docker部署、滑动验证码、第三方认证登录、分布式事务、</span><font color=\"#333333\">分布式文件存储</font><span style=\"color: rgb(51, 51, 51);\">、分库分表处理等技术特点。</span></p><p><img src=\"https://foruda.gitee.com/images/1773931848342439032/a4d22313_1815095.png\" style=\"width: 64px;\"><br></p><p><span style=\"color: rgb(230, 0, 0);\">官网及演示</span></p><p><span style=\"color: rgb(51, 51, 51);\">质衡 Demo地址：&nbsp;</span><a href=\"http://localhost:8081\" target=\"_blank\">http://localhost:8081</a><a href=\"http://localhost:8081\" target=\"_blank\"></a></p><p><span style=\"color: rgb(51, 51, 51);\">质衡文档地址：&nbsp;</span><a href=\"http://doc.Qualitest Demo.vip\" target=\"_blank\">http://doc.Qualitest Demo.vip</a><br></p><p><span style=\"color: rgb(51, 51, 51);\">演示地址【不分离版】：&nbsp;</span><a href=\"http://demo.Qualitest Demo.vip\" target=\"_blank\">http://demo.Qualitest Demo.vip</a></p><p><span style=\"color: rgb(51, 51, 51);\">演示地址【分离版本】：&nbsp;</span><a href=\"http://vue.Qualitest Demo.vip\" target=\"_blank\">http://vue.Qualitest Demo.vip</a></p><p><span style=\"color: rgb(51, 51, 51);\">演示地址【微服务版】：&nbsp;</span><a href=\"http://cloud.Qualitest Demo.vip\" target=\"_blank\">http://cloud.Qualitest Demo.vip</a></p><p><span style=\"color: rgb(51, 51, 51);\">演示地址【移动端版】：&nbsp;</span><a href=\"http://h5.Qualitest Demo.vip\" target=\"_blank\">http://h5.Qualitest Demo.vip</a></p><p><br style=\"color: rgb(48, 49, 51); font-family: &quot;Helvetica Neue&quot;, Helvetica, Arial, sans-serif; font-size: 12px;\"></p>','0','admin','2026-06-20 09:21:56','',NULL,'管理员');
/*!40000 ALTER TABLE `sys_notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_notice_read`
--

DROP TABLE IF EXISTS `sys_notice_read`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_notice_read` (
  `read_id` bigint NOT NULL AUTO_INCREMENT COMMENT '已读主键',
  `notice_id` int NOT NULL COMMENT '公告id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `read_time` datetime NOT NULL COMMENT '阅读时间',
  PRIMARY KEY (`read_id`),
  UNIQUE KEY `uk_user_notice` (`user_id`,`notice_id`) COMMENT '同一用户同一公告只记录一次'
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='公告已读记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_notice_read`
--

LOCK TABLES `sys_notice_read` WRITE;
/*!40000 ALTER TABLE `sys_notice_read` DISABLE KEYS */;
INSERT INTO `sys_notice_read` VALUES (1,3,1,'2026-06-20 09:58:22'),(2,2,1,'2026-06-20 09:58:22'),(3,1,1,'2026-06-20 09:58:22');
/*!40000 ALTER TABLE `sys_notice_read` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_oper_log`
--

DROP TABLE IF EXISTS `sys_oper_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_oper_log` (
  `oper_id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '模块标题',
  `business_type` int DEFAULT '0' COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '请求方式',
  `operator_type` int DEFAULT '0' COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '操作人员',
  `dept_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '部门名称',
  `oper_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '请求参数',
  `json_result` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '返回参数',
  `status` int DEFAULT '0' COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime DEFAULT NULL COMMENT '操作时间',
  `cost_time` bigint DEFAULT '0' COMMENT '消耗时间',
  PRIMARY KEY (`oper_id`),
  KEY `idx_sys_oper_log_bt` (`business_type`),
  KEY `idx_sys_oper_log_s` (`status`),
  KEY `idx_sys_oper_log_ot` (`oper_time`)
) ENGINE=InnoDB AUTO_INCREMENT=162 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='操作日志记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_oper_log`
--

LOCK TABLES `sys_oper_log` WRITE;
/*!40000 ALTER TABLE `sys_oper_log` DISABLE KEYS */;
INSERT INTO `sys_oper_log` VALUES (100,'参数管理',2,'com.demo.web.controller.system.SysConfigController.edit()','PUT',1,'admin','研发部门','/system/config','127.0.0.1','内网IP','{\"configId\":4,\"configKey\":\"sys.account.captchaEnabled\",\"configName\":\"账号自助-验证码开关\",\"configType\":\"Y\",\"configValue\":\"false\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 09:21:56\",\"params\":{},\"remark\":\"是否开启验证码功能（true开启，false关闭）\",\"updateBy\":\"admin\"} ','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 09:58:53',50),(101,'参数管理',9,'com.demo.web.controller.system.SysConfigController.refreshCache()','DELETE',1,'admin','研发部门','/system/config/refreshCache','127.0.0.1','内网IP','','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 09:58:54',19),(102,'参数管理',2,'com.demo.web.controller.system.SysConfigController.edit()','PUT',1,'admin','研发部门','/system/config','127.0.0.1','内网IP','{\"configId\":3,\"configKey\":\"sys.index.sideTheme\",\"configName\":\"主框架页-侧边栏主题\",\"configType\":\"Y\",\"configValue\":\"theme-light\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 09:21:56\",\"params\":{},\"remark\":\"深色主题theme-dark，浅色主题theme-light\",\"updateBy\":\"admin\"} ','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 10:03:54',12),(103,'参数管理',9,'com.demo.web.controller.system.SysConfigController.refreshCache()','DELETE',1,'admin','研发部门','/system/config/refreshCache','127.0.0.1','内网IP','','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 10:03:54',9),(104,'代码生成',6,'com.demo.generator.controller.GenController.importTableSave()','POST',1,'admin','研发部门','/tool/gen/importTable','127.0.0.1','内网IP','{\"tables\":\"sys_user\",\"tplWebType\":\"element-plus\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 10:08:49',332),(105,'代码生成',3,'com.demo.generator.controller.GenController.remove()','DELETE',1,'admin','研发部门','/tool/gen/1','127.0.0.1','内网IP','[1] ','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 18:32:37',199),(106,'代码生成',6,'com.demo.generator.controller.GenController.importTableSave()','POST',1,'admin','研发部门','/tool/gen/importTable','127.0.0.1','内网IP','{\"tables\":\"mall_product_sku,mall_product,mall_order_refund_item,mall_order_refund,mall_order_item,mall_order,mall_category,mall_cart,account,account_address\",\"tplWebType\":\"element-plus\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 18:35:10',656),(107,'代码生成',6,'com.demo.generator.controller.GenController.importTableSave()','POST',1,'admin','研发部门','/tool/gen/importTable','127.0.0.1','内网IP','{\"tables\":\"account_coupon,coupon\",\"tplWebType\":\"element-plus\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 18:35:19',88),(108,'代码生成',2,'com.demo.generator.controller.GenController.editSave()','PUT',1,'admin','研发部门','/tool/gen','127.0.0.1','内网IP','{\"abbTableName\":\"ac\",\"businessName\":\"accountCoupon\",\"className\":\"AccountCoupon\",\"columns\":[{\"capJavaField\":\"AccountCouponId\",\"columnComment\":\"账号优惠券ID\",\"columnId\":172,\"columnName\":\"account_coupon_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:19\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":false,\"isIncrement\":\"1\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"accountCouponId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":12,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"CouponId\",\"columnComment\":\"优惠券ID\",\"columnId\":173,\"columnName\":\"coupon_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:19\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"couponId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":12,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"AccountId\",\"columnComment\":\"账号ID\",\"columnId\":174,\"columnName\":\"account_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:19\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"accountId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":12,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"CouponName\",\"columnComment\":\"券名称\",\"columnId\":175,\"columnName\":\"coupon_name\",\"columnType\":\"varchar(64)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:19\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 18:38:19',123),(109,'代码生成',2,'com.demo.generator.controller.GenController.editSave()','PUT',1,'admin','研发部门','/tool/gen','127.0.0.1','内网IP','{\"abbTableName\":\"c\",\"businessName\":\"coupon\",\"className\":\"Coupon\",\"columns\":[{\"capJavaField\":\"CouponId\",\"columnComment\":\"优惠券ID\",\"columnId\":187,\"columnName\":\"coupon_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:19\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":false,\"isIncrement\":\"1\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"couponId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":13,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"CouponName\",\"columnComment\":\"优惠券名称\",\"columnId\":188,\"columnName\":\"coupon_name\",\"columnType\":\"varchar(64)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:19\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"couponName\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":13,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"ThresholdAmount\",\"columnComment\":\"满金额\",\"columnId\":189,\"columnName\":\"threshold_amount\",\"columnType\":\"decimal(10,2)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:19\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"0\",\"isRequired\":\"0\",\"javaField\":\"thresholdAmount\",\"javaType\":\"BigDecimal\",\"list\":true,\"params\":{},\"pk\":false,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":13,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"DiscountAmount\",\"columnComment\":\"减金额\",\"columnId\":190,\"columnName\":\"discount_amount\",\"columnType\":\"decimal(10,2)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:19\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\"','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 18:39:00',38),(110,'代码生成',2,'com.demo.generator.controller.GenController.editSave()','PUT',1,'admin','研发部门','/tool/gen','127.0.0.1','内网IP','{\"abbTableName\":\"mor\",\"businessName\":\"mallOrderRefund\",\"className\":\"MallOrderRefund\",\"columns\":[{\"capJavaField\":\"RefundId\",\"columnComment\":\"退款单ID\",\"columnId\":116,\"columnName\":\"refund_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":false,\"isIncrement\":\"0\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"refundId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":8,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"RefundNo\",\"columnComment\":\"退款单号\",\"columnId\":117,\"columnName\":\"refund_no\",\"columnType\":\"varchar(32)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"refundNo\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":8,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"OrderId\",\"columnComment\":\"订单ID\",\"columnId\":118,\"columnName\":\"order_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"orderId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":8,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"AccountId\",\"columnComment\":\"账号ID\",\"columnId\":119,\"columnName\":\"account_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQ','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 18:40:24',45),(111,'代码生成',2,'com.demo.generator.controller.GenController.synchDb()','GET',1,'admin','研发部门','/tool/gen/synchDb/mall_order_refund','127.0.0.1','内网IP','{}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 18:40:44',62),(112,'代码生成',2,'com.demo.generator.controller.GenController.editSave()','PUT',1,'admin','研发部门','/tool/gen','127.0.0.1','内网IP','{\"abbTableName\":\"mor\",\"businessName\":\"mallOrderRefund\",\"className\":\"MallOrderRefund\",\"columns\":[{\"capJavaField\":\"RefundId\",\"columnComment\":\"退款单ID\",\"columnId\":116,\"columnName\":\"refund_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":false,\"isIncrement\":\"0\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"refundId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":8,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 18:40:44\",\"usableColumn\":false},{\"capJavaField\":\"RefundNo\",\"columnComment\":\"退款单号\",\"columnId\":117,\"columnName\":\"refund_no\",\"columnType\":\"varchar(32)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"refundNo\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":8,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 18:40:44\",\"usableColumn\":false},{\"capJavaField\":\"OrderId\",\"columnComment\":\"订单ID\",\"columnId\":118,\"columnName\":\"order_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"orderId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":8,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 18:40:44\",\"usableColumn\":false},{\"capJavaField\":\"AccountId\",\"columnComment\":\"账号ID\",\"columnId\":119,\"columnName\":\"account_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 18:41:15',39),(113,'代码生成',2,'com.demo.generator.controller.GenController.editSave()','PUT',1,'admin','研发部门','/tool/gen','127.0.0.1','内网IP','{\"abbTableName\":\"mori\",\"businessName\":\"mallOrderRefundItem\",\"className\":\"MallOrderRefundItem\",\"columns\":[{\"capJavaField\":\"RefundItemId\",\"columnComment\":\"退款明细ID\",\"columnId\":134,\"columnName\":\"refund_item_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":false,\"isIncrement\":\"0\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"refundItemId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":9,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"RefundId\",\"columnComment\":\"退款单ID\",\"columnId\":135,\"columnName\":\"refund_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"refundId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":9,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"OrderId\",\"columnComment\":\"订单ID\",\"columnId\":136,\"columnName\":\"order_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"orderId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":9,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"OrderItemId\",\"columnComment\":\"订单明细ID\",\"columnId\":137,\"columnName\":\"order_item_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isL','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 20:57:03',315),(114,'代码生成',2,'com.demo.generator.controller.GenController.editSave()','PUT',1,'admin','研发部门','/tool/gen','127.0.0.1','内网IP','{\"abbTableName\":\"mp\",\"businessName\":\"mallProduct\",\"className\":\"MallProduct\",\"columns\":[{\"capJavaField\":\"ProductId\",\"columnComment\":\"商品ID\",\"columnId\":143,\"columnName\":\"product_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":false,\"isIncrement\":\"0\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"productId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":10,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"CategoryId\",\"columnComment\":\"分类ID\",\"columnId\":144,\"columnName\":\"category_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"categoryId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":10,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"ProductName\",\"columnComment\":\"商品名称\",\"columnId\":145,\"columnName\":\"product_name\",\"columnType\":\"varchar(128)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"productName\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":10,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"ProductCode\",\"columnComment\":\"商品编码\",\"columnId\":146,\"columnName\":\"product_code\",\"columnType\":\"varchar(64)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"i','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 20:57:57',160),(115,'代码生成',2,'com.demo.generator.controller.GenController.synchDb()','GET',1,'admin','研发部门','/tool/gen/synchDb/mall_product','127.0.0.1','内网IP','{}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 20:59:04',207),(116,'代码生成',2,'com.demo.generator.controller.GenController.synchDb()','GET',1,'admin','研发部门','/tool/gen/synchDb/mall_product_sku','127.0.0.1','内网IP','{}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 20:59:05',139),(117,'代码生成',2,'com.demo.generator.controller.GenController.synchDb()','GET',1,'admin','研发部门','/tool/gen/synchDb/account_address','127.0.0.1','内网IP','{}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 20:59:07',150),(118,'代码生成',2,'com.demo.generator.controller.GenController.synchDb()','GET',1,'admin','研发部门','/tool/gen/synchDb/mall_cart','127.0.0.1','内网IP','{}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 20:59:08',98),(119,'代码生成',2,'com.demo.generator.controller.GenController.synchDb()','GET',1,'admin','研发部门','/tool/gen/synchDb/mall_category','127.0.0.1','内网IP','{}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 20:59:10',93),(120,'代码生成',2,'com.demo.generator.controller.GenController.editSave()','PUT',1,'admin','研发部门','/tool/gen','127.0.0.1','内网IP','{\"abbTableName\":\"mp\",\"businessName\":\"mallProduct\",\"className\":\"MallProduct\",\"columns\":[{\"capJavaField\":\"ProductId\",\"columnComment\":\"商品ID\",\"columnId\":143,\"columnName\":\"product_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":false,\"isIncrement\":\"0\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"productId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":10,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 20:59:04\",\"usableColumn\":false},{\"capJavaField\":\"CategoryId\",\"columnComment\":\"分类ID\",\"columnId\":144,\"columnName\":\"category_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"categoryId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":10,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 20:59:04\",\"usableColumn\":false},{\"capJavaField\":\"ProductName\",\"columnComment\":\"商品名称\",\"columnId\":145,\"columnName\":\"product_name\",\"columnType\":\"varchar(128)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"productName\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":10,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 20:59:04\",\"usableColumn\":false},{\"capJavaField\":\"ProductDesc\",\"columnComment\":\"商品详情\",\"columnId\":148,\"columnName\":\"product_desc\",\"columnType\":\"text\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":true,\"','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 20:59:38',173),(121,'代码生成',2,'com.demo.generator.controller.GenController.synchDb()','GET',1,'admin','研发部门','/tool/gen/synchDb/mall_product','127.0.0.1','内网IP','{}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 20:59:42',131),(122,'代码生成',2,'com.demo.generator.controller.GenController.editSave()','PUT',1,'admin','研发部门','/tool/gen','127.0.0.1','内网IP','{\"abbTableName\":\"mp\",\"businessName\":\"mallProduct\",\"className\":\"MallProduct\",\"columns\":[{\"capJavaField\":\"ProductId\",\"columnComment\":\"商品ID\",\"columnId\":143,\"columnName\":\"product_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":false,\"isIncrement\":\"0\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"productId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":10,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 20:59:42\",\"usableColumn\":false},{\"capJavaField\":\"CategoryId\",\"columnComment\":\"分类ID\",\"columnId\":144,\"columnName\":\"category_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"categoryId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":10,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 20:59:42\",\"usableColumn\":false},{\"capJavaField\":\"ProductName\",\"columnComment\":\"商品名称\",\"columnId\":145,\"columnName\":\"product_name\",\"columnType\":\"varchar(128)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"productName\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":10,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 20:59:42\",\"usableColumn\":false},{\"capJavaField\":\"UnitName\",\"columnComment\":\"计量单位\",\"columnId\":149,\"columnName\":\"unit_name\",\"columnType\":\"varchar(16)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":true,','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 21:00:11',101),(123,'代码生成',2,'com.demo.generator.controller.GenController.editSave()','PUT',1,'admin','研发部门','/tool/gen','127.0.0.1','内网IP','{\"abbTableName\":\"mps\",\"businessName\":\"mallProductSku\",\"className\":\"MallProductSku\",\"columns\":[{\"capJavaField\":\"SkuId\",\"columnComment\":\"SKU ID\",\"columnId\":160,\"columnName\":\"sku_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":false,\"isIncrement\":\"0\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"skuId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":11,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 20:59:05\",\"usableColumn\":false},{\"capJavaField\":\"ProductId\",\"columnComment\":\"商品ID\",\"columnId\":161,\"columnName\":\"product_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"productId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":11,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 20:59:05\",\"usableColumn\":false},{\"capJavaField\":\"SkuName\",\"columnComment\":\"SKU名称\",\"columnId\":162,\"columnName\":\"sku_name\",\"columnType\":\"varchar(128)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"skuName\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":11,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 20:59:05\",\"usableColumn\":false},{\"capJavaField\":\"MarketPrice\",\"columnComment\":\"市场价\",\"columnId\":163,\"columnName\":\"market_price\",\"columnType\":\"decimal(10,2)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":true,\"htmlType\"','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 21:00:42',69),(124,'保存菜单排序',2,'com.demo.web.controller.system.SysMenuController.updateSort()','PUT',1,'admin','研发部门','/system/menu/updateSort','127.0.0.1','内网IP','{\"menuIds\":\"1,2,3,4\",\"orderNums\":\"21,22,23,24\"} ','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 21:01:15',39),(125,'菜单管理',1,'com.demo.web.controller.system.SysMenuController.add()','POST',1,'admin','研发部门','/system/menu','127.0.0.1','内网IP','{\"children\":[],\"createBy\":\"admin\",\"icon\":\"user\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuName\":\"用户管理\",\"menuType\":\"M\",\"orderNum\":1,\"params\":{},\"parentId\":0,\"path\":\"accountManages\",\"status\":\"0\",\"visible\":\"0\"} ','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 21:01:53',56),(126,'菜单管理',1,'com.demo.web.controller.system.SysMenuController.add()','POST',1,'admin','研发部门','/system/menu','127.0.0.1','内网IP','{\"children\":[],\"createBy\":\"admin\",\"icon\":\"shopping\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuName\":\"商城管理\",\"menuType\":\"M\",\"orderNum\":2,\"params\":{},\"parentId\":0,\"path\":\"mallManages\",\"status\":\"0\",\"visible\":\"0\"} ','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 21:02:28',31),(127,'代码生成',2,'com.demo.generator.controller.GenController.editSave()','PUT',1,'admin','研发部门','/tool/gen','127.0.0.1','内网IP','{\"abbTableName\":\"ac\",\"businessName\":\"accountCoupon\",\"className\":\"AccountCoupon\",\"columns\":[{\"capJavaField\":\"AccountCouponId\",\"columnComment\":\"账号优惠券ID\",\"columnId\":172,\"columnName\":\"account_coupon_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:19\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":false,\"isIncrement\":\"1\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"accountCouponId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":12,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 18:38:19\",\"usableColumn\":false},{\"capJavaField\":\"CouponId\",\"columnComment\":\"优惠券ID\",\"columnId\":173,\"columnName\":\"coupon_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:19\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"couponId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":12,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 18:38:19\",\"usableColumn\":false},{\"capJavaField\":\"AccountId\",\"columnComment\":\"账号ID\",\"columnId\":174,\"columnName\":\"account_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:19\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"accountId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":12,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 18:38:19\",\"usableColumn\":false},{\"capJavaField\":\"CouponName\",\"columnComment\":\"券名称\",\"columnId\":175,\"columnName\":\"coupon_name\",\"columnType\":\"varchar(64)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:19\",\"dictType\":\"\",\"edit','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 21:02:54',122),(128,'代码生成',2,'com.demo.generator.controller.GenController.editSave()','PUT',1,'admin','研发部门','/tool/gen','127.0.0.1','内网IP','{\"abbTableName\":\"c\",\"businessName\":\"coupon\",\"className\":\"Coupon\",\"columns\":[{\"capJavaField\":\"CouponId\",\"columnComment\":\"优惠券ID\",\"columnId\":187,\"columnName\":\"coupon_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:19\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":false,\"isIncrement\":\"1\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"couponId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":13,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 18:39:00\",\"usableColumn\":false},{\"capJavaField\":\"CouponName\",\"columnComment\":\"优惠券名称\",\"columnId\":188,\"columnName\":\"coupon_name\",\"columnType\":\"varchar(64)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:19\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"couponName\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":13,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 18:39:00\",\"usableColumn\":false},{\"capJavaField\":\"ThresholdAmount\",\"columnComment\":\"满金额\",\"columnId\":189,\"columnName\":\"threshold_amount\",\"columnType\":\"decimal(10,2)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:19\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"0\",\"isRequired\":\"0\",\"javaField\":\"thresholdAmount\",\"javaType\":\"BigDecimal\",\"list\":true,\"params\":{},\"pk\":false,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":13,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 18:39:00\",\"usableColumn\":false},{\"capJavaField\":\"DiscountAmount\",\"columnComment\":\"减金额\",\"columnId\":190,\"columnName\":\"discount_amount\",\"columnType\":\"decimal(10,2)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:19\",\"d','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 21:03:30',99),(129,'代码生成',2,'com.demo.generator.controller.GenController.editSave()','PUT',1,'admin','研发部门','/tool/gen','127.0.0.1','内网IP','{\"abbTableName\":\"mor\",\"businessName\":\"mallOrderRefund\",\"className\":\"MallOrderRefund\",\"columns\":[{\"capJavaField\":\"RefundId\",\"columnComment\":\"退款单ID\",\"columnId\":116,\"columnName\":\"refund_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":false,\"isIncrement\":\"0\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"refundId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":8,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 18:41:15\",\"usableColumn\":false},{\"capJavaField\":\"RefundNo\",\"columnComment\":\"退款单号\",\"columnId\":117,\"columnName\":\"refund_no\",\"columnType\":\"varchar(32)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"refundNo\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":8,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 18:41:15\",\"usableColumn\":false},{\"capJavaField\":\"OrderId\",\"columnComment\":\"订单ID\",\"columnId\":118,\"columnName\":\"order_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"orderId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":8,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 18:41:15\",\"usableColumn\":false},{\"capJavaField\":\"AccountId\",\"columnComment\":\"账号ID\",\"columnId\":119,\"columnName\":\"account_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 21:03:43',105),(130,'代码生成',2,'com.demo.generator.controller.GenController.editSave()','PUT',1,'admin','研发部门','/tool/gen','127.0.0.1','内网IP','{\"abbTableName\":\"mor\",\"businessName\":\"mallOrderRefund\",\"className\":\"MallOrderRefund\",\"columns\":[{\"capJavaField\":\"RefundId\",\"columnComment\":\"退款单ID\",\"columnId\":116,\"columnName\":\"refund_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":false,\"isIncrement\":\"0\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"refundId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":8,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:03:43\",\"usableColumn\":false},{\"capJavaField\":\"RefundNo\",\"columnComment\":\"退款单号\",\"columnId\":117,\"columnName\":\"refund_no\",\"columnType\":\"varchar(32)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"refundNo\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":8,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:03:43\",\"usableColumn\":false},{\"capJavaField\":\"OrderId\",\"columnComment\":\"订单ID\",\"columnId\":118,\"columnName\":\"order_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"orderId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":8,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:03:43\",\"usableColumn\":false},{\"capJavaField\":\"AccountId\",\"columnComment\":\"账号ID\",\"columnId\":119,\"columnName\":\"account_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 21:04:37',118),(131,'代码生成',2,'com.demo.generator.controller.GenController.editSave()','PUT',1,'admin','研发部门','/tool/gen','127.0.0.1','内网IP','{\"abbTableName\":\"mori\",\"businessName\":\"mallOrderRefundItem\",\"className\":\"MallOrderRefundItem\",\"columns\":[{\"capJavaField\":\"RefundItemId\",\"columnComment\":\"退款明细ID\",\"columnId\":134,\"columnName\":\"refund_item_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":false,\"isIncrement\":\"0\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"refundItemId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":9,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 20:57:02\",\"usableColumn\":false},{\"capJavaField\":\"RefundId\",\"columnComment\":\"退款单ID\",\"columnId\":135,\"columnName\":\"refund_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"refundId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":9,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 20:57:02\",\"usableColumn\":false},{\"capJavaField\":\"OrderId\",\"columnComment\":\"订单ID\",\"columnId\":136,\"columnName\":\"order_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"orderId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":9,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 20:57:02\",\"usableColumn\":false},{\"capJavaField\":\"OrderItemId\",\"columnComment\":\"订单明细ID\",\"columnId\":137,\"columnName\":\"order_item_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":t','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 21:04:44',78),(132,'代码生成',2,'com.demo.generator.controller.GenController.editSave()','PUT',1,'admin','研发部门','/tool/gen','127.0.0.1','内网IP','{\"abbTableName\":\"mp\",\"businessName\":\"mallProduct\",\"className\":\"MallProduct\",\"columns\":[{\"capJavaField\":\"ProductId\",\"columnComment\":\"商品ID\",\"columnId\":143,\"columnName\":\"product_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":false,\"isIncrement\":\"0\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"productId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":10,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:00:11\",\"usableColumn\":false},{\"capJavaField\":\"CategoryId\",\"columnComment\":\"分类ID\",\"columnId\":144,\"columnName\":\"category_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"categoryId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":10,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:00:11\",\"usableColumn\":false},{\"capJavaField\":\"ProductName\",\"columnComment\":\"商品名称\",\"columnId\":145,\"columnName\":\"product_name\",\"columnType\":\"varchar(128)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"productName\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":10,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:00:11\",\"usableColumn\":false},{\"capJavaField\":\"UnitName\",\"columnComment\":\"计量单位\",\"columnId\":149,\"columnName\":\"unit_name\",\"columnType\":\"varchar(16)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":true,','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 21:04:52',82),(133,'代码生成',2,'com.demo.generator.controller.GenController.editSave()','PUT',1,'admin','研发部门','/tool/gen','127.0.0.1','内网IP','{\"abbTableName\":\"mps\",\"businessName\":\"mallProductSku\",\"className\":\"MallProductSku\",\"columns\":[{\"capJavaField\":\"SkuId\",\"columnComment\":\"SKU ID\",\"columnId\":160,\"columnName\":\"sku_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":false,\"isIncrement\":\"0\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"skuId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":11,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:00:42\",\"usableColumn\":false},{\"capJavaField\":\"ProductId\",\"columnComment\":\"商品ID\",\"columnId\":161,\"columnName\":\"product_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"productId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":11,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:00:42\",\"usableColumn\":false},{\"capJavaField\":\"SkuName\",\"columnComment\":\"SKU名称\",\"columnId\":162,\"columnName\":\"sku_name\",\"columnType\":\"varchar(128)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"skuName\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":11,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:00:42\",\"usableColumn\":false},{\"capJavaField\":\"MarketPrice\",\"columnComment\":\"市场价\",\"columnId\":163,\"columnName\":\"market_price\",\"columnType\":\"decimal(10,2)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":true,\"htmlType\"','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 21:05:03',77),(134,'代码生成',2,'com.demo.generator.controller.GenController.editSave()','PUT',1,'admin','研发部门','/tool/gen','127.0.0.1','内网IP','{\"abbTableName\":\"a\",\"businessName\":\"account\",\"className\":\"Account\",\"columns\":[{\"capJavaField\":\"AccountId\",\"columnComment\":\"账号ID\",\"columnId\":21,\"columnName\":\"account_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":false,\"isIncrement\":\"1\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"accountId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":2,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"AccountNo\",\"columnComment\":\"账号编号\",\"columnId\":22,\"columnName\":\"account_no\",\"columnType\":\"varchar(32)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"accountNo\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":2,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"NickName\",\"columnComment\":\"昵称\",\"columnId\":23,\"columnName\":\"nick_name\",\"columnType\":\"varchar(64)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"nickName\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":2,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"Mobile\",\"columnComment\":\"手机号\",\"columnId\":24,\"columnName\":\"mobile\",\"columnType\":\"varchar(11)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 21:05:54',74),(135,'代码生成',2,'com.demo.generator.controller.GenController.synchDb()','GET',1,'admin','研发部门','/tool/gen/synchDb/account','127.0.0.1','内网IP','{}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 21:06:13',116),(136,'代码生成',2,'com.demo.generator.controller.GenController.editSave()','PUT',1,'admin','研发部门','/tool/gen','127.0.0.1','内网IP','{\"abbTableName\":\"a\",\"businessName\":\"account\",\"className\":\"Account\",\"columns\":[{\"capJavaField\":\"AccountId\",\"columnComment\":\"账号ID\",\"columnId\":21,\"columnName\":\"account_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":false,\"isIncrement\":\"1\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"accountId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":2,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:06:13\",\"usableColumn\":false},{\"capJavaField\":\"NickName\",\"columnComment\":\"昵称\",\"columnId\":23,\"columnName\":\"nick_name\",\"columnType\":\"varchar(64)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"nickName\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":2,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:06:13\",\"usableColumn\":false},{\"capJavaField\":\"Mobile\",\"columnComment\":\"手机号\",\"columnId\":24,\"columnName\":\"mobile\",\"columnType\":\"varchar(11)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"mobile\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":2,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:06:13\",\"usableColumn\":false},{\"capJavaField\":\"Password\",\"columnComment\":\"密码\",\"columnId\":25,\"columnName\":\"password\",\"columnType\":\"varchar(100)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 21:06:39',81),(137,'代码生成',2,'com.demo.generator.controller.GenController.editSave()','PUT',1,'admin','研发部门','/tool/gen','127.0.0.1','内网IP','{\"abbTableName\":\"aa\",\"businessName\":\"accountAddress\",\"className\":\"AccountAddress\",\"columns\":[{\"capJavaField\":\"AddressId\",\"columnComment\":\"地址ID\",\"columnId\":35,\"columnName\":\"address_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":false,\"isIncrement\":\"1\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"addressId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 20:59:07\",\"usableColumn\":false},{\"capJavaField\":\"AccountId\",\"columnComment\":\"账号ID\",\"columnId\":36,\"columnName\":\"account_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"accountId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 20:59:07\",\"usableColumn\":false},{\"capJavaField\":\"ReceiverName\",\"columnComment\":\"收货人姓名\",\"columnId\":37,\"columnName\":\"receiver_name\",\"columnType\":\"varchar(32)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"receiverName\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 20:59:07\",\"usableColumn\":false},{\"capJavaField\":\"ReceiverPhone\",\"columnComment\":\"收货人手机\",\"columnId\":38,\"columnName\":\"receiver_phone\",\"columnType\":\"varchar(11)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"ed','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 21:07:20',97),(138,'代码生成',2,'com.demo.generator.controller.GenController.editSave()','PUT',1,'admin','研发部门','/tool/gen','127.0.0.1','内网IP','{\"abbTableName\":\"mc\",\"businessName\":\"mallCart\",\"className\":\"MallCart\",\"columns\":[{\"capJavaField\":\"CartId\",\"columnComment\":\"购物车ID\",\"columnId\":49,\"columnName\":\"cart_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":false,\"isIncrement\":\"0\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"cartId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":4,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 20:59:08\",\"usableColumn\":false},{\"capJavaField\":\"AccountId\",\"columnComment\":\"账号ID\",\"columnId\":50,\"columnName\":\"account_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"accountId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":4,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 20:59:08\",\"usableColumn\":false},{\"capJavaField\":\"ProductId\",\"columnComment\":\"商品ID\",\"columnId\":51,\"columnName\":\"product_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"productId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":4,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 20:59:08\",\"usableColumn\":false},{\"capJavaField\":\"SkuId\",\"columnComment\":\"SKU ID\",\"columnId\":52,\"columnName\":\"sku_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":tru','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 21:07:47',67),(139,'代码生成',2,'com.demo.generator.controller.GenController.synchDb()','GET',1,'admin','研发部门','/tool/gen/synchDb/mall_cart','127.0.0.1','内网IP','{}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 21:08:15',101),(140,'代码生成',2,'com.demo.generator.controller.GenController.editSave()','PUT',1,'admin','研发部门','/tool/gen','127.0.0.1','内网IP','{\"abbTableName\":\"mc\",\"businessName\":\"mallCart\",\"className\":\"MallCart\",\"columns\":[{\"capJavaField\":\"CartId\",\"columnComment\":\"购物车ID\",\"columnId\":49,\"columnName\":\"cart_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":false,\"isIncrement\":\"0\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"cartId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":4,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:08:15\",\"usableColumn\":false},{\"capJavaField\":\"AccountId\",\"columnComment\":\"账号ID\",\"columnId\":50,\"columnName\":\"account_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"accountId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":4,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:08:15\",\"usableColumn\":false},{\"capJavaField\":\"ProductId\",\"columnComment\":\"商品ID\",\"columnId\":51,\"columnName\":\"product_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"productId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":4,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:08:15\",\"usableColumn\":false},{\"capJavaField\":\"SkuId\",\"columnComment\":\"SKU ID\",\"columnId\":52,\"columnName\":\"sku_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":tru','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 21:08:30',51),(141,'代码生成',2,'com.demo.generator.controller.GenController.editSave()','PUT',1,'admin','研发部门','/tool/gen','127.0.0.1','内网IP','{\"abbTableName\":\"mc\",\"businessName\":\"mallCategory\",\"className\":\"MallCategory\",\"columns\":[{\"capJavaField\":\"CategoryId\",\"columnComment\":\"分类ID\",\"columnId\":58,\"columnName\":\"category_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":false,\"isIncrement\":\"0\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"categoryId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":5,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 20:59:10\",\"usableColumn\":false},{\"capJavaField\":\"CategoryName\",\"columnComment\":\"分类名称\",\"columnId\":59,\"columnName\":\"category_name\",\"columnType\":\"varchar(64)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"categoryName\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":5,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 20:59:10\",\"usableColumn\":false},{\"capJavaField\":\"CategoryCode\",\"columnComment\":\"分类编码\",\"columnId\":60,\"columnName\":\"category_code\",\"columnType\":\"varchar(32)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"categoryCode\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":5,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 20:59:10\",\"usableColumn\":false},{\"capJavaField\":\"OrderNum\",\"columnComment\":\"显示顺序\",\"columnId\":61,\"columnName\":\"order_num\",\"columnType\":\"int\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 21:08:51',62),(142,'代码生成',2,'com.demo.generator.controller.GenController.synchDb()','GET',1,'admin','研发部门','/tool/gen/synchDb/mall_category','127.0.0.1','内网IP','{}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 21:09:11',82),(143,'代码生成',2,'com.demo.generator.controller.GenController.editSave()','PUT',1,'admin','研发部门','/tool/gen','127.0.0.1','内网IP','{\"abbTableName\":\"mc\",\"businessName\":\"mallCategory\",\"className\":\"MallCategory\",\"columns\":[{\"capJavaField\":\"CategoryId\",\"columnComment\":\"分类ID\",\"columnId\":58,\"columnName\":\"category_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":false,\"isIncrement\":\"0\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"categoryId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":5,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:09:11\",\"usableColumn\":false},{\"capJavaField\":\"CategoryName\",\"columnComment\":\"分类名称\",\"columnId\":59,\"columnName\":\"category_name\",\"columnType\":\"varchar(64)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"categoryName\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":5,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:09:11\",\"usableColumn\":false},{\"capJavaField\":\"OrderNum\",\"columnComment\":\"显示顺序\",\"columnId\":61,\"columnName\":\"order_num\",\"columnType\":\"int\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"0\",\"isRequired\":\"0\",\"javaField\":\"orderNum\",\"javaType\":\"Integer\",\"list\":true,\"params\":{},\"pk\":false,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":true,\"tableId\":5,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:09:11\",\"usableColumn\":true},{\"capJavaField\":\"Status\",\"columnComment\":\"状态（0正常 1停用）\",\"columnId\":62,\"columnName\":\"status\",\"columnType\":\"tinyint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":true,\"htmlType','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 21:09:33',49),(144,'代码生成',2,'com.demo.generator.controller.GenController.editSave()','PUT',1,'admin','研发部门','/tool/gen','127.0.0.1','内网IP','{\"abbTableName\":\"mo\",\"businessName\":\"mallOrder\",\"className\":\"MallOrder\",\"columns\":[{\"capJavaField\":\"OrderId\",\"columnComment\":\"订单ID\",\"columnId\":67,\"columnName\":\"order_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":false,\"isIncrement\":\"0\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"orderId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":6,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"OrderNo\",\"columnComment\":\"订单编号\",\"columnId\":68,\"columnName\":\"order_no\",\"columnType\":\"varchar(32)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"orderNo\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":6,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"AccountId\",\"columnComment\":\"账号ID\",\"columnId\":69,\"columnName\":\"account_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"accountId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":6,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"OrderStatus\",\"columnComment\":\"订单状态（0待付款 1待发货 2待收货 3已完成 4已取消 5退款中）\",\"columnId\":70,\"columnName\":\"order_status\",\"columnType\":\"tinyint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"radio\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 21:11:43',187),(145,'代码生成',2,'com.demo.generator.controller.GenController.editSave()','PUT',1,'admin','研发部门','/tool/gen','127.0.0.1','内网IP','{\"abbTableName\":\"moi\",\"businessName\":\"mallOrderItem\",\"className\":\"MallOrderItem\",\"columns\":[{\"capJavaField\":\"OrderItemId\",\"columnComment\":\"订单明细ID\",\"columnId\":101,\"columnName\":\"order_item_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":false,\"isIncrement\":\"0\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"orderItemId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":7,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"OrderId\",\"columnComment\":\"订单ID\",\"columnId\":102,\"columnName\":\"order_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"orderId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":7,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"ProductId\",\"columnComment\":\"商品ID\",\"columnId\":103,\"columnName\":\"product_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"productId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":7,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"SkuId\",\"columnComment\":\"SKU ID\",\"columnId\":104,\"columnName\":\"sku_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuer','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 21:12:04',96),(146,'代码生成',2,'com.demo.generator.controller.GenController.synchDb()','GET',1,'admin','研发部门','/tool/gen/synchDb/mall_order_item','127.0.0.1','内网IP','{}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 21:12:24',96),(147,'代码生成',2,'com.demo.generator.controller.GenController.editSave()','PUT',1,'admin','研发部门','/tool/gen','127.0.0.1','内网IP','{\"abbTableName\":\"moi\",\"businessName\":\"mallOrderItem\",\"className\":\"MallOrderItem\",\"columns\":[{\"capJavaField\":\"OrderItemId\",\"columnComment\":\"订单明细ID\",\"columnId\":101,\"columnName\":\"order_item_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"orderItemId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":7,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:12:23\",\"usableColumn\":false},{\"capJavaField\":\"OrderId\",\"columnComment\":\"订单ID\",\"columnId\":102,\"columnName\":\"order_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"orderId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":7,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:12:23\",\"usableColumn\":false},{\"capJavaField\":\"ProductId\",\"columnComment\":\"商品ID\",\"columnId\":103,\"columnName\":\"product_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"productId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":7,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:12:23\",\"usableColumn\":false},{\"capJavaField\":\"SkuId\",\"columnComment\":\"SKU ID\",\"columnId\":104,\"columnName\":\"sku_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":true,\"htmlType','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 21:13:17',99),(148,'代码生成',2,'com.demo.generator.controller.GenController.editSave()','PUT',1,'admin','研发部门','/tool/gen','127.0.0.1','内网IP','{\"abbTableName\":\"mo\",\"businessName\":\"mallOrder\",\"className\":\"MallOrder\",\"columns\":[{\"capJavaField\":\"OrderId\",\"columnComment\":\"订单ID\",\"columnId\":67,\"columnName\":\"order_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"orderId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":6,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:11:43\",\"usableColumn\":false},{\"capJavaField\":\"OrderNo\",\"columnComment\":\"订单编号\",\"columnId\":68,\"columnName\":\"order_no\",\"columnType\":\"varchar(32)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"orderNo\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":6,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:11:43\",\"usableColumn\":false},{\"capJavaField\":\"AccountId\",\"columnComment\":\"账号ID\",\"columnId\":69,\"columnName\":\"account_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"accountId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":6,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:11:43\",\"usableColumn\":false},{\"capJavaField\":\"OrderStatus\",\"columnComment\":\"订单状态（0待付款 1待发货 2待收货 3已完成 4已取消 5退款中）\",\"columnId\":70,\"columnName\":\"order_status\",\"columnType\":\"tinyint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 21:13:28',202),(149,'代码生成',2,'com.demo.generator.controller.GenController.editSave()','PUT',1,'admin','研发部门','/tool/gen','127.0.0.1','内网IP','{\"abbTableName\":\"mc\",\"businessName\":\"mallCategory\",\"className\":\"MallCategory\",\"columns\":[{\"capJavaField\":\"CategoryId\",\"columnComment\":\"分类ID\",\"columnId\":58,\"columnName\":\"category_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"categoryId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":5,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:09:33\",\"usableColumn\":false},{\"capJavaField\":\"CategoryName\",\"columnComment\":\"分类名称\",\"columnId\":59,\"columnName\":\"category_name\",\"columnType\":\"varchar(64)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"categoryName\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":5,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:09:33\",\"usableColumn\":false},{\"capJavaField\":\"OrderNum\",\"columnComment\":\"显示顺序\",\"columnId\":61,\"columnName\":\"order_num\",\"columnType\":\"int\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"0\",\"isRequired\":\"0\",\"javaField\":\"orderNum\",\"javaType\":\"Integer\",\"list\":true,\"params\":{},\"pk\":false,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":true,\"tableId\":5,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:09:33\",\"usableColumn\":true},{\"capJavaField\":\"Status\",\"columnComment\":\"状态（0正常 1停用）\",\"columnId\":62,\"columnName\":\"status\",\"columnType\":\"tinyint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 21:13:37',55),(150,'代码生成',2,'com.demo.generator.controller.GenController.editSave()','PUT',1,'admin','研发部门','/tool/gen','127.0.0.1','内网IP','{\"abbTableName\":\"mc\",\"businessName\":\"mallCart\",\"className\":\"MallCart\",\"columns\":[{\"capJavaField\":\"CartId\",\"columnComment\":\"购物车ID\",\"columnId\":49,\"columnName\":\"cart_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"cartId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":4,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:08:30\",\"usableColumn\":false},{\"capJavaField\":\"AccountId\",\"columnComment\":\"账号ID\",\"columnId\":50,\"columnName\":\"account_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"accountId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":4,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:08:30\",\"usableColumn\":false},{\"capJavaField\":\"ProductId\",\"columnComment\":\"商品ID\",\"columnId\":51,\"columnName\":\"product_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"productId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":4,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:08:30\",\"usableColumn\":false},{\"capJavaField\":\"SkuId\",\"columnComment\":\"SKU ID\",\"columnId\":52,\"columnName\":\"sku_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":fals','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 21:13:47',49),(151,'代码生成',2,'com.demo.generator.controller.GenController.editSave()','PUT',1,'admin','研发部门','/tool/gen','127.0.0.1','内网IP','{\"abbTableName\":\"aa\",\"businessName\":\"accountAddress\",\"className\":\"AccountAddress\",\"columns\":[{\"capJavaField\":\"AddressId\",\"columnComment\":\"地址ID\",\"columnId\":35,\"columnName\":\"address_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":true,\"isIncrement\":\"1\",\"isInsert\":\"1\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"addressId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:07:20\",\"usableColumn\":false},{\"capJavaField\":\"AccountId\",\"columnComment\":\"账号ID\",\"columnId\":36,\"columnName\":\"account_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"accountId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:07:20\",\"usableColumn\":false},{\"capJavaField\":\"ReceiverName\",\"columnComment\":\"收货人姓名\",\"columnId\":37,\"columnName\":\"receiver_name\",\"columnType\":\"varchar(32)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"receiverName\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:07:20\",\"usableColumn\":false},{\"capJavaField\":\"ReceiverPhone\",\"columnComment\":\"收货人手机\",\"columnId\":38,\"columnName\":\"receiver_phone\",\"columnType\":\"varchar(11)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"di','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 21:13:54',82),(152,'代码生成',2,'com.demo.generator.controller.GenController.editSave()','PUT',1,'admin','研发部门','/tool/gen','127.0.0.1','内网IP','{\"abbTableName\":\"a\",\"businessName\":\"account\",\"className\":\"Account\",\"columns\":[{\"capJavaField\":\"AccountId\",\"columnComment\":\"账号ID\",\"columnId\":21,\"columnName\":\"account_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":true,\"isIncrement\":\"1\",\"isInsert\":\"1\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"accountId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":2,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:06:39\",\"usableColumn\":false},{\"capJavaField\":\"NickName\",\"columnComment\":\"昵称\",\"columnId\":23,\"columnName\":\"nick_name\",\"columnType\":\"varchar(64)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"nickName\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":2,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:06:39\",\"usableColumn\":false},{\"capJavaField\":\"Mobile\",\"columnComment\":\"手机号\",\"columnId\":24,\"columnName\":\"mobile\",\"columnType\":\"varchar(11)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"mobile\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":2,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:06:39\",\"usableColumn\":false},{\"capJavaField\":\"Password\",\"columnComment\":\"密码\",\"columnId\":25,\"columnName\":\"password\",\"columnType\":\"varchar(100)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:09\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"in','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 21:14:07',90),(153,'代码生成',2,'com.demo.generator.controller.GenController.editSave()','PUT',1,'admin','研发部门','/tool/gen','127.0.0.1','内网IP','{\"abbTableName\":\"mps\",\"businessName\":\"mallProductSku\",\"className\":\"MallProductSku\",\"columns\":[{\"capJavaField\":\"SkuId\",\"columnComment\":\"SKU ID\",\"columnId\":160,\"columnName\":\"sku_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"skuId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":11,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:05:03\",\"usableColumn\":false},{\"capJavaField\":\"ProductId\",\"columnComment\":\"商品ID\",\"columnId\":161,\"columnName\":\"product_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"productId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":11,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:05:03\",\"usableColumn\":false},{\"capJavaField\":\"SkuName\",\"columnComment\":\"SKU名称\",\"columnId\":162,\"columnName\":\"sku_name\",\"columnType\":\"varchar(128)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"skuName\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":11,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:05:03\",\"usableColumn\":false},{\"capJavaField\":\"MarketPrice\",\"columnComment\":\"市场价\",\"columnId\":163,\"columnName\":\"market_price\",\"columnType\":\"decimal(10,2)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":t','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 21:14:14',86),(154,'代码生成',2,'com.demo.generator.controller.GenController.editSave()','PUT',1,'admin','研发部门','/tool/gen','127.0.0.1','内网IP','{\"abbTableName\":\"mp\",\"businessName\":\"mallProduct\",\"className\":\"MallProduct\",\"columns\":[{\"capJavaField\":\"ProductId\",\"columnComment\":\"商品ID\",\"columnId\":143,\"columnName\":\"product_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"productId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":10,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:04:52\",\"usableColumn\":false},{\"capJavaField\":\"CategoryId\",\"columnComment\":\"分类ID\",\"columnId\":144,\"columnName\":\"category_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"categoryId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":10,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:04:52\",\"usableColumn\":false},{\"capJavaField\":\"ProductName\",\"columnComment\":\"商品名称\",\"columnId\":145,\"columnName\":\"product_name\",\"columnType\":\"varchar(128)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"productName\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":10,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:04:52\",\"usableColumn\":false},{\"capJavaField\":\"UnitName\",\"columnComment\":\"计量单位\",\"columnId\":149,\"columnName\":\"unit_name\",\"columnType\":\"varchar(16)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 21:14:20',91),(155,'代码生成',2,'com.demo.generator.controller.GenController.editSave()','PUT',1,'admin','研发部门','/tool/gen','127.0.0.1','内网IP','{\"abbTableName\":\"mori\",\"businessName\":\"mallOrderRefundItem\",\"className\":\"MallOrderRefundItem\",\"columns\":[{\"capJavaField\":\"RefundItemId\",\"columnComment\":\"退款明细ID\",\"columnId\":134,\"columnName\":\"refund_item_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"refundItemId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":9,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:04:44\",\"usableColumn\":false},{\"capJavaField\":\"RefundId\",\"columnComment\":\"退款单ID\",\"columnId\":135,\"columnName\":\"refund_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"refundId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":9,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:04:44\",\"usableColumn\":false},{\"capJavaField\":\"OrderId\",\"columnComment\":\"订单ID\",\"columnId\":136,\"columnName\":\"order_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"orderId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":9,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:04:44\",\"usableColumn\":false},{\"capJavaField\":\"OrderItemId\",\"columnComment\":\"订单明细ID\",\"columnId\":137,\"columnName\":\"order_item_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictTyp','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 21:14:28',76),(156,'代码生成',2,'com.demo.generator.controller.GenController.editSave()','PUT',1,'admin','研发部门','/tool/gen','127.0.0.1','内网IP','{\"abbTableName\":\"mor\",\"businessName\":\"mallOrderRefund\",\"className\":\"MallOrderRefund\",\"columns\":[{\"capJavaField\":\"RefundId\",\"columnComment\":\"退款单ID\",\"columnId\":116,\"columnName\":\"refund_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"refundId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":8,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:04:36\",\"usableColumn\":false},{\"capJavaField\":\"RefundNo\",\"columnComment\":\"退款单号\",\"columnId\":117,\"columnName\":\"refund_no\",\"columnType\":\"varchar(32)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"refundNo\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":8,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:04:36\",\"usableColumn\":false},{\"capJavaField\":\"OrderId\",\"columnComment\":\"订单ID\",\"columnId\":118,\"columnName\":\"order_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"orderId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":8,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:04:36\",\"usableColumn\":false},{\"capJavaField\":\"AccountId\",\"columnComment\":\"账号ID\",\"columnId\":119,\"columnName\":\"account_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:10\",\"dictType\":\"\",\"edit\":true,\"htmlT','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 21:14:34',138),(157,'代码生成',2,'com.demo.generator.controller.GenController.editSave()','PUT',1,'admin','研发部门','/tool/gen','127.0.0.1','内网IP','{\"abbTableName\":\"c\",\"businessName\":\"coupon\",\"className\":\"Coupon\",\"columns\":[{\"capJavaField\":\"CouponId\",\"columnComment\":\"优惠券ID\",\"columnId\":187,\"columnName\":\"coupon_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:19\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":true,\"isIncrement\":\"1\",\"isInsert\":\"1\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"couponId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":13,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:03:30\",\"usableColumn\":false},{\"capJavaField\":\"CouponName\",\"columnComment\":\"优惠券名称\",\"columnId\":188,\"columnName\":\"coupon_name\",\"columnType\":\"varchar(64)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:19\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"couponName\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":13,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:03:30\",\"usableColumn\":false},{\"capJavaField\":\"ThresholdAmount\",\"columnComment\":\"满金额\",\"columnId\":189,\"columnName\":\"threshold_amount\",\"columnType\":\"decimal(10,2)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:19\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"0\",\"isRequired\":\"0\",\"javaField\":\"thresholdAmount\",\"javaType\":\"BigDecimal\",\"list\":true,\"params\":{},\"pk\":false,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":13,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:03:30\",\"usableColumn\":false},{\"capJavaField\":\"DiscountAmount\",\"columnComment\":\"减金额\",\"columnId\":190,\"columnName\":\"discount_amount\",\"columnType\":\"decimal(10,2)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-2','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 21:14:39',109),(158,'代码生成',2,'com.demo.generator.controller.GenController.editSave()','PUT',1,'admin','研发部门','/tool/gen','127.0.0.1','内网IP','{\"abbTableName\":\"ac\",\"businessName\":\"accountCoupon\",\"className\":\"AccountCoupon\",\"columns\":[{\"capJavaField\":\"AccountCouponId\",\"columnComment\":\"账号优惠券ID\",\"columnId\":172,\"columnName\":\"account_coupon_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:19\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":true,\"isIncrement\":\"1\",\"isInsert\":\"1\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"accountCouponId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":12,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:02:54\",\"usableColumn\":false},{\"capJavaField\":\"CouponId\",\"columnComment\":\"优惠券ID\",\"columnId\":173,\"columnName\":\"coupon_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:19\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"couponId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":2,\"superColumn\":false,\"tableId\":12,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:02:54\",\"usableColumn\":false},{\"capJavaField\":\"AccountId\",\"columnComment\":\"账号ID\",\"columnId\":174,\"columnName\":\"account_id\",\"columnType\":\"bigint\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:19\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"0\",\"javaField\":\"accountId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":12,\"updateBy\":\"\",\"updateTime\":\"2026-06-20 21:02:54\",\"usableColumn\":false},{\"capJavaField\":\"CouponName\",\"columnComment\":\"券名称\",\"columnId\":175,\"columnName\":\"coupon_name\",\"columnType\":\"varchar(64)\",\"createBy\":\"admin\",\"createTime\":\"2026-06-20 18:35:19\",\"dict','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2026-06-20 21:14:45',89),(159,'代码生成',8,'com.demo.generator.controller.GenController.batchGenCode()','GET',1,'admin','研发部门','/tool/gen/batchGenCode','127.0.0.1','内网IP','{\"tables\":\"account_coupon,coupon,mall_order_refund,mall_order_refund_item,mall_product,mall_product_sku,account,account_address,mall_cart,mall_category,mall_order,mall_order_item\"}',NULL,0,NULL,'2026-06-20 21:15:14',2894),(160,'代码生成',8,'com.demo.generator.controller.GenController.batchGenCode()','GET',1,'admin','研发部门','/tool/gen/batchGenCode','127.0.0.1','内网IP','{\"tables\":\"account_coupon,coupon,mall_order_refund,mall_order_refund_item,mall_product,mall_product_sku,account,account_address,mall_cart,mall_category,mall_order,mall_order_item\"}',NULL,0,NULL,'2026-06-20 21:52:38',3383),(161,'代码生成',8,'com.demo.generator.controller.GenController.batchGenCode()','GET',1,'admin','研发部门','/tool/gen/batchGenCode','127.0.0.1','内网IP','{\"tables\":\"account_coupon,coupon,mall_order_refund,mall_order_refund_item,mall_product,mall_product_sku,account,account_address,mall_cart,mall_category,mall_order,mall_order_item\"}',NULL,0,NULL,'2026-06-20 22:04:12',4465);
/*!40000 ALTER TABLE `sys_oper_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_post`
--

DROP TABLE IF EXISTS `sys_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_post` (
  `post_id` bigint NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `post_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '岗位编码',
  `post_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '岗位名称',
  `post_sort` int NOT NULL COMMENT '显示顺序',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='岗位信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_post`
--

LOCK TABLES `sys_post` WRITE;
/*!40000 ALTER TABLE `sys_post` DISABLE KEYS */;
INSERT INTO `sys_post` VALUES (1,'ceo','董事长',1,'0','admin','2026-06-20 09:21:55','',NULL,''),(2,'se','项目经理',2,'0','admin','2026-06-20 09:21:55','',NULL,''),(3,'hr','人力资源',3,'0','admin','2026-06-20 09:21:55','',NULL,''),(4,'user','普通员工',4,'0','admin','2026-06-20 09:21:55','',NULL,'');
/*!40000 ALTER TABLE `sys_post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role` (
  `role_id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色权限字符串',
  `role_sort` int NOT NULL COMMENT '显示顺序',
  `data_scope` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `menu_check_strictly` tinyint(1) DEFAULT '1' COMMENT '菜单树选择项是否关联显示',
  `dept_check_strictly` tinyint(1) DEFAULT '1' COMMENT '部门树选择项是否关联显示',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (1,'超级管理员','admin',1,'1',1,1,'0','0','admin','2026-06-20 09:21:55','',NULL,'超级管理员'),(2,'普通角色','common',2,'2',1,1,'0','0','admin','2026-06-20 09:21:55','',NULL,'普通角色');
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_dept`
--

DROP TABLE IF EXISTS `sys_role_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_dept` (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `dept_id` bigint NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`role_id`,`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色和部门关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_dept`
--

LOCK TABLES `sys_role_dept` WRITE;
/*!40000 ALTER TABLE `sys_role_dept` DISABLE KEYS */;
INSERT INTO `sys_role_dept` VALUES (2,100),(2,101),(2,105);
/*!40000 ALTER TABLE `sys_role_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_menu` (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色和菜单关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu`
--

LOCK TABLES `sys_role_menu` WRITE;
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
INSERT INTO `sys_role_menu` VALUES (1,2215),(1,2216),(1,2217),(1,2218),(1,2219),(1,2220),(2,1),(2,2),(2,3),(2,4),(2,100),(2,101),(2,102),(2,103),(2,104),(2,105),(2,106),(2,107),(2,108),(2,109),(2,110),(2,111),(2,112),(2,113),(2,114),(2,115),(2,116),(2,117),(2,500),(2,501),(2,1000),(2,1001),(2,1002),(2,1003),(2,1004),(2,1005),(2,1006),(2,1007),(2,1008),(2,1009),(2,1010),(2,1011),(2,1012),(2,1013),(2,1014),(2,1015),(2,1016),(2,1017),(2,1018),(2,1019),(2,1020),(2,1021),(2,1022),(2,1023),(2,1024),(2,1025),(2,1026),(2,1027),(2,1028),(2,1029),(2,1030),(2,1031),(2,1032),(2,1033),(2,1034),(2,1035),(2,1036),(2,1037),(2,1038),(2,1039),(2,1040),(2,1041),(2,1042),(2,1043),(2,1044),(2,1045),(2,1046),(2,1047),(2,1048),(2,1049),(2,1050),(2,1051),(2,1052),(2,1053),(2,1054),(2,1055),(2,1056),(2,1057),(2,1058),(2,1059),(2,1060);
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `dept_id` bigint DEFAULT NULL COMMENT '部门ID',
  `user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户账号',
  `nick_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户昵称',
  `user_type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '00' COMMENT '用户类型（00系统用户）',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '手机号码',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '头像地址',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '密码',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '账号状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登录时间',
  `pwd_update_date` datetime DEFAULT NULL COMMENT '密码最后更新时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,103,'admin','质衡','00','ry@163.com','15888888888','1','','$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2','0','0','127.0.0.1','2026-06-25 12:07:28','2026-06-20 09:21:55','admin','2026-06-20 09:21:55','',NULL,'管理员'),(2,105,'ry','质衡','00','ry@qq.com','15666666666','1','','$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2','0','0','127.0.0.1','2026-06-20 09:21:55','2026-06-20 09:21:55','admin','2026-06-20 09:21:55','',NULL,'测试员');
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_post`
--

DROP TABLE IF EXISTS `sys_user_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_post` (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `post_id` bigint NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`user_id`,`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户与岗位关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_post`
--

LOCK TABLES `sys_user_post` WRITE;
/*!40000 ALTER TABLE `sys_user_post` DISABLE KEYS */;
INSERT INTO `sys_user_post` VALUES (1,1),(2,2);
/*!40000 ALTER TABLE `sys_user_post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_role` (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户和角色关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` VALUES (1,1),(2,2);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-28 19:27:21
