package com.demo.mall.service;

import java.util.List;
import com.demo.mall.domain.MallProduct;
import com.demo.mall.params.MallProductParams;
import com.demo.mall.apiParams.MallProductApiParams;
import com.demo.mall.result.MallProductResult;
import com.demo.mall.apiResult.MallProductApiResult;

/**
 * 商城商品SPUService接口
 * 
 * @author demo
 * @date 2026-06-20
 */
public interface IMallProductService {
    /**
     * 查询商城商品SPU列表
     *
     * @param mallProduct 商城商品SPU
     * @return 商城商品SPU集合
     */
    List<MallProduct> selectMallProductList(MallProduct mallProduct);

    /**
     * 查询商城商品SPU
     *
     * @param productId 商城商品SPU主键
     * @return 商城商品SPU
     */
    MallProduct selectMallProductById(Long productId);

    /**
     * 查询商城商品SPUResult列表
     *
     * @param params 商城商品SPUParams
     * @return 商城商品SPUResult集合
     */
    List<MallProductResult> selectMallProductResultList(MallProductParams params);

    /**
     * 获取商城商品SPU详细信息
     *
     * @param productId 商城商品SPU主键
     * @return 商城商品SPUResult
     */
    MallProductResult selectMallProductResult(Long productId);

    /**
     * 查询商城商品SPUApiResult列表（客户端）
     *
     * @param params 商城商品SPUApiParams
     * @return 商城商品SPUApiResult集合
     */
    List<MallProductApiResult> selectMallProductApiResultList(MallProductApiParams params);

    /**
     * 获取商城商品SPUApiResult详细信息（客户端）
     *
     * @param productId 商城商品SPU主键
     * @return 商城商品SPUApiResult
     */
    MallProductApiResult selectMallProductApiResult(Long productId);

    /**
     * 新增商城商品SPU
     * 
     * @param mallProduct 商城商品SPU
     * @return 结果
     */
    int insertMallProduct(MallProduct mallProduct);

    /**
     * 修改商城商品SPU
     * 
     * @param mallProduct 商城商品SPU
     * @return 结果
     */
    int updateMallProduct(MallProduct mallProduct);

    /**
     * 批量删除商城商品SPU
     * 
     * @param productIdList 需要删除的商城商品SPU主键集合
     * @return 结果
     */
    int deleteMallProductByIdList(List<Long> productIdList);

    /**
     * 删除商城商品SPU信息
     * 
     * @param productId 商城商品SPU主键
     * @return 结果
     */
    public int deleteMallProductById(Long productId);

    /**
     * 修改商城商品SPU为逻辑删除
     *
     * @param productId 商城商品SPUID
     * @return 结果
     */
    int logicDeleteMallProductById(Long productId);

    /**
     * 批量修改商城商品SPU为逻辑删除
     *
     * @param productIdList 商城商品SPUID集合
     * @return 结果
     */
    int logicDeleteMallProductByIdList(List<Long> productIdList);

    /**
     * 查询商城商品SPU数量
     *
     * @param params 商城商品SPUParams
     * @return 数量
     */
    int selectMallProductCount(MallProductParams params);

    /**
     * 按条件查询单条商城商品SPU
     *
     * @param params 商城商品SPUParams
     * @return 商城商品SPU
     */
    MallProduct selectMallProductOne(MallProductParams params);

    /**
     * 上架/下架
     */
    void changeMallProductShelfStatus(Long productId, Integer shelfStatus);
}
