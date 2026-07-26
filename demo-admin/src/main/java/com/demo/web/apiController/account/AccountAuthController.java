package com.demo.web.apiController.account;

import com.demo.account.apiParams.AccountLoginApiParams;
import com.demo.account.apiParams.AccountPasswordUpdateApiParams;
import com.demo.account.apiParams.AccountProfileUpdateApiParams;
import com.demo.account.apiParams.AccountRegisterApiParams;
import com.demo.account.apiResult.AccountApiResult;
import com.demo.account.apiResult.AccountAuthApiResult;
import com.demo.common.annotation.Anonymous;
import com.demo.common.core.controller.ApiController;
import com.demo.common.core.domain.R;
import com.demo.framework.web.service.AccountLoginService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客户端账号认证与个人中心
 *
 * @api.group 客户端.账号.认证
 */
@RestController
@RequestMapping("/api/account/auth")
@AllArgsConstructor
public class AccountAuthController extends ApiController {

    private final AccountLoginService accountLoginService;

    /**
     * 客户端用户注册
     *
     * @param params 注册信息（mobile、password、nickName 可选）
     * @return accountId 与 token
     * @api.group 客户端.账号.认证
     */
    @Anonymous
    @PostMapping("/register")
    public R<AccountAuthApiResult> register(@RequestBody AccountRegisterApiParams params) {
        AccountAuthApiResult result = accountLoginService.register(params.getMobile(), params.getPassword(), params.getNickName());
        return ok(result);
    }

    /**
     * 客户端用户登录
     *
     * @param params 登录信息（mobile、password）
     * @return accountId、token、nickName
     * @api.group 客户端.账号.认证
     */
    @Anonymous
    @PostMapping("/login")
    public R<AccountAuthApiResult> login(@RequestBody AccountLoginApiParams params) {
        AccountAuthApiResult result = accountLoginService.login(params.getMobile(), params.getPassword());
        return ok(result);
    }

    /**
     * 客户端用户退出
     *
     * @api.group 客户端.账号.认证
     */
    @PostMapping("/logout")
    public R<Void> logout(HttpServletRequest request) {
        accountLoginService.logout(request);
        return ok();
    }

    /**
     * 获取当前用户信息
     *
     * @return 账号信息（含 balance，不含 password）
     * @api.group 客户端.账号.个人中心
     */
    @GetMapping("/profile")
    public R<AccountApiResult> profile() {
        Long accountId = getAccountId();
        return ok(accountLoginService.getProfile(accountId));
    }

    /**
     * 修改当前用户资料
     *
     * @param params nickName、gender 可选
     * @api.group 客户端.账号.个人中心
     */
    @PutMapping("/profile")
    public R<Void> updateProfile(@RequestBody AccountProfileUpdateApiParams params) {
        Long accountId = getAccountId();
        accountLoginService.updateProfile(accountId, params.getNickName(), params.getGender());
        return ok();
    }

    /**
     * 修改当前用户密码
     *
     * @param params oldPassword、newPassword
     * @api.group 客户端.账号.个人中心
     */
    @PutMapping("/password")
    public R<Void> updatePassword(@RequestBody AccountPasswordUpdateApiParams params) {
        Long accountId = getAccountId();
        accountLoginService.updatePassword(accountId, params.getOldPassword(), params.getNewPassword());
        return ok();
    }
}
