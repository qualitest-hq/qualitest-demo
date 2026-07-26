-- ============================================================================
-- 【成功场景 S08】满减券可领取 + 账号2 未领券
-- 对应 §10 场景 4 / 12：领券成功；重复领取失败（需接口触发第二次）
-- 前置: reset_test_data.bat
-- 预期: GET /api/coupon/coupon/available 含 3002；账号2 可 POST receive 3002
-- ============================================================================

SET NAMES utf8mb4;

-- 无额外 INSERT；基线 seed 已满足：
-- coupon 3002 receive_count=0 可领
-- account 30002 无 account_coupon 记录

SELECT '基线已满足领券成功场景；重复领券失败请对 account 30001 再次 receive coupon 3001' AS hint;
