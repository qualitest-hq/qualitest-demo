package com.demo.mall.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.demo.common.constant.HttpStatus;
import com.demo.common.exception.ServiceException;
import com.demo.common.utils.SecurityUtils;
import java.util.List;
import java.util.Objects;
import com.demo.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.mall.mapper.MallCartMapper;
import com.demo.mall.domain.MallCart;
import com.demo.mall.domain.MallProduct;
import com.demo.mall.domain.MallProductSku;
import com.demo.mall.params.MallCartParams;
import com.demo.mall.apiParams.MallCartAddApiParams;
import com.demo.mall.apiParams.MallCartApiParams;
import com.demo.mall.apiParams.MallCartQuantityApiParams;
import com.demo.mall.result.MallCartResult;
import com.demo.mall.apiResult.MallCartApiResult;
import com.demo.mall.service.IMallCartService;
import com.demo.mall.service.IMallProductService;
import com.demo.mall.service.IMallProductSkuService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 商城购物车Service业务层处理
 * 
 * @author demo
 * @date 2026-06-20
 */
@Service
public class MallCartServiceImpl implements IMallCartService {
    @Autowired
    private MallCartMapper mallCartMapper;

    @Autowired
    private IMallProductSkuService mallProductSkuService;

    @Autowired
    private IMallProductService mallProductService;

    /**
     * 查询商城购物车列表
     *
     * @param mallCart 商城购物车
     * @return 商城购物车
     */
    @Override
    public List<MallCart> selectMallCartList(MallCart mallCart) {
        return mallCartMapper.selectMallCartList(mallCart);
    }

    /**
     * 查询商城购物车
     *
     * @param cartId 商城购物车主键
     * @return 商城购物车
     */
    @Override
    public MallCart selectMallCartById(Long cartId) {
        return mallCartMapper.selectMallCartById(cartId);
    }

    /**
     * 查询商城购物车Result列表
     *
     * @param params 商城购物车Params
     * @return 商城购物车Result集合
     */
    @Override
    public List<MallCartResult> selectMallCartResultList(MallCartParams params) {
        return mallCartMapper.selectMallCartResultList(params);
    }

    /**
     * 获取商城购物车详细信息
     *
     * @param cartId 商城购物车主键
     * @return 商城购物车Result
     */
    @Override
    public MallCartResult selectMallCartResult(Long cartId) {
        return mallCartMapper.selectMallCartResult(cartId);
    }

    /**
     * 查询商城购物车ApiResult列表（客户端）
     */
    @Override
    public List<MallCartApiResult> selectMallCartApiResultList(MallCartApiParams params) {
        return mallCartMapper.selectMallCartApiResultList(params);
    }

    /**
     * 获取商城购物车ApiResult详细信息（客户端）
     */
    @Override
    public MallCartApiResult selectMallCartApiResult(Long cartId) {
        return mallCartMapper.selectMallCartApiResult(cartId);
    }

    /**
     * 新增商城购物车
     *
     * @param mallCart 商城购物车
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertMallCart(MallCart mallCart) {
        if (Objects.isNull(mallCart.getCartId())) {
            mallCart.setCartId(IdUtil.getSnowflakeNextId());
        }
        mallCart.setCreateTime(DateUtils.getNowDate());
        return mallCartMapper.insertMallCart(mallCart);
    }

    /**
     * 修改商城购物车
     *
     * @param mallCart 商城购物车
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateMallCart(MallCart mallCart) {
        mallCart.setUpdateTime(DateUtils.getNowDate());
        return mallCartMapper.updateMallCart(mallCart);
    }

    /**
     * 批量删除商城购物车
     * 
     * @param cartIdList 需要删除的商城购物车主键集合
     * @return 结果
     */
    @Override
    public int deleteMallCartByIdList(List<Long> cartIdList) {
        return mallCartMapper.deleteMallCartByIdList(cartIdList);
    }

    /**
     * 删除商城购物车信息
     * 
     * @param cartId 商城购物车主键
     * @return 结果
     */
    @Override
    public int deleteMallCartById(Long cartId) {
        return mallCartMapper.deleteMallCartById(cartId);
    }

    /**
     * 逻辑删除商城购物车信息
     * 
     * @param cartId 商城购物车主键
     * @return 结果
     */
    @Override
    public int logicDeleteMallCartById(Long cartId) {
        return mallCartMapper.logicDeleteMallCartById(cartId);
    }

    /**
     * 批量逻辑删除商城购物车信息
     * 
     * @param cartIdList 商城购物车主键集合
     * @return 结果
     */
    @Override
    public int logicDeleteMallCartByIdList(List<Long> cartIdList) {
        return mallCartMapper.logicDeleteMallCartByIdList(cartIdList);
    }

