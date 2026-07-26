package com.demo.account.service.impl;

import cn.hutool.core.util.IdUtil;
import com.demo.account.apiParams.AccountAddressApiParams;
import com.demo.account.apiParams.AccountAddressMySaveApiParams;
import com.demo.common.constant.HttpStatus;
import com.demo.common.exception.ServiceException;
import com.demo.common.utils.StringUtils;
import java.util.List;
import java.util.Objects;
import com.demo.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.account.mapper.AccountAddressMapper;
import com.demo.account.domain.AccountAddress;
import com.demo.account.params.AccountAddressParams;
import com.demo.account.apiParams.AccountAddressApiParams;
import com.demo.account.result.AccountAddressResult;
import com.demo.account.apiResult.AccountAddressApiResult;
import com.demo.account.service.IAccountAddressService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 账号收货地址Service业务层处理
 * 
 * @author demo
 * @date 2026-06-20
 */
@Service
public class AccountAddressServiceImpl implements IAccountAddressService {
    @Autowired
    private AccountAddressMapper accountAddressMapper;

    /**
     * 查询账号收货地址列表
     *
     * @param accountAddress 账号收货地址
     * @return 账号收货地址
     */
    @Override
    public List<AccountAddress> selectAccountAddressList(AccountAddress accountAddress) {
        return accountAddressMapper.selectAccountAddressList(accountAddress);
    }

    /**
     * 查询账号收货地址
     *
     * @param addressId 账号收货地址主键
     * @return 账号收货地址
     */
    @Override
    public AccountAddress selectAccountAddressById(Long addressId) {
        return accountAddressMapper.selectAccountAddressById(addressId);
    }

    /**
     * 查询账号收货地址Result列表
     *
     * @param params 账号收货地址Params
     * @return 账号收货地址Result集合
     */
    @Override
    public List<AccountAddressResult> selectAccountAddressResultList(AccountAddressParams params) {
        return accountAddressMapper.selectAccountAddressResultList(params);
    }

    /**
     * 获取账号收货地址详细信息
     *
     * @param addressId 账号收货地址主键
     * @return 账号收货地址Result
     */
    @Override
    public AccountAddressResult selectAccountAddressResult(Long addressId) {
        return accountAddressMapper.selectAccountAddressResult(addressId);
    }

    /**
     * 查询账号收货地址ApiResult列表（客户端）
     */
    @Override
    public List<AccountAddressApiResult> selectAccountAddressApiResultList(AccountAddressApiParams params) {
        return accountAddressMapper.selectAccountAddressApiResultList(params);
    }

    /**
     * 获取收货地址详情（客户端）；校验地址归属当前账号
     */
    @Override
    public AccountAddressApiResult selectAccountAddressApiResult(Long addressId, Long accountId) {
        requireOwnedAddress(accountId, addressId);
        return accountAddressMapper.selectAccountAddressApiResult(addressId);
    }

    /**
     * 新增收货地址（客户端）；首条地址自动设为默认
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public AccountAddressApiResult saveAccountAddressForAccount(Long accountId, AccountAddressMySaveApiParams params) {
        validateSaveParams(params, false);
        AccountAddress address = buildAddress(accountId, params);
        address.setAddressId(IdUtil.getSnowflakeNextId());
        address.setDelFlag(0);
        address.setCreateTime(DateUtils.getNowDate());
        if (address.getIsDefault() != null && address.getIsDefault() == 1) {
            accountAddressMapper.clearDefaultByAccountId(accountId, null);
        } else {
            AccountAddressApiParams query = new AccountAddressApiParams();
            query.setAccountId(accountId);
            if (accountAddressMapper.selectAccountAddressApiResultList(query).isEmpty()) {
                address.setIsDefault(1);
            } else if (address.getIsDefault() == null) {
                address.setIsDefault(0);
            }
        }
        accountAddressMapper.insertAccountAddress(address);
        return accountAddressMapper.selectAccountAddressApiResult(address.getAddressId());
    }

    /**
     * 修改收货地址（客户端）
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateAccountAddressForAccount(Long accountId, AccountAddressMySaveApiParams params) {
        validateSaveParams(params, true);
        requireOwnedAddress(accountId, params.getAddressId());
        if (params.getIsDefault() != null && params.getIsDefault() == 1) {
            accountAddressMapper.clearDefaultByAccountId(accountId, params.getAddressId());
        }
        AccountAddress update = buildAddress(accountId, params);
        update.setAddressId(params.getAddressId());
        update.setUpdateTime(DateUtils.getNowDate());
        accountAddressMapper.updateAccountAddress(update);
    }

    /**
     * 删除收货地址（客户端）；逻辑删除
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteAccountAddressForAccount(Long accountId, Long addressId) {
        requireOwnedAddress(accountId, addressId);
        accountAddressMapper.logicDeleteAccountAddressById(addressId);
    }

    /**
     * 设为默认收货地址（客户端）
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void setDefaultAccountAddress(Long accountId, Long addressId) {
        requireOwnedAddress(accountId, addressId);
        accountAddressMapper.clearDefaultByAccountId(accountId, addressId);
        AccountAddress update = new AccountAddress();
        update.setAddressId(addressId);
        update.setIsDefault(1);
        update.setUpdateTime(DateUtils.getNowDate());
        accountAddressMapper.updateAccountAddress(update);
    }

    /**
     * 校验地址存在且归属当前账号
     */
    private AccountAddress requireOwnedAddress(Long accountId, Long addressId) {
        if (addressId == null) {
            throw new ServiceException("地址ID不能为空");
        }
        AccountAddress address = accountAddressMapper.selectAccountAddressById(addressId);
        if (address == null || (address.getDelFlag() != null && address.getDelFlag() == 1)) {
            throw new ServiceException("地址不存在");
        }
        if (!accountId.equals(address.getAccountId())) {
            throw new ServiceException("无权访问该地址", HttpStatus.FORBIDDEN);
        }
        return address;
    }

