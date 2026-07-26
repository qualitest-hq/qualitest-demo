-- ============================================================================
-- 【成功场景 S02】待付款订单 + 优惠券占券
-- 对应 §10 场景 4 / 4b / 6b：用券下单占券、待付款释放占券、超时关单
-- 前置: reset_test_data.bat
--
-- 订单 7001（待付款）:
--   明细: 2122×1(198) + 2112×1(99) = totalAmount 297.00
--   券 6001: couponAmount 10, freight 12, payAmount 299.00
--   account_coupon 6001: order_id=7001, coupon_status=0（占券未核销）
-- 预期: pay 前 coupon_status=0；cancel/关单后 order_id 清空
-- ============================================================================

SET NAMES utf8mb4;

-- 扣减库存（与 create 对称）
UPDATE `mall_product_sku` SET `stock` = 99, `sales_count` = 1, `update_time` = '2026-06-21 10:05:00' WHERE `sku_id` IN (2122, 2112);
UPDATE `mall_product` SET `stock_total` = 199, `sales_count` = 1, `update_time` = '2026-06-21 10:05:00' WHERE `product_id` IN (2002, 2003);

-- 占券（不核销 used_count）
UPDATE `account_coupon` SET `order_id` = 7001, `update_time` = '2026-06-21 10:05:00' WHERE `account_coupon_id` = 6001;

INSERT INTO `mall_order` (`order_id`, `order_no`, `account_id`, `order_status`, `total_amount`, `discount_amount`, `coupon_id`, `coupon_name`, `coupon_threshold_amount`, `coupon_discount_amount`, `coupon_amount`, `freight_amount`, `pay_amount`, `account_coupon_id`, `receiver_name`, `receiver_phone`, `receiver_address`, `buyer_remark`, `cancel_reason`, `delivery_time`, `receive_time`, `finish_time`, `payment_no`, `pay_type`, `pay_status`, `transaction_no`, `pay_time`, `refund_amount`, `refund_status`, `refund_count`, `del_flag`, `create_time`, `update_time`, `remark`) VALUES
(7001, 'MO2026062110050001', 30001, 0, 297.00, 10.00, 3001, '满100减10', 100.00, 10.00, 10.00, 12.00, 299.00, 6001, '张三', '13800000001', '广东省深圳市南山区科技园南路1号', 'seed待付款用券', '', NULL, NULL, NULL, '', 0, 0, '', NULL, 0.00, 0, 0, 0, '2026-06-21 10:05:00', '2026-06-21 10:05:00', 'S02');

INSERT INTO `mall_order_item` (`order_item_id`, `order_id`, `product_id`, `sku_id`, `product_name`, `sku_name`, `sale_price`, `quantity`, `total_amount`, `refunded_quantity`, `refunded_amount`, `del_flag`, `create_time`, `update_time`) VALUES
(8001, 7001, 2003, 2122, '坚果礼盒', '大盒装', 198.00, 1, 198.00, 0, 0.00, 0, '2026-06-21 10:05:00', '2026-06-21 10:05:00'),
(8002, 7001, 2002, 2112, 'T 恤',     'L码黑色',  99.00, 1,  99.00, 0, 0.00, 0, '2026-06-21 10:05:00', '2026-06-21 10:05:00');
