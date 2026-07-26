-- ============================================================================
-- 【失败场景 F04】库存不足
-- 对应 §10 场景 10：create 超库存 → 整单失败，零副作用
-- 前置: reset_test_data.bat
-- 预期: order/create items[{skuId:2101, quantity:5}] 失败（stock=2）
--       断言 stock 仍为 2，券 order_id 不变
-- ============================================================================

SET NAMES utf8mb4;

UPDATE `mall_product_sku` SET `stock` = 2, `update_time` = '2026-06-21 11:00:00', `remark` = 'F04低库存' WHERE `sku_id` = 2101;
UPDATE `mall_product` SET `stock_total` = 102, `update_time` = '2026-06-21 11:00:00' WHERE `product_id` = 2001;
