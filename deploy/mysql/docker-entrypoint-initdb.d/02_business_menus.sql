-- 补全业务侧栏菜单（账号 / 商城 / 优惠券），并隐藏误导性外链
-- 与 frontend JUMP_PAGE_ACTIVE_MENU、views 路径对齐；admin 走全量树，common 一并授权

SET NAMES utf8mb4;

-- 隐藏错误外链「质衡 Demo」
UPDATE sys_menu
SET visible = '1',
    path = 'qualitestGuide',
    is_frame = 1,
    remark = '已隐藏：原 localhost:8801 外链易误导'
WHERE menu_id = 4;

-- 用户管理子菜单
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES
(2002, '用户账号', 2000, 1, 'account', 'account/account/index', '', '', 1, 0, 'C', '0', '0', 'account:account:list', 'user', 'admin', NOW(), ''),
(2003, '收货地址', 2000, 2, 'accountAddress', 'account/accountAddress/index', '', '', 1, 0, 'C', '1', '0', 'account:accountAddress:list', '#', 'admin', NOW(), '隐藏页，从账号跳转')
ON DUPLICATE KEY UPDATE
  menu_name = VALUES(menu_name), path = VALUES(path), component = VALUES(component),
  perms = VALUES(perms), visible = VALUES(visible), status = VALUES(status);

-- 商城管理子菜单
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES
(2004, '商品分类', 2001, 1, 'mallCategory', 'mall/mallCategory/index', '', '', 1, 0, 'C', '0', '0', 'mall:mallCategory:list', 'tree', 'admin', NOW(), ''),
(2005, '商品', 2001, 2, 'mallProduct', 'mall/mallProduct/index', '', '', 1, 0, 'C', '0', '0', 'mall:mallProduct:list', 'shopping', 'admin', NOW(), ''),
(2006, '商品SKU', 2001, 3, 'mallProductSku', 'mall/mallProductSku/index', '', '', 1, 0, 'C', '1', '0', 'mall:mallProductSku:list', '#', 'admin', NOW(), '隐藏页'),
(2007, '购物车', 2001, 4, 'mallCart', 'mall/mallCart/index', '', '', 1, 0, 'C', '0', '0', 'mall:mallCart:list', 'shopping-cart', 'admin', NOW(), ''),
(2008, '订单', 2001, 5, 'mallOrder', 'mall/mallOrder/index', '', '', 1, 0, 'C', '0', '0', 'mall:mallOrder:list', 'list', 'admin', NOW(), ''),
(2009, '订单明细', 2001, 6, 'mallOrderItem', 'mall/mallOrderItem/index', '', '', 1, 0, 'C', '1', '0', 'mall:mallOrderItem:list', '#', 'admin', NOW(), '隐藏页'),
(2010, '订单退款', 2001, 7, 'mallOrderRefund', 'mall/mallOrderRefund/index', '', '', 1, 0, 'C', '0', '0', 'mall:mallOrderRefund:list', 'money', 'admin', NOW(), ''),
(2011, '退款明细', 2001, 8, 'mallOrderRefundItem', 'mall/mallOrderRefundItem/index', '', '', 1, 0, 'C', '1', '0', 'mall:mallOrderRefundItem:list', '#', 'admin', NOW(), '隐藏页')
ON DUPLICATE KEY UPDATE
  menu_name = VALUES(menu_name), path = VALUES(path), component = VALUES(component),
  perms = VALUES(perms), visible = VALUES(visible), status = VALUES(status);

-- 优惠券管理
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES
(2020, '优惠券管理', 0, 3, 'couponManages', NULL, NULL, '', 1, 0, 'M', '0', '0', '', 'coupon', 'admin', NOW(), ''),
(2021, '优惠券', 2020, 1, 'coupon', 'coupon/coupon/index', '', '', 1, 0, 'C', '0', '0', 'coupon:coupon:list', 'coupon', 'admin', NOW(), ''),
(2022, '用户优惠券', 2020, 2, 'accountCoupon', 'coupon/accountCoupon/index', '', '', 1, 0, 'C', '0', '0', 'coupon:accountCoupon:list', 'peoples', 'admin', NOW(), '')
ON DUPLICATE KEY UPDATE
  menu_name = VALUES(menu_name), path = VALUES(path), component = VALUES(component),
  perms = VALUES(perms), visible = VALUES(visible), status = VALUES(status);

