package com.demo.common.core.controller;

import com.demo.common.core.domain.R;
import com.demo.common.core.domain.model.AccountLoginUser;
import com.demo.common.core.page.PageDomain;
import com.demo.common.core.page.TableData;
import com.demo.common.core.page.TableSupport;
import com.demo.common.utils.AccountSecurityUtils;
import com.demo.common.utils.DateUtils;
import com.demo.common.utils.PageUtils;
import com.demo.common.utils.StringUtils;
import com.demo.common.utils.sql.SqlUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;

/**
 * 客户端 API 控制器基类（/api/**）。
 * <p>
 * 面向 C 端账号认证，身份主体为 {@link AccountLoginUser}，通过 {@link #getAccountId()} 获取当前登录账号。
 * 提供分页、R 统一响应等 Web 层通用方法。
 *
 * @author qualitest
 */
public class ApiController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(DateUtils.parseDate(text));
            }
        });
    }

    /**
     * 设置请求分页数据
     */
    protected void startPage() {
        PageUtils.startPage();
    }

    /**
     * 设置请求排序数据
     */
    protected void startOrderBy() {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        if (StringUtils.isNotEmpty(pageDomain.getOrderBy())) {
            String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            PageHelper.orderBy(orderBy);
        }
    }

    /**
     * 清理分页的线程变量
     */
    protected void clearPage() {
        PageUtils.clearPage();
    }

    /**
     * 响应分页列表（R 统一格式，data 含 rows/total）
     */
    protected <T> R<TableData<T>> getTableData(List<T> list) {
        TableData<T> data = new TableData<>(list, new PageInfo<>(list).getTotal());
        return ok(data);
    }

    public <T> R<T> ok() {
        return R.ok();
    }

    public <T> R<T> ok(T data) {
        return R.ok(data);
    }

    public <T> R<T> ok(T data, String msg) {
        return R.ok(data, msg);
    }

    public <T> R<T> fail() {
        return R.fail();
    }

    public <T> R<T> fail(String msg) {
        return R.fail(msg);
    }

    protected <T> R<T> toR(int rows) {
        return rows > 0 ? R.ok() : R.fail();
    }

    protected <T> R<T> toR(boolean result) {
        return result ? ok() : fail();
    }

    /**
     * 获取当前客户端登录账号信息
     */
    public AccountLoginUser getAccountLoginUser() {
        return AccountSecurityUtils.getAccountLoginUser();
    }

    /**
     * 获取当前客户端登录账号 ID
     */
    public Long getAccountId() {
        return AccountSecurityUtils.getAccountId();
    }
}
