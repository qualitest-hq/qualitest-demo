package com.demo.mall.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.demo.mall.domain.MallCart;
import com.demo.mall.params.MallCartParams;
import com.demo.mall.apiParams.MallCartApiParams;
import com.demo.mall.result.MallCartResult;
import com.demo.mall.apiResult.MallCartApiResult;

/**
 * 商城购物车Mapper接口
 * 
 * @author demo
 * @date 2026-06-20
 */
@Mapper
public interface MallCartMapper {
    /**
     * 查询商城购物车列表
     *
     * @param mallCart 商城购物车
     * @return 商城购物车集合
     */
    List<MallCart> selectMallCartList(MallCart mallCart);

    /**
     * 查询商城购物车
     *
     * @param cartId 商城购物车主键
     * @return 商城购物车
     */
    MallCart selectMallCartById(Long cartId);

    /**
     * 查询商城购物车Result列表
     *
     * @param params 商城购物车Params
     * @return 商城购物车Result集合
     */
    List<MallCartResult> selectMallCartResultList(MallCartParams params);

    /**
     * 获取商城购物车详细信息
     *
     * @param cartId 商城购物车主键
     * @return 商城购物车Result
     */
    MallCartResult selectMallCartResult(Long cartId);

    /**
     * 查询商城购物车ApiResult列表（客户端）
     *
     * @param params 商城购物车ApiParams
     * @return 商城购物车ApiResult集合
     */
    List<MallCartApiResult> selectMallCartApiResultList(MallCartApiParams params);

    /**
     * 获取商城购物车ApiResult详细信息（客户端）
     *
     * @param cartId 商城购物车主键
     * @return 商城购物车ApiResult
     */
    MallCartApiResult selectMallCartApiResult(Long cartId);

    /**
     * 查询商城购物车数量
     *
     * @param params 商城购物车Params
     * @return 数量
     */
    int selectMallCartCount(MallCartParams params);

    /**
     * 按条件查询单条商城购物车
     *
     * @param params 商城购物车Params
     * @return 商城购物车
     */
    MallCart selectMallCartOne(MallCartParams params);

    /**
     * 新增商城购物车
     * 
     * @param mallCart 商城购物车
     * @return 结果
     */
    int insertMallCart(MallCart mallCart);

    /**
     * 修改商城购物车
     * 
     * @param mallCart 商城购物车
     * @return 结果
     */
    int updateMallCart(MallCart mallCart);

    /**
     * 删除商城购物车
     * 
     * @param cartId 商城购物车主键
     * @return 结果
     */
    int deleteMallCartById(Long cartId);

    /**
     * 批量删除商城购物车
     * 
     * @param cartIdList 需要删除的数据主键集合
     * @return 结果
     */
    int deleteMallCartByIdList(@Param("list") List<Long> cartIdList);

    /**
     * 逻辑删除商城购物车
     * 
     * @param cartId 商城购物车主键
     * @return 结果
     */
    int logicDeleteMallCartById(Long cartId);

    /**
     * 批量逻辑删除商城购物车
     * 
     * @param cartIdList 商城购物车主键集合
     * @return 结果
     */
    int logicDeleteMallCartByIdList(@Param("list") List<Long> cartIdList);

    int logicDeleteByAccountId(@Param("accountId") Long accountId);
}
