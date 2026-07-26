package com.demo.framework.web.service;

import com.demo.common.constant.CacheConstants;
import com.demo.common.constant.Constants;
import com.demo.common.core.domain.model.AccountLoginUser;
import com.demo.common.core.redis.RedisCache;
import com.demo.common.utils.ServletUtils;
import com.demo.common.utils.StringUtils;
import com.demo.common.utils.http.UserAgentUtils;
import com.demo.common.utils.ip.AddressUtils;
import com.demo.common.utils.ip.IpUtils;
import com.demo.common.utils.uuid.IdUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 客户端账号 token 验证处理
 *
 * @author demo
 */
@Component
public class AccountTokenService {

    protected static final long MILLIS_SECOND = 1000;
    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;
    private static final Logger log = LoggerFactory.getLogger(AccountTokenService.class);
    private static final Long MILLIS_MINUTE_TWENTY = 20 * 60 * 1000L;

    @Value("${token.header}")
    private String header;

    @Value("${token.secret}")
    private String secret;

    @Value("${token.expireTime}")
    private int expireTime;

    @Autowired
    private RedisCache redisCache;

    public AccountLoginUser getAccountLoginUser(HttpServletRequest request) {
        String token = getToken(request);
        if (StringUtils.isNotEmpty(token)) {
            try {
                Claims claims = parseToken(token);
                if (!Constants.LOGIN_TYPE_ACCOUNT.equals(String.valueOf(claims.get(Constants.JWT_LOGIN_TYPE)))) {
                    return null;
                }
                String uuid = (String) claims.get(Constants.LOGIN_USER_KEY);
                return redisCache.getCacheObject(getTokenKey(uuid));
            } catch (Exception e) {
                log.error("获取客户端账号信息异常'{}'", e.getMessage());
            }
        }
        return null;
    }

    public void delLoginUser(String token) {
        if (StringUtils.isNotEmpty(token)) {
            redisCache.deleteObject(getTokenKey(token));
        }
    }

    public String createToken(AccountLoginUser loginUser) {
        String token = IdUtils.fastUUID();
        loginUser.setToken(token);
        setUserAgent(loginUser);
        refreshToken(loginUser);

        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.LOGIN_USER_KEY, token);
        claims.put(Constants.JWT_USERNAME, loginUser.getUsername());
        claims.put(Constants.JWT_LOGIN_TYPE, Constants.LOGIN_TYPE_ACCOUNT);
        return createToken(claims);
    }

    public void verifyToken(AccountLoginUser loginUser) {
        long expireTimeMs = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTimeMs - currentTime <= MILLIS_MINUTE_TWENTY) {
            refreshToken(loginUser);
        }
    }

    public void refreshToken(AccountLoginUser loginUser) {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + expireTime * MILLIS_MINUTE);
        redisCache.setCacheObject(getTokenKey(loginUser.getToken()), loginUser, expireTime, TimeUnit.MINUTES);
    }

    public void setUserAgent(AccountLoginUser loginUser) {
        String userAgent = ServletUtils.getRequest().getHeader("User-Agent");
        String ip = IpUtils.getIpAddr();
        loginUser.setIpaddr(ip);
        loginUser.setLoginLocation(AddressUtils.getRealAddressByIP(ip));
        loginUser.setBrowser(UserAgentUtils.getBrowser(userAgent));
        loginUser.setOs(UserAgentUtils.getOperatingSystem(userAgent));
    }

    private String createToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    private Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(header);
        if (StringUtils.isNotEmpty(token) && token.startsWith(Constants.TOKEN_PREFIX)) {
            token = token.replace(Constants.TOKEN_PREFIX, "");
        }
        return token;
    }

    private String getTokenKey(String uuid) {
        return CacheConstants.ACCOUNT_LOGIN_TOKEN_KEY + uuid;
    }
}
