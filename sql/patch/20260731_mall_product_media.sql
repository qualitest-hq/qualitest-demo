-- 商品 SPU 媒体字段（demo/product-media 分支）
-- 已有库可手动执行；全新 Compose 初始化以 dump 中 CREATE TABLE 为准。

ALTER TABLE `mall_product`
  ADD COLUMN `cover_image` varchar(512) DEFAULT NULL COMMENT '封面图URL' AFTER `sale_price`,
  ADD COLUMN `detail_images` varchar(2000) DEFAULT NULL COMMENT '详情图URL，逗号分隔' AFTER `cover_image`,
  ADD COLUMN `video_url` varchar(512) DEFAULT NULL COMMENT '视频URL' AFTER `detail_images`;
