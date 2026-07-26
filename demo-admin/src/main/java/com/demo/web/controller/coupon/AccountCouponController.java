package com.demo.web.controller.coupon;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.demo.common.annotation.Log;
import com.demo.common.core.controller.BaseController;
import com.demo.common.core.domain.R;
import com.demo.common.enums.BusinessType;
import com.demo.coupon.domain.AccountCoupon;
import com.demo.coupon.params.AccountCouponParams;
import com.demo.coupon.result.AccountCouponResult;
import com.demo.coupon.service.IAccountCouponService;
import com.demo.common.core.text.Convert;
import com.demo.common.utils.poi.ExcelUtil;
import com.demo.common.core.page.TableDataInfo;

/**
 * 账号优惠券Controller
 *
 * @api.group 管理端.优惠券.用户优惠券
 *
 * @author demo
 * @date 2026-06-20
 */
@RestController
@RequestMapping("/web/coupon/accountCoupon")
@AllArgsConstructor
public class AccountCouponController extends BaseController {

    private final IAccountCouponService accountCouponService;

    /**
     * 查询账号优惠券列表
     */
    @PreAuthorize("@ss.hasPermi('coupon:accountCoupon:list')")
    @GetMapping("/list")
    public TableDataInfo list(AccountCouponParams params) {
        startPage();
        List<AccountCouponResult> list = accountCouponService.selectAccountCouponResultList(params);
        return getDataTable(list);
    }

    /**
     * 导出账号优惠券列表
     */
    @PreAuthorize("@ss.hasPermi('coupon:accountCoupon:export')")
    @Log(title = "账号优惠券", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AccountCouponParams params) {
        List<AccountCouponResult> list = accountCouponService.selectAccountCouponResultList(params);
        ExcelUtil<AccountCouponResult> util = new ExcelUtil<>(AccountCouponResult.class);
        util.exportExcel(response, list, "账号优惠券数据");
    }

    /**
     * 获取账号优惠券详细信息
     */
    @PreAuthorize("@ss.hasPermi('coupon:accountCoupon:query')")
    @GetMapping(value = "/{accountCouponId}")
    public R<AccountCouponResult> getInfo(@PathVariable("accountCouponId") Long accountCouponId) {
        return ok(accountCouponService.selectAccountCouponResult(accountCouponId));
    }

    /**
     * 新增账号优惠券
     */
    @PreAuthorize("@ss.hasPermi('coupon:accountCoupon:add')")
    @Log(title = "账号优惠券", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Void> add(@RequestBody AccountCoupon accountCoupon) {
        return toR(accountCouponService.insertAccountCoupon(accountCoupon));
    }

    /**
     * 修改账号优惠券
     */
    @PreAuthorize("@ss.hasPermi('coupon:accountCoupon:edit')")
    @Log(title = "账号优惠券", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Void> edit(@RequestBody AccountCoupon accountCoupon) {
        return toR(accountCouponService.updateAccountCoupon(accountCoupon));
    }

    /**
     * 删除账号优惠券
     */
    @PreAuthorize("@ss.hasPermi('coupon:accountCoupon:remove')")
    @Log(title = "账号优惠券", businessType = BusinessType.DELETE)
    @DeleteMapping("/{accountCouponIds}")
    public R<Void> remove(@PathVariable Long[] accountCouponIds) {
        List<Long> idList = Convert.toLongList(accountCouponIds);
        return toR(accountCouponService.logicDeleteAccountCouponByIdList(idList));
    }
}
