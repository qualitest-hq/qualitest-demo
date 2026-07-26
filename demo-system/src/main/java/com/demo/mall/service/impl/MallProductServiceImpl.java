package com.demo.mall.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.demo.common.utils.SecurityUtils;
import java.util.List;
import java.util.Objects;
import com.demo.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.mall.mapper.MallProductMapper;
import com.demo.mall.domain.MallProduct;
import com.demo.mall.params.MallProductParams;
import com.demo.mall.apiParams.MallProductApiParams;
import com.demo.mall.result.MallProductResult;
import com.demo.mall.apiResult.MallProductApiResult;
import com.demo.common.exception.ServiceException;
import com.demo.mall.apiParams.MallProductSkuApiParams;
import com.demo.mall.apiResult.MallProductSkuApiResult;
import com.demo.mall.service.IMallProductSkuService;
import com.demo.mall.service.IMallProductService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 商城商品SPUService业务层处理
 * 
 * @author demo
 * @date 2026-06-20
 */
@Service
public class MallProductServiceImpl implements IMallProductService {
    @Autowired
    private MallProductMapper mallProductMapper;

    @Autowired
    private IMallProductSkuService mallProductSkuService;

    /**
     * 查询商城商品SPU列表
     *
     * @param mallProduct 商城商品SPU
     * @return 商城商品SPU
     */
    @Override
    public List<MallProduct> selectMallProductList(MallProduct mallProduct) {
        return mallProductMapper.selectMallProductList(mallProduct);
    }

    /**
     * 查询商城商品SPU
     *
     * @param productId 商城商品SPU主键
     * @return 商城商品SPU
     */
    @Override
    public MallProduct selectMallProductById(Long productId) {
        return mallProductMapper.selectMallProductById(productId);
    }

    /**
     * 查询商城商品SPUResult列表
     *
     * @param params 商城商品SPUParams
     * @return 商城商品SPUResult集合
     */
    @Override
    public List<MallProductResult> selectMallProductResultList(MallProductParams params) {
        return mallProductMapper.selectMallProductResultList(params);
    }

    /**
     * 获取商城商品SPU详细信息
     *
     * @param productId 商城商品SPU主键
     * @return 商城商品SPUResult
     */
    @Override
    public MallProductResult selectMallProductResult(Long productId) {
        return mallProductMapper.selectMallProductResult(productId);
    }

    /**
     * 查询商城商品SPUApiResult列表（客户端）
     */
    @Override
    public List<MallProductApiResult> selectMallProductApiResultList(MallProductApiParams params) {
        return mallProductMapper.selectMallProductApiResultList(params);
    }

    /**
     * 获取商城商品SPUApiResult详细信息（客户端）
     */
    @Override
    public MallProductApiResult selectMallProductApiResult(Long productId) {
        MallProductApiResult product = mallProductMapper.selectMallProductApiResult(productId);
        if (product == null || product.getShelfStatus() == null || product.getShelfStatus() != 1) {
            throw new ServiceException("商品不存在或已下架");
        }
        MallProductSkuApiParams params = new MallProductSkuApiParams();
        params.setProductId(productId);
        List<MallProductSkuApiResult> skus = mallProductSkuService.selectMallProductSkuApiResultList(params);
        product.setSkus(skus);
        return product;
    }

    /**
     * 新增商城商品SPU
     *
     * @param mallProduct 商城商品SPU
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertMallProduct(MallProduct mallProduct) {
        if (Objects.isNull(mallProduct.getProductId())) {
            mallProduct.setProductId(IdUtil.getSnowflakeNextId());
        }
        mallProduct.setCreateTime(DateUtils.getNowDate());
        return mallProductMapper.insertMallProduct(mallProduct);
    }

    /**
     * 修改商城商品SPU
     *
     * @param mallProduct 商城商品SPU
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateMallProduct(MallProduct mallProduct) {
        mallProduct.setUpdateTime(DateUtils.getNowDate());
        return mallProductMapper.updateMallProduct(mallProduct);
    }

    /**
     * 批量删除商城商品SPU
     * 
     * @param productIdList 需要删除的商城商品SPU主键集合
     * @return 结果
     */
    @Override
    public int deleteMallProductByIdList(List<Long> productIdList) {
        return mallProductMapper.deleteMallProductByIdList(productIdList);
    }

    /**
     * 删除商城商品SPU信息
     * 
     * @param productId 商城商品SPU主键
     * @return 结果
     */
    @Override
    public int deleteMallProductById(Long productId) {
        return mallProductMapper.deleteMallProductById(productId);
    }

    /**
     * 逻辑删除商城商品SPU信息
     * 
     * @param productId 商城商品SPU主键
     * @return 结果
     */
    @Override
    public int logicDeleteMallProductById(Long productId) {
        return mallProductMapper.logicDeleteMallProductById(productId);
    }

    /**
     * 批量逻辑删除商城商品SPU信息
     * 
     * @param productIdList 商城商品SPU主键集合
     * @return 结果
     */
    @Override
    public int logicDeleteMallProductByIdList(List<Long> productIdList) {
        return mallProductMapper.logicDeleteMallProductByIdList(productIdList);
    }

    /**
     * 查询商城商品SPU数量
     *
     * @param params 商城商品SPUParams
     * @return 数量
     */
    @Override
    public int selectMallProductCount(MallProductParams params) {
        return mallProductMapper.selectMallProductCount(params);
    }

    /**
     * 按条件查询单条商城商品SPU
     *
     * @param params 商城商品SPUParams
     * @return 商城商品SPU
     */
    @Override
    public MallProduct selectMallProductOne(MallProductParams params) {
        return mallProductMapper.selectMallProductOne(params);
    }

    /**
     * 切换商品上架/下架状态
     */
    @Override
    public void changeMallProductShelfStatus(Long productId, Integer shelfStatus) {
        if (productId == null) {
            throw new ServiceException("商品ID不能为空");
        }
        if (shelfStatus == null || (shelfStatus != 0 && shelfStatus != 1)) {
            throw new ServiceException("上架状态无效");
        }
        MallProduct product = mallProductMapper.selectMallProductById(productId);
        if (product == null || (product.getDelFlag() != null && product.getDelFlag() == 1)) {
            throw new ServiceException("商品不存在");
        }
        MallProduct update = new MallProduct();
        update.setProductId(productId);
        update.setShelfStatus(shelfStatus);
        mallProductMapper.updateMallProduct(update);
    }
}
