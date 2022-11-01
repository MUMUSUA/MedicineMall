/*
 Navicat Premium Data Transfer

 Source Server         : Mysql
 Source Server Type    : MySQL
 Source Server Version : 80031
 Source Host           : localhost:3306
 Source Schema         : xmall

 Target Server Type    : MySQL
 Target Server Version : 80031
 File Encoding         : 65001

 Date: 31/10/2022 09:32:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for address
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address`  (
                            `address_areaId` char(6) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '主键ID',
                            `address_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '地区名称',
                            `address_regionId` char(6) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '父级地区ID',
                            PRIMARY KEY (`address_areaId`) USING BTREE,
                            INDEX `address_regionId`(`address_regionId`) USING BTREE,
                            CONSTRAINT `address_ibfk_1` FOREIGN KEY (`address_regionId`) REFERENCES `address` (`address_areaId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '中国省市区县' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
                          `admin_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '管理员ID',
                          `admin_realname` varchar(25) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '管理员姓名',
                          `admin_nickname` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '管理员昵称',
                          `admin_password` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '管理员密码',
                          `admin_profile_picture_src` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '管理员头像',
                          `permission` int(0) NULL DEFAULT NULL COMMENT '管理员权限1，超级管理员 2，普通管理员',
                          PRIMARY KEY (`admin_id`) USING BTREE,
                          UNIQUE INDEX `un_admin_name`(`admin_realname`) USING BTREE,
                          INDEX `un_admin_nickname`(`admin_nickname`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 66666 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '管理员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (66666, '张帆', 'Nick', '123456', '', 1);

-- ----------------------------
-- Table structure for brand
-- ----------------------------
DROP TABLE IF EXISTS `brand`;
CREATE TABLE `brand`  (
                          `brand_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '品牌ID',
                          `brand_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '品牌名称',
                          `brand_logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '品牌LOGO',
                          `brand_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '品牌描述',
                          `brand_status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '品牌状态',
                          `brand_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '品牌网址',
                          `created_time` date NULL DEFAULT NULL COMMENT '品牌加入时间',
                          `updated_time` date NULL DEFAULT NULL COMMENT '品牌信息更新时间',
                          PRIMARY KEY (`brand_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2112 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '品牌表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of brand
-- ----------------------------
INSERT INTO `brand` VALUES (2111, '祥和', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `brand` VALUES (2112, '奇正', NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
                             `category_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键 分类id主键',
                             `category_name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '分类名称 分类名称',
                             `category_level` int(0) NOT NULL COMMENT '分类层级 分类得类型，\n1:一级大分类\n2:二级分类\n3:三级小分类',
                             `parent_id` int(0) NOT NULL COMMENT '父层级id 父id 上一级依赖的id，1级分类则为0，二级三级分别依赖上一级',
                             `category_icon` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '图标 logo',
                             `category_slogan` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '口号',
                             `category_pic` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '分类图',
                             `category_bg_color` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '背景颜色',
                             PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 322222 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '商品分类' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (311111, '药品类', 1, 0, NULL, NULL, NULL, NULL);
INSERT INTO `category` VALUES (322222, '器械类', 1, 0, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for deliver
-- ----------------------------
DROP TABLE IF EXISTS `deliver`;
CREATE TABLE `deliver`  (
                            `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '快递主键ID',
                            `item_id` int(0) NULL DEFAULT NULL COMMENT '订单详情ID',
                            `flow_id` int(0) NULL DEFAULT NULL COMMENT '物流单号',
                            `ship_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '快递公司名称',
                            `ship_phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '快递公司联系方式',
                            `deliver_man` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '快递员姓名',
                            `deliver_phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '快递员联系电话',
                            `source` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '始发地',
                            `current_addr` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '当前所在地',
                            `destination` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '目的地',
                            `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '邮费价钱',
                            `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '快递状态 1：待收揽  2：派送中....',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 556 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单物流表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of deliver
-- ----------------------------
INSERT INTO `deliver` VALUES (555, 1114, 1115, '中通快递', '95720', '张海超', '15678995689', NULL, NULL, NULL, 0.00, '1');
INSERT INTO `deliver` VALUES (556, 1116, 1117, '顺丰快递', '95338', '王华', '18566985648', NULL, NULL, NULL, 0.00, '1');

-- ----------------------------
-- Table structure for index_img
-- ----------------------------
DROP TABLE IF EXISTS `index_img`;
CREATE TABLE `index_img`  (
                              `img_id` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '主键',
                              `img_url` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '图片 图片地址',
                              `img_bg_color` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '背景色 背景颜色',
                              `prod_id` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '商品id 商品id',
                              `category_id` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '商品分类id 商品分类id',
                              `index_type` int(0) NOT NULL COMMENT '轮播图类型 轮播图类型，用于判断，可以根据商品id或者分类进行页面跳转，1：商品 2：分类',
                              `seq` int(0) NOT NULL COMMENT '轮播图展示顺序 轮播图展示顺序，从小到大',
                              `status` int(0) NOT NULL COMMENT '是否展示:1表示正常显示，0表示下线 是否展示，1：展示    0：不展示',
                              `create_time` datetime(0) NOT NULL COMMENT '创建时间 创建时间',
                              `update_time` datetime(0) NOT NULL COMMENT '更新时间 更新',
                              PRIMARY KEY (`img_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '轮播图 ' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of index_img
-- ----------------------------
INSERT INTO `index_img` VALUES ('12222', '1111111', NULL, '110', '311111', 2, 1, 1, '2022-08-11 20:13:20', '2022-09-14 20:13:30');
INSERT INTO `index_img` VALUES ('12223', '2222222', NULL, '111', '322222', 2, 1, 1, '2022-08-25 20:14:42', '2022-10-14 20:14:49');

-- ----------------------------
-- Table structure for order_item
-- ----------------------------
DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item`  (
                               `item_id` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '订单项ID',
                               `order_id` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '订单ID',
                               `product_id` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '商品ID',
                               `product_name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '商品名称',
                               `product_img` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '商品图片',
                               `sku_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT 'skuID',
                               `sku_name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT 'sku名称',
                               `product_price` decimal(32, 8) NOT NULL COMMENT '商品价格',
                               `buy_counts` int(0) NOT NULL COMMENT '购买数量',
                               `total_amount` decimal(32, 8) NULL DEFAULT NULL COMMENT '商品总金额',
                               `basket_date` datetime(0) NULL DEFAULT NULL COMMENT '加入购物车时间',
                               `buy_time` datetime(0) NULL DEFAULT NULL COMMENT '购买时间',
                               `is_comment` int(0) NULL DEFAULT NULL COMMENT '评论状态： 0 未评价  1 已评价',
                               PRIMARY KEY (`item_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '订单项/快照 ' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of order_item
-- ----------------------------
INSERT INTO `order_item` VALUES ('1114', '1452255585656655', '110', '口罩', NULL, '120', '黑色N95五层防护口罩', 56.00000000, 2, 112.00000000, '2022-08-19 21:27:59', '2022-09-07 21:28:07', 1);
INSERT INTO `order_item` VALUES ('42', '154558889966566655', '111', '青鹏软膏', NULL, '26', '120g青鹏软膏', 35.00000000, 1, 35.00000000, '2022-09-15 21:41:43', '2022-10-04 21:41:48', 1);

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
                           `order_id` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '订单ID 同时也是订单编号',
                           `user_id` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户ID',
                           `untitled` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '产品名称（多个产品用,隔开）',
                           `receiver_name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '收货人快照',
                           `receiver_mobile` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '收货人手机号快照',
                           `receiver_address` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '收货地址快照',
                           `total_amount` decimal(32, 8) NULL DEFAULT NULL COMMENT '订单总价格',
                           `actual_amount` int(0) NULL DEFAULT NULL COMMENT '实际支付总价格',
                           `pay_type` int(0) NULL DEFAULT NULL COMMENT '支付方式 1:微信 2:支付宝',
                           `order_remark` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '订单备注',
                           `status` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '订单状态 1:待付款 2:待发货 3:待收货 4:待评价 5:已完成 6:已关闭',
                           `delivery_type` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '配送方式',
                           `delivery_flow_id` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '物流单号',
                           `order_freight` decimal(32, 8) NULL DEFAULT 0.00000000 COMMENT '订单运费 默认可以为零，代表包邮',
                           `delete_status` int(0) NULL DEFAULT 0 COMMENT '逻辑删除状态 1: 删除 0:未删除',
                           `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间（成交时间）',
                           `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
                           `pay_time` datetime(0) NULL DEFAULT NULL COMMENT '付款时间',
                           `delivery_time` datetime(0) NULL DEFAULT NULL COMMENT '发货时间',
                           `flish_time` datetime(0) NULL DEFAULT NULL COMMENT '完成时间',
                           `cancel_time` datetime(0) NULL DEFAULT NULL COMMENT '取消时间',
                           `close_type` int(0) NULL DEFAULT NULL COMMENT '订单关闭类型1-超时未支付 2-退款关闭 4-买家取消 15-已通过货到付款交易',
                           PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '订单 ' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES ('1452255585656655', '1', '口罩', 'Danny', '188888', '天津市西青区精武镇', 114.00000000, 56, 1, '尽快发货', '1', '快递', '121221211155448', 0.00000000, 0, '2022-10-05 17:10:59', '2022-10-19 17:11:04', '2022-10-05 17:11:17', '2022-10-19 17:11:10', '2022-10-06 17:11:25', '2022-10-05 17:11:33', NULL);
INSERT INTO `orders` VALUES ('154558889966566655', '2', '青鹏软膏', '小惠', '522224', '甘肃省天水市甘谷县新兴镇', 42.00000000, 35, 1, '尽快发货', '1', '快递', '254456687898889', 0.00000000, 0, '2022-10-13 17:15:19', '2022-10-14 17:15:22', '2022-10-13 17:15:27', '2022-10-15 17:16:26', '2022-10-15 17:16:30', '2022-10-13 17:16:53', NULL);

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
                            `product_id` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '商品主键id',
                            `product_name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '商品名称 商品名称',
                            `category_id` int(0) NOT NULL COMMENT '分类外键id 分类id',
                            `root_category_id` int(0) NOT NULL COMMENT '一级分类外键id 一级分类id，用于优化查询',
                            `sold_num` int(0) NOT NULL COMMENT '销量 累计销售',
                            `product_status` int(0) NOT NULL COMMENT '默认是1，表示正常状态, -1表示删除, 0下架 默认是1，表示正常状态, -1表示删除, 0下架',
                            `content` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '商品内容 商品内容',
                            `create_time` datetime(0) NOT NULL COMMENT '创建时间',
                            `update_time` datetime(0) NOT NULL COMMENT '更新时间',
                            PRIMARY KEY (`product_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '商品 商品信息相关表：分类表，商品图片表，商品规格表，商品参数表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('110', '口罩', 666, 66, 50000, 1, '有效阻隔飞沫粉尘，吸附细菌等微颗粒物', '2021-07-04 16:39:41', '2021-11-17 16:39:54');
INSERT INTO `product` VALUES ('111', '青鹏软膏', 999, 99, 4482, 1, '风湿性、类风湿性关节炎、骨关节炎、痛风、急慢性扭挫伤', '2022-05-18 16:44:16', '2022-07-22 16:44:29');

-- ----------------------------
-- Table structure for product_comments
-- ----------------------------
DROP TABLE IF EXISTS `product_comments`;
CREATE TABLE `product_comments`  (
                                     `comm_id` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT 'ID',
                                     `product_id` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '商品id',
                                     `product_name` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '商品名称',
                                     `order_item_id` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '订单项(商品快照)ID 可为空',
                                     `user_id` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '评论用户id 用户名须脱敏',
                                     `is_anonymous` int(0) NULL DEFAULT NULL COMMENT '是否匿名（1:是，0:否）',
                                     `comm_type` int(0) NULL DEFAULT NULL COMMENT '评价类型（1好评，0中评，-1差评）',
                                     `comm_level` int(0) NOT NULL COMMENT '评价等级 1：好评 2：中评 3：差评',
                                     `comm_content` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '评价内容',
                                     `comm_imgs` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '评价晒图(JSON {img1:url1,img2:url2}  )',
                                     `sepc_name` datetime(0) NULL DEFAULT NULL COMMENT '评价时间 可为空',
                                     `reply_status` int(0) NULL DEFAULT NULL COMMENT '是否回复（0:未回复，1:已回复）',
                                     `reply_content` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '回复内容',
                                     `reply_time` datetime(0) NULL DEFAULT NULL COMMENT '回复时间',
                                     `is_show` int(0) NULL DEFAULT NULL COMMENT '是否显示（1:是，0:否）',
                                     PRIMARY KEY (`comm_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '商品评价 ' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of product_comments
-- ----------------------------
INSERT INTO `product_comments` VALUES ('3', '110', '口罩', NULL, '1', 1, 1, 1, '非常好用', '', '2022-10-05 16:50:18', 1, '非常感谢您的支持', '2022-10-11 16:51:00', 1);
INSERT INTO `product_comments` VALUES ('4', '111', '青鹏软膏', NULL, '2', 1, 1, 1, '改善了很多', NULL, '2022-09-14 16:52:42', 1, '您的反馈是对我们最大的支持', '2022-10-12 16:53:22', 1);

-- ----------------------------
-- Table structure for product_img
-- ----------------------------
DROP TABLE IF EXISTS `product_img`;
CREATE TABLE `product_img`  (
                                `id` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '图片主键',
                                `item_id` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '商品外键id 商品外键id',
                                `url` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '图片地址 图片地址',
                                `sort` int(0) NOT NULL COMMENT '顺序 图片顺序，从小到大',
                                `is_main` int(0) NOT NULL COMMENT '是否主图 是否主图，1：是，0：否',
                                `created_time` datetime(0) NOT NULL COMMENT '创建时间',
                                `updated_time` datetime(0) NOT NULL COMMENT '更新时间',
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '商品图片 ' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of product_img
-- ----------------------------
INSERT INTO `product_img` VALUES ('7', '521', 'https://mro365.com/images/201712/goods_img/12866_P_1512410063548.jpg', 1, 1, '2022-09-08 16:57:42', '2022-10-03 16:57:48');
INSERT INTO `product_img` VALUES ('8', '522', 'https://c1.yaofangwang.net/common/upload/medicine/216/216961/10e97121-b53a-40ac-a266-17ff77a1390d4959.jpg_syp.jpg', 1, 1, '2022-05-12 16:59:01', '2022-07-22 16:59:11');

-- ----------------------------
-- Table structure for product_params
-- ----------------------------
DROP TABLE IF EXISTS `product_params`;
CREATE TABLE `product_params`  (
                                   `param_id` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '商品参数id',
                                   `product_id` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '商品id',
                                   `product_place` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '产地 ',
                                   `foot_period` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '保质期 ',
                                   `brand` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '品牌名 ',
                                   `factory_name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '生产厂名',
                                   `factory_address` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '生产厂址',
                                   `packaging_method` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '包装方式',
                                   `weight` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '规格重量',
                                   `storage_method` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '存储方法',
                                   `eat_method` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '食用方式 食用方式，例：开袋即食',
                                   `create_time` datetime(0) NOT NULL COMMENT '创建时间',
                                   `update_time` datetime(0) NOT NULL COMMENT '更新时间',
                                   PRIMARY KEY (`param_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '商品参数 ' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of product_params
-- ----------------------------
INSERT INTO `product_params` VALUES ('1111', '110', '湖北省黄冈市', '4年', '祥和', '祥和医疗股份有限公司', '山东省临汾市', '盒装 密封', '60g', '独立密封包装', '外用', '2022-09-24 09:13:45', '2022-10-06 12:13:52');
INSERT INTO `product_params` VALUES ('2222', '111', '西藏林芝市巴渝区', '2年', '奇正', '西藏奇正药业股份有限公司', '甘肃省兰州市榆中奇正大道', '盒装', '20g', '密封，置阴凉处', '外用', '2022-09-30 16:27:04', '2022-10-27 16:27:13');

-- ----------------------------
-- Table structure for product_sku
-- ----------------------------
DROP TABLE IF EXISTS `product_sku`;
CREATE TABLE `product_sku`  (
                                `sku_id` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT 'sku编号',
                                `product_id` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '商品id',
                                `sku_name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT 'sku名称',
                                `sku_img` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT 'sku图片',
                                `params` varchar(400) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '属性组合（格式是p1:v1;p2:v2）',
                                `original_price` int(0) NOT NULL COMMENT '原价',
                                `sell_price` int(0) NOT NULL COMMENT '销售价格',
                                `discounts` decimal(4, 2) NOT NULL COMMENT '折扣力度',
                                `sold_num` int(0) NULL DEFAULT NULL COMMENT '销售量',
                                `stock` int(0) NOT NULL COMMENT '库存',
                                `create_time` datetime(0) NOT NULL COMMENT '创建时间',
                                `update_time` datetime(0) NOT NULL COMMENT '更新时间',
                                `status` int(0) NULL DEFAULT NULL COMMENT 'sku状态(1启用，0禁用，-1删除)',
                                `brand_id` int(0) NULL DEFAULT NULL COMMENT '品牌ID',
                                `supplier_id` int(0) NULL DEFAULT NULL COMMENT '供应商名称',
                                `brand_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '品牌名称',
                                `supplier_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '供应商名称',
                                PRIMARY KEY (`sku_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '商品规格 每一件商品都有不同的规格，不同的规格又有不同的价格和优惠力度，规格表为此设计' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of product_sku
-- ----------------------------
INSERT INTO `product_sku` VALUES ('25', '110', '黑色N95五层防护口罩', 'default', NULL, 57, 28, 50.00, 1000, 2000, '2020-05-14 15:42:01', '2022-06-01 15:42:12', 1, NULL, NULL, NULL, NULL);
INSERT INTO `product_sku` VALUES ('26', '111', '120g青鹏软膏', 'default', NULL, 42, 35, 0.80, 233335, 122, '2022-08-16 15:50:02', '2022-09-23 05:12:18', 1, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for shopping_cart
-- ----------------------------
DROP TABLE IF EXISTS `shopping_cart`;
CREATE TABLE `shopping_cart`  (
                                  `cart_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                  `product_id` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '商品ID',
                                  `sku_id` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT 'skuID',
                                  `user_id` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户ID',
                                  `cart_num` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '购物车商品数量',
                                  `cart_time` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '添加购物车时间',
                                  `product_price` decimal(32, 8) NULL DEFAULT NULL COMMENT '添加购物车时商品价格',
                                  `sku_props` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '选择的套餐的属性',
                                  PRIMARY KEY (`cart_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '购物车 ' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of shopping_cart
-- ----------------------------
INSERT INTO `shopping_cart` VALUES (21, '110', '120', '1', '5', '2019-6-25', 57.00000000, '');
INSERT INTO `shopping_cart` VALUES (22, '111', '121', '2', '3', '2022-10-16', 42.00000000, NULL);

-- ----------------------------
-- Table structure for supplier
-- ----------------------------
DROP TABLE IF EXISTS `supplier`;
CREATE TABLE `supplier`  (
                             `supplier_id` int(0) NOT NULL COMMENT '供应商ID',
                             `supplier_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '供应商标识码',
                             `supplier_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '供应商名称',
                             `supplier_type` tinyint(0) NULL DEFAULT NULL COMMENT '供应商类型 1：自营 2：官方',
                             `link_man` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系人',
                             `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系人电话',
                             `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '供应地',
                             `supplier_status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '供应商状态',
                             `crested_time` date NULL DEFAULT NULL COMMENT '供应商加入时间',
                             `updated_time` date NULL DEFAULT NULL COMMENT '供应商更新时间',
                             PRIMARY KEY (`supplier_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '供应商信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of supplier
-- ----------------------------
INSERT INTO `supplier` VALUES (19, '11111111', '人人康大药房', 2, '张强', '15244896387', '湖南省长沙市长沙县嘉园里11单元120号', '正在营业', '2020-07-01', '2021-11-29');
INSERT INTO `supplier` VALUES (20, '22222222', '正泰医药', 1, '张禅风', '18722596654', '北京市朝阳区清华园24单元112号', '歇业中', '2021-07-29', '2021-12-23');

-- ----------------------------
-- Table structure for user_addr
-- ----------------------------
DROP TABLE IF EXISTS `user_addr`;
CREATE TABLE `user_addr`  (
                              `addr_id` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '地址主键id',
                              `user_id` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户ID',
                              `receiver_name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '收件人姓名',
                              `receiver_mobile` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '收件人手机号',
                              `province` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '省份',
                              `city` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '城市',
                              `area` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '区县',
                              `addr` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '详细地址',
                              `post_code` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '邮编',
                              `status` int(0) NULL DEFAULT NULL COMMENT '状态,1正常，0无效',
                              `common_addr` int(0) NULL DEFAULT NULL COMMENT '是否默认地址 1是 1:是  0:否',
                              `create_time` datetime(0) NOT NULL COMMENT '创建时间',
                              `update_time` datetime(0) NOT NULL COMMENT '更新时间',
                              PRIMARY KEY (`addr_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '用户地址 ' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user_addr
-- ----------------------------
INSERT INTO `user_addr` VALUES ('17', '1', 'Danny', '188888', '', '天津市', '西青区', '天津市西青区精武镇', '125888', 1, 1, '2022-10-06 11:21:33', '2022-10-23 11:21:39');
INSERT INTO `user_addr` VALUES ('18', '2', '小惠', '522224', '甘肃省', '天水市', '甘谷县', '甘肃省天水市甘谷县新兴镇', '741200', 1, 0, '2019-07-26 11:23:48', '2022-10-06 11:24:27');

-- ----------------------------
-- Table structure for user_browse
-- ----------------------------
DROP TABLE IF EXISTS `user_browse`;
CREATE TABLE `user_browse`  (
                                `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                `user_id` int(0) NULL DEFAULT NULL COMMENT '用户ID',
                                `product_id` int(0) NULL DEFAULT NULL COMMENT '商品ID',
                                `sku_id` int(0) NOT NULL COMMENT 'SKU ID',
                                `browse_time` datetime(0) NULL DEFAULT NULL COMMENT '浏览时间',
                                `browse_status` tinyint(0) NULL DEFAULT NULL COMMENT '浏览状态删除与否',
                                `product_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品名称',
                                `sku_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'sku名称',
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户流览表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_browse
-- ----------------------------
INSERT INTO `user_browse` VALUES (15, 1, 110, 120, '2022-10-07 11:10:47', 1, '口罩', '黑色N95五层防护口罩');
INSERT INTO `user_browse` VALUES (16, 2, 111, 121, '2022-05-18 11:12:38', 1, '青鹏软膏', '120g青鹏软膏');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
                              `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                              `user_id` int(0) NOT NULL COMMENT '用户ID\r\n',
                              `realname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户真实姓名',
                              `user_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户头像，默认为default',
                              `user_mobile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户手机号码',
                              `user_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户邮箱',
                              `user_sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户性别',
                              `user_birth` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户生日',
                              `user_regtime` date NULL DEFAULT NULL COMMENT '用户注册时间',
                              `user_modtime` date NULL DEFAULT NULL COMMENT '用户最近一次更改信息的时间',
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (13, 1, 'wanghuan', 'default.png', '188888', '15555', 'M', '2002-10-29', '2021-04-15', '2021-12-24');
INSERT INTO `user_info` VALUES (14, 2, 'honghu', 'default.png', '522224', '12555', 'F', '2005-12-03', '2021-04-16', '2022-09-07');

-- ----------------------------
-- Table structure for user_login
-- ----------------------------
DROP TABLE IF EXISTS `user_login`;
CREATE TABLE `user_login`  (
                               `id` int(0) NOT NULL COMMENT '主键ID',
                               `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户登录名',
                               `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户密码',
                               `status` tinyint(0) NULL DEFAULT NULL COMMENT '用户状态',
                               `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户手机话',
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户登陆表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_login
-- ----------------------------
INSERT INTO `user_login` VALUES (1, 'zhangsan', '1111', 1, '188888');
INSERT INTO `user_login` VALUES (2, 'hongli', '2222', 0, '522224');

-- ----------------------------
-- Table structure for user_login_history
-- ----------------------------
DROP TABLE IF EXISTS `user_login_history`;
CREATE TABLE `user_login_history`  (
                                       `ID` int(0) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                       `AREA` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '地区',
                                       `COUNTRY` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '国家',
                                       `USER_ID` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '用户id',
                                       `IP` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT 'IP',
                                       `LOGIN_TIME` date NOT NULL COMMENT '登录时间',
                                       `LOGOUT_TIME` date NULL DEFAULT NULL COMMENT '退出登录时间',
                                       PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '登录历史表 ' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user_login_history
-- ----------------------------
INSERT INTO `user_login_history` VALUES (11, 'ShangHai', 'China', '1', 'ShangHai', '2021-04-02', '2021-04-03');
INSERT INTO `user_login_history` VALUES (12, 'ZhaoYang', 'China', '2', 'BeiJing', '2021-11-11', '2022-11-11');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
                          `user_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键id 用户id',
                          `username` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户名 用户名',
                          `password` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '密码 密码',
                          `nickname` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '昵称 昵称',
                          `realname` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '真实姓名 真实姓名',
                          `user_img` varchar(1024) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '头像 头像',
                          `user_mobile` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '手机号 手机号',
                          `user_email` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '邮箱地址 邮箱地址',
                          `user_sex` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '性别 M(男) or F(女)',
                          `user_birth` date NULL DEFAULT NULL COMMENT '生日 生日',
                          `user_regtime` datetime(0) NOT NULL COMMENT '注册时间 创建时间',
                          `user_modtime` datetime(0) NOT NULL COMMENT '更新时间 更新时间',
                          PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '用户 ' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'zhangsan', '1111', 'Tom', 'wanghuan', 'default.png', '188888', '15555', 'M', '2002-10-29', '2021-04-15 16:10:53', '2021-04-15 16:10:53');
INSERT INTO `users` VALUES (2, 'hongli', '2222', 'Tony', 'honghu', 'default.png', '522224', '12555', 'F', '2005-12-03', '2021-04-16 15:36:52', '2021-04-16 15:36:52');

SET FOREIGN_KEY_CHECKS = 1;
