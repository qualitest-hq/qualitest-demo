package com.demo.web.apiController.mall;

import com.demo.common.core.controller.ApiController;
import com.demo.common.core.domain.R;
import com.demo.common.core.page.TableData;
import com.demo.mall.apiParams.MallOrderRefundApiParams;
import com.demo.mall.apiParams.MallOrderRefundApplyApiParams;
import com.demo.mall.apiResult.MallOrderRefundApiResult;
import com.demo.mall.apiResult.MallOrderRefundPreviewApiResult;
import com.demo.mall.service.IMallOrderRefundService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商城退款单客户端 API
 *
 * @api.group 客户端.商城.退款单
 *
 * @author demo
 * @date 2026-06-20
 */
@RestController
@RequestMapping("/api/mall/mallOrderRefund")
@AllArgsConstructor
public class MallOrderRefundApiController extends ApiController {

    private final IMallOrderRefundService mallOrderRefundService;

    /**
     * 退款预览
     */
    @PostMapping("/preview")
    public R<MallOrderRefundPreviewApiResult> preview(@RequestBody MallOrderRefundApplyApiParams params) {
        Long accountId = getAccountId();
        return ok(mallOrderRefundService.previewMallOrderRefund(accountId, params));
    }

    /**
     * 申请退款
     */
    @PostMapping("/apply")
    public R<MallOrderRefundApiResult> apply(@RequestBody MallOrderRefundApplyApiParams params) {
        Long accountId = getAccountId();
        return ok(mallOrderRefundService.applyMallOrderRefund(accountId, params));
    }

    /**
     * 我的退款单
     */
    @GetMapping("/my")
    public R<TableData<MallOrderRefundApiResult>> myList() {
        Long accountId = getAccountId();
        MallOrderRefundApiParams params = new MallOrderRefundApiParams();
        params.setAccountId(accountId);
        startPage();
        List<MallOrderRefundApiResult> list = mallOrderRefundService.selectMallOrderRefundApiResultList(params);
        return getTableData(list);
    }

    /**
     * 退款详情
     */
    @GetMapping("/my/{refundId}")
    public R<MallOrderRefundApiResult> myDetail(@PathVariable("refundId") Long refundId) {
        Long accountId = getAccountId();
        return ok(mallOrderRefundService.selectMallOrderRefundApiResult(refundId, accountId));
    }

    /**
     * 撤销待审核退款申请
     */
    @PostMapping("/my/{refundId}/cancel")
    public R<Void> myCancel(@PathVariable("refundId") Long refundId) {
        Long accountId = getAccountId();
        mallOrderRefundService.cancelMallOrderRefund(accountId, refundId);
        return ok();
    }
}
