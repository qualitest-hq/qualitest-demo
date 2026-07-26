-- ============================================================================
-- 【失败场景 F06】满减门槛不满足
-- 对应 §5.7.2 / §10：totalAmount(98) < threshold(100) → 用券整单失败
-- 前置: reset_test_data.bat
--
-- 购物车: 2101×2 = 98.00（不含运费），accountCouponId=6001 不可用
-- 预期: order/preview 或 create 传 accountCouponId=6001 → code≠200
-- ============================================================================

SET NAMES utf8mb4;

INSERT INTO `mall_cart` (`cart_id`, `account_id`, `product_id`, `sku_id`, `quantity`, `del_flag`, `create_time`, `update_time`) VALUES
(5003, 30001, 2001, 2101, 2, 0, '2026-06-21 11:00:00', '2026-06-21 11:00:00');
