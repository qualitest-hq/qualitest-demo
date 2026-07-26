package com.demo.web.apiController.mall;

import com.demo.common.core.controller.ApiController;
import com.demo.common.core.domain.R;
import com.demo.common.core.page.TableData;
import com.demo.mall.apiParams.MallOrderApiParams;
import com.demo.mall.apiParams.MallOrderCancelApiParams;
import com.demo.mall.apiParams.MallOrderPayApiParams;
import com.demo.mall.apiParams.MallOrderSubmitApiParams;
import com.demo.mall.apiResult.MallOrderApiResult;
import com.demo.mall.apiResult.MallOrderPreviewApiResult;
import com.demo.mall.service.IMallOrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商城订单客户端 API
 *
 * @api.group 客户端.商城.订单
 *
 * @author demo
 * @date 2026-06-20
 */
@RestController
@RequestMapping("/api/mall/mallOrder")
@AllArgsConstructor
public class MallOrderApiController extends ApiController {

    private final IMallOrderService mallOrderService;

    /**
     * 结算预览
     */
    @PostMapping("/preview")
    public R<MallOrderPreviewApiResult> preview(@RequestBody MallOrderSubmitApiParams params) {
        Long accountId = getAccountId();
        return ok(mallOrderService.previewMallOrder(accountId, params));
    }

    /**
     * 创建订单
     */
    @PostMapping("/create")
    public R<MallOrderPreviewApiResult> create(@RequestBody MallOrderSubmitApiParams params) {
        Long accountId = getAccountId();
        return ok(mallOrderService.createMallOrder(accountId, params));
    }

    /**
     * 我的订单列表
     */
    @GetMapping("/my")
    public R<TableData<MallOrderApiResult>> myList(MallOrderApiParams params) {
        Long accountId = getAccountId();
        if (params == null) {
            params = new MallOrderApiParams();
        }
        params.setAccountId(accountId);
        startPage();
        List<MallOrderApiResult> list = mallOrderService.selectMallOrderApiResultList(params);
        return getTableData(list);
    }

    /**
     * 我的订单详情
     */
    @GetMapping("/my/{orderId}")
    public R<MallOrderApiResult> myDetail(@PathVariable("orderId") Long orderId) {
        Long accountId = getAccountId();
        return ok(mallOrderService.selectMallOrderApiResult(orderId, accountId));
    }

    /**
     * 模拟支付
     */
    @PostMapping("/my/{orderId}/pay")
    public R<Void> myPay(@PathVariable("orderId") Long orderId, @RequestBody MallOrderPayApiParams params) {
        Long accountId = getAccountId();
        mallOrderService.payMallOrder(accountId, orderId, params);
        return ok();
    }

    /**
     * 取消待付款订单
     */
    @PostMapping("/my/{orderId}/cancel")
    public R<Void> myCancel(@PathVariable("orderId") Long orderId, @RequestBody(required = false) MallOrderCancelApiParams params) {
        Long accountId = getAccountId();
        mallOrderService.cancelMallOrder(accountId, orderId, params);
        return ok();
    }

    /**
     * 确认收货
     */
    @PostMapping("/my/{orderId}/confirmReceive")
    public R<Void> myConfirmReceive(@PathVariable("orderId") Long orderId) {
        Long accountId = getAccountId();
        mallOrderService.confirmReceiveMallOrder(accountId, orderId);
        return ok();
    }
}
