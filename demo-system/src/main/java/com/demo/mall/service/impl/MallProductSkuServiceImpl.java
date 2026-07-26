package com.demo.mall.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.demo.common.utils.SecurityUtils;
import java.util.List;
import java.util.Objects;
import com.demo.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.mall.mapper.MallProductSkuMapper;
import com.demo.mall.domain.MallProductSku;
import com.demo.mall.params.MallProductSkuParams;
import com.demo.mall.apiParams.MallProductSkuApiParams;
import com.demo.mall.result.MallProductSkuResult;
import com.demo.mall.apiResult.MallProductSkuApiResult;
import com.demo.mall.service.IMallProductSkuService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 商城商品SKUService业务层处理
 * 
 * @author demo
 * @date 2026-06-20
 */
@Service
public class MallProductSkuServiceImpl implements IMallProductSkuService {
    @Autowired
    private MallProductSkuMapper mallProductSkuMapper;

    /**
     * 查询商城商品SKU列表
     *
     * @param mallProductSku 商城商品SKU
     * @return 商城商品SKU
     */
    @Override
    public List<MallProductSku> selectMallProductSkuList(MallProductSku mallProductSku) {
        return mallProductSkuMapper.selectMallProductSkuList(mallProductSku);
    }

    /**
     * 查询商城商品SKU
     *
     * @param skuId 商城商品SKU主键
     * @return 商城商品SKU
     */
    @Override
    public MallProductSku selectMallProductSkuById(Long skuId) {
        return mallProductSkuMapper.selectMallProductSkuById(skuId);
    }

    /**
     * 查询商城商品SKUResult列表
     *
     * @param params 商城商品SKUParams
     * @return 商城商品SKUResult集合
     */
    @Override
    public List<MallProductSkuResult> selectMallProductSkuResultList(MallProductSkuParams params) {
        return mallProductSkuMapper.selectMallProductSkuResultList(params);
    }

    /**
     * 获取商城商品SKU详细信息
     *
     * @param skuId 商城商品SKU主键
     * @return 商城商品SKUResult
     */
    @Override
    public MallProductSkuResult selectMallProductSkuResult(Long skuId) {
        return mallProductSkuMapper.selectMallProductSkuResult(skuId);
    }

    /**
     * 查询商城商品SKUApiResult列表（客户端）
     */
    @Override
    public List<MallProductSkuApiResult> selectMallProductSkuApiResultList(MallProductSkuApiParams params) {
        return mallProductSkuMapper.selectMallProductSkuApiResultList(params);
    }

    /**
     * 获取商城商品SKUApiResult详细信息（客户端）
     */
    @Override
    public MallProductSkuApiResult selectMallProductSkuApiResult(Long skuId) {
        return mallProductSkuMapper.selectMallProductSkuApiResult(skuId);
    }

    /**
     * 新增商城商品SKU
     *
     * @param mallProductSku 商城商品SKU
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertMallProductSku(MallProductSku mallProductSku) {
        if (Objects.isNull(mallProductSku.getSkuId())) {
            mallProductSku.setSkuId(IdUtil.getSnowflakeNextId());
        }
        mallProductSku.setCreateTime(DateUtils.getNowDate());
        return mallProductSkuMapper.insertMallProductSku(mallProductSku);
    }

    /**
     * 修改商城商品SKU
     *
     * @param mallProductSku 商城商品SKU
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateMallProductSku(MallProductSku mallProductSku) {
        mallProductSku.setUpdateTime(DateUtils.getNowDate());
        return mallProductSkuMapper.updateMallProductSku(mallProductSku);
    }

    /**
     * 批量删除商城商品SKU
     * 
     * @param skuIdList 需要删除的商城商品SKU主键集合
     * @return 结果
     */
    @Override
    public int deleteMallProductSkuByIdList(List<Long> skuIdList) {
        return mallProductSkuMapper.deleteMallProductSkuByIdList(skuIdList);
    }

    /**
     * 删除商城商品SKU信息
     * 
     * @param skuId 商城商品SKU主键
     * @return 结果
     */
    @Override
    public int deleteMallProductSkuById(Long skuId) {
        return mallProductSkuMapper.deleteMallProductSkuById(skuId);
    }

    /**
     * 逻辑删除商城商品SKU信息
     * 
     * @param skuId 商城商品SKU主键
     * @return 结果
     */
    @Override
    public int logicDeleteMallProductSkuById(Long skuId) {
        return mallProductSkuMapper.logicDeleteMallProductSkuById(skuId);
    }

    /**
     * 批量逻辑删除商城商品SKU信息
     * 
     * @param skuIdList 商城商品SKU主键集合
     * @return 结果
     */
    @Override
    public int logicDeleteMallProductSkuByIdList(List<Long> skuIdList) {
        return mallProductSkuMapper.logicDeleteMallProductSkuByIdList(skuIdList);
    }

    /**
     * 查询商城商品SKU数量
     *
     * @param params 商城商品SKUParams
     * @return 数量
     */
    @Override
    public int selectMallProductSkuCount(MallProductSkuParams params) {
        return mallProductSkuMapper.selectMallProductSkuCount(params);
    }

    /**
     * 按条件查询单条商城商品SKU
     *
     * @param params 商城商品SKUParams
     * @return 商城商品SKU
     */
    @Override
    public MallProductSku selectMallProductSkuOne(MallProductSkuParams params) {
        return mallProductSkuMapper.selectMallProductSkuOne(params);
    }
}
