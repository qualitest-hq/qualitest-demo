-- ============================================================================
-- 【失败场景 F12】重复领取优惠券
-- 对应 §10 场景 12：同一 accountId+couponId 第二次 receive → 失败
-- 前置: reset_test_data.bat（基线已含 account 30001 的 6001/3001）
-- 预期: 账号1 再次 POST /api/coupon/coupon/3001/receive → code≠200
-- ============================================================================

SET NAMES utf8mb4;

SELECT '基线 account 30001 已持有 coupon 3001(account_coupon_id=6001)，直接调接口断言重复领取失败' AS hint;