    /**
     * 校验保存参数必填项
     *
     * @param requireAddressId 修改场景要求 addressId 非空
     */
    private void validateSaveParams(AccountAddressMySaveApiParams params, boolean requireAddressId) {
        if (params == null) {
            throw new ServiceException("参数不能为空");
        }
        if (requireAddressId && params.getAddressId() == null) {
            throw new ServiceException("地址ID不能为空");
        }
        if (StringUtils.isEmpty(params.getReceiverName())) {
            throw new ServiceException("收货人姓名不能为空");
        }
        if (StringUtils.isEmpty(params.getReceiverPhone())) {
            throw new ServiceException("收货人手机不能为空");
        }
        if (StringUtils.isEmpty(params.getDetailAddress())) {
            throw new ServiceException("详细地址不能为空");
        }
    }

    /**
     * 将客户端保存参数转换为地址实体
     */
    private AccountAddress buildAddress(Long accountId, AccountAddressMySaveApiParams params) {
        AccountAddress address = new AccountAddress();
        address.setAccountId(accountId);
        address.setReceiverName(params.getReceiverName());
        address.setReceiverPhone(params.getReceiverPhone());
        address.setProvince(params.getProvince());
        address.setCity(params.getCity());
        address.setDistrict(params.getDistrict());
        address.setDetailAddress(params.getDetailAddress());
        address.setPostalCode(params.getPostalCode());
        address.setIsDefault(params.getIsDefault());
        return address;
    }

    /**
     * 新增账号收货地址
     *
     * @param accountAddress 账号收货地址
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertAccountAddress(AccountAddress accountAddress) {
        if (Objects.isNull(accountAddress.getAddressId())) {
            accountAddress.setAddressId(IdUtil.getSnowflakeNextId());
        }
        accountAddress.setCreateTime(DateUtils.getNowDate());
        return accountAddressMapper.insertAccountAddress(accountAddress);
    }

    /**
     * 修改账号收货地址
     *
     * @param accountAddress 账号收货地址
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateAccountAddress(AccountAddress accountAddress) {
        accountAddress.setUpdateTime(DateUtils.getNowDate());
        return accountAddressMapper.updateAccountAddress(accountAddress);
    }

    /**
     * 批量删除账号收货地址
     * 
     * @param addressIdList 需要删除的账号收货地址主键集合
     * @return 结果
     */
    @Override
    public int deleteAccountAddressByIdList(List<Long> addressIdList) {
        return accountAddressMapper.deleteAccountAddressByIdList(addressIdList);
    }

    /**
     * 删除账号收货地址信息
     * 
     * @param addressId 账号收货地址主键
     * @return 结果
     */
    @Override
    public int deleteAccountAddressById(Long addressId) {
        return accountAddressMapper.deleteAccountAddressById(addressId);
    }

    /**
     * 逻辑删除账号收货地址信息
     * 
     * @param addressId 账号收货地址主键
     * @return 结果
     */
    @Override
    public int logicDeleteAccountAddressById(Long addressId) {
        return accountAddressMapper.logicDeleteAccountAddressById(addressId);
    }

    /**
     * 批量逻辑删除账号收货地址信息
     * 
     * @param addressIdList 账号收货地址主键集合
     * @return 结果
     */
    @Override
    public int logicDeleteAccountAddressByIdList(List<Long> addressIdList) {
        return accountAddressMapper.logicDeleteAccountAddressByIdList(addressIdList);
    }

    /**
     * 查询账号收货地址数量
     *
     * @param params 账号收货地址Params
     * @return 数量
     */
    @Override
    public int selectAccountAddressCount(AccountAddressParams params) {
        return accountAddressMapper.selectAccountAddressCount(params);
    }

    /**
     * 按条件查询单条账号收货地址
     *
     * @param params 账号收货地址Params
     * @return 账号收货地址
     */
    @Override
    public AccountAddress selectAccountAddressOne(AccountAddressParams params) {
        return accountAddressMapper.selectAccountAddressOne(params);
    }
}
