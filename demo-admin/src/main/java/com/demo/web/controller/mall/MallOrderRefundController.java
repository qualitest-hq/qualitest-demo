package com.demo.web.controller.mall;

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
import com.demo.common.exception.ServiceException;
import com.demo.mall.domain.MallOrderRefund;
import com.demo.mall.params.MallOrderRefundItemParams;
import com.demo.mall.params.MallOrderRefundParams;
import com.demo.mall.params.RejectRefundParams;
import com.demo.mall.result.MallOrderRefundResult;
import com.demo.mall.service.IMallOrderRefundItemService;
import com.demo.mall.service.IMallOrderRefundService;
import com.demo.common.core.text.Convert;
import com.demo.common.utils.poi.ExcelUtil;
import com.demo.common.core.page.TableDataInfo;

/**
 * 商城退款单Controller
 *
 * @api.group 管理端.商城.退款单
 *
 * @author demo
 * @date 2026-06-20
 */
@RestController
@RequestMapping("/web/mall/mallOrderRefund")
@AllArgsConstructor
public class MallOrderRefundController extends BaseController {

    private final IMallOrderRefundService mallOrderRefundService;

    private final IMallOrderRefundItemService mallOrderRefundItemService;

    /**
     * 查询商城退款单列表
     */
    @PreAuthorize("@ss.hasPermi('mall:mallOrderRefund:list')")
    @GetMapping("/list")
    public TableDataInfo list(MallOrderRefundParams params) {
        startPage();
        List<MallOrderRefundResult> list = mallOrderRefundService.selectMallOrderRefundResultList(params);
        return getDataTable(list);
    }

    /**
     * 导出商城退款单列表
     */
    @PreAuthorize("@ss.hasPermi('mall:mallOrderRefund:export')")
    @Log(title = "商城退款单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MallOrderRefundParams params) {
        List<MallOrderRefundResult> list = mallOrderRefundService.selectMallOrderRefundResultList(params);
        ExcelUtil<MallOrderRefundResult> util = new ExcelUtil<>(MallOrderRefundResult.class);
        util.exportExcel(response, list, "商城退款单数据");
    }

    /**
     * 获取商城退款单详情
     */
    @PreAuthorize("@ss.hasPermi('mall:mallOrderRefund:query')")
    @GetMapping(value = "/{refundId}/detail")
    public R<MallOrderRefundResult> getDetail(@PathVariable("refundId") Long refundId) {
        MallOrderRefundResult result = mallOrderRefundService.selectMallOrderRefundResult(refundId);
        if (result == null) {
            throw new ServiceException("退款单不存在");
        }
        MallOrderRefundItemParams itemParams = MallOrderRefundItemParams.builder().refundId(refundId).build();
        result.setRefundItems(mallOrderRefundItemService.selectMallOrderRefundItemResultList(itemParams));
        return ok(result);
    }

    /**
     * 审核通过
     */
    @PreAuthorize("@ss.hasPermi('mall:mallOrderRefund:approve')")
    @Log(title = "商城退款单", businessType = BusinessType.UPDATE)
    @PostMapping("/{refundId}/approve")
    public R<Void> approve(@PathVariable("refundId") Long refundId) {
        mallOrderRefundService.approveMallOrderRefund(refundId);
        return ok();
    }

    /**
     * 审核拒绝
     */
    @PreAuthorize("@ss.hasPermi('mall:mallOrderRefund:reject')")
    @Log(title = "商城退款单", businessType = BusinessType.UPDATE)
    @PostMapping("/{refundId}/reject")
    public R<Void> reject(@PathVariable("refundId") Long refundId, @RequestBody(required = false) RejectRefundParams params) {
        String handleRemark = params != null ? params.getHandleRemark() : null;
        mallOrderRefundService.rejectMallOrderRefund(refundId, handleRemark);
        return ok();
    }

    /**
     * 确认退款完成
     */
    @PreAuthorize("@ss.hasPermi('mall:mallOrderRefund:complete')")
    @Log(title = "商城退款单", businessType = BusinessType.UPDATE)
    @PostMapping("/{refundId}/complete")
    public R<Void> complete(@PathVariable("refundId") Long refundId) {
        mallOrderRefundService.completeMallOrderRefund(refundId);
        return ok();
    }

    /**
     * 获取商城退款单详细信息
     */
    @PreAuthorize("@ss.hasPermi('mall:mallOrderRefund:query')")
    @GetMapping(value = "/{refundId}")
    public R<MallOrderRefundResult> getInfo(@PathVariable("refundId") Long refundId) {
        return ok(mallOrderRefundService.selectMallOrderRefundResult(refundId));
    }

    /**
     * 新增商城退款单
     */
    @PreAuthorize("@ss.hasPermi('mall:mallOrderRefund:add')")
    @Log(title = "商城退款单", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Void> add(@RequestBody MallOrderRefund mallOrderRefund) {
        return toR(mallOrderRefundService.insertMallOrderRefund(mallOrderRefund));
    }

    /**
     * 修改商城退款单
     */
    @PreAuthorize("@ss.hasPermi('mall:mallOrderRefund:edit')")
    @Log(title = "商城退款单", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Void> edit(@RequestBody MallOrderRefund mallOrderRefund) {
        return toR(mallOrderRefundService.updateMallOrderRefund(mallOrderRefund));
    }

    /**
     * 删除商城退款单
     */
    @PreAuthorize("@ss.hasPermi('mall:mallOrderRefund:remove')")
    @Log(title = "商城退款单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{refundIds}")
    public R<Void> remove(@PathVariable Long[] refundIds) {
        List<Long> idList = Convert.toLongList(refundIds);
        return toR(mallOrderRefundService.logicDeleteMallOrderRefundByIdList(idList));
    }
}
