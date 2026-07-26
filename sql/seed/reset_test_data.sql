-- ============================================================================
-- qualitest-demo 一键重置业务测试数据（入口脚本）
-- 对应文档 §8.2 reset_test_data.sql
--
-- 行为: 清空 13 张业务表 → 加载 01_base_seed + 02_account_seed
--       不加载 03_trade_seed，保证干净起点
--
-- 执行方式（Windows）:
--   双击 sql/seed/reset_test_data.bat
-- 或手动:
--   mysql -u root -p qualitest-demo < sql/seed/00_truncate_business.sql
--   mysql -u root -p qualitest-demo < sql/seed/01_base_seed.sql
--   mysql -u root -p qualitest-demo < sql/seed/02_account_seed.sql
--
-- 场景 SQL 请在 reset 后按需加载 scenarios/success 或 scenarios/fail 下脚本
-- ============================================================================

-- 本文件仅作说明；实际重置请使用 reset_test_data.bat 或按上方顺序执行 00/01/02

SELECT '请使用 reset_test_data.bat 或依次执行 00_truncate_business / 01_base_seed / 02_account_seed' AS hint;