-- 按钮权限（list 页操作）；幂等
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES
(2301, '账号查询', 2002, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'account:account:query', '#', 'admin', NOW(), ''),
(2302, '账号新增', 2002, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'account:account:add', '#', 'admin', NOW(), ''),
(2303, '账号修改', 2002, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'account:account:edit', '#', 'admin', NOW(), ''),
(2304, '账号删除', 2002, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'account:account:remove', '#', 'admin', NOW(), ''),
(2305, '账号导出', 2002, 5, '', '', '', '', 1, 0, 'F', '0', '0', 'account:account:export', '#', 'admin', NOW(), ''),
(2311, '地址查询', 2003, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'account:accountAddress:query', '#', 'admin', NOW(), ''),
(2312, '地址新增', 2003, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'account:accountAddress:add', '#', 'admin', NOW(), ''),
(2313, '地址修改', 2003, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'account:accountAddress:edit', '#', 'admin', NOW(), ''),
(2314, '地址删除', 2003, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'account:accountAddress:remove', '#', 'admin', NOW(), ''),
(2315, '地址导出', 2003, 5, '', '', '', '', 1, 0, 'F', '0', '0', 'account:accountAddress:export', '#', 'admin', NOW(), ''),
(2321, '分类查询', 2004, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'mall:mallCategory:query', '#', 'admin', NOW(), ''),
(2322, '分类新增', 2004, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'mall:mallCategory:add', '#', 'admin', NOW(), ''),
(2323, '分类修改', 2004, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'mall:mallCategory:edit', '#', 'admin', NOW(), ''),
(2324, '分类删除', 2004, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'mall:mallCategory:remove', '#', 'admin', NOW(), ''),
(2325, '分类导出', 2004, 5, '', '', '', '', 1, 0, 'F', '0', '0', 'mall:mallCategory:export', '#', 'admin', NOW(), ''),
(2331, '商品查询', 2005, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'mall:mallProduct:query', '#', 'admin', NOW(), ''),
(2332, '商品新增', 2005, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'mall:mallProduct:add', '#', 'admin', NOW(), ''),
(2333, '商品修改', 2005, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'mall:mallProduct:edit', '#', 'admin', NOW(), ''),
(2334, '商品删除', 2005, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'mall:mallProduct:remove', '#', 'admin', NOW(), ''),
(2335, '商品导出', 2005, 5, '', '', '', '', 1, 0, 'F', '0', '0', 'mall:mallProduct:export', '#', 'admin', NOW(), ''),
(2341, 'SKU查询', 2006, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'mall:mallProductSku:query', '#', 'admin', NOW(), ''),
(2342, 'SKU新增', 2006, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'mall:mallProductSku:add', '#', 'admin', NOW(), ''),
(2343, 'SKU修改', 2006, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'mall:mallProductSku:edit', '#', 'admin', NOW(), ''),
(2344, 'SKU删除', 2006, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'mall:mallProductSku:remove', '#', 'admin', NOW(), ''),
(2345, 'SKU导出', 2006, 5, '', '', '', '', 1, 0, 'F', '0', '0', 'mall:mallProductSku:export', '#', 'admin', NOW(), ''),
(2351, '购物车查询', 2007, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'mall:mallCart:query', '#', 'admin', NOW(), ''),
(2352, '购物车新增', 2007, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'mall:mallCart:add', '#', 'admin', NOW(), ''),
(2353, '购物车修改', 2007, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'mall:mallCart:edit', '#', 'admin', NOW(), ''),
(2354, '购物车删除', 2007, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'mall:mallCart:remove', '#', 'admin', NOW(), ''),
(2355, '购物车导出', 2007, 5, '', '', '', '', 1, 0, 'F', '0', '0', 'mall:mallCart:export', '#', 'admin', NOW(), ''),
(2361, '订单查询', 2008, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'mall:mallOrder:query', '#', 'admin', NOW(), ''),
(2362, '订单新增', 2008, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'mall:mallOrder:add', '#', 'admin', NOW(), ''),
(2363, '订单修改', 2008, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'mall:mallOrder:edit', '#', 'admin', NOW(), ''),
(2364, '订单删除', 2008, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'mall:mallOrder:remove', '#', 'admin', NOW(), ''),
(2365, '订单导出', 2008, 5, '', '', '', '', 1, 0, 'F', '0', '0', 'mall:mallOrder:export', '#', 'admin', NOW(), ''),
(2371, '订单明细查询', 2009, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'mall:mallOrderItem:query', '#', 'admin', NOW(), ''),
(2372, '订单明细导出', 2009, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'mall:mallOrderItem:export', '#', 'admin', NOW(), ''),
(2381, '退款查询', 2010, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'mall:mallOrderRefund:query', '#', 'admin', NOW(), ''),
(2382, '退款新增', 2010, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'mall:mallOrderRefund:add', '#', 'admin', NOW(), ''),
(2383, '退款修改', 2010, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'mall:mallOrderRefund:edit', '#', 'admin', NOW(), ''),
(2384, '退款删除', 2010, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'mall:mallOrderRefund:remove', '#', 'admin', NOW(), ''),
(2385, '退款导出', 2010, 5, '', '', '', '', 1, 0, 'F', '0', '0', 'mall:mallOrderRefund:export', '#', 'admin', NOW(), ''),
(2391, '退款明细查询', 2011, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'mall:mallOrderRefundItem:query', '#', 'admin', NOW(), ''),
(2392, '退款明细导出', 2011, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'mall:mallOrderRefundItem:export', '#', 'admin', NOW(), ''),
(2401, '优惠券查询', 2021, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'coupon:coupon:query', '#', 'admin', NOW(), ''),
(2402, '优惠券新增', 2021, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'coupon:coupon:add', '#', 'admin', NOW(), ''),
(2403, '优惠券修改', 2021, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'coupon:coupon:edit', '#', 'admin', NOW(), ''),
(2404, '优惠券删除', 2021, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'coupon:coupon:remove', '#', 'admin', NOW(), ''),
(2405, '优惠券导出', 2021, 5, '', '', '', '', 1, 0, 'F', '0', '0', 'coupon:coupon:export', '#', 'admin', NOW(), ''),
(2411, '用户券查询', 2022, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'coupon:accountCoupon:query', '#', 'admin', NOW(), ''),
(2412, '用户券新增', 2022, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'coupon:accountCoupon:add', '#', 'admin', NOW(), ''),
(2413, '用户券修改', 2022, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'coupon:accountCoupon:edit', '#', 'admin', NOW(), ''),
(2414, '用户券删除', 2022, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'coupon:accountCoupon:remove', '#', 'admin', NOW(), ''),
(2415, '用户券导出', 2022, 5, '', '', '', '', 1, 0, 'F', '0', '0', 'coupon:accountCoupon:export', '#', 'admin', NOW(), '')
ON DUPLICATE KEY UPDATE perms = VALUES(perms), parent_id = VALUES(parent_id);

