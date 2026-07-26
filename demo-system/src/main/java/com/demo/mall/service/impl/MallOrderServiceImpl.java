package com.demo.mall.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.demo.account.domain.AccountAddress;
import com.demo.account.mapper.AccountAddressMapper;
import com.demo.account.service.IAccountBalanceRecordService;
import com.demo.common.enums.AccountBalanceRecordType;
import com.demo.common.constant.HttpStatus;
import com.demo.common.exception.ServiceException;
import com.demo.common.utils.DateUtils;
import com.demo.common.utils.StringUtils;
import com.demo.coupon.domain.AccountCoupon;
import com.demo.coupon.mapper.AccountCouponMapper;
import com.demo.mall.apiParams.MallOrderApiParams;
import com.demo.mall.apiParams.MallOrderCancelApiParams;
import com.demo.mall.apiParams.MallOrderItemApiParams;
import com.demo.mall.apiParams.MallOrderPayApiParams;
import com.demo.mall.apiParams.MallOrderSubmitApiParams;
import com.demo.mall.apiParams.MallOrderSubmitItemApiParams;
import com.demo.mall.apiResult.MallOrderAddressSnapshotApiResult;
import com.demo.mall.apiResult.MallOrderApiResult;
import com.demo.mall.apiResult.MallOrderItemApiResult;
import com.demo.mall.apiResult.MallOrderPreviewApiResult;
import com.demo.mall.apiResult.MallOrderPreviewItemApiResult;
import com.demo.mall.domain.MallCart;
import com.demo.mall.domain.MallOrder;
import com.demo.mall.domain.MallOrderItem;
import com.demo.mall.domain.MallProduct;
import com.demo.mall.domain.MallProductSku;
import com.demo.mall.mapper.MallCartMapper;
import com.demo.mall.mapper.MallOrderItemMapper;
import com.demo.mall.mapper.MallOrderMapper;
import com.demo.mall.mapper.MallProductMapper;
import com.demo.mall.mapper.MallProductSkuMapper;
import com.demo.mall.params.MallOrderParams;
import com.demo.mall.result.MallOrderResult;
import com.demo.mall.service.IMallOrderItemService;
import com.demo.mall.service.IMallOrderService;
import com.demo.mall.service.MallOrderSupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 商城订单Service业务层处理
 *
 * @author demo
 * @date 2026-06-20
 */
@Service
public class MallOrderServiceImpl implements IMallOrderService {

    @Autowired
    private MallOrderMapper mallOrderMapper;

    @Autowired
    private MallOrderItemMapper mallOrderItemMapper;

    @Autowired
    private MallCartMapper mallCartMapper;

    @Autowired
    private MallProductSkuMapper mallProductSkuMapper;

    @Autowired
    private MallProductMapper mallProductMapper;

    @Autowired
    private AccountAddressMapper accountAddressMapper;

    @Autowired
    private AccountCouponMapper accountCouponMapper;

    @Autowired
    private IAccountBalanceRecordService accountBalanceRecordService;

    @Autowired
    private IMallOrderItemService mallOrderItemService;

    @Autowired
    private MallOrderSupportService mallOrderSupportService;

    /**
     * 查询商城订单列表
     */
    @Override
    public List<MallOrder> selectMallOrderList(MallOrder mallOrder) {
        return mallOrderMapper.selectMallOrderList(mallOrder);
    }

    /**
     * 按主键查询订单
     */
    @Override
    public MallOrder selectMallOrderById(Long orderId) {
        return mallOrderMapper.selectMallOrderById(orderId);
    }

    /**
     * 查询订单 Result 列表（管理端）
     */
    @Override
    public List<MallOrderResult> selectMallOrderResultList(MallOrderParams params) {
        return mallOrderMapper.selectMallOrderResultList(params);
    }

    /**
     * 获取订单 Result 详情（管理端）
     */
    @Override
    public MallOrderResult selectMallOrderResult(Long orderId) {
        return mallOrderMapper.selectMallOrderResult(orderId);
    }

