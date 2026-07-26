package com.demo.mall.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.demo.common.utils.SecurityUtils;
import java.util.List;
import java.util.Objects;
import com.demo.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.mall.mapper.MallOrderItemMapper;
import com.demo.mall.domain.MallOrderItem;
import com.demo.mall.params.MallOrderItemParams;
import com.demo.mall.apiParams.MallOrderItemApiParams;
import com.demo.mall.result.MallOrderItemResult;
import com.demo.mall.apiResult.MallOrderItemApiResult;
import com.demo.mall.service.IMallOrderItemService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 商城订单明细Service业务层处理
 * 
 * @author demo
 * @date 2026-06-20
 */
@Service
public class MallOrderItemServiceImpl implements IMallOrderItemService {
    @Autowired
    private MallOrderItemMapper mallOrderItemMapper;

    /**
     * 查询商城订单明细列表
     *
     * @param mallOrderItem 商城订单明细
     * @return 商城订单明细
     */
    @Override
    public List<MallOrderItem> selectMallOrderItemList(MallOrderItem mallOrderItem) {
        return mallOrderItemMapper.selectMallOrderItemList(mallOrderItem);
    }

    /**
     * 查询商城订单明细
     *
     * @param orderItemId 商城订单明细主键
     * @return 商城订单明细
     */
    @Override
    public MallOrderItem selectMallOrderItemById(Long orderItemId) {
        return mallOrderItemMapper.selectMallOrderItemById(orderItemId);
    }

    /**
     * 查询商城订单明细Result列表
     *
     * @param params 商城订单明细Params
     * @return 商城订单明细Result集合
     */
    @Override
    public List<MallOrderItemResult> selectMallOrderItemResultList(MallOrderItemParams params) {
        return mallOrderItemMapper.selectMallOrderItemResultList(params);
    }

    /**
     * 获取商城订单明细详细信息
     *
     * @param orderItemId 商城订单明细主键
     * @return 商城订单明细Result
     */
    @Override
    public MallOrderItemResult selectMallOrderItemResult(Long orderItemId) {
        return mallOrderItemMapper.selectMallOrderItemResult(orderItemId);
    }

    /**
     * 查询商城订单明细ApiResult列表（客户端）
     */
    @Override
    public List<MallOrderItemApiResult> selectMallOrderItemApiResultList(MallOrderItemApiParams params) {
        return mallOrderItemMapper.selectMallOrderItemApiResultList(params);
    }

    /**
     * 获取商城订单明细ApiResult详细信息（客户端）
     */
    @Override
    public MallOrderItemApiResult selectMallOrderItemApiResult(Long orderItemId) {
        return mallOrderItemMapper.selectMallOrderItemApiResult(orderItemId);
    }

    /**
     * 新增商城订单明细
     *
     * @param mallOrderItem 商城订单明细
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertMallOrderItem(MallOrderItem mallOrderItem) {
        if (Objects.isNull(mallOrderItem.getOrderItemId())) {
            mallOrderItem.setOrderItemId(IdUtil.getSnowflakeNextId());
        }
        mallOrderItem.setCreateTime(DateUtils.getNowDate());
        return mallOrderItemMapper.insertMallOrderItem(mallOrderItem);
    }

    /**
     * 修改商城订单明细
     *
     * @param mallOrderItem 商城订单明细
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateMallOrderItem(MallOrderItem mallOrderItem) {
        mallOrderItem.setUpdateTime(DateUtils.getNowDate());
        return mallOrderItemMapper.updateMallOrderItem(mallOrderItem);
    }

    /**
     * 批量删除商城订单明细
     * 
     * @param orderItemIdList 需要删除的商城订单明细主键集合
     * @return 结果
     */
    @Override
    public int deleteMallOrderItemByIdList(List<Long> orderItemIdList) {
        return mallOrderItemMapper.deleteMallOrderItemByIdList(orderItemIdList);
    }

    /**
     * 删除商城订单明细信息
     * 
     * @param orderItemId 商城订单明细主键
     * @return 结果
     */
    @Override
    public int deleteMallOrderItemById(Long orderItemId) {
        return mallOrderItemMapper.deleteMallOrderItemById(orderItemId);
    }

    /**
     * 逻辑删除商城订单明细信息
     * 
     * @param orderItemId 商城订单明细主键
     * @return 结果
     */
    @Override
    public int logicDeleteMallOrderItemById(Long orderItemId) {
        return mallOrderItemMapper.logicDeleteMallOrderItemById(orderItemId);
    }

    /**
     * 批量逻辑删除商城订单明细信息
     * 
     * @param orderItemIdList 商城订单明细主键集合
     * @return 结果
     */
    @Override
    public int logicDeleteMallOrderItemByIdList(List<Long> orderItemIdList) {
        return mallOrderItemMapper.logicDeleteMallOrderItemByIdList(orderItemIdList);
    }

    /**
     * 查询商城订单明细数量
     *
     * @param params 商城订单明细Params
     * @return 数量
     */
    @Override
    public int selectMallOrderItemCount(MallOrderItemParams params) {
        return mallOrderItemMapper.selectMallOrderItemCount(params);
    }

    /**
     * 按条件查询单条商城订单明细
     *
     * @param params 商城订单明细Params
     * @return 商城订单明细
     */
    @Override
    public MallOrderItem selectMallOrderItemOne(MallOrderItemParams params) {
        return mallOrderItemMapper.selectMallOrderItemOne(params);
    }
}
