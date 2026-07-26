package com.demo.mall.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.demo.account.service.IAccountBalanceRecordService;
import com.demo.common.enums.AccountBalanceRecordType;
import com.demo.common.constant.HttpStatus;
import com.demo.common.exception.ServiceException;
import com.demo.common.utils.DateUtils;
import com.demo.coupon.domain.AccountCoupon;
import com.demo.coupon.domain.Coupon;
import com.demo.coupon.mapper.AccountCouponMapper;
import com.demo.coupon.mapper.CouponMapper;
import com.demo.mall.apiParams.MallOrderRefundApplyApiParams;
import com.demo.mall.apiParams.MallOrderRefundApplyItemApiParams;
import com.demo.mall.apiParams.MallOrderRefundApiParams;
import com.demo.mall.apiParams.MallOrderRefundItemApiParams;
import com.demo.mall.apiResult.MallOrderRefundApiResult;
import com.demo.mall.apiResult.MallOrderRefundItemApiResult;
import com.demo.mall.apiResult.MallOrderRefundPreviewApiResult;
import com.demo.mall.apiResult.MallOrderRefundPreviewItemApiResult;
import com.demo.mall.domain.MallOrder;
import com.demo.mall.domain.MallOrderItem;
import com.demo.mall.domain.MallOrderRefund;
import com.demo.mall.domain.MallOrderRefundItem;
import com.demo.mall.domain.MallProduct;
import com.demo.mall.domain.MallProductSku;
import com.demo.mall.mapper.MallOrderItemMapper;
import com.demo.mall.mapper.MallOrderMapper;
import com.demo.mall.mapper.MallOrderRefundItemMapper;
import com.demo.mall.mapper.MallOrderRefundMapper;
import com.demo.mall.mapper.MallProductMapper;
import com.demo.mall.mapper.MallProductSkuMapper;
import com.demo.mall.params.MallOrderRefundItemParams;
import com.demo.mall.params.MallOrderRefundParams;
import com.demo.mall.result.MallOrderRefundItemResult;
import com.demo.mall.result.MallOrderRefundResult;
import com.demo.mall.service.IMallOrderRefundItemService;
import com.demo.mall.service.IMallOrderRefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 商城退款单Service业务层处理
 * 
 * @author demo
 * @date 2026-06-20
 */
@Service
public class MallOrderRefundServiceImpl implements IMallOrderRefundService {

    private static final int REFUND_STATUS_PENDING = 0;
    private static final int REFUND_STATUS_APPROVED = 1;
    private static final int REFUND_STATUS_REJECTED = 2;
    private static final int REFUND_STATUS_COMPLETED = 4;

    @Autowired
    private MallOrderRefundMapper mallOrderRefundMapper;

    @Autowired
    private MallOrderRefundItemMapper mallOrderRefundItemMapper;

    @Autowired
    private MallOrderMapper mallOrderMapper;

    @Autowired
    private MallOrderItemMapper mallOrderItemMapper;

    @Autowired
    private MallProductSkuMapper mallProductSkuMapper;

    @Autowired
    private MallProductMapper mallProductMapper;

    @Autowired
    private IAccountBalanceRecordService accountBalanceRecordService;

    @Autowired
    private AccountCouponMapper accountCouponMapper;

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private IMallOrderRefundItemService mallOrderRefundItemService;

    /**
     * 查询商城退款单列表
     *
     * @param mallOrderRefund 商城退款单
     * @return 商城退款单
     */
    @Override
    public List<MallOrderRefund> selectMallOrderRefundList(MallOrderRefund mallOrderRefund) {
        return mallOrderRefundMapper.selectMallOrderRefundList(mallOrderRefund);
    }

    /**
     * 查询商城退款单
     *
     * @param refundId 商城退款单主键
     * @return 商城退款单
     */
    @Override
    public MallOrderRefund selectMallOrderRefundById(Long refundId) {
        return mallOrderRefundMapper.selectMallOrderRefundById(refundId);
    }

