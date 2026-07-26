package com.demo.mall.service;

import java.util.List;
import com.demo.mall.domain.MallOrderRefund;
import com.demo.mall.params.MallOrderRefundParams;
import com.demo.mall.apiParams.MallOrderRefundApiParams;
import com.demo.mall.apiParams.MallOrderRefundApplyApiParams;
import com.demo.mall.result.MallOrderRefundResult;
import com.demo.mall.apiResult.MallOrderRefundApiResult;
import com.demo.mall.apiResult.MallOrderRefundPreviewApiResult;

/**
 * 商城退款单Service接口
 * 
 * @author demo
 * @date 2026-06-20
 */
public interface IMallOrderRefundService {
    /**
     * 查询商城退款单列表
     *
     * @param mallOrderRefund 商城退款单
     * @return 商城退款单集合
     */
    List<MallOrderRefund> selectMallOrderRefundList(MallOrderRefund mallOrderRefund);

    /**
     * 查询商城退款单
     *
     * @param refundId 商城退款单主键
     * @return 商城退款单
     */
    MallOrderRefund selectMallOrderRefundById(Long refundId);

    /**
     * 查询商城退款单Result列表
     *
     * @param params 商城退款单Params
     * @return 商城退款单Result集合
     */
    List<MallOrderRefundResult> selectMallOrderRefundResultList(MallOrderRefundParams params);

    /**
     * 获取商城退款单详细信息
     *
     * @param refundId 商城退款单主键
     * @return 商城退款单Result
     */
    MallOrderRefundResult selectMallOrderRefundResult(Long refundId);

    /**
     * 查询商城退款单ApiResult列表（客户端）
     *
     * @param params 商城退款单ApiParams
     * @return 商城退款单ApiResult集合
     */
    List<MallOrderRefundApiResult> selectMallOrderRefundApiResultList(MallOrderRefundApiParams params);

    /**
     * 获取商城退款单ApiResult详细信息（客户端）
     *
     * @param refundId 商城退款单主键
     * @return 商城退款单ApiResult
     */
    MallOrderRefundApiResult selectMallOrderRefundApiResult(Long refundId);

    /**
     * 新增商城退款单
     * 
     * @param mallOrderRefund 商城退款单
     * @return 结果
     */
    int insertMallOrderRefund(MallOrderRefund mallOrderRefund);

    /**
     * 修改商城退款单
     * 
     * @param mallOrderRefund 商城退款单
     * @return 结果
     */
    int updateMallOrderRefund(MallOrderRefund mallOrderRefund);

    /**
     * 批量删除商城退款单
     * 
     * @param refundIdList 需要删除的商城退款单主键集合
     * @return 结果
     */
    int deleteMallOrderRefundByIdList(List<Long> refundIdList);

    /**
     * 删除商城退款单信息
     * 
     * @param refundId 商城退款单主键
     * @return 结果
     */
    public int deleteMallOrderRefundById(Long refundId);

    /**
     * 修改商城退款单为逻辑删除
     *
     * @param refundId 商城退款单ID
     * @return 结果
     */
    int logicDeleteMallOrderRefundById(Long refundId);

    /**
     * 批量修改商城退款单为逻辑删除
     *
     * @param refundIdList 商城退款单ID集合
     * @return 结果
     */
    int logicDeleteMallOrderRefundByIdList(List<Long> refundIdList);

    /**
     * 查询商城退款单数量
     *
     * @param params 商城退款单Params
     * @return 数量
     */
    int selectMallOrderRefundCount(MallOrderRefundParams params);

    /**
     * 按条件查询单条商城退款单
     *
     * @param params 商城退款单Params
     * @return 商城退款单
     */
    MallOrderRefund selectMallOrderRefundOne(MallOrderRefundParams params);

    /**
     * 退款预览（校验可退性并计算金额，不落库）
     */
    MallOrderRefundPreviewApiResult previewMallOrderRefund(Long accountId, MallOrderRefundApplyApiParams params);

    /**
     * 申请退款（创建退款单并更新订单状态）
     */
    MallOrderRefundApiResult applyMallOrderRefund(Long accountId, MallOrderRefundApplyApiParams params);

    /**
     * 撤销待审核退款申请
     */
    void cancelMallOrderRefund(Long accountId, Long refundId);

    /**
     * 获取退款单详情（校验归属并加载明细）
     */
    MallOrderRefundApiResult selectMallOrderRefundApiResult(Long refundId, Long accountId);

    /**
     * 审核通过
     */
    void approveMallOrderRefund(Long refundId);

    /**
     * 审核拒绝
     */
    void rejectMallOrderRefund(Long refundId, String handleRemark);

    /**
     * 确认退款完成
     */
    void completeMallOrderRefund(Long refundId);
}
