package com.demo.mall.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.demo.common.utils.SecurityUtils;
import java.util.List;
import java.util.Objects;
import com.demo.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.mall.mapper.MallCategoryMapper;
import com.demo.mall.domain.MallCategory;
import com.demo.mall.params.MallCategoryParams;
import com.demo.mall.apiParams.MallCategoryApiParams;
import com.demo.mall.result.MallCategoryResult;
import com.demo.mall.apiResult.MallCategoryApiResult;
import com.demo.mall.service.IMallCategoryService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 商城商品分类Service业务层处理
 * 
 * @author demo
 * @date 2026-06-20
 */
@Service
public class MallCategoryServiceImpl implements IMallCategoryService {
    @Autowired
    private MallCategoryMapper mallCategoryMapper;

    /**
     * 查询商城商品分类列表
     *
     * @param mallCategory 商城商品分类
     * @return 商城商品分类
     */
    @Override
    public List<MallCategory> selectMallCategoryList(MallCategory mallCategory) {
        return mallCategoryMapper.selectMallCategoryList(mallCategory);
    }

    /**
     * 查询商城商品分类
     *
     * @param categoryId 商城商品分类主键
     * @return 商城商品分类
     */
    @Override
    public MallCategory selectMallCategoryById(Long categoryId) {
        return mallCategoryMapper.selectMallCategoryById(categoryId);
    }

    /**
     * 查询商城商品分类Result列表
     *
     * @param params 商城商品分类Params
     * @return 商城商品分类Result集合
     */
    @Override
    public List<MallCategoryResult> selectMallCategoryResultList(MallCategoryParams params) {
        return mallCategoryMapper.selectMallCategoryResultList(params);
    }

    /**
     * 获取商城商品分类详细信息
     *
     * @param categoryId 商城商品分类主键
     * @return 商城商品分类Result
     */
    @Override
    public MallCategoryResult selectMallCategoryResult(Long categoryId) {
        return mallCategoryMapper.selectMallCategoryResult(categoryId);
    }

    /**
     * 查询商城商品分类ApiResult列表（客户端）
     */
    @Override
    public List<MallCategoryApiResult> selectMallCategoryApiResultList(MallCategoryApiParams params) {
        return mallCategoryMapper.selectMallCategoryApiResultList(params);
    }

    /**
     * 获取商城商品分类ApiResult详细信息（客户端）
     */
    @Override
    public MallCategoryApiResult selectMallCategoryApiResult(Long categoryId) {
        return mallCategoryMapper.selectMallCategoryApiResult(categoryId);
    }

    /**
     * 新增商城商品分类
     *
     * @param mallCategory 商城商品分类
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertMallCategory(MallCategory mallCategory) {
        if (Objects.isNull(mallCategory.getCategoryId())) {
            mallCategory.setCategoryId(IdUtil.getSnowflakeNextId());
        }
        mallCategory.setCreateTime(DateUtils.getNowDate());
        return mallCategoryMapper.insertMallCategory(mallCategory);
    }

    /**
     * 修改商城商品分类
     *
     * @param mallCategory 商城商品分类
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateMallCategory(MallCategory mallCategory) {
        mallCategory.setUpdateTime(DateUtils.getNowDate());
        return mallCategoryMapper.updateMallCategory(mallCategory);
    }

    /**
     * 批量删除商城商品分类
     * 
     * @param categoryIdList 需要删除的商城商品分类主键集合
     * @return 结果
     */
    @Override
    public int deleteMallCategoryByIdList(List<Long> categoryIdList) {
        return mallCategoryMapper.deleteMallCategoryByIdList(categoryIdList);
    }

    /**
     * 删除商城商品分类信息
     * 
     * @param categoryId 商城商品分类主键
     * @return 结果
     */
    @Override
    public int deleteMallCategoryById(Long categoryId) {
        return mallCategoryMapper.deleteMallCategoryById(categoryId);
    }

    /**
     * 逻辑删除商城商品分类信息
     * 
     * @param categoryId 商城商品分类主键
     * @return 结果
     */
    @Override
    public int logicDeleteMallCategoryById(Long categoryId) {
        return mallCategoryMapper.logicDeleteMallCategoryById(categoryId);
    }

    /**
     * 批量逻辑删除商城商品分类信息
     * 
     * @param categoryIdList 商城商品分类主键集合
     * @return 结果
     */
    @Override
    public int logicDeleteMallCategoryByIdList(List<Long> categoryIdList) {
        return mallCategoryMapper.logicDeleteMallCategoryByIdList(categoryIdList);
    }

    /**
     * 查询商城商品分类数量
     *
     * @param params 商城商品分类Params
     * @return 数量
     */
    @Override
    public int selectMallCategoryCount(MallCategoryParams params) {
        return mallCategoryMapper.selectMallCategoryCount(params);
    }

    /**
     * 按条件查询单条商城商品分类
     *
     * @param params 商城商品分类Params
     * @return 商城商品分类
     */
    @Override
    public MallCategory selectMallCategoryOne(MallCategoryParams params) {
        return mallCategoryMapper.selectMallCategoryOne(params);
    }
}
