-- ============================================================================
-- 【失败场景 F07】余额不足支付
-- 对应 §10 场景 5b / 5b-ext：balance(100) < payAmount(210) → pay 失败；recharge 后可再 pay
-- 前置: reset_test_data.bat
--
-- 账号 30002 待付款订单 7008: 2122×1 = 198 + 运费 12 = 210
-- 预期: POST pay payType=3 → code≠200；POST recharge 后可重试 pay 成功
-- ============================================================================

SET NAMES utf8mb4;

UPDATE `mall_product_sku` SET `stock` = 99, `sales_count` = 1, `update_time` = '2026-06-21 11:00:00' WHERE `sku_id` = 2122;
UPDATE `mall_product` SET `stock_total` = 199, `sales_count` = 1, `update_time` = '2026-06-21 11:00:00' WHERE `product_id` = 2003;

INSERT INTO `mall_order` (`order_id`, `order_no`, `account_id`, `order_status`, `total_amount`, `discount_amount`, `coupon_id`, `coupon_name`, `coupon_threshold_amount`, `coupon_discount_amount`, `coupon_amount`, `freight_amount`, `pay_amount`, `account_coupon_id`, `receiver_name`, `receiver_phone`, `receiver_address`, `buyer_remark`, `cancel_reason`, `delivery_time`, `receive_time`, `finish_time`, `payment_no`, `pay_type`, `pay_status`, `transaction_no`, `pay_time`, `refund_amount`, `refund_status`, `refund_count`, `del_flag`, `create_time`, `update_time`, `remark`) VALUES
(7008, 'MO2026062111000001', 30002, 0, 198.00, 0.00, 0, '', 0.00, 0.00, 0.00, 12.00, 210.00, 0, '李四', '13800000002', '广东省广州市天河区体育西路100号', 'seed余额不足', '', NULL, NULL, NULL, '', 0, 0, '', NULL, 0.00, 0, 0, 0, '2026-06-21 11:00:00', '2026-06-21 11:00:00', 'F07');

INSERT INTO `mall_order_item` (`order_item_id`, `order_id`, `product_id`, `sku_id`, `product_name`, `sku_name`, `sale_price`, `quantity`, `total_amount`, `refunded_quantity`, `refunded_amount`, `del_flag`, `create_time`, `update_time`) VALUES
(8009, 7008, 2003, 2122, '坚果礼盒', '大盒装', 198.00, 1, 198.00, 0, 0.00, 0, '2026-06-21 11:00:00', '2026-06-21 11:00:00');
