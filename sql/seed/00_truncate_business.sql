-- ============================================================================
-- qualitest-demo 业务表清空（保留 sys_* / gen_* / qrtz_* 等系统表）
-- 用法: mysql -u root -p qualitest-demo < sql/seed/00_truncate_business.sql
-- ============================================================================

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE `mall_order_refund_item`;
TRUNCATE TABLE `mall_order_refund`;
TRUNCATE TABLE `mall_order_item`;
TRUNCATE TABLE `mall_order`;
TRUNCATE TABLE `mall_cart`;
TRUNCATE TABLE `account_coupon`;
TRUNCATE TABLE `account_balance_record`;
TRUNCATE TABLE `account_address`;
TRUNCATE TABLE `account`;
TRUNCATE TABLE `mall_product_sku`;
TRUNCATE TABLE `mall_product`;
TRUNCATE TABLE `mall_category`;
TRUNCATE TABLE `coupon`;

SET FOREIGN_KEY_CHECKS = 1;
