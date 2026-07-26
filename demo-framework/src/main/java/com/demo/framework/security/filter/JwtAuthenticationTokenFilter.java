package com.demo.framework.security.filter;

import com.demo.common.constant.Constants;
import com.demo.common.core.domain.model.AccountLoginUser;
import com.demo.common.core.domain.model.LoginUser;
import com.demo.common.utils.StringUtils;
import com.demo.framework.web.service.AccountTokenService;
import com.demo.framework.web.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * token过滤器 验证token有效性（管理端 / 客户端按路径分流）
 *
 * @author qualitest
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AccountTokenService accountTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        Authentication existingAuth = SecurityContextHolder.getContext().getAuthentication();
        if (existingAuth != null && existingAuth.isAuthenticated()
                && (existingAuth.getPrincipal() instanceof LoginUser
                || existingAuth.getPrincipal() instanceof AccountLoginUser)) {
            chain.doFilter(request, response);
            return;
        }
        String uri = request.getRequestURI();
        String loginType = tokenService.resolveLoginType(request);
        if (uri.startsWith("/api")) {
            authenticateAccount(request, loginType);
        } else {
            authenticateAdmin(request, loginType);
        }
        chain.doFilter(request, response);
    }

    private void authenticateAccount(HttpServletRequest request, String loginType) {
        if (!Constants.LOGIN_TYPE_ACCOUNT.equals(loginType)) {
            return;
        }
        AccountLoginUser loginUser = accountTokenService.getAccountLoginUser(request);
        if (StringUtils.isNotNull(loginUser)) {
            accountTokenService.verifyToken(loginUser);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
    }

    private void authenticateAdmin(HttpServletRequest request, String loginType) {
        if (Constants.LOGIN_TYPE_ACCOUNT.equals(loginType)) {
            return;
        }
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginUser)) {
            tokenService.verifyToken(loginUser);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
    }
}
