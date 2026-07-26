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
import com.demo.coupon.domain.Coupon;
import com.demo.coupon.params.CouponParams;
import com.demo.coupon.result.CouponResult;
import com.demo.coupon.service.ICouponService;
import com.demo.common.core.text.Convert;
import com.demo.common.utils.poi.ExcelUtil;
import com.demo.common.core.page.TableDataInfo;

/**
 * 优惠券Controller
 *
 * @api.group 管理端.优惠券.优惠券
 *
 * @author demo
 * @date 2026-06-20
 */
@RestController
@RequestMapping("/web/coupon/coupon")
@AllArgsConstructor
public class CouponController extends BaseController {

    private final ICouponService couponService;

    /**
     * 查询优惠券列表
     */
    @PreAuthorize("@ss.hasPermi('coupon:coupon:list')")
    @GetMapping("/list")
    public TableDataInfo list(CouponParams params) {
        startPage();
        List<CouponResult> list = couponService.selectCouponResultList(params);
        return getDataTable(list);
    }

    /**
     * 导出优惠券列表
     */
    @PreAuthorize("@ss.hasPermi('coupon:coupon:export')")
    @Log(title = "优惠券", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CouponParams params) {
        List<CouponResult> list = couponService.selectCouponResultList(params);
        ExcelUtil<CouponResult> util = new ExcelUtil<>(CouponResult.class);
        util.exportExcel(response, list, "优惠券数据");
    }

    /**
     * 获取优惠券详细信息
     */
    @PreAuthorize("@ss.hasPermi('coupon:coupon:query')")
    @GetMapping(value = "/{couponId}")
    public R<CouponResult> getInfo(@PathVariable("couponId") Long couponId) {
        return ok(couponService.selectCouponResult(couponId));
    }

    /**
     * 新增优惠券
     */
    @PreAuthorize("@ss.hasPermi('coupon:coupon:add')")
    @Log(title = "优惠券", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Void> add(@RequestBody Coupon coupon) {
        return toR(couponService.insertCoupon(coupon));
    }

    /**
     * 修改优惠券
     */
    @PreAuthorize("@ss.hasPermi('coupon:coupon:edit')")
    @Log(title = "优惠券", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Void> edit(@RequestBody Coupon coupon) {
        return toR(couponService.updateCoupon(coupon));
    }

    /**
     * 删除优惠券
     */
    @PreAuthorize("@ss.hasPermi('coupon:coupon:remove')")
    @Log(title = "优惠券", businessType = BusinessType.DELETE)
    @DeleteMapping("/{couponIds}")
    public R<Void> remove(@PathVariable Long[] couponIds) {
        List<Long> idList = Convert.toLongList(couponIds);
        return toR(couponService.logicDeleteCouponByIdList(idList));
    }
}
