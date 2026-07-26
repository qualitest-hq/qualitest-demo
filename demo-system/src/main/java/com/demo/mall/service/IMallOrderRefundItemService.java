package com.demo.mall.service;

import java.util.List;
import com.demo.mall.domain.MallOrderRefundItem;
import com.demo.mall.params.MallOrderRefundItemParams;
import com.demo.mall.apiParams.MallOrderRefundItemApiParams;
import com.demo.mall.result.MallOrderRefundItemResult;
import com.demo.mall.apiResult.MallOrderRefundItemApiResult;

/**
 * 商城退款明细Service接口
 * 
 * @author demo
 * @date 2026-06-20
 */
public interface IMallOrderRefundItemService {
    /**
     * 查询商城退款明细列表
     *
     * @param mallOrderRefundItem 商城退款明细
     * @return 商城退款明细集合
     */
    List<MallOrderRefundItem> selectMallOrderRefundItemList(MallOrderRefundItem mallOrderRefundItem);

    /**
     * 查询商城退款明细
     *
     * @param refundItemId 商城退款明细主键
     * @return 商城退款明细
     */
    MallOrderRefundItem selectMallOrderRefundItemById(Long refundItemId);

    /**
     * 查询商城退款明细Result列表
     *
     * @param params 商城退款明细Params
     * @return 商城退款明细Result集合
     */
    List<MallOrderRefundItemResult> selectMallOrderRefundItemResultList(MallOrderRefundItemParams params);

    /**
     * 获取商城退款明细详细信息
     *
     * @param refundItemId 商城退款明细主键
     * @return 商城退款明细Result
     */
    MallOrderRefundItemResult selectMallOrderRefundItemResult(Long refundItemId);

    /**
     * 查询商城退款明细ApiResult列表（客户端）
     *
     * @param params 商城退款明细ApiParams
     * @return 商城退款明细ApiResult集合
     */
    List<MallOrderRefundItemApiResult> selectMallOrderRefundItemApiResultList(MallOrderRefundItemApiParams params);

    /**
     * 获取商城退款明细ApiResult详细信息（客户端）
     *
     * @param refundItemId 商城退款明细主键
     * @return 商城退款明细ApiResult
     */
    MallOrderRefundItemApiResult selectMallOrderRefundItemApiResult(Long refundItemId);

    /**
     * 新增商城退款明细
     * 
     * @param mallOrderRefundItem 商城退款明细
     * @return 结果
     */
    int insertMallOrderRefundItem(MallOrderRefundItem mallOrderRefundItem);

    /**
     * 修改商城退款明细
     * 
     * @param mallOrderRefundItem 商城退款明细
     * @return 结果
     */
    int updateMallOrderRefundItem(MallOrderRefundItem mallOrderRefundItem);

    /**
     * 批量删除商城退款明细
     * 
     * @param refundItemIdList 需要删除的商城退款明细主键集合
     * @return 结果
     */
    int deleteMallOrderRefundItemByIdList(List<Long> refundItemIdList);

    /**
     * 删除商城退款明细信息
     * 
     * @param refundItemId 商城退款明细主键
     * @return 结果
     */
    public int deleteMallOrderRefundItemById(Long refundItemId);

    /**
     * 修改商城退款明细为逻辑删除
     *
     * @param refundItemId 商城退款明细ID
     * @return 结果
     */
    int logicDeleteMallOrderRefundItemById(Long refundItemId);

    /**
     * 批量修改商城退款明细为逻辑删除
     *
     * @param refundItemIdList 商城退款明细ID集合
     * @return 结果
     */
    int logicDeleteMallOrderRefundItemByIdList(List<Long> refundItemIdList);

    /**
     * 查询商城退款明细数量
     *
     * @param params 商城退款明细Params
     * @return 数量
     */
    int selectMallOrderRefundItemCount(MallOrderRefundItemParams params);

    /**
     * 按条件查询单条商城退款明细
     *
     * @param params 商城退款明细Params
     * @return 商城退款明细
     */
    MallOrderRefundItem selectMallOrderRefundItemOne(MallOrderRefundItemParams params);
}