    /**
     * 查询商城退款单Result列表
     *
     * @param params 商城退款单Params
     * @return 商城退款单Result集合
     */
    @Override
    public List<MallOrderRefundResult> selectMallOrderRefundResultList(MallOrderRefundParams params) {
        return mallOrderRefundMapper.selectMallOrderRefundResultList(params);
    }

    /**
     * 获取商城退款单详细信息
     *
     * @param refundId 商城退款单主键
     * @return 商城退款单Result
     */
    @Override
    public MallOrderRefundResult selectMallOrderRefundResult(Long refundId) {
        return mallOrderRefundMapper.selectMallOrderRefundResult(refundId);
    }

    /**
     * 查询商城退款单ApiResult列表（客户端）
     */
    @Override
    public List<MallOrderRefundApiResult> selectMallOrderRefundApiResultList(MallOrderRefundApiParams params) {
        return mallOrderRefundMapper.selectMallOrderRefundApiResultList(params);
    }

    /**
     * 获取商城退款单ApiResult详细信息（客户端）
     */
    @Override
    public MallOrderRefundApiResult selectMallOrderRefundApiResult(Long refundId) {
        return mallOrderRefundMapper.selectMallOrderRefundApiResult(refundId);
    }

    /**
     * 新增商城退款单
     *
     * @param mallOrderRefund 商城退款单
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertMallOrderRefund(MallOrderRefund mallOrderRefund) {
        if (Objects.isNull(mallOrderRefund.getRefundId())) {
            mallOrderRefund.setRefundId(IdUtil.getSnowflakeNextId());
        }
        mallOrderRefund.setCreateTime(DateUtils.getNowDate());
        return mallOrderRefundMapper.insertMallOrderRefund(mallOrderRefund);
    }

    /**
     * 修改商城退款单
     *
     * @param mallOrderRefund 商城退款单
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateMallOrderRefund(MallOrderRefund mallOrderRefund) {
        mallOrderRefund.setUpdateTime(DateUtils.getNowDate());
        return mallOrderRefundMapper.updateMallOrderRefund(mallOrderRefund);
    }

    /**
     * 批量删除商城退款单
     * 
     * @param refundIdList 需要删除的商城退款单主键集合
     * @return 结果
     */
    @Override
    public int deleteMallOrderRefundByIdList(List<Long> refundIdList) {
        return mallOrderRefundMapper.deleteMallOrderRefundByIdList(refundIdList);
    }

    /**
     * 删除商城退款单信息
     * 
     * @param refundId 商城退款单主键
     * @return 结果
     */
    @Override
    public int deleteMallOrderRefundById(Long refundId) {
        return mallOrderRefundMapper.deleteMallOrderRefundById(refundId);
    }

    /**
     * 逻辑删除商城退款单信息
     * 
     * @param refundId 商城退款单主键
     * @return 结果
     */
    @Override
    public int logicDeleteMallOrderRefundById(Long refundId) {
        return mallOrderRefundMapper.logicDeleteMallOrderRefundById(refundId);
    }

    /**
     * 批量逻辑删除商城退款单信息
     * 
     * @param refundIdList 商城退款单主键集合
     * @return 结果
     */
    @Override
    public int logicDeleteMallOrderRefundByIdList(List<Long> refundIdList) {
        return mallOrderRefundMapper.logicDeleteMallOrderRefundByIdList(refundIdList);
    }

    /**
     * 查询商城退款单数量
     *
     * @param params 商城退款单Params
     * @return 数量
     */
    @Override
    public int selectMallOrderRefundCount(MallOrderRefundParams params) {
        return mallOrderRefundMapper.selectMallOrderRefundCount(params);
    }

    /**
     * 按条件查询单条商城退款单
     *
     * @param params 商城退款单Params
     * @return 商城退款单
     */
    @Override
    public MallOrderRefund selectMallOrderRefundOne(MallOrderRefundParams params) {
        return mallOrderRefundMapper.selectMallOrderRefundOne(params);
    }

