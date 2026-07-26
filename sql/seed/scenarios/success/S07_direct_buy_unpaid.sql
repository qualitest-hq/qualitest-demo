-- ============================================================================
-- 【成功场景 S07】直接购买待付款（无券）
-- 对应 §10 场景 15 / 6 / 6c：items[] 直接下单、取消、管理端关单
-- 前置: reset_test_data.bat
--
-- 订单 7006: 待付款，2101×1 = 49 + 运费 8 = payAmount 57
-- 预期: pay/cancel/admin close 均可操作
-- ============================================================================

SET NAMES utf8mb4;

UPDATE `mall_product_sku` SET `stock` = 99, `sales_count` = 1, `update_time` = '2026-06-21 10:35:00' WHERE `sku_id` = 2101;
UPDATE `mall_product` SET `stock_total` = 199, `sales_count` = 1, `update_time` = '2026-06-21 10:35:00' WHERE `product_id` = 2001;

INSERT INTO `mall_order` (`order_id`, `order_no`, `account_id`, `order_status`, `total_amount`, `discount_amount`, `coupon_id`, `coupon_name`, `coupon_threshold_amount`, `coupon_discount_amount`, `coupon_amount`, `freight_amount`, `pay_amount`, `account_coupon_id`, `receiver_name`, `receiver_phone`, `receiver_address`, `buyer_remark`, `cancel_reason`, `delivery_time`, `receive_time`, `finish_time`, `payment_no`, `pay_type`, `pay_status`, `transaction_no`, `pay_time`, `refund_amount`, `refund_status`, `refund_count`, `del_flag`, `create_time`, `update_time`, `remark`) VALUES
(7006, 'MO2026062110350001', 30001, 0, 49.00, 0.00, 0, '', 0.00, 0.00, 0.00, 8.00, 57.00, 0, '张三', '13800000001', '广东省深圳市南山区科技园南路1号', 'seed直接购买', '', NULL, NULL, NULL, '', 0, 0, '', NULL, 0.00, 0, 0, 0, '2026-06-21 10:35:00', '2026-06-21 10:35:00', 'S07');

INSERT INTO `mall_order_item` (`order_item_id`, `order_id`, `product_id`, `sku_id`, `product_name`, `sku_name`, `sale_price`, `quantity`, `total_amount`, `refunded_quantity`, `refunded_amount`, `del_flag`, `create_time`, `update_time`) VALUES
(8008, 7006, 2001, 2101, 'iPhone 壳', '透明款', 49.00, 1, 49.00, 0, 0.00, 0, '2026-06-21 10:35:00', '2026-06-21 10:35:00');
