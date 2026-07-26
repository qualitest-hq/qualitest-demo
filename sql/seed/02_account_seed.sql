-- ============================================================================
-- qualitest-demo 账号测试数据（用户 / 地址 / 已领券）
-- 对应文档 §8.3 测试账号
-- 密码明文: Test@123456（BCrypt 加密存储）
-- ---------------------------------------------------------------------------
-- account_id  | mobile        | balance  | 用途
-- 30001       | 13800000001   | 1000.00  | 主测试账号（余额支付）
-- 30002       | 13800000002   |  100.00  | 余额不足场景
-- 30003       | 13800000003   |  500.00  | 越权测试（场景 11）
-- ============================================================================

SET NAMES utf8mb4;

-- 密码 Test@123456
SET @pwd = '$2b$10$uz/DBz4vrflekbw.SSsXTu1f3FaTyCQr0XE9g7.Mf1YIk5sdDDWpi';

INSERT INTO `account` (`account_id`, `nick_name`, `mobile`, `password`, `balance`, `gender`, `register_source`, `status`, `last_login_ip`, `last_login_time`, `del_flag`, `create_time`, `update_time`, `remark`) VALUES
(30001, '测试用户A', '13800000001', @pwd, 1000.00, 2, 'app', 0, '', NULL, 0, '2026-06-21 10:00:00', '2026-06-21 10:00:00', '主测试账号'),
(30002, '测试用户B', '13800000002', @pwd,  100.00, 2, 'app', 0, '', NULL, 0, '2026-06-21 10:00:00', '2026-06-21 10:00:00', '余额不足场景'),
(30003, '测试用户C', '13800000003', @pwd,  500.00, 2, 'app', 0, '', NULL, 0, '2026-06-21 10:00:00', '2026-06-21 10:00:00', '越权测试');

INSERT INTO `account_address` (`address_id`, `account_id`, `receiver_name`, `receiver_phone`, `province`, `city`, `district`, `detail_address`, `postal_code`, `is_default`, `del_flag`, `create_time`, `update_time`, `remark`) VALUES
(4001, 30001, '张三', '13800000001', '广东省', '深圳市', '南山区', '科技园南路1号', '', 1, 0, '2026-06-21 10:00:00', '2026-06-21 10:00:00', 'seed'),
(4002, 30002, '李四', '13800000002', '广东省', '广州市', '天河区', '体育西路100号', '', 1, 0, '2026-06-21 10:00:00', '2026-06-21 10:00:00', 'seed'),
(4003, 30003, '王五', '13800000003', '北京市', '北京市', '朝阳区', '建国路88号', '', 1, 0, '2026-06-21 10:00:00', '2026-06-21 10:00:00', 'seed');

-- 账号1 预领 coupon_id=3001（account_coupon_id=6001）
INSERT INTO `account_coupon` (`account_coupon_id`, `coupon_id`, `account_id`, `coupon_name`, `threshold_amount`, `discount_amount`, `valid_start_time`, `valid_end_time`, `coupon_status`, `receive_time`, `use_time`, `order_id`, `del_flag`, `create_time`, `update_time`) VALUES
(6001, 3001, 30001, '满100减10', 100.00, 10.00, '2026-01-01 00:00:00', '2027-12-31 23:59:59', 0, '2026-06-21 10:00:00', NULL, 0, 0, '2026-06-21 10:00:00', '2026-06-21 10:00:00');