    /**
     * 查询订单 ApiResult 列表（客户端）
     */
    @Override
    public List<MallOrderApiResult> selectMallOrderApiResultList(MallOrderApiParams params) {
        return mallOrderMapper.selectMallOrderApiResultList(params);
    }

    /**
     * 获取订单详情（客户端）；校验归属并附带明细
     */
    @Override
    public MallOrderApiResult selectMallOrderApiResult(Long orderId, Long accountId) {
        requireOwnedOrder(accountId, orderId);
        MallOrderApiResult result = mallOrderMapper.selectMallOrderApiResult(orderId);
        if (result == null) {
            throw new ServiceException("订单不存在");
        }
        MallOrderItemApiParams itemParams = new MallOrderItemApiParams();
        itemParams.setOrderId(orderId);
        List<MallOrderItemApiResult> items = mallOrderItemService.selectMallOrderItemApiResultList(itemParams);
        result.setItems(items);
        return result;
    }

    /**
     * 下单结算预览；计算金额与明细，不写库
     */
    @Override
    public MallOrderPreviewApiResult previewMallOrder(Long accountId, MallOrderSubmitApiParams params) {
        OrderBuildContext ctx = buildOrderContext(accountId, params, false);
        return toPreviewResult(ctx, null, null);
    }

    /**
     * 创建订单；扣减库存、占用优惠券、清除已选购物车项
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public MallOrderPreviewApiResult createMallOrder(Long accountId, MallOrderSubmitApiParams params) {
        OrderBuildContext ctx = buildOrderContext(accountId, params, true);
        Long orderId = IdUtil.getSnowflakeNextId();
        String orderNo = generateOrderNo();
        Date now = DateUtils.getNowDate();

        MallOrder order = new MallOrder();
        order.setOrderId(orderId);
        order.setOrderNo(orderNo);
        order.setAccountId(accountId);
        order.setOrderStatus(0);
        order.setPayStatus(0);
        order.setPayType(0);
        order.setRefundAmount(BigDecimal.ZERO);
        order.setRefundStatus(0);
        order.setRefundCount(0);
        order.setTotalAmount(ctx.totalAmount);
        order.setDiscountAmount(ctx.discountAmount);
        order.setCouponAmount(ctx.couponAmount);
        order.setFreightAmount(ctx.freightAmount);
        order.setPayAmount(ctx.payAmount);
        order.setBuyerRemark(params.getBuyerRemark());
        order.setDelFlag(0);
        order.setCreateTime(now);
        order.setUpdateTime(now);
        if (ctx.accountCoupon != null) {
            order.setAccountCouponId(ctx.accountCoupon.getAccountCouponId());
            order.setCouponId(ctx.accountCoupon.getCouponId());
            order.setCouponName(ctx.accountCoupon.getCouponName());
            order.setCouponThresholdAmount(ctx.accountCoupon.getThresholdAmount());
            order.setCouponDiscountAmount(ctx.accountCoupon.getDiscountAmount());
        } else {
            order.setAccountCouponId(0L);
            order.setCouponId(0L);
        }
        if (ctx.addressSnapshot != null) {
            order.setReceiverName(ctx.addressSnapshot.getReceiverName());
            order.setReceiverPhone(ctx.addressSnapshot.getReceiverPhone());
            order.setReceiverAddress(ctx.addressSnapshot.getReceiverAddress());
        }

        List<MallOrderItem> orderItems = new ArrayList<>();
        for (OrderLine line : ctx.lines) {
            MallOrderItem item = new MallOrderItem();
            item.setOrderItemId(IdUtil.getSnowflakeNextId());
            item.setOrderId(orderId);
            item.setProductId(line.productId);
            item.setSkuId(line.skuId);
            item.setProductName(line.productName);
            item.setSkuName(line.skuName);
            item.setSalePrice(line.salePrice);
            item.setQuantity(line.quantity);
            item.setTotalAmount(line.lineTotal);
            item.setRefundedQuantity(0);
            item.setRefundedAmount(BigDecimal.ZERO);
            item.setDelFlag(0);
            item.setCreateTime(now);
            item.setUpdateTime(now);
            orderItems.add(item);
        }

        mallOrderMapper.insertMallOrder(order);
        for (MallOrderItem item : orderItems) {
            mallOrderItemMapper.insertMallOrderItem(item);
        }
        mallOrderSupportService.deductStockForOrderItems(orderItems);
        if (ctx.accountCoupon != null) {
            mallOrderSupportService.holdCoupon(ctx.accountCoupon.getAccountCouponId(), orderId, accountId);
        }
        if (!ctx.cartIds.isEmpty()) {
            mallCartMapper.logicDeleteMallCartByIdList(ctx.cartIds);
        }
        return toPreviewResult(ctx, orderId, orderNo);
    }

    /**
     * 模拟支付；支持微信/支付宝/余额，支付后核销优惠券
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void payMallOrder(Long accountId, Long orderId, MallOrderPayApiParams params) {
        MallOrder order = requireOwnedOrder(accountId, orderId);
        if (order.getPayStatus() != null && order.getPayStatus() == 1) {
            throw new ServiceException("订单已支付");
        }
        if (order.getOrderStatus() == null || order.getOrderStatus() != 0) {
            throw new ServiceException("仅待付款订单可支付");
        }
        if (order.getPayStatus() == null || order.getPayStatus() != 0) {
            throw new ServiceException("订单支付状态异常");
        }
        if (params == null || params.getPayType() == null) {
            throw new ServiceException("支付方式不能为空");
        }
        int payType = params.getPayType();
        if (payType < 1 || payType > 3) {
            throw new ServiceException("支付方式无效");
        }
        BigDecimal payAmount = order.getPayAmount() != null ? order.getPayAmount() : BigDecimal.ZERO;
        if (payType == 3) {
            accountBalanceRecordService.changeBalance(
                    accountId,
                    payAmount.negate(),
                    AccountBalanceRecordType.ORDER_PAY,
                    orderId,
                    order.getOrderNo() != null ? order.getOrderNo() : String.valueOf(orderId),
                    "订单余额支付");
        }
        Date now = DateUtils.getNowDate();
        MallOrder update = new MallOrder();
        update.setOrderId(orderId);
        update.setPayStatus(1);
        update.setOrderStatus(1);
        update.setPayType(payType);
        update.setPayTime(now);
        update.setPaymentNo(generatePaymentNo());
        update.setTransactionNo(generateTransactionNo());
        update.setUpdateTime(now);
        mallOrderMapper.updateMallOrder(update);
        mallOrderSupportService.consumeCouponOnPay(order);
    }

    /**
     * 取消待付款订单；回滚库存并释放优惠券占用
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void cancelMallOrder(Long accountId, Long orderId, MallOrderCancelApiParams params) {
        MallOrder order = requireOwnedOrder(accountId, orderId);
        if (order.getOrderStatus() == null || order.getOrderStatus() != 0) {
            throw new ServiceException("仅待付款订单可取消");
        }
        if (order.getPayStatus() == null || order.getPayStatus() != 0) {
            throw new ServiceException("仅未支付订单可取消");
        }
        mallOrderSupportService.rollbackStockForOrder(orderId);
        mallOrderSupportService.releaseOccupiedCoupon(order);
        MallOrder update = new MallOrder();
        update.setOrderId(orderId);
        update.setOrderStatus(4);
        update.setPayStatus(2);
        update.setCancelReason(params != null && StringUtils.isNotEmpty(params.getCancelReason())
                ? params.getCancelReason() : "用户取消");
        update.setUpdateTime(DateUtils.getNowDate());
        mallOrderMapper.updateMallOrder(update);
    }

    /**
     * 确认收货；待收货订单变为已完成
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void confirmReceiveMallOrder(Long accountId, Long orderId) {
        MallOrder order = requireOwnedOrder(accountId, orderId);
        if (order.getOrderStatus() == null || order.getOrderStatus() != 2) {
            throw new ServiceException("仅待收货订单可确认收货");
        }
        Date now = DateUtils.getNowDate();
        MallOrder update = new MallOrder();
        update.setOrderId(orderId);
        update.setOrderStatus(3);
        update.setReceiveTime(now);
        update.setFinishTime(now);
        update.setUpdateTime(now);
        mallOrderMapper.updateMallOrder(update);
    }

    /**
     * 管理端发货；待发货订单变为待收货
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deliverMallOrder(Long orderId) {
        MallOrder order = mallOrderMapper.selectMallOrderById(orderId);
        if (order == null) {
            throw new ServiceException("订单不存在");
        }
        if (order.getOrderStatus() == null || order.getOrderStatus() != 1) {
            throw new ServiceException("仅待发货订单可发货");
        }
        MallOrder update = new MallOrder();
        update.setOrderId(orderId);
        update.setOrderStatus(2);
        update.setDeliveryTime(DateUtils.getNowDate());
        mallOrderMapper.updateMallOrder(update);
    }

    /**
     * 管理端关闭待付款订单；回滚库存并释放优惠券占用
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void closeMallOrder(Long orderId, String cancelReason) {
        MallOrder order = mallOrderMapper.selectMallOrderById(orderId);
        if (order == null) {
            throw new ServiceException("订单不存在");
        }
        if (order.getOrderStatus() == null || order.getOrderStatus() != 0) {
            throw new ServiceException("仅待付款订单可关闭");
        }
        if (order.getPayStatus() == null || order.getPayStatus() != 0) {
            throw new ServiceException("仅未支付订单可关闭");
        }
        mallOrderSupportService.rollbackStockForOrder(orderId);
        mallOrderSupportService.releaseOccupiedCoupon(order);
        MallOrder update = new MallOrder();
        update.setOrderId(orderId);
        update.setOrderStatus(4);
        update.setPayStatus(2);
        update.setCancelReason(cancelReason != null ? cancelReason : "管理员关闭");
        mallOrderMapper.updateMallOrder(update);
    }

    /**
     * 新增订单
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertMallOrder(MallOrder mallOrder) {
        if (Objects.isNull(mallOrder.getOrderId())) {
            mallOrder.setOrderId(IdUtil.getSnowflakeNextId());
        }
        mallOrder.setCreateTime(DateUtils.getNowDate());
        return mallOrderMapper.insertMallOrder(mallOrder);
    }

    /**
     * 修改订单
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateMallOrder(MallOrder mallOrder) {
        mallOrder.setUpdateTime(DateUtils.getNowDate());
        return mallOrderMapper.updateMallOrder(mallOrder);
    }

    @Override
    public int deleteMallOrderByIdList(List<Long> orderIdList) {
        return mallOrderMapper.deleteMallOrderByIdList(orderIdList);
    }

    @Override
    public int deleteMallOrderById(Long orderId) {
        return mallOrderMapper.deleteMallOrderById(orderId);
    }

    @Override
    public int logicDeleteMallOrderById(Long orderId) {
        return mallOrderMapper.logicDeleteMallOrderById(orderId);
    }

    @Override
    public int logicDeleteMallOrderByIdList(List<Long> orderIdList) {
        return mallOrderMapper.logicDeleteMallOrderByIdList(orderIdList);
    }

    @Override
    public int selectMallOrderCount(MallOrderParams params) {
        return mallOrderMapper.selectMallOrderCount(params);
    }

    @Override
    public MallOrder selectMallOrderOne(MallOrderParams params) {
        return mallOrderMapper.selectMallOrderOne(params);
    }

    /**
     * 构建下单上下文：解析购物车/直购明细、计算金额、校验优惠券与地址
     *
     * @param forCreate true 表示正式下单，地址必填
     */
    private OrderBuildContext buildOrderContext(Long accountId, MallOrderSubmitApiParams params, boolean forCreate) {
        if (params == null) {
            throw new ServiceException("参数不能为空");
        }
        boolean hasCart = params.getCartIds() != null && !params.getCartIds().isEmpty();
        boolean hasItems = params.getItems() != null && !params.getItems().isEmpty();
        if (hasCart && hasItems) {
            throw new ServiceException("cartIds与items不可同时传");
        }
        if (!hasCart && !hasItems) {
            throw new ServiceException("cartIds与items须二选一");
        }
        if (forCreate && params.getAddressId() == null) {
            throw new ServiceException("收货地址不能为空");
        }

        OrderBuildContext ctx = new OrderBuildContext();
        if (hasCart) {
            resolveFromCart(accountId, params.getCartIds(), ctx);
        } else {
            resolveFromItems(params.getItems(), ctx);
        }
        calculateAmounts(ctx);
        resolveCoupon(accountId, params.getAccountCouponId(), ctx);
        resolveAddress(accountId, params.getAddressId(), forCreate, ctx);
        return ctx;
    }

