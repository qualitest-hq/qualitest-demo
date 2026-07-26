-- ============================================================================
-- 【成功场景 S03】待发货 + 已支付（余额支付）
-- 对应 §10 场景 5 / 4c / 7 / 7b / 7c / 7e / 9：支付、退款申请、重复支付拦截
-- 前置: reset_test_data.bat
--
-- 订单 7002:
--   totalAmount 297, couponAmount 10, freight 12, payAmount 299
--   order_status=1, pay_status=1, pay_type=3
--   账号 30001 balance: 1000 - 299 = 701
--   券 6001 已核销 coupon_status=1, coupon.used_count=1
-- 预期: refund/preview canRefund=true；重复 pay 失败
-- ============================================================================

SET NAMES utf8mb4;

UPDATE `mall_product_sku` SET `stock` = 99, `sales_count` = 1, `update_time` = '2026-06-21 10:10:00' WHERE `sku_id` IN (2122, 2112);
UPDATE `mall_product` SET `stock_total` = 199, `sales_count` = 1, `update_time` = '2026-06-21 10:10:00' WHERE `product_id` IN (2002, 2003);
UPDATE `account` SET `balance` = 701.00, `update_time` = '2026-06-21 10:10:00' WHERE `account_id` = 30001;
UPDATE `coupon` SET `used_count` = 1, `update_time` = '2026-06-21 10:10:00' WHERE `coupon_id` = 3001;
UPDATE `account_coupon` SET `coupon_status` = 1, `order_id` = 7002, `use_time` = '2026-06-21 10:10:00', `update_time` = '2026-06-21 10:10:00' WHERE `account_coupon_id` = 6001;

INSERT INTO `mall_order` (`order_id`, `order_no`, `account_id`, `order_status`, `total_amount`, `discount_amount`, `coupon_id`, `coupon_name`, `coupon_threshold_amount`, `coupon_discount_amount`, `coupon_amount`, `freight_amount`, `pay_amount`, `account_coupon_id`, `receiver_name`, `receiver_phone`, `receiver_address`, `buyer_remark`, `cancel_reason`, `delivery_time`, `receive_time`, `finish_time`, `payment_no`, `pay_type`, `pay_status`, `transaction_no`, `pay_time`, `refund_amount`, `refund_status`, `refund_count`, `del_flag`, `create_time`, `update_time`, `remark`) VALUES
(7002, 'MO2026062110100001', 30001, 1, 297.00, 10.00, 3001, '满100减10', 100.00, 10.00, 10.00, 12.00, 299.00, 6001, '张三', '13800000001', '广东省深圳市南山区科技园南路1号', 'seed待发货', '', NULL, NULL, NULL, 'PAY20260621101001', 3, 1, 'TX20260621101001', '2026-06-21 10:10:00', 0.00, 0, 0, 0, '2026-06-21 10:09:00', '2026-06-21 10:10:00', 'S03');

INSERT INTO `mall_order_item` (`order_item_id`, `order_id`, `product_id`, `sku_id`, `product_name`, `sku_name`, `sale_price`, `quantity`, `total_amount`, `refunded_quantity`, `refunded_amount`, `del_flag`, `create_time`, `update_time`) VALUES
(8003, 7002, 2003, 2122, '坚果礼盒', '大盒装', 198.00, 1, 198.00, 0, 0.00, 0, '2026-06-21 10:09:00', '2026-06-21 10:10:00'),
(8004, 7002, 2002, 2112, 'T 恤',     'L码黑色',  99.00, 1,  99.00, 0, 0.00, 0, '2026-06-21 10:09:00', '2026-06-21 10:10:00');
