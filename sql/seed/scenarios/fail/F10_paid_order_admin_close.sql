-- ============================================================================
-- 【失败场景 F10】已支付订单管理端关单拒绝
-- 对应 §10 场景 6c：admin close 仅待付款成功，已支付应拒绝
-- 前置: reset_test_data.bat
--
-- 订单 7010: order_status=1, pay_status=1（待发货已支付）
-- 预期: POST /web/mall/mallOrder/{orderId}/close → code≠200
-- ============================================================================

SET NAMES utf8mb4;

UPDATE `mall_product_sku` SET `stock` = 99, `sales_count` = 1, `update_time` = '2026-06-21 11:10:00' WHERE `sku_id` = 2111;
UPDATE `mall_product` SET `stock_total` = 199, `sales_count` = 1, `update_time` = '2026-06-21 11:10:00' WHERE `product_id` = 2002;
UPDATE `account` SET `balance` = 911.00, `update_time` = '2026-06-21 11:10:00' WHERE `account_id` = 30001;

INSERT INTO `mall_order` (`order_id`, `order_no`, `account_id`, `order_status`, `total_amount`, `discount_amount`, `coupon_id`, `coupon_name`, `coupon_threshold_amount`, `coupon_discount_amount`, `coupon_amount`, `freight_amount`, `pay_amount`, `account_coupon_id`, `receiver_name`, `receiver_phone`, `receiver_address`, `buyer_remark`, `cancel_reason`, `delivery_time`, `receive_time`, `finish_time`, `payment_no`, `pay_type`, `pay_status`, `transaction_no`, `pay_time`, `refund_amount`, `refund_status`, `refund_count`, `del_flag`, `create_time`, `update_time`, `remark`) VALUES
(7010, 'MO2026062111100001', 30001, 1, 89.00, 0.00, 0, '', 0.00, 0.00, 0.00, 0.00, 89.00, 0, '张三', '13800000001', '广东省深圳市南山区科技园南路1号', 'seed已支付关单', '', NULL, NULL, NULL, 'PAY20260621111001', 3, 1, 'TX20260621111001', '2026-06-21 11:10:00', 0.00, 0, 0, 0, '2026-06-21 11:09:00', '2026-06-21 11:10:00', 'F10');

INSERT INTO `mall_order_item` (`order_item_id`, `order_id`, `product_id`, `sku_id`, `product_name`, `sku_name`, `sale_price`, `quantity`, `total_amount`, `refunded_quantity`, `refunded_amount`, `del_flag`, `create_time`, `update_time`) VALUES
(8011, 7010, 2002, 2111, 'T 恤', 'M码白色', 89.00, 1, 89.00, 0, 0.00, 0, '2026-06-21 11:09:00', '2026-06-21 11:10:00');