    private void resolveFromCart(Long accountId, List<Long> cartIds, OrderBuildContext ctx) {
        for (Long cartId : cartIds) {
            if (cartId == null) {
                throw new ServiceException("购物车ID不能为空");
            }
            MallCart cart = mallCartMapper.selectMallCartById(cartId);
            if (cart == null || (cart.getDelFlag() != null && cart.getDelFlag() == 1)) {
                throw new ServiceException("购物车项不存在");
            }
            if (!accountId.equals(cart.getAccountId())) {
                throw new ServiceException("无权操作该购物车项", HttpStatus.FORBIDDEN);
            }
            ctx.cartIds.add(cartId);
            addLineFromSku(cart.getSkuId(), cart.getQuantity(), ctx);
        }
    }

    private void resolveFromItems(List<MallOrderSubmitItemApiParams> items, OrderBuildContext ctx) {
        for (MallOrderSubmitItemApiParams item : items) {
            if (item == null || item.getSkuId() == null) {
                throw new ServiceException("SKU ID不能为空");
            }
            int quantity = item.getQuantity() == null ? 1 : item.getQuantity();
            if (quantity <= 0) {
                throw new ServiceException("数量须大于0");
            }
            addLineFromSku(item.getSkuId(), quantity, ctx);
        }
    }

