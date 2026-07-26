package com.demo.mall.service;

import com.demo.common.exception.ServiceException;
import com.demo.common.utils.DateUtils;
import com.demo.coupon.domain.AccountCoupon;
import com.demo.coupon.mapper.AccountCouponMapper;
import com.demo.coupon.mapper.CouponMapper;
import com.demo.mall.domain.MallOrder;
import com.demo.mall.domain.MallOrderItem;
import com.demo.mall.domain.MallProduct;
import com.demo.mall.domain.MallProductSku;
import com.demo.mall.mapper.MallOrderItemMapper;
import com.demo.mall.mapper.MallProductMapper;
import com.demo.mall.mapper.MallProductSkuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 订单库存与优惠券辅助操作
 */
@Service
public class MallOrderSupportService {

    @Autowired
    private MallOrderItemMapper mallOrderItemMapper;

    @Autowired
    private MallProductSkuMapper mallProductSkuMapper;

    @Autowired
    private MallProductMapper mallProductMapper;

    @Autowired
    private AccountCouponMapper accountCouponMapper;

    @Autowired
    private CouponMapper couponMapper;

    /**
     * 按订单明细扣减 SKU/SPU 库存并增加销量
     */
    public void deductStockForOrderItems(List<MallOrderItem> items) {
        for (MallOrderItem item : items) {
            if (item.getSkuId() == null || item.getQuantity() == null || item.getQuantity() <= 0) {
                continue;
            }
            int qty = item.getQuantity();
            MallProductSku sku = mallProductSkuMapper.selectMallProductSkuById(item.getSkuId());
            if (sku == null) {
                throw new ServiceException("SKU不存在");
            }
            int stock = sku.getStock() != null ? sku.getStock() : 0;
            if (stock < qty) {
                throw new ServiceException("库存不足");
            }
            MallProductSku skuUpdate = new MallProductSku();
            skuUpdate.setSkuId(sku.getSkuId());
            skuUpdate.setStock(stock - qty);
            skuUpdate.setSalesCount((sku.getSalesCount() != null ? sku.getSalesCount() : 0) + qty);
            mallProductSkuMapper.updateMallProductSku(skuUpdate);
            if (item.getProductId() != null) {
                MallProduct product = mallProductMapper.selectMallProductById(item.getProductId());
                if (product != null) {
                    MallProduct productUpdate = new MallProduct();
                    productUpdate.setProductId(product.getProductId());
                    int stockTotal = product.getStockTotal() != null ? product.getStockTotal() : 0;
                    productUpdate.setStockTotal(Math.max(0, stockTotal - qty));
                    int sales = product.getSalesCount() != null ? product.getSalesCount() : 0;
                    productUpdate.setSalesCount(sales + qty);
                    mallProductMapper.updateMallProduct(productUpdate);
                }
            }
        }
    }

    /**
     * 按订单明细回滚 SKU/SPU 库存并扣减销量
     */
    public void rollbackStockForOrder(Long orderId) {
        MallOrderItem query = new MallOrderItem();
        query.setOrderId(orderId);
        List<MallOrderItem> items = mallOrderItemMapper.selectMallOrderItemList(query);
        for (MallOrderItem item : items) {
            if (item.getSkuId() == null || item.getQuantity() == null || item.getQuantity() <= 0) {
                continue;
            }
            int qty = item.getQuantity();
            MallProductSku sku = mallProductSkuMapper.selectMallProductSkuById(item.getSkuId());
            if (sku != null) {
                MallProductSku skuUpdate = new MallProductSku();
                skuUpdate.setSkuId(sku.getSkuId());
                skuUpdate.setStock((sku.getStock() != null ? sku.getStock() : 0) + qty);
                int sales = sku.getSalesCount() != null ? sku.getSalesCount() : 0;
                skuUpdate.setSalesCount(Math.max(0, sales - qty));
                mallProductSkuMapper.updateMallProductSku(skuUpdate);
            }
            if (item.getProductId() != null) {
                MallProduct product = mallProductMapper.selectMallProductById(item.getProductId());
                if (product != null) {
                    MallProduct productUpdate = new MallProduct();
                    productUpdate.setProductId(product.getProductId());
                    productUpdate.setStockTotal((product.getStockTotal() != null ? product.getStockTotal() : 0) + qty);
                    int sales = product.getSalesCount() != null ? product.getSalesCount() : 0;
                    productUpdate.setSalesCount(Math.max(0, sales - qty));
                    mallProductMapper.updateMallProduct(productUpdate);
                }
            }
        }
    }

    /**
     * 待付款阶段占用账号优惠券
     */
    public void holdCoupon(Long accountCouponId, Long orderId, Long accountId) {
        AccountCoupon accountCoupon = accountCouponMapper.selectAccountCouponById(accountCouponId);
        if (accountCoupon == null || (accountCoupon.getDelFlag() != null && accountCoupon.getDelFlag() == 1)) {
            throw new ServiceException("优惠券不存在");
        }
        if (!accountId.equals(accountCoupon.getAccountId())) {
            throw new ServiceException("无权使用该优惠券");
        }
        AccountCoupon update = new AccountCoupon();
        update.setAccountCouponId(accountCouponId);
        update.setOrderId(orderId);
        update.setUpdateTime(DateUtils.getNowDate());
        accountCouponMapper.updateAccountCoupon(update);
    }

    /**
     * 释放订单占用的账号优惠券
     */
    public void releaseOccupiedCoupon(MallOrder order) {
        if (order.getAccountCouponId() == null || order.getAccountCouponId() <= 0) {
            return;
        }
        AccountCoupon accountCoupon = accountCouponMapper.selectAccountCouponById(order.getAccountCouponId());
        if (accountCoupon == null) {
            return;
        }
        AccountCoupon update = new AccountCoupon();
        update.setAccountCouponId(accountCoupon.getAccountCouponId());
        update.setOrderId(0L);
        update.setCouponStatus(0);
        update.setUseTime(null);
        update.setUpdateTime(DateUtils.getNowDate());
        accountCouponMapper.updateAccountCoupon(update);
    }

    /**
     * 支付成功后核销账号优惠券并更新模板已使用数量
     */
    public void consumeCouponOnPay(MallOrder order) {
        if (order.getAccountCouponId() == null || order.getAccountCouponId() <= 0) {
            return;
        }
        AccountCoupon accountCoupon = accountCouponMapper.selectAccountCouponById(order.getAccountCouponId());
        if (accountCoupon == null) {
            return;
        }
        Date now = DateUtils.getNowDate();
        AccountCoupon update = new AccountCoupon();
        update.setAccountCouponId(accountCoupon.getAccountCouponId());
        update.setCouponStatus(1);
        update.setUseTime(now);
        update.setUpdateTime(now);
        accountCouponMapper.updateAccountCoupon(update);
        if (order.getCouponId() != null && order.getCouponId() > 0) {
            couponMapper.incrementUsedCount(order.getCouponId());
        }
    }
}
