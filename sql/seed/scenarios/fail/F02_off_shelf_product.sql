-- ============================================================================
-- 【失败场景 F02】商品 SPU 已下架
-- 对应 §10 场景 13：shelf_status=0 → 加购/下单失败
-- 前置: reset_test_data.bat
-- 预期: POST /api/mall/mallCart/my skuId=2111 → 失败
-- ============================================================================

SET NAMES utf8mb4;

UPDATE `mall_product` SET `shelf_status` = 0, `update_time` = '2026-06-21 11:00:00', `remark` = 'F02下架' WHERE `product_id` = 2002;