    private void addLineFromSku(Long skuId, int quantity, OrderBuildContext ctx) {
        MallProductSku sku = mallProductSkuMapper.selectMallProductSkuById(skuId);
        if (sku == null || (sku.getDelFlag() != null && sku.getDelFlag() == 1)) {
            throw new ServiceException("SKU不存在");
        }
        if (sku.getStatus() != null && sku.getStatus() != 0) {
            throw new ServiceException("SKU已停用");
        }
        if (sku.getStock() == null || sku.getStock() < quantity) {
            throw new ServiceException("库存不足");
        }
        MallProduct product = mallProductMapper.selectMallProductById(sku.getProductId());
        if (product == null || (product.getDelFlag() != null && product.getDelFlag() == 1)) {
            throw new ServiceException("商品不存在");
        }
        if (product.getShelfStatus() == null || product.getShelfStatus() != 1) {
            throw new ServiceException("商品已下架");
        }
        BigDecimal salePrice = sku.getSalePrice() != null ? sku.getSalePrice() : BigDecimal.ZERO;
        BigDecimal lineTotal = salePrice.multiply(BigDecimal.valueOf(quantity)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal freight = product.getFreightAmount() != null ? product.getFreightAmount() : BigDecimal.ZERO;
        OrderLine line = new OrderLine();
        line.skuId = sku.getSkuId();
        line.productId = product.getProductId();
        line.productName = product.getProductName();
        line.skuName = sku.getSkuName();
        line.salePrice = salePrice;
        line.quantity = quantity;
        line.lineTotal = lineTotal;
        line.freightAmount = freight;
        ctx.lines.add(line);
    }

    private void calculateAmounts(OrderBuildContext ctx) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        Map<Long, BigDecimal> freightByProduct = new LinkedHashMap<>();
        for (OrderLine line : ctx.lines) {
            totalAmount = totalAmount.add(line.lineTotal);
            freightByProduct.putIfAbsent(line.productId, line.freightAmount);
        }
        BigDecimal freightAmount = BigDecimal.ZERO;
        for (BigDecimal freight : freightByProduct.values()) {
            freightAmount = freightAmount.add(freight);
        }
        ctx.totalAmount = totalAmount.setScale(2, RoundingMode.HALF_UP);
        ctx.freightAmount = freightAmount.setScale(2, RoundingMode.HALF_UP);
        ctx.couponAmount = BigDecimal.ZERO;
        ctx.discountAmount = BigDecimal.ZERO;
        ctx.payAmount = ctx.totalAmount.subtract(ctx.discountAmount).add(ctx.freightAmount).setScale(2, RoundingMode.HALF_UP);
    }