    /**
     * 退款预览（客户端）；校验可退性并计算拟退金额，不落库
     */
    @Override
    public MallOrderRefundPreviewApiResult previewMallOrderRefund(Long accountId, MallOrderRefundApplyApiParams params) {
        if (params == null || params.getOrderId() == null) {
            throw new ServiceException("订单ID不能为空");
        }
        RefundBuildContext ctx = buildRefundContext(accountId, params, true);
        return toPreviewResult(ctx);
    }

    /**
     * 申请退款（客户端）；创建退款单并将订单置为退款中
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public MallOrderRefundApiResult applyMallOrderRefund(Long accountId, MallOrderRefundApplyApiParams params) {
        if (params == null || params.getOrderId() == null) {
            throw new ServiceException("订单ID不能为空");
        }
        if (params.getRefundType() == null) {
            throw new ServiceException("退款类型不能为空");
        }
        if (params.getItems() == null || params.getItems().isEmpty()) {
            throw new ServiceException("退款明细不能为空");
        }
        RefundBuildContext ctx = buildRefundContext(accountId, params, false);
        if (!ctx.canRefund) {
            throw new ServiceException(ctx.refuseReason);
        }
        Long refundId = IdUtil.getSnowflakeNextId();
        String refundNo = generateRefundNo();
        Date now = DateUtils.getNowDate();

        MallOrderRefund refund = new MallOrderRefund();
        refund.setRefundId(refundId);
        refund.setRefundNo(refundNo);
        refund.setOrderId(params.getOrderId());
        refund.setAccountId(accountId);
        refund.setRefundType(params.getRefundType());
        refund.setRefundAmount(ctx.refundAmount);
        refund.setRefundStatus(0);
        refund.setRefundReason(params.getRefundReason());
        refund.setApplyTime(now);
        refund.setDelFlag(0);
        refund.setCreateTime(now);
        refund.setUpdateTime(now);
        mallOrderRefundMapper.insertMallOrderRefund(refund);

        for (RefundLine line : ctx.lines) {
            if (line.refundQuantity == null || line.refundQuantity <= 0) {
                continue;
            }
            MallOrderRefundItem item = new MallOrderRefundItem();
            item.setRefundItemId(IdUtil.getSnowflakeNextId());
            item.setRefundId(refundId);
            item.setOrderId(params.getOrderId());
            item.setOrderItemId(line.orderItemId);
            item.setRefundQuantity(line.refundQuantity);
            item.setRefundAmount(line.refundAmount);
            item.setDelFlag(0);
            item.setCreateTime(now);
            item.setUpdateTime(now);
            mallOrderRefundItemMapper.insertMallOrderRefundItem(item);
        }

        MallOrder orderUpdate = new MallOrder();
        orderUpdate.setOrderId(params.getOrderId());
        orderUpdate.setOrderStatus(5);
        orderUpdate.setRefundStatus(3);
        orderUpdate.setUpdateTime(now);
        mallOrderMapper.updateMallOrder(orderUpdate);

        return selectMallOrderRefundApiResult(refundId, accountId);
    }

    /**
     * 获取退款单详情（客户端）；校验归属并加载明细
     */
    @Override
    public MallOrderRefundApiResult selectMallOrderRefundApiResult(Long refundId, Long accountId) {
        requireOwnedRefund(accountId, refundId);
        MallOrderRefundApiResult result = mallOrderRefundMapper.selectMallOrderRefundApiResult(refundId);
        MallOrderRefundItemApiParams itemParams = new MallOrderRefundItemApiParams();
        itemParams.setRefundId(refundId);
        result.setItems(mallOrderRefundItemService.selectMallOrderRefundItemApiResultList(itemParams));
        return result;
    }

