-- ============================================================================
-- 【成功场景 S06】退款待审核（退款中）
-- 对应 §10 场景 14 前置 / 7c reject 前置：存在进行中退款单
-- 前置: reset_test_data.bat
--
-- 订单 7005: order_status=5（退款中），refund_status=3
-- 退款单 9002: refund_status=0（待审核）
-- 预期: 再次 apply 失败；reject 后 order_status 恢复 1
-- ============================================================================

SET NAMES utf8mb4;

UPDATE `mall_product_sku` SET `stock` = 99, `sales_count` = 1, `update_time` = '2026-06-21 10:30:00' WHERE `sku_id` = 2112;
UPDATE `mall_product` SET `stock_total` = 199, `sales_count` = 1, `update_time` = '2026-06-21 10:30:00' WHERE `product_id` = 2002;
UPDATE `account` SET `balance` = 901.00, `update_time` = '2026-06-21 10:30:00' WHERE `account_id` = 30001;

INSERT INTO `mall_order` (`order_id`, `order_no`, `account_id`, `order_status`, `total_amount`, `discount_amount`, `coupon_id`, `coupon_name`, `coupon_threshold_amount`, `coupon_discount_amount`, `coupon_amount`, `freight_amount`, `pay_amount`, `account_coupon_id`, `receiver_name`, `receiver_phone`, `receiver_address`, `buyer_remark`, `cancel_reason`, `delivery_time`, `receive_time`, `finish_time`, `payment_no`, `pay_type`, `pay_status`, `transaction_no`, `pay_time`, `refund_amount`, `refund_status`, `refund_count`, `del_flag`, `create_time`, `update_time`, `remark`) VALUES
(7005, 'MO2026062110300001', 30001, 5, 99.00, 0.00, 0, '', 0.00, 0.00, 0.00, 0.00, 99.00, 0, '张三', '13800000001', '广东省深圳市南山区科技园南路1号', 'seed退款中', '', NULL, NULL, NULL, 'PAY20260621103001', 3, 1, 'TX20260621103001', '2026-06-21 10:30:00', 0.00, 3, 0, 0, '2026-06-21 10:29:00', '2026-06-21 10:31:00', 'S06');

INSERT INTO `mall_order_item` (`order_item_id`, `order_id`, `product_id`, `sku_id`, `product_name`, `sku_name`, `sale_price`, `quantity`, `total_amount`, `refunded_quantity`, `refunded_amount`, `del_flag`, `create_time`, `update_time`) VALUES
(8007, 7005, 2002, 2112, 'T 恤', 'L码黑色', 99.00, 1, 99.00, 0, 0.00, 0, '2026-06-21 10:29:00', '2026-06-21 10:31:00');

INSERT INTO `mall_order_refund` (`refund_id`, `refund_no`, `order_id`, `account_id`, `refund_type`, `refund_amount`, `refund_status`, `refund_reason`, `transaction_no`, `apply_time`, `refund_time`, `handle_time`, `handle_remark`, `del_flag`, `create_time`, `update_time`, `remark`) VALUES
(9002, 'RF2026062110310001', 7005, 30001, 1, 99.00, 0, '仅退款测试', '', '2026-06-21 10:31:00', NULL, NULL, '', 0, '2026-06-21 10:31:00', '2026-06-21 10:31:00', 'S06');

INSERT INTO `mall_order_refund_item` (`refund_item_id`, `refund_id`, `order_id`, `order_item_id`, `refund_quantity`, `refund_amount`, `del_flag`, `create_time`, `update_time`) VALUES
(9102, 9002, 7005, 8007, 1, 99.00, 0, '2026-06-21 10:31:00', '2026-06-21 10:31:00');
