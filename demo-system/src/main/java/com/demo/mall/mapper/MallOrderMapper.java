package com.demo.mall.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.demo.mall.domain.MallOrder;
import com.demo.mall.params.MallOrderParams;
import com.demo.mall.apiParams.MallOrderApiParams;
import com.demo.mall.result.MallOrderResult;
import com.demo.mall.apiResult.MallOrderApiResult;

/**
 * 商城订单Mapper接口
 * 
 * @author demo
 * @date 2026-06-20
 */
@Mapper
public interface MallOrderMapper {
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
     * 获取商城订单ApiResult详细信息（客户端）
     *
     * @param orderId 商城订单主键
     * @return 商城订单ApiResult
     */
    MallOrderApiResult selectMallOrderApiResult(Long orderId);

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
     * 删除商城订单
     * 
     * @param orderId 商城订单主键
     * @return 结果
     */
    int deleteMallOrderById(Long orderId);

    /**
     * 批量删除商城订单
     * 
     * @param orderIdList 需要删除的数据主键集合
     * @return 结果
     */
    int deleteMallOrderByIdList(@Param("list") List<Long> orderIdList);

    /**
     * 逻辑删除商城订单
     * 
     * @param orderId 商城订单主键
     * @return 结果
     */
    int logicDeleteMallOrderById(Long orderId);

    /**
     * 批量逻辑删除商城订单
     * 
     * @param orderIdList 商城订单主键集合
     * @return 结果
     */
    int logicDeleteMallOrderByIdList(@Param("list") List<Long> orderIdList);
}
