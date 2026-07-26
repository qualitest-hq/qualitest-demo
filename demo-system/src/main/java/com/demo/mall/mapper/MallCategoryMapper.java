package com.demo.mall.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.demo.mall.domain.MallCategory;
import com.demo.mall.params.MallCategoryParams;
import com.demo.mall.apiParams.MallCategoryApiParams;
import com.demo.mall.result.MallCategoryResult;
import com.demo.mall.apiResult.MallCategoryApiResult;

/**
 * 商城商品分类Mapper接口
 * 
 * @author demo
 * @date 2026-06-20
 */
@Mapper
public interface MallCategoryMapper {
    /**
     * 查询商城商品分类列表
     *
     * @param mallCategory 商城商品分类
     * @return 商城商品分类集合
     */
    List<MallCategory> selectMallCategoryList(MallCategory mallCategory);

    /**
     * 查询商城商品分类
     *
     * @param categoryId 商城商品分类主键
     * @return 商城商品分类
     */
    MallCategory selectMallCategoryById(Long categoryId);

    /**
     * 查询商城商品分类Result列表
     *
     * @param params 商城商品分类Params
     * @return 商城商品分类Result集合
     */
    List<MallCategoryResult> selectMallCategoryResultList(MallCategoryParams params);

    /**
     * 获取商城商品分类详细信息
     *
     * @param categoryId 商城商品分类主键
     * @return 商城商品分类Result
     */
    MallCategoryResult selectMallCategoryResult(Long categoryId);

    /**
     * 查询商城商品分类ApiResult列表（客户端）
     *
     * @param params 商城商品分类ApiParams
     * @return 商城商品分类ApiResult集合
     */
    List<MallCategoryApiResult> selectMallCategoryApiResultList(MallCategoryApiParams params);

    /**
     * 获取商城商品分类ApiResult详细信息（客户端）
     *
     * @param categoryId 商城商品分类主键
     * @return 商城商品分类ApiResult
     */
    MallCategoryApiResult selectMallCategoryApiResult(Long categoryId);

    /**
     * 查询商城商品分类数量
     *
     * @param params 商城商品分类Params
     * @return 数量
     */
    int selectMallCategoryCount(MallCategoryParams params);

    /**
     * 按条件查询单条商城商品分类
     *
     * @param params 商城商品分类Params
     * @return 商城商品分类
     */
    MallCategory selectMallCategoryOne(MallCategoryParams params);

    /**
     * 新增商城商品分类
     * 
     * @param mallCategory 商城商品分类
     * @return 结果
     */
    int insertMallCategory(MallCategory mallCategory);

    /**
     * 修改商城商品分类
     * 
     * @param mallCategory 商城商品分类
     * @return 结果
     */
    int updateMallCategory(MallCategory mallCategory);

    /**
     * 删除商城商品分类
     * 
     * @param categoryId 商城商品分类主键
     * @return 结果
     */
    int deleteMallCategoryById(Long categoryId);

    /**
     * 批量删除商城商品分类
     * 
     * @param categoryIdList 需要删除的数据主键集合
     * @return 结果
     */
    int deleteMallCategoryByIdList(@Param("list") List<Long> categoryIdList);

    /**
     * 逻辑删除商城商品分类
     * 
     * @param categoryId 商城商品分类主键
     * @return 结果
     */
    int logicDeleteMallCategoryById(Long categoryId);

    /**
     * 批量逻辑删除商城商品分类
     * 
     * @param categoryIdList 商城商品分类主键集合
     * @return 结果
     */
    int logicDeleteMallCategoryByIdList(@Param("list") List<Long> categoryIdList);
}
