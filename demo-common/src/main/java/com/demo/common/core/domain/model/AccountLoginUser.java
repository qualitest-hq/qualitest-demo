package com.demo.common.core.domain.model;

import com.alibaba.fastjson2.annotation.JSONField;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * 客户端账号登录身份，存入 SecurityContext 供 /api/** 接口鉴权使用。
 *
 * @author demo
 */
public class AccountLoginUser implements UserDetails {

    private static final long serialVersionUID = 1L;

    /**
     * 账号主键
     */
    private Long accountId;

    /**
     * 登录手机号，同时作为 {@link #getUsername()} 返回值
     */
    private String mobile;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 账号状态（0正常 1停用）
     */
    private Integer status;

    /**
     * 会话 token 唯一标识，对应 Redis 中的登录缓存键
     */
    private String token;

    /**
     * 登录时间戳（毫秒）
     */
    private Long loginTime;

    /**
     * 会话过期时间戳（毫秒）
     */
    private Long expireTime;

    /**
     * 登录 IP
     */
    private String ipaddr;

    /**
     * 登录地点
     */
    private String loginLocation;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    public AccountLoginUser() {
    }

    public AccountLoginUser(Long accountId, String mobile, String nickName, Integer status) {
        this.accountId = accountId;
        this.mobile = mobile;
        this.nickName = nickName;
        this.status = status;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Long loginTime) {
        this.loginTime = loginTime;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public String getIpaddr() {
        return ipaddr;
    }

    public void setIpaddr(String ipaddr) {
        this.ipaddr = ipaddr;
    }

    public String getLoginLocation() {
        return loginLocation;
    }

    public void setLoginLocation(String loginLocation) {
        this.loginLocation = loginLocation;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    @JSONField(serialize = false)
    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return mobile;
    }

    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonLocked() {
        return status == null || status == 0;
    }

    @JSONField(serialize = false)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JSONField(serialize = false)
    @Override
    public boolean isEnabled() {
        return status == null || status == 0;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }
}
