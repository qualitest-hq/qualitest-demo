package com.demo.web.controller.system;

import com.demo.common.config.DemoConfig;
import com.demo.common.core.domain.AjaxResult;
import com.demo.common.core.domain.entity.SysUser;
import com.demo.common.utils.SecurityUtils;
import com.demo.common.utils.StringUtils;
import com.demo.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 首页
 *
 * @api.group 管理端.系统.首页
 *
 * @author qualitest
 */
@RestController
public class SysIndexController {
    /**
     * 系统基础配置
     */
    @Autowired
    private DemoConfig demoConfig;

    @Autowired
    private ISysUserService userService;

    /**
     * 访问首页，提示语
     */
    @RequestMapping("/")
    public String index() {
        return StringUtils.format("欢迎使用{}，当前版本：v{}，请通过前端地址访问。", demoConfig.getName(), demoConfig.getVersion());
    }

    /**
     * 解锁屏幕
     */
    @PostMapping("/unlockscreen")
    public AjaxResult unlockScreen(@RequestBody Map<String, String> body) {
        String password = body.get("password");
        if (StringUtils.isEmpty(password)) {
            return AjaxResult.error("密码不能为空");
        }
        String username = SecurityUtils.getUsername();
        SysUser user = userService.selectUserByUserName(username);
        if (user == null) {
            return AjaxResult.error("服务器超时，请重新登录");
        }
        if (!SecurityUtils.matchesPassword(password, user.getPassword())) {
            return AjaxResult.error("密码错误，请重新输入");
        }

        return AjaxResult.success("解锁成功");
    }
}