    private void resolveCoupon(Long accountId, Long accountCouponId, OrderBuildContext ctx) {
        if (accountCouponId == null || accountCouponId <= 0) {
            return;
        }
        AccountCoupon accountCoupon = accountCouponMapper.selectAccountCouponById(accountCouponId);
        if (accountCoupon == null || (accountCoupon.getDelFlag() != null && accountCoupon.getDelFlag() == 1)) {
            throw new ServiceException("优惠券不存在");
        }
        if (!accountId.equals(accountCoupon.getAccountId())) {
            throw new ServiceException("无权使用该优惠券", HttpStatus.FORBIDDEN);
        }
        if (accountCoupon.getCouponStatus() != null && accountCoupon.getCouponStatus() != 0) {
            throw new ServiceException("优惠券不可用");
        }
        Long occupiedOrderId = accountCoupon.getOrderId();
        if (occupiedOrderId != null && occupiedOrderId > 0) {
            throw new ServiceException("优惠券已被占用");
        }
        Date now = DateUtils.getNowDate();
        if (accountCoupon.getValidStartTime() != null && now.before(accountCoupon.getValidStartTime())) {
            throw new ServiceException("优惠券未生效");
        }
        if (accountCoupon.getValidEndTime() != null && now.after(accountCoupon.getValidEndTime())) {
            throw new ServiceException("优惠券已过期");
        }
        BigDecimal threshold = accountCoupon.getThresholdAmount() != null
                ? accountCoupon.getThresholdAmount() : BigDecimal.ZERO;
        if (ctx.totalAmount.compareTo(threshold) < 0) {
            throw new ServiceException("未达到优惠券使用门槛");
        }
        BigDecimal discount = accountCoupon.getDiscountAmount() != null
                ? accountCoupon.getDiscountAmount() : BigDecimal.ZERO;
        BigDecimal couponAmount = discount.min(ctx.totalAmount).setScale(2, RoundingMode.HALF_UP);
        ctx.accountCoupon = accountCoupon;
        ctx.couponAmount = couponAmount;
        ctx.discountAmount = couponAmount;
        ctx.payAmount = ctx.totalAmount.subtract(ctx.discountAmount).add(ctx.freightAmount).setScale(2, RoundingMode.HALF_UP);
    }

