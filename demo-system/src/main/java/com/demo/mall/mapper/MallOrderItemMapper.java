package com.demo.mall.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.demo.mall.domain.MallOrderItem;
import com.demo.mall.params.MallOrderItemParams;
import com.demo.mall.apiParams.MallOrderItemApiParams;
import com.demo.mall.result.MallOrderItemResult;
import com.demo.mall.apiResult.MallOrderItemApiResult;

/**
 * 商城订单明细Mapper接口
 * 
 * @author demo
 * @date 2026-06-20
 */
@Mapper
public interface MallOrderItemMapper {
    /**
     * 查询商城订单明细列表
     *
     * @param mallOrderItem 商城订单明细
     * @return 商城订单明细集合
     */
    List<MallOrderItem> selectMallOrderItemList(MallOrderItem mallOrderItem);

    /**
     * 查询商城订单明细
     *
     * @param orderItemId 商城订单明细主键
     * @return 商城订单明细
     */
    MallOrderItem selectMallOrderItemById(Long orderItemId);

    /**
     * 查询商城订单明细Result列表
     *
     * @param params 商城订单明细Params
     * @return 商城订单明细Result集合
     */
    List<MallOrderItemResult> selectMallOrderItemResultList(MallOrderItemParams params);

    /**
     * 获取商城订单明细详细信息
     *
     * @param orderItemId 商城订单明细主键
     * @return 商城订单明细Result
     */
    MallOrderItemResult selectMallOrderItemResult(Long orderItemId);

    /**
     * 查询商城订单明细ApiResult列表（客户端）
     *
     * @param params 商城订单明细ApiParams
     * @return 商城订单明细ApiResult集合
     */
    List<MallOrderItemApiResult> selectMallOrderItemApiResultList(MallOrderItemApiParams params);

    /**
     * 获取商城订单明细ApiResult详细信息（客户端）
     *
     * @param orderItemId 商城订单明细主键
     * @return 商城订单明细ApiResult
     */
    MallOrderItemApiResult selectMallOrderItemApiResult(Long orderItemId);

    /**
     * 查询商城订单明细数量
     *
     * @param params 商城订单明细Params
     * @return 数量
     */
    int selectMallOrderItemCount(MallOrderItemParams params);

    /**
     * 按条件查询单条商城订单明细
     *
     * @param params 商城订单明细Params
     * @return 商城订单明细
     */
    MallOrderItem selectMallOrderItemOne(MallOrderItemParams params);

    /**
     * 新增商城订单明细
     * 
     * @param mallOrderItem 商城订单明细
     * @return 结果
     */
    int insertMallOrderItem(MallOrderItem mallOrderItem);

    /**
     * 修改商城订单明细
     * 
     * @param mallOrderItem 商城订单明细
     * @return 结果
     */
    int updateMallOrderItem(MallOrderItem mallOrderItem);

    /**
     * 删除商城订单明细
     * 
     * @param orderItemId 商城订单明细主键
     * @return 结果
     */
    int deleteMallOrderItemById(Long orderItemId);

    /**
     * 批量删除商城订单明细
     * 
     * @param orderItemIdList 需要删除的数据主键集合
     * @return 结果
     */
    int deleteMallOrderItemByIdList(@Param("list") List<Long> orderItemIdList);

    /**
     * 逻辑删除商城订单明细
     * 
     * @param orderItemId 商城订单明细主键
     * @return 结果
     */
    int logicDeleteMallOrderItemById(Long orderItemId);

    /**
     * 批量逻辑删除商城订单明细
     * 
     * @param orderItemIdList 商城订单明细主键集合
     * @return 结果
     */
    int logicDeleteMallOrderItemByIdList(@Param("list") List<Long> orderItemIdList);
}
