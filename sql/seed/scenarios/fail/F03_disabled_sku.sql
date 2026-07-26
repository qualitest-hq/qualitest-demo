-- ============================================================================
-- 【失败场景 F03】SKU 已停用
-- 对应 §10 场景 13：sku.status=1 → 加购/下单失败
-- 前置: reset_test_data.bat
-- 预期: POST /api/mall/mallCart/my skuId=2111 → 失败
-- ============================================================================

SET NAMES utf8mb4;

UPDATE `mall_product_sku` SET `status` = 1, `update_time` = '2026-06-21 11:00:00', `remark` = 'F03停用' WHERE `sku_id` = 2111;