    /**
     * 撤销待审核退款申请（客户端）
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void cancelMallOrderRefund(Long accountId, Long refundId) {
        MallOrderRefund refund = requireOwnedRefund(accountId, refundId);
        if (refund.getRefundStatus() == null || refund.getRefundStatus() != 0) {
            throw new ServiceException("仅待审核退款单可撤销");
        }
        MallOrderRefund update = new MallOrderRefund();
        update.setRefundId(refundId);
        update.setRefundStatus(5);
        update.setUpdateTime(DateUtils.getNowDate());
        mallOrderRefundMapper.updateMallOrderRefund(update);
        restoreOrderAfterRefundClosed(refund.getOrderId(), refundId);
    }

    /**
     * 管理端审核通过退款申请
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void approveMallOrderRefund(Long refundId) {
        MallOrderRefund refund = mallOrderRefundMapper.selectMallOrderRefundById(refundId);
        if (refund == null) {
            throw new ServiceException("退款单不存在");
        }
        if (refund.getRefundStatus() == null || refund.getRefundStatus() != REFUND_STATUS_PENDING) {
            throw new ServiceException("仅待审核退款单可通过");
        }
        MallOrderRefund update = new MallOrderRefund();
        update.setRefundId(refundId);
        update.setRefundStatus(REFUND_STATUS_APPROVED);
        update.setHandleTime(DateUtils.getNowDate());
        mallOrderRefundMapper.updateMallOrderRefund(update);
    }

    /**
     * 管理端拒绝退款申请；恢复订单可退状态
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void rejectMallOrderRefund(Long refundId, String handleRemark) {
        MallOrderRefund refund = mallOrderRefundMapper.selectMallOrderRefundById(refundId);
        if (refund == null) {
            throw new ServiceException("退款单不存在");
        }
        if (refund.getRefundStatus() == null || refund.getRefundStatus() != REFUND_STATUS_PENDING) {
            throw new ServiceException("仅待审核退款单可拒绝");
        }
        MallOrderRefund update = new MallOrderRefund();
        update.setRefundId(refundId);
        update.setRefundStatus(REFUND_STATUS_REJECTED);
        update.setHandleRemark(handleRemark);
        update.setHandleTime(DateUtils.getNowDate());
        mallOrderRefundMapper.updateMallOrderRefund(update);
        restoreOrderAfterRefundClosed(refund.getOrderId(), refundId);
    }

    /**
     * 确认退款完成；回写明细已退量、余额退款、库存回滚（退货退款）并更新订单退款状态
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void completeMallOrderRefund(Long refundId) {
        MallOrderRefund refund = mallOrderRefundMapper.selectMallOrderRefundById(refundId);
        if (refund == null) {
            throw new ServiceException("退款单不存在");
        }
        if (refund.getRefundStatus() == null || refund.getRefundStatus() != REFUND_STATUS_APPROVED) {
            throw new ServiceException("仅已通过退款单可完成退款");
        }
        MallOrder order = mallOrderMapper.selectMallOrderById(refund.getOrderId());
        if (order == null) {
            throw new ServiceException("关联订单不存在");
        }
        BigDecimal thisRefundAmount = refund.getRefundAmount() != null ? refund.getRefundAmount() : BigDecimal.ZERO;

        MallOrderRefundItemParams itemParams = MallOrderRefundItemParams.builder().refundId(refundId).build();
        List<MallOrderRefundItemResult> refundItems = mallOrderRefundItemMapper.selectMallOrderRefundItemResultList(itemParams);

        for (MallOrderRefundItemResult refundItem : refundItems) {
            MallOrderItem orderItem = mallOrderItemMapper.selectMallOrderItemById(refundItem.getOrderItemId());
            if (orderItem == null) {
                continue;
            }
            int refundQty = refundItem.getRefundQuantity() != null ? refundItem.getRefundQuantity() : 0;
            BigDecimal refundAmt = refundItem.getRefundAmount() != null ? refundItem.getRefundAmount() : BigDecimal.ZERO;
            MallOrderItem itemUpdate = new MallOrderItem();
            itemUpdate.setOrderItemId(orderItem.getOrderItemId());
            itemUpdate.setRefundedQuantity((orderItem.getRefundedQuantity() != null ? orderItem.getRefundedQuantity() : 0) + refundQty);
            itemUpdate.setRefundedAmount((orderItem.getRefundedAmount() != null ? orderItem.getRefundedAmount() : BigDecimal.ZERO).add(refundAmt));
            mallOrderItemMapper.updateMallOrderItem(itemUpdate);
        }

        if (refund.getRefundType() != null && refund.getRefundType() == 2) {
            rollbackStockForRefund(refundItems);
        }

        BigDecimal totalRefunded = (order.getRefundAmount() != null ? order.getRefundAmount() : BigDecimal.ZERO).add(thisRefundAmount);
        int refundCount = (order.getRefundCount() != null ? order.getRefundCount() : 0) + 1;
        BigDecimal payAmount = order.getPayAmount() != null ? order.getPayAmount() : BigDecimal.ZERO;
        boolean fullRefund = totalRefunded.compareTo(payAmount) >= 0;

        Date now = DateUtils.getNowDate();
        String transactionNo = "RF" + IdUtil.getSnowflakeNextIdStr();

        MallOrderRefund refundUpdate = new MallOrderRefund();
        refundUpdate.setRefundId(refundId);
        refundUpdate.setRefundStatus(REFUND_STATUS_COMPLETED);
        refundUpdate.setRefundTime(now);
        refundUpdate.setTransactionNo(transactionNo);
        mallOrderRefundMapper.updateMallOrderRefund(refundUpdate);

        if (order.getPayType() != null && order.getPayType() == 3) {
            accountBalanceRecordService.changeBalance(
                    order.getAccountId(),
                    thisRefundAmount,
                    AccountBalanceRecordType.ORDER_REFUND,
                    refundId,
                    transactionNo,
                    "订单退款入账");
        }

        MallOrder orderUpdate = new MallOrder();
        orderUpdate.setOrderId(order.getOrderId());
        orderUpdate.setRefundAmount(totalRefunded);
        orderUpdate.setRefundCount(refundCount);
        if (fullRefund) {
            orderUpdate.setOrderStatus(3);
            orderUpdate.setRefundStatus(2);
            rollbackCouponIfNeeded(order);
        } else {
            orderUpdate.setOrderStatus(1);
            orderUpdate.setRefundStatus(1);
        }
        mallOrderMapper.updateMallOrder(orderUpdate);
    }

    /**
     * 构建退款上下文：校验订单可退性、计算各明细可退/拟退数量与金额
     *
     * @param previewMode true 时允许不传退款明细，仅展示可退余量
     */
    private RefundBuildContext buildRefundContext(Long accountId, MallOrderRefundApplyApiParams params, boolean previewMode) {
        RefundBuildContext ctx = new RefundBuildContext();
        ctx.orderId = params.getOrderId();
        MallOrder order = requireOwnedOrder(accountId, params.getOrderId());
        ctx.payAmount = order.getPayAmount() != null ? order.getPayAmount() : BigDecimal.ZERO;
        ctx.refundedAmount = order.getRefundAmount() != null ? order.getRefundAmount() : BigDecimal.ZERO;

        String baseReason = validateOrderRefundable(order);
        if (baseReason != null) {
            ctx.canRefund = false;
            ctx.refuseReason = baseReason;
            loadPreviewLines(ctx, params, previewMode);
            return ctx;
        }
        if (hasInProgressRefund(order.getOrderId(), null)) {
            ctx.canRefund = false;
            ctx.refuseReason = "存在进行中的退款单";
            loadPreviewLines(ctx, params, previewMode);
            return ctx;
        }

        MallOrderItem query = new MallOrderItem();
        query.setOrderId(order.getOrderId());
        List<MallOrderItem> orderItems = mallOrderItemMapper.selectMallOrderItemList(query);
        Map<Long, Integer> requestQtyMap = buildRequestQtyMap(params.getItems());

        if (!previewMode || (params.getItems() != null && !params.getItems().isEmpty())) {
            if (params.getItems() == null || params.getItems().isEmpty()) {
                ctx.canRefund = false;
                ctx.refuseReason = "退款明细不能为空";
                return ctx;
            }
            for (MallOrderRefundApplyItemApiParams itemParam : params.getItems()) {
                if (itemParam == null || itemParam.getOrderItemId() == null) {
                    ctx.canRefund = false;
                    ctx.refuseReason = "订单明细ID不能为空";
                    return ctx;
                }
                if (itemParam.getRefundQuantity() == null || itemParam.getRefundQuantity() <= 0) {
                    ctx.canRefund = false;
                    ctx.refuseReason = "退款数量须大于0";
                    return ctx;
                }
            }
        }

        BigDecimal totalRefund = BigDecimal.ZERO;
        for (MallOrderItem orderItem : orderItems) {
            if (orderItem.getDelFlag() != null && orderItem.getDelFlag() == 1) {
                continue;
            }
            int quantity = orderItem.getQuantity() != null ? orderItem.getQuantity() : 0;
            int refunded = orderItem.getRefundedQuantity() != null ? orderItem.getRefundedQuantity() : 0;
            int refundable = Math.max(0, quantity - refunded);
            Integer requestQty = requestQtyMap.get(orderItem.getOrderItemId());
            RefundLine line = new RefundLine();
            line.orderItemId = orderItem.getOrderItemId();
            line.productName = orderItem.getProductName();
            line.skuName = orderItem.getSkuName();
            line.salePrice = orderItem.getSalePrice() != null ? orderItem.getSalePrice() : BigDecimal.ZERO;
            line.quantity = quantity;
            line.refundedQuantity = refunded;
            line.refundableQuantity = refundable;
            if (requestQty != null) {
                if (requestQty > refundable) {
                    ctx.canRefund = false;
                    ctx.refuseReason = "退款数量超过可退余量";
                } else {
                    line.refundQuantity = requestQty;
                    line.refundAmount = line.salePrice.multiply(BigDecimal.valueOf(requestQty))
                            .setScale(2, RoundingMode.HALF_UP);
                    totalRefund = totalRefund.add(line.refundAmount);
                }
            }
            ctx.lines.add(line);
        }

        if (ctx.canRefund == null) {
            ctx.canRefund = true;
            ctx.refundAmount = requestQtyMap.isEmpty()
                    ? BigDecimal.ZERO
                    : totalRefund.setScale(2, RoundingMode.HALF_UP);
        }
        return ctx;
    }

