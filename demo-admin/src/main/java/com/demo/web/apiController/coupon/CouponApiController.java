package com.demo.web.apiController.coupon;

import com.demo.common.core.controller.ApiController;
import com.demo.common.core.domain.R;
import com.demo.common.core.page.TableData;
import com.demo.coupon.apiParams.CouponApiParams;
import com.demo.coupon.apiResult.AccountCouponApiResult;
import com.demo.coupon.apiResult.CouponApiResult;
import com.demo.coupon.service.ICouponService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 优惠券客户端 API
 *
 * @api.group 客户端.优惠券.优惠券
 *
 * @author demo
 * @date 2026-06-20
 */
@RestController
@RequestMapping("/api/coupon/coupon")
@AllArgsConstructor
public class CouponApiController extends ApiController {

    private final ICouponService couponService;

    /**
     * 可领取的券模板列表
     */
    @GetMapping("/available")
    public R<TableData<CouponApiResult>> available(CouponApiParams params) {
        Long accountId = getAccountId();
        if (params == null) {
            params = new CouponApiParams();
        }
        params.setAccountId(accountId);
        startPage();
        List<CouponApiResult> list = couponService.selectCouponApiResultList(params);
        return getTableData(list);
    }

    /**
     * 领取优惠券
     */
    @PostMapping("/{couponId}/receive")
    public R<AccountCouponApiResult> receive(@PathVariable("couponId") Long couponId) {
        Long accountId = getAccountId();
        return ok(couponService.receiveCoupon(accountId, couponId));
    }
}
