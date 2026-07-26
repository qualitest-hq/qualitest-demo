package com.demo.mall.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.demo.mall.domain.MallOrderRefund;
import com.demo.mall.params.MallOrderRefundParams;
import com.demo.mall.apiParams.MallOrderRefundApiParams;
import com.demo.mall.result.MallOrderRefundResult;
import com.demo.mall.apiResult.MallOrderRefundApiResult;

/**
 * 商城退款单Mapper接口
 * 
 * @author demo
 * @date 2026-06-20
 */
@Mapper
public interface MallOrderRefundMapper {
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
     * 删除商城退款单
     * 
     * @param refundId 商城退款单主键
     * @return 结果
     */
    int deleteMallOrderRefundById(Long refundId);

    /**
     * 批量删除商城退款单
     * 
     * @param refundIdList 需要删除的数据主键集合
     * @return 结果
     */
    int deleteMallOrderRefundByIdList(@Param("list") List<Long> refundIdList);

    /**
     * 逻辑删除商城退款单
     * 
     * @param refundId 商城退款单主键
     * @return 结果
     */
    int logicDeleteMallOrderRefundById(Long refundId);

    /**
     * 批量逻辑删除商城退款单
     * 
     * @param refundIdList 商城退款单主键集合
     * @return 结果
     */
    int logicDeleteMallOrderRefundByIdList(@Param("list") List<Long> refundIdList);
}
