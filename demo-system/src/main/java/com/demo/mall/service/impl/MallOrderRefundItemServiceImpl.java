package com.demo.mall.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.demo.common.utils.SecurityUtils;
import java.util.List;
import java.util.Objects;
import com.demo.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.mall.mapper.MallOrderRefundItemMapper;
import com.demo.mall.domain.MallOrderRefundItem;
import com.demo.mall.params.MallOrderRefundItemParams;
import com.demo.mall.apiParams.MallOrderRefundItemApiParams;
import com.demo.mall.result.MallOrderRefundItemResult;
import com.demo.mall.apiResult.MallOrderRefundItemApiResult;
import com.demo.mall.service.IMallOrderRefundItemService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 商城退款明细Service业务层处理
 * 
 * @author demo
 * @date 2026-06-20
 */
@Service
public class MallOrderRefundItemServiceImpl implements IMallOrderRefundItemService {
    @Autowired
    private MallOrderRefundItemMapper mallOrderRefundItemMapper;

    /**
     * 查询商城退款明细列表
     *
     * @param mallOrderRefundItem 商城退款明细
     * @return 商城退款明细
     */
    @Override
    public List<MallOrderRefundItem> selectMallOrderRefundItemList(MallOrderRefundItem mallOrderRefundItem) {
        return mallOrderRefundItemMapper.selectMallOrderRefundItemList(mallOrderRefundItem);
    }

    /**
     * 查询商城退款明细
     *
     * @param refundItemId 商城退款明细主键
     * @return 商城退款明细
     */
    @Override
    public MallOrderRefundItem selectMallOrderRefundItemById(Long refundItemId) {
        return mallOrderRefundItemMapper.selectMallOrderRefundItemById(refundItemId);
    }

    /**
     * 查询商城退款明细Result列表
     *
     * @param params 商城退款明细Params
     * @return 商城退款明细Result集合
     */
    @Override
    public List<MallOrderRefundItemResult> selectMallOrderRefundItemResultList(MallOrderRefundItemParams params) {
        return mallOrderRefundItemMapper.selectMallOrderRefundItemResultList(params);
    }

    /**
     * 获取商城退款明细详细信息
     *
     * @param refundItemId 商城退款明细主键
     * @return 商城退款明细Result
     */
    @Override
    public MallOrderRefundItemResult selectMallOrderRefundItemResult(Long refundItemId) {
        return mallOrderRefundItemMapper.selectMallOrderRefundItemResult(refundItemId);
    }

    /**
     * 查询商城退款明细ApiResult列表（客户端）
     */
    @Override
    public List<MallOrderRefundItemApiResult> selectMallOrderRefundItemApiResultList(MallOrderRefundItemApiParams params) {
        return mallOrderRefundItemMapper.selectMallOrderRefundItemApiResultList(params);
    }

    /**
     * 获取商城退款明细ApiResult详细信息（客户端）
     */
    @Override
    public MallOrderRefundItemApiResult selectMallOrderRefundItemApiResult(Long refundItemId) {
        return mallOrderRefundItemMapper.selectMallOrderRefundItemApiResult(refundItemId);
    }

    /**
     * 新增商城退款明细
     *
     * @param mallOrderRefundItem 商城退款明细
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertMallOrderRefundItem(MallOrderRefundItem mallOrderRefundItem) {
        if (Objects.isNull(mallOrderRefundItem.getRefundItemId())) {
            mallOrderRefundItem.setRefundItemId(IdUtil.getSnowflakeNextId());
        }
        mallOrderRefundItem.setCreateTime(DateUtils.getNowDate());
        return mallOrderRefundItemMapper.insertMallOrderRefundItem(mallOrderRefundItem);
    }

    /**
     * 修改商城退款明细
     *
     * @param mallOrderRefundItem 商城退款明细
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateMallOrderRefundItem(MallOrderRefundItem mallOrderRefundItem) {
        mallOrderRefundItem.setUpdateTime(DateUtils.getNowDate());
        return mallOrderRefundItemMapper.updateMallOrderRefundItem(mallOrderRefundItem);
    }

    /**
     * 批量删除商城退款明细
     * 
     * @param refundItemIdList 需要删除的商城退款明细主键集合
     * @return 结果
     */
    @Override
    public int deleteMallOrderRefundItemByIdList(List<Long> refundItemIdList) {
        return mallOrderRefundItemMapper.deleteMallOrderRefundItemByIdList(refundItemIdList);
    }

    /**
     * 删除商城退款明细信息
     * 
     * @param refundItemId 商城退款明细主键
     * @return 结果
     */
    @Override
    public int deleteMallOrderRefundItemById(Long refundItemId) {
        return mallOrderRefundItemMapper.deleteMallOrderRefundItemById(refundItemId);
    }

    /**
     * 逻辑删除商城退款明细信息
     * 
     * @param refundItemId 商城退款明细主键
     * @return 结果
     */
    @Override
    public int logicDeleteMallOrderRefundItemById(Long refundItemId) {
        return mallOrderRefundItemMapper.logicDeleteMallOrderRefundItemById(refundItemId);
    }

    /**
     * 批量逻辑删除商城退款明细信息
     * 
     * @param refundItemIdList 商城退款明细主键集合
     * @return 结果
     */
    @Override
    public int logicDeleteMallOrderRefundItemByIdList(List<Long> refundItemIdList) {
        return mallOrderRefundItemMapper.logicDeleteMallOrderRefundItemByIdList(refundItemIdList);
    }

    /**
     * 查询商城退款明细数量
     *
     * @param params 商城退款明细Params
     * @return 数量
     */
    @Override
    public int selectMallOrderRefundItemCount(MallOrderRefundItemParams params) {
        return mallOrderRefundItemMapper.selectMallOrderRefundItemCount(params);
    }

    /**
     * 按条件查询单条商城退款明细
     *
     * @param params 商城退款明细Params
     * @return 商城退款明细
     */
    @Override
    public MallOrderRefundItem selectMallOrderRefundItemOne(MallOrderRefundItemParams params) {
        return mallOrderRefundItemMapper.selectMallOrderRefundItemOne(params);
    }
}