    private void loadPreviewLines(RefundBuildContext ctx, MallOrderRefundApplyApiParams params, boolean previewMode) {
        MallOrderItem query = new MallOrderItem();
        query.setOrderId(params.getOrderId());
        List<MallOrderItem> orderItems = mallOrderItemMapper.selectMallOrderItemList(query);
        Map<Long, Integer> requestQtyMap = buildRequestQtyMap(params.getItems());
        for (MallOrderItem orderItem : orderItems) {
            if (orderItem.getDelFlag() != null && orderItem.getDelFlag() == 1) {
                continue;
            }
            int quantity = orderItem.getQuantity() != null ? orderItem.getQuantity() : 0;
            int refunded = orderItem.getRefundedQuantity() != null ? orderItem.getRefundedQuantity() : 0;
            RefundLine line = new RefundLine();
            line.orderItemId = orderItem.getOrderItemId();
            line.productName = orderItem.getProductName();
            line.skuName = orderItem.getSkuName();
            line.salePrice = orderItem.getSalePrice() != null ? orderItem.getSalePrice() : BigDecimal.ZERO;
            line.quantity = quantity;
            line.refundedQuantity = refunded;
            line.refundableQuantity = Math.max(0, quantity - refunded);
            Integer requestQty = requestQtyMap.get(orderItem.getOrderItemId());
            if (requestQty != null) {
                line.refundQuantity = requestQty;
                line.refundAmount = line.salePrice.multiply(BigDecimal.valueOf(requestQty))
                        .setScale(2, RoundingMode.HALF_UP);
            }
            ctx.lines.add(line);
        }
        if (previewMode && requestQtyMap.isEmpty()) {
            ctx.refundAmount = BigDecimal.ZERO;
        }
    }

