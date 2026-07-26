package com.demo.framework.web.service;

import com.demo.account.apiResult.AccountApiResult;
import com.demo.account.apiResult.AccountAuthApiResult;
import com.demo.account.domain.Account;
import com.demo.account.service.IAccountService;
import com.demo.common.core.domain.model.AccountLoginUser;
import com.demo.common.exception.ServiceException;
import com.demo.common.utils.DateUtils;
import com.demo.common.utils.SecurityUtils;
import com.demo.common.utils.StringUtils;
import com.demo.common.utils.ip.IpUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 客户端账号登录校验
 *
 * @author demo
 */
@Component
public class AccountLoginService {

    @Autowired
    private AccountTokenService accountTokenService;

    @Autowired
    private IAccountService accountService;

    public AccountAuthApiResult register(String mobile, String password, String nickName) {
        validateMobileAndPassword(mobile, password);
        if (accountService.existsByMobile(mobile)) {
            throw new ServiceException("手机号已注册");
        }
        Account account = Account.builder()
                .mobile(mobile)
                .password(SecurityUtils.encryptPassword(password))
                .nickName(StringUtils.isNotEmpty(nickName) ? nickName : "用户" + mobile.substring(mobile.length() - 4))
                .balance(BigDecimal.ZERO)
                .gender(2)
                .registerSource("app")
                .status(0)
                .delFlag(0)
                .build();
        accountService.insertAccount(account);
        String token = accountTokenService.createToken(buildLoginUser(account));
        return AccountAuthApiResult.builder()
                .accountId(account.getAccountId())
                .token(token)
                .nickName(account.getNickName())
                .build();
    }

    public AccountAuthApiResult login(String mobile, String password) {
        validateMobileAndPassword(mobile, password);
        Account account = accountService.selectAccountByMobile(mobile);
        if (account == null) {
            throw new ServiceException("手机号或密码错误");
        }
        if (account.getDelFlag() != null && account.getDelFlag() == 1) {
            throw new ServiceException("账号已注销，无法登录");
        }
        if (account.getStatus() != null && account.getStatus() == 1) {
            throw new ServiceException("账号已停用，无法登录");
        }
        if (!SecurityUtils.matchesPassword(password, account.getPassword())) {
            throw new ServiceException("手机号或密码错误");
        }
        accountService.updateLoginInfo(account.getAccountId(), IpUtils.getIpAddr(), DateUtils.getNowDate());
        String token = accountTokenService.createToken(buildLoginUser(account));
        return AccountAuthApiResult.builder()
                .accountId(account.getAccountId())
                .token(token)
                .nickName(account.getNickName())
                .build();
    }

    public void logout(HttpServletRequest request) {
        AccountLoginUser loginUser = accountTokenService.getAccountLoginUser(request);
        if (loginUser != null && StringUtils.isNotEmpty(loginUser.getToken())) {
            accountTokenService.delLoginUser(loginUser.getToken());
        }
    }

    public AccountApiResult getProfile(Long accountId) {
        AccountApiResult profile = accountService.selectAccountApiResult(accountId);
        if (profile == null) {
            throw new ServiceException("账号不存在");
        }
        profile.setPassword(null);
        return profile;
    }

    public void updateProfile(Long accountId, String nickName, Integer gender) {
        if (StringUtils.isEmpty(nickName)) {
            throw new ServiceException("昵称不能为空");
        }
        accountService.updateProfile(accountId, nickName, gender);
    }

    public void updatePassword(Long accountId, String oldPassword, String newPassword) {
        if (StringUtils.isEmpty(oldPassword) || StringUtils.isEmpty(newPassword)) {
            throw new ServiceException("密码不能为空");
        }
        accountService.updatePassword(accountId, oldPassword, newPassword);
    }

    private AccountLoginUser buildLoginUser(Account account) {
        return new AccountLoginUser(account.getAccountId(), account.getMobile(), account.getNickName(), account.getStatus());
    }

    private void validateMobileAndPassword(String mobile, String password) {
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new ServiceException("手机号和密码不能为空");
        }
    }
}