    private void resolveAddress(Long accountId, Long addressId, boolean forCreate, OrderBuildContext ctx) {
        if (addressId == null) {
            return;
        }
        AccountAddress address = accountAddressMapper.selectAccountAddressById(addressId);
        if (address == null || (address.getDelFlag() != null && address.getDelFlag() == 1)) {
            throw new ServiceException("地址不存在");
        }
        if (!accountId.equals(address.getAccountId())) {
            throw new ServiceException("无权使用该地址", HttpStatus.FORBIDDEN);
        }
        MallOrderAddressSnapshotApiResult snapshot = new MallOrderAddressSnapshotApiResult();
        snapshot.setReceiverName(address.getReceiverName());
        snapshot.setReceiverPhone(address.getReceiverPhone());
        snapshot.setReceiverAddress(buildFullAddress(address));
        ctx.addressSnapshot = snapshot;
    }

    private String buildFullAddress(AccountAddress address) {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotEmpty(address.getProvince())) {
            sb.append(address.getProvince());
        }
        if (StringUtils.isNotEmpty(address.getCity())) {
            sb.append(address.getCity());
        }
        if (StringUtils.isNotEmpty(address.getDistrict())) {
            sb.append(address.getDistrict());
        }
        if (StringUtils.isNotEmpty(address.getDetailAddress())) {
            sb.append(address.getDetailAddress());
        }
        return sb.toString();
    }

    private MallOrderPreviewApiResult toPreviewResult(OrderBuildContext ctx, Long orderId, String orderNo) {
        List<MallOrderPreviewItemApiResult> items = new ArrayList<>();
        for (OrderLine line : ctx.lines) {
            items.add(MallOrderPreviewItemApiResult.builder()
                    .skuId(line.skuId)
                    .productName(line.productName)
                    .skuName(line.skuName)
                    .salePrice(line.salePrice)
                    .quantity(line.quantity)
                    .totalAmount(line.lineTotal)
                    .build());
        }
        return MallOrderPreviewApiResult.builder()
                .orderId(orderId)
                .orderNo(orderNo)
                .totalAmount(ctx.totalAmount)
                .couponAmount(ctx.couponAmount)
                .discountAmount(ctx.discountAmount)
                .freightAmount(ctx.freightAmount)
                .payAmount(ctx.payAmount)
                .items(items)
                .address(ctx.addressSnapshot)
                .build();
    }

    /**
     * 校验订单存在且归属当前账号
     */
    private MallOrder requireOwnedOrder(Long accountId, Long orderId) {
        if (orderId == null) {
            throw new ServiceException("订单ID不能为空");
        }
        MallOrder order = mallOrderMapper.selectMallOrderById(orderId);
        if (order == null || (order.getDelFlag() != null && order.getDelFlag() == 1)) {
            throw new ServiceException("订单不存在");
        }
        if (!accountId.equals(order.getAccountId())) {
            throw new ServiceException("无权访问该订单", HttpStatus.FORBIDDEN);
        }
        return order;
    }

    private String generateOrderNo() {
        return "MO" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + RandomUtil.randomNumbers(4);
    }

    private String generatePaymentNo() {
        return "PAY" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + RandomUtil.randomNumbers(4);
    }

    private String generateTransactionNo() {
        return "TX" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + RandomUtil.randomNumbers(4);
    }

    private static class OrderBuildContext {
        private final List<OrderLine> lines = new ArrayList<>();
        private final List<Long> cartIds = new ArrayList<>();
        private AccountCoupon accountCoupon;
        private MallOrderAddressSnapshotApiResult addressSnapshot;
        private BigDecimal totalAmount = BigDecimal.ZERO;
        private BigDecimal couponAmount = BigDecimal.ZERO;
        private BigDecimal discountAmount = BigDecimal.ZERO;
        private BigDecimal freightAmount = BigDecimal.ZERO;
        private BigDecimal payAmount = BigDecimal.ZERO;
    }

    private static class OrderLine {
        private Long skuId;
        private Long productId;
        private String productName;
        private String skuName;
        private BigDecimal salePrice;
        private Integer quantity;
        private BigDecimal lineTotal;
        private BigDecimal freightAmount;
    }
}
