-- ============================================================================
-- 【失败场景 F11】已发货不可退款
-- 对应 §10 场景 7d：order_status=2 → refund preview/apply canRefund=false
-- 前置: reset_test_data.bat
-- ============================================================================

SET NAMES utf8mb4;

UPDATE `mall_product_sku` SET `stock` = 98, `sales_count` = 2, `update_time` = '2026-06-21 11:15:00' WHERE `sku_id` = 2101;
UPDATE `mall_product` SET `stock_total` = 198, `sales_count` = 2, `update_time` = '2026-06-21 11:15:00' WHERE `product_id` = 2001;
UPDATE `account` SET `balance` = 894.00, `update_time` = '2026-06-21 11:15:00' WHERE `account_id` = 30001;

INSERT INTO `mall_order` (`order_id`, `order_no`, `account_id`, `order_status`, `total_amount`, `discount_amount`, `coupon_id`, `coupon_name`, `coupon_threshold_amount`, `coupon_discount_amount`, `coupon_amount`, `freight_amount`, `pay_amount`, `account_coupon_id`, `receiver_name`, `receiver_phone`, `receiver_address`, `buyer_remark`, `cancel_reason`, `delivery_time`, `receive_time`, `finish_time`, `payment_no`, `pay_type`, `pay_status`, `transaction_no`, `pay_time`, `refund_amount`, `refund_status`, `refund_count`, `del_flag`, `create_time`, `update_time`, `remark`) VALUES
(7011, 'MO2026062111150001', 30001, 2, 98.00, 0.00, 0, '', 0.00, 0.00, 0.00, 8.00, 106.00, 0, '张三', '13800000001', '广东省深圳市南山区科技园南路1号', 'seed已发货不可退', '', '2026-06-21 12:00:00', NULL, NULL, 'PAY20260621111501', 3, 1, 'TX20260621111501', '2026-06-21 11:15:00', 0.00, 0, 0, 0, '2026-06-21 11:14:00', '2026-06-21 12:00:00', 'F11');

INSERT INTO `mall_order_item` (`order_item_id`, `order_id`, `product_id`, `sku_id`, `product_name`, `sku_name`, `sale_price`, `quantity`, `total_amount`, `refunded_quantity`, `refunded_amount`, `del_flag`, `create_time`, `update_time`) VALUES
(8012, 7011, 2001, 2101, 'iPhone 壳', '透明款', 49.00, 2, 98.00, 0, 0.00, 0, '2026-06-21 11:14:00', '2026-06-21 12:00:00');
