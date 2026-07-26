-- ============================================================================
-- 【失败场景 F05】过期优惠券
-- 对应 §8 场景 17：valid_end_time 在过去时 my/available 不返回
-- 前置: reset_test_data.bat
--
-- account_coupon 6003: 已过期 valid_end_time 在过去
-- 预期: GET accountCoupon/my/available 不含 6003
-- ============================================================================

SET NAMES utf8mb4;

INSERT INTO `account_coupon` (`account_coupon_id`, `coupon_id`, `account_id`, `coupon_name`, `threshold_amount`, `discount_amount`, `valid_start_time`, `valid_end_time`, `coupon_status`, `receive_time`, `use_time`, `order_id`, `del_flag`, `create_time`, `update_time`) VALUES
(6003, 3002, 30002, '满200减30', 200.00, 30.00, '2025-01-01 00:00:00', '2025-12-31 23:59:59', 0, '2025-06-01 10:00:00', NULL, 0, 0, '2025-06-01 10:00:00', '2026-06-21 11:00:00');

UPDATE `coupon` SET `receive_count` = 1, `update_time` = '2026-06-21 11:00:00' WHERE `coupon_id` = 3002;