    private Map<Long, Integer> buildRequestQtyMap(List<MallOrderRefundApplyItemApiParams> items) {
        Map<Long, Integer> map = new HashMap<>();
        if (items == null) {
            return map;
        }
        for (MallOrderRefundApplyItemApiParams item : items) {
            if (item == null || item.getOrderItemId() == null) {
                continue;
            }
            map.put(item.getOrderItemId(), item.getRefundQuantity());
        }
        return map;
    }

    /**
     * 校验订单是否允许发起退款，不可退时返回原因文案
     */
    private String validateOrderRefundable(MallOrder order) {
        if (order.getPayStatus() == null || order.getPayStatus() != 1) {
            return "仅已支付订单可申请退款";
        }
        if (order.getOrderStatus() == null || order.getOrderStatus() != 1) {
            return "仅待发货订单可申请退款";
        }
        return null;
    }

    private boolean hasInProgressRefund(Long orderId, Long excludeRefundId) {
        MallOrderRefund query = new MallOrderRefund();
        query.setOrderId(orderId);
        List<MallOrderRefund> refunds = mallOrderRefundMapper.selectMallOrderRefundList(query);
        for (MallOrderRefund r : refunds) {
            if (excludeRefundId != null && excludeRefundId.equals(r.getRefundId())) {
                continue;
            }
            if (r.getDelFlag() != null && r.getDelFlag() == 1) {
                continue;
            }
            Integer status = r.getRefundStatus();
            if (status != null && (status == 0 || status == 1 || status == 3)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 退款单关闭后恢复订单状态（无其他进行中退款时退出退款中）
     */
    private void restoreOrderAfterRefundClosed(Long orderId, Long excludeRefundId) {
        if (orderId == null || hasInProgressRefund(orderId, excludeRefundId)) {
            return;
        }
        MallOrder order = mallOrderMapper.selectMallOrderById(orderId);
        if (order == null) {
            return;
        }
        MallOrder update = new MallOrder();
        update.setOrderId(orderId);
        update.setOrderStatus(1);
        BigDecimal refunded = order.getRefundAmount() != null ? order.getRefundAmount() : BigDecimal.ZERO;
        update.setRefundStatus(refunded.compareTo(BigDecimal.ZERO) > 0 ? 1 : 0);
        update.setUpdateTime(DateUtils.getNowDate());
        mallOrderMapper.updateMallOrder(update);
    }

    private MallOrderRefundPreviewApiResult toPreviewResult(RefundBuildContext ctx) {
        List<MallOrderRefundPreviewItemApiResult> items = new ArrayList<>();
        for (RefundLine line : ctx.lines) {
            items.add(MallOrderRefundPreviewItemApiResult.builder()
                    .orderItemId(line.orderItemId)
                    .productName(line.productName)
                    .skuName(line.skuName)
                    .salePrice(line.salePrice)
                    .quantity(line.quantity)
                    .refundedQuantity(line.refundedQuantity)
                    .refundableQuantity(line.refundableQuantity)
                    .refundQuantity(line.refundQuantity)
                    .refundAmount(line.refundAmount)
                    .build());
        }
        return MallOrderRefundPreviewApiResult.builder()
                .canRefund(ctx.canRefund != null && ctx.canRefund)
                .refuseReason(ctx.refuseReason)
                .orderId(ctx.orderId)
                .payAmount(ctx.payAmount)
                .refundedAmount(ctx.refundedAmount)
                .refundAmount(ctx.refundAmount != null ? ctx.refundAmount : BigDecimal.ZERO)
                .items(items)
                .build();
    }

    /**
     * 校验订单归属当前账号
     */
    private MallOrder requireOwnedOrder(Long accountId, Long orderId) {
        MallOrder order = mallOrderMapper.selectMallOrderById(orderId);
        if (order == null || (order.getDelFlag() != null && order.getDelFlag() == 1)) {
            throw new ServiceException("订单不存在");
        }
        if (!accountId.equals(order.getAccountId())) {
            throw new ServiceException("无权访问该订单", HttpStatus.FORBIDDEN);
        }
        return order;
    }

    /**
     * 校验退款单归属当前账号
     */
    private MallOrderRefund requireOwnedRefund(Long accountId, Long refundId) {
        if (refundId == null) {
            throw new ServiceException("退款单ID不能为空");
        }
        MallOrderRefund refund = mallOrderRefundMapper.selectMallOrderRefundById(refundId);
        if (refund == null || (refund.getDelFlag() != null && refund.getDelFlag() == 1)) {
            throw new ServiceException("退款单不存在");
        }
        if (!accountId.equals(refund.getAccountId())) {
            throw new ServiceException("无权访问该退款单", HttpStatus.FORBIDDEN);
        }
        return refund;
    }

    private String generateRefundNo() {
        return "RF" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + RandomUtil.randomNumbers(4);
    }

    /**
     * 退货退款完成时回滚 SKU 库存
     */
    private void rollbackStockForRefund(List<MallOrderRefundItemResult> refundItems) {
        for (MallOrderRefundItemResult refundItem : refundItems) {
            MallOrderItem orderItem = mallOrderItemMapper.selectMallOrderItemById(refundItem.getOrderItemId());
            if (orderItem == null) {
                continue;
            }
            int qty = refundItem.getRefundQuantity() != null ? refundItem.getRefundQuantity() : 0;
            if (qty <= 0) {
                continue;
            }
            if (orderItem.getSkuId() != null) {
                MallProductSku sku = mallProductSkuMapper.selectMallProductSkuById(orderItem.getSkuId());
                if (sku != null) {
                    MallProductSku skuUpdate = new MallProductSku();
                    skuUpdate.setSkuId(sku.getSkuId());
                    skuUpdate.setStock((sku.getStock() != null ? sku.getStock() : 0) + qty);
                    int sales = sku.getSalesCount() != null ? sku.getSalesCount() : 0;
                    skuUpdate.setSalesCount(Math.max(0, sales - qty));
                    mallProductSkuMapper.updateMallProductSku(skuUpdate);
                }
            }
            if (orderItem.getProductId() != null) {
                MallProduct product = mallProductMapper.selectMallProductById(orderItem.getProductId());
                if (product != null) {
                    MallProduct productUpdate = new MallProduct();
                    productUpdate.setProductId(product.getProductId());
                    productUpdate.setStockTotal((product.getStockTotal() != null ? product.getStockTotal() : 0) + qty);
                    mallProductMapper.updateMallProduct(productUpdate);
                }
            }
        }
    }

    /**
     * 全额退款完成后释放订单占用的优惠券
     */
    private void rollbackCouponIfNeeded(MallOrder order) {
        if (order.getAccountCouponId() == null || order.getAccountCouponId() <= 0) {
            return;
        }
        AccountCoupon accountCoupon = accountCouponMapper.selectAccountCouponById(order.getAccountCouponId());
        if (accountCoupon == null) {
            return;
        }
        AccountCoupon acUpdate = new AccountCoupon();
        acUpdate.setAccountCouponId(accountCoupon.getAccountCouponId());
        acUpdate.setCouponStatus(0);
        acUpdate.setOrderId(0L);
        acUpdate.setUseTime(null);
        accountCouponMapper.updateAccountCoupon(acUpdate);
        if (accountCoupon.getCouponId() != null) {
            Coupon coupon = couponMapper.selectCouponById(accountCoupon.getCouponId());
            if (coupon != null) {
                Coupon couponUpdate = new Coupon();
                couponUpdate.setCouponId(coupon.getCouponId());
                int used = coupon.getUsedCount() != null ? coupon.getUsedCount() : 0;
                couponUpdate.setUsedCount(Math.max(0, used - 1));
                couponMapper.updateCoupon(couponUpdate);
            }
        }
    }

    private static class RefundBuildContext {
        private Long orderId;
        private Boolean canRefund;
        private String refuseReason;
        private BigDecimal payAmount = BigDecimal.ZERO;
        private BigDecimal refundedAmount = BigDecimal.ZERO;
        private BigDecimal refundAmount = BigDecimal.ZERO;
        private final List<RefundLine> lines = new ArrayList<>();
    }

    private static class RefundLine {
        private Long orderItemId;
        private String productName;
        private String skuName;
        private BigDecimal salePrice;
        private Integer quantity;
        private Integer refundedQuantity;
        private Integer refundableQuantity;
        private Integer refundQuantity;
        private BigDecimal refundAmount;
    }
}
