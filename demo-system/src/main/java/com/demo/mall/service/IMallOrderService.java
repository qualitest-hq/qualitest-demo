package com.demo.mall.service;

import java.util.List;
import com.demo.mall.domain.MallOrder;
import com.demo.mall.params.MallOrderParams;
import com.demo.mall.apiParams.MallOrderApiParams;
import com.demo.mall.apiParams.MallOrderCancelApiParams;
import com.demo.mall.apiParams.MallOrderPayApiParams;
import com.demo.mall.apiParams.MallOrderSubmitApiParams;
import com.demo.mall.result.MallOrderResult;
import com.demo.mall.apiResult.MallOrderApiResult;
import com.demo.mall.apiResult.MallOrderPreviewApiResult;

/**
 * 商城订单Service接口
 * 
 * @author demo
 * @date 2026-06-20
 */
public interface IMallOrderService {
    /**
     * 查询商城订单列表
     *
     * @param mallOrder 商城订单
     * @return 商城订单集合
     */
    List<MallOrder> selectMallOrderList(MallOrder mallOrder);

    /**
     * 查询商城订单
     *
     * @param orderId 商城订单主键
     * @return 商城订单
     */
    MallOrder selectMallOrderById(Long orderId);

    /**
     * 查询商城订单Result列表
     *
     * @param params 商城订单Params
     * @return 商城订单Result集合
     */
    List<MallOrderResult> selectMallOrderResultList(MallOrderParams params);

    /**
     * 获取商城订单详细信息
     *
     * @param orderId 商城订单主键
     * @return 商城订单Result
     */
    MallOrderResult selectMallOrderResult(Long orderId);

    /**
     * 查询商城订单ApiResult列表（客户端）
     *
     * @param params 商城订单ApiParams
     * @return 商城订单ApiResult集合
     */
    List<MallOrderApiResult> selectMallOrderApiResultList(MallOrderApiParams params);

    /**
     * 获取商城订单ApiResult详细信息（客户端，校验归属并含明细）
     *
     * @param orderId 商城订单主键
     * @param accountId 账号ID
     * @return 商城订单ApiResult
     */
    MallOrderApiResult selectMallOrderApiResult(Long orderId, Long accountId);

    /**
     * 结算预览（计算金额，不落库）
     */
    MallOrderPreviewApiResult previewMallOrder(Long accountId, MallOrderSubmitApiParams params);

    /**
     * 创建订单（落库并返回订单号）
     */
    MallOrderPreviewApiResult createMallOrder(Long accountId, MallOrderSubmitApiParams params);

    /**
     * 模拟支付（微信/支付宝/余额）
     */
    void payMallOrder(Long accountId, Long orderId, MallOrderPayApiParams params);

    /**
     * 取消待付款订单
     */
    void cancelMallOrder(Long accountId, Long orderId, MallOrderCancelApiParams params);

    /**
     * 确认收货（待收货 → 已完成）
     */
    void confirmReceiveMallOrder(Long accountId, Long orderId);

    /**
     * 发货
     */
    void deliverMallOrder(Long orderId);

    /**
     * 关闭订单
     */
    void closeMallOrder(Long orderId, String cancelReason);

    /**
     * 新增商城订单
     * 
     * @param mallOrder 商城订单
     * @return 结果
     */
    int insertMallOrder(MallOrder mallOrder);

    /**
     * 修改商城订单
     * 
     * @param mallOrder 商城订单
     * @return 结果
     */
    int updateMallOrder(MallOrder mallOrder);

    /**
     * 批量删除商城订单
     * 
     * @param orderIdList 需要删除的商城订单主键集合
     * @return 结果
     */
    int deleteMallOrderByIdList(List<Long> orderIdList);

    /**
     * 删除商城订单信息
     * 
     * @param orderId 商城订单主键
     * @return 结果
     */
    public int deleteMallOrderById(Long orderId);

    /**
     * 修改商城订单为逻辑删除
     *
     * @param orderId 商城订单ID
     * @return 结果
     */
    int logicDeleteMallOrderById(Long orderId);

    /**
     * 批量修改商城订单为逻辑删除
     *
     * @param orderIdList 商城订单ID集合
     * @return 结果
     */
    int logicDeleteMallOrderByIdList(List<Long> orderIdList);

    /**
     * 查询商城订单数量
     *
     * @param params 商城订单Params
     * @return 数量
     */
    int selectMallOrderCount(MallOrderParams params);

    /**
     * 按条件查询单条商城订单
     *
     * @param params 商城订单Params
     * @return 商城订单
     */
    MallOrder selectMallOrderOne(MallOrderParams params);
}