-- common 角色挂上业务菜单 + 场景（admin 不依赖 role_menu）
INSERT IGNORE INTO sys_role_menu (role_id, menu_id) VALUES
(2, 2000), (2, 2001), (2, 2002), (2, 2003),
(2, 2004), (2, 2005), (2, 2006), (2, 2007), (2, 2008), (2, 2009), (2, 2010), (2, 2011),
(2, 2020), (2, 2021), (2, 2022),
(2, 2215), (2, 2216), (2, 2217), (2, 2218), (2, 2219), (2, 2220),
(2, 2301), (2, 2302), (2, 2303), (2, 2304), (2, 2305),
(2, 2311), (2, 2312), (2, 2313), (2, 2314), (2, 2315),
(2, 2321), (2, 2322), (2, 2323), (2, 2324), (2, 2325),
(2, 2331), (2, 2332), (2, 2333), (2, 2334), (2, 2335),
(2, 2341), (2, 2342), (2, 2343), (2, 2344), (2, 2345),
(2, 2351), (2, 2352), (2, 2353), (2, 2354), (2, 2355),
(2, 2361), (2, 2362), (2, 2363), (2, 2364), (2, 2365),
(2, 2371), (2, 2372),
(2, 2381), (2, 2382), (2, 2383), (2, 2384), (2, 2385),
(2, 2391), (2, 2392),
(2, 2401), (2, 2402), (2, 2403), (2, 2404), (2, 2405),
(2, 2411), (2, 2412), (2, 2413), (2, 2414), (2, 2415);
