-- ============================================================================
-- 【成功场景 S05】部分退款已完成
-- 对应 §10 场景 18 / 20：部分退后 orderStatus 恢复为 1；可再次 apply
-- 前置: reset_test_data.bat
--
-- 订单 7004: 待发货，已部分退款 refund_status=1
--   明细 8006: 2122×2，已退 1 件 refundAmount 198
--   订单累计 refund_amount=198, order_status=1
-- 预期: 第二笔 apply 不同明细可成功
-- ============================================================================

SET NAMES utf8mb4;

UPDATE `mall_product_sku` SET `stock` = 98, `sales_count` = 2, `update_time` = '2026-06-21 10:20:00' WHERE `sku_id` = 2122;
UPDATE `mall_product` SET `stock_total` = 198, `sales_count` = 2, `update_time` = '2026-06-21 10:20:00' WHERE `product_id` = 2003;
UPDATE `account` SET `balance` = 899.00, `update_time` = '2026-06-21 10:25:00' WHERE `account_id` = 30001;

INSERT INTO `mall_order` (`order_id`, `order_no`, `account_id`, `order_status`, `total_amount`, `discount_amount`, `coupon_id`, `coupon_name`, `coupon_threshold_amount`, `coupon_discount_amount`, `coupon_amount`, `freight_amount`, `pay_amount`, `account_coupon_id`, `receiver_name`, `receiver_phone`, `receiver_address`, `buyer_remark`, `cancel_reason`, `delivery_time`, `receive_time`, `finish_time`, `payment_no`, `pay_type`, `pay_status`, `transaction_no`, `pay_time`, `refund_amount`, `refund_status`, `refund_count`, `del_flag`, `create_time`, `update_time`, `remark`) VALUES
(7004, 'MO2026062110200001', 30001, 1, 396.00, 0.00, 0, '', 0.00, 0.00, 0.00, 12.00, 408.00, 0, '张三', '13800000001', '广东省深圳市南山区科技园南路1号', 'seed部分退', '', NULL, NULL, NULL, 'PAY20260621102001', 3, 1, 'TX20260621102001', '2026-06-21 10:20:00', 198.00, 1, 1, 0, '2026-06-21 10:19:00', '2026-06-21 10:25:00', 'S05');

INSERT INTO `mall_order_item` (`order_item_id`, `order_id`, `product_id`, `sku_id`, `product_name`, `sku_name`, `sale_price`, `quantity`, `total_amount`, `refunded_quantity`, `refunded_amount`, `del_flag`, `create_time`, `update_time`) VALUES
(8006, 7004, 2003, 2122, '坚果礼盒', '大盒装', 198.00, 2, 396.00, 1, 198.00, 0, '2026-06-21 10:19:00', '2026-06-21 10:25:00');

INSERT INTO `mall_order_refund` (`refund_id`, `refund_no`, `order_id`, `account_id`, `refund_type`, `refund_amount`, `refund_status`, `refund_reason`, `transaction_no`, `apply_time`, `refund_time`, `handle_time`, `handle_remark`, `del_flag`, `create_time`, `update_time`, `remark`) VALUES
(9001, 'RF2026062110250001', 7004, 30001, 2, 198.00, 4, '部分退货', 'RFTX20260621102501', '2026-06-21 10:22:00', '2026-06-21 10:25:00', '2026-06-21 10:23:00', '审核通过并完成', 0, '2026-06-21 10:22:00', '2026-06-21 10:25:00', 'S05');

INSERT INTO `mall_order_refund_item` (`refund_item_id`, `refund_id`, `order_id`, `order_item_id`, `refund_quantity`, `refund_amount`, `del_flag`, `create_time`, `update_time`) VALUES
(9101, 9001, 7004, 8006, 1, 198.00, 0, '2026-06-21 10:22:00', '2026-06-21 10:25:00');
