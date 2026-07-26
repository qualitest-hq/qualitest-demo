package com.demo.common.utils;

import com.demo.common.constant.HttpStatus;
import com.demo.common.core.domain.model.AccountLoginUser;
import com.demo.common.exception.ServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 客户端账号安全工具类，从 SecurityContext 读取 {@link AccountLoginUser}。
 *
 * @author demo
 */
public class AccountSecurityUtils {

    /**
     * 获取当前登录账号 ID
     *
     * @return 账号主键
     * @throws ServiceException 未登录或身份类型不匹配时抛出 401
     */
    public static Long getAccountId() {
        return getAccountLoginUser().getAccountId();
    }

    /**
     * 获取当前登录账号完整信息
     *
     * @return 客户端登录身份
     * @throws ServiceException 未登录或身份类型不匹配时抛出 401
     */
    public static AccountLoginUser getAccountLoginUser() {
        try {
            Authentication authentication = getAuthentication();
            if (authentication != null && authentication.getPrincipal() instanceof AccountLoginUser accountLoginUser) {
                return accountLoginUser;
            }
            throw new ServiceException("获取账号信息异常", HttpStatus.UNAUTHORIZED);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("获取账号信息异常", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 获取当前 SecurityContext 中的认证对象
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
