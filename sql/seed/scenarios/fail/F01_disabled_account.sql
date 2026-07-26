-- ============================================================================
-- 【失败场景 F01】停用账号不可登录
-- 对应 §10 场景 1：account.status=1 → login 拒绝
-- 前置: reset_test_data.bat
-- 预期: POST /api/account/auth/login mobile=13800000002 → code≠200
-- ============================================================================

SET NAMES utf8mb4;

UPDATE `account` SET `status` = 1, `update_time` = '2026-06-21 11:00:00', `remark` = 'F01停用' WHERE `account_id` = 30002;