    /**
     * 查询商城购物车数量
     *
     * @param params 商城购物车Params
     * @return 数量
     */
    @Override
    public int selectMallCartCount(MallCartParams params) {
        return mallCartMapper.selectMallCartCount(params);
    }

    /**
     * 按条件查询单条商城购物车
     *
     * @param params 商城购物车Params
     * @return 商城购物车
     */
    @Override
    public MallCart selectMallCartOne(MallCartParams params) {
        return mallCartMapper.selectMallCartOne(params);
    }

    /**
     * 加入购物车；校验 SKU 可售与库存，同 SKU 累加数量
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addMallCartItem(Long accountId, MallCartAddApiParams params) {
        if (params == null || params.getSkuId() == null) {
            throw new ServiceException("SKU ID不能为空");
        }
        int quantity = params.getQuantity() == null ? 1 : params.getQuantity();
        if (quantity <= 0) {
            throw new ServiceException("数量须大于0");
        }
        MallProductSku sku = validateSkuForCart(params.getSkuId(), quantity);
        MallCartParams query = new MallCartParams();
        query.setAccountId(accountId);
        query.setSkuId(params.getSkuId());
        MallCart existing = mallCartMapper.selectMallCartOne(query);
        if (existing != null) {
            int newQty = existing.getQuantity() + quantity;
            validateSkuForCart(params.getSkuId(), newQty);
            MallCart update = new MallCart();
            update.setCartId(existing.getCartId());
            update.setQuantity(newQty);
            update.setUpdateTime(DateUtils.getNowDate());
            mallCartMapper.updateMallCart(update);
            return;
        }
        MallCart cart = new MallCart();
        cart.setCartId(IdUtil.getSnowflakeNextId());
        cart.setAccountId(accountId);
        cart.setProductId(sku.getProductId());
        cart.setSkuId(sku.getSkuId());
        cart.setQuantity(quantity);
        cart.setDelFlag(0);
        cart.setCreateTime(DateUtils.getNowDate());
        mallCartMapper.insertMallCart(cart);
    }

    /**
     * 修改购物车数量；校验归属与库存
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateMallCartQuantity(Long accountId, MallCartQuantityApiParams params) {
        if (params == null || params.getCartId() == null) {
            throw new ServiceException("购物车ID不能为空");
        }
        if (params.getQuantity() == null || params.getQuantity() <= 0) {
            throw new ServiceException("数量须大于0");
        }
        MallCart cart = requireOwnedCart(accountId, params.getCartId());
        validateSkuForCart(cart.getSkuId(), params.getQuantity());
        MallCart update = new MallCart();
        update.setCartId(cart.getCartId());
        update.setQuantity(params.getQuantity());
        update.setUpdateTime(DateUtils.getNowDate());
        mallCartMapper.updateMallCart(update);
    }

    /**
     * 删除购物车项；逻辑删除
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteMallCartItem(Long accountId, Long cartId) {
        requireOwnedCart(accountId, cartId);
        mallCartMapper.logicDeleteMallCartById(cartId);
    }

    /**
     * 清空当前账号全部购物车项
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void clearMallCartByAccountId(Long accountId) {
        mallCartMapper.logicDeleteByAccountId(accountId);
    }

    /**
     * 校验购物车项存在且归属当前账号
     */
    private MallCart requireOwnedCart(Long accountId, Long cartId) {
        MallCart cart = mallCartMapper.selectMallCartById(cartId);
        if (cart == null || (cart.getDelFlag() != null && cart.getDelFlag() == 1)) {
            throw new ServiceException("购物车项不存在");
        }
        if (!accountId.equals(cart.getAccountId())) {
            throw new ServiceException("无权操作该购物车项", HttpStatus.FORBIDDEN);
        }
        return cart;
    }

    /**
     * 校验 SKU 可售、商品上架且库存充足
     */
    private MallProductSku validateSkuForCart(Long skuId, int quantity) {
        MallProductSku sku = mallProductSkuService.selectMallProductSkuById(skuId);
        if (sku == null || (sku.getDelFlag() != null && sku.getDelFlag() == 1)) {
            throw new ServiceException("SKU不存在");
        }
        if (sku.getStatus() != null && sku.getStatus() != 0) {
            throw new ServiceException("SKU已停用");
        }
        if (sku.getStock() == null || sku.getStock() < quantity) {
            throw new ServiceException("库存不足");
        }
        MallProduct product = mallProductService.selectMallProductById(sku.getProductId());
        if (product == null || (product.getDelFlag() != null && product.getDelFlag() == 1)) {
            throw new ServiceException("商品不存在");
        }
        if (product.getShelfStatus() == null || product.getShelfStatus() != 1) {
            throw new ServiceException("商品已下架");
        }
        return sku;
    }
}
