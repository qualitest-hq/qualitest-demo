package com.demo.mall.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.demo.mall.domain.MallProductSku;
import com.demo.mall.params.MallProductSkuParams;
import com.demo.mall.apiParams.MallProductSkuApiParams;
import com.demo.mall.result.MallProductSkuResult;
import com.demo.mall.apiResult.MallProductSkuApiResult;

/**
 * 商城商品SKUMapper接口
 * 
 * @author demo
 * @date 2026-06-20
 */
@Mapper
public interface MallProductSkuMapper {
    /**
     * 查询商城商品SKU列表
     *
     * @param mallProductSku 商城商品SKU
     * @return 商城商品SKU集合
     */
    List<MallProductSku> selectMallProductSkuList(MallProductSku mallProductSku);

    /**
     * 查询商城商品SKU
     *
     * @param skuId 商城商品SKU主键
     * @return 商城商品SKU
     */
    MallProductSku selectMallProductSkuById(Long skuId);

    /**
     * 查询商城商品SKUResult列表
     *
     * @param params 商城商品SKUParams
     * @return 商城商品SKUResult集合
     */
    List<MallProductSkuResult> selectMallProductSkuResultList(MallProductSkuParams params);

    /**
     * 获取商城商品SKU详细信息
     *
     * @param skuId 商城商品SKU主键
     * @return 商城商品SKUResult
     */
    MallProductSkuResult selectMallProductSkuResult(Long skuId);

    /**
     * 查询商城商品SKUApiResult列表（客户端）
     *
     * @param params 商城商品SKUApiParams
     * @return 商城商品SKUApiResult集合
     */
    List<MallProductSkuApiResult> selectMallProductSkuApiResultList(MallProductSkuApiParams params);

    /**
     * 获取商城商品SKUApiResult详细信息（客户端）
     *
     * @param skuId 商城商品SKU主键
     * @return 商城商品SKUApiResult
     */
    MallProductSkuApiResult selectMallProductSkuApiResult(Long skuId);

    /**
     * 查询商城商品SKU数量
     *
     * @param params 商城商品SKUParams
     * @return 数量
     */
    int selectMallProductSkuCount(MallProductSkuParams params);

    /**
     * 按条件查询单条商城商品SKU
     *
     * @param params 商城商品SKUParams
     * @return 商城商品SKU
     */
    MallProductSku selectMallProductSkuOne(MallProductSkuParams params);

    /**
     * 新增商城商品SKU
     * 
     * @param mallProductSku 商城商品SKU
     * @return 结果
     */
    int insertMallProductSku(MallProductSku mallProductSku);

    /**
     * 修改商城商品SKU
     * 
     * @param mallProductSku 商城商品SKU
     * @return 结果
     */
    int updateMallProductSku(MallProductSku mallProductSku);

    /**
     * 删除商城商品SKU
     * 
     * @param skuId 商城商品SKU主键
     * @return 结果
     */
    int deleteMallProductSkuById(Long skuId);

    /**
     * 批量删除商城商品SKU
     * 
     * @param skuIdList 需要删除的数据主键集合
     * @return 结果
     */
    int deleteMallProductSkuByIdList(@Param("list") List<Long> skuIdList);

    /**
     * 逻辑删除商城商品SKU
     * 
     * @param skuId 商城商品SKU主键
     * @return 结果
     */
    int logicDeleteMallProductSkuById(Long skuId);

    /**
     * 批量逻辑删除商城商品SKU
     * 
     * @param skuIdList 商城商品SKU主键集合
     * @return 结果
     */
    int logicDeleteMallProductSkuByIdList(@Param("list") List<Long> skuIdList);
}
