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
import com.demo.mall.domain.MallOrder;
import com.demo.mall.params.CloseOrderParams;
import com.demo.mall.params.MallOrderItemParams;
import com.demo.mall.params.MallOrderParams;
import com.demo.mall.result.MallOrderResult;
import com.demo.mall.service.IMallOrderItemService;
import com.demo.mall.service.IMallOrderService;
import com.demo.common.core.text.Convert;
import com.demo.common.utils.poi.ExcelUtil;
import com.demo.common.core.page.TableDataInfo;

/**
 * 商城订单Controller
 *
 * @api.group 管理端.商城.订单
 *
 * @author demo
 * @date 2026-06-20
 */
@RestController
@RequestMapping("/web/mall/mallOrder")
@AllArgsConstructor
public class MallOrderController extends BaseController {

    private final IMallOrderService mallOrderService;

    private final IMallOrderItemService mallOrderItemService;

    /**
     * 查询商城订单列表
     */
    @PreAuthorize("@ss.hasPermi('mall:mallOrder:list')")
    @GetMapping("/list")
    public TableDataInfo list(MallOrderParams params) {
        startPage();
        List<MallOrderResult> list = mallOrderService.selectMallOrderResultList(params);
        return getDataTable(list);
    }

    /**
     * 导出商城订单列表
     */
    @PreAuthorize("@ss.hasPermi('mall:mallOrder:export')")
    @Log(title = "商城订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MallOrderParams params) {
        List<MallOrderResult> list = mallOrderService.selectMallOrderResultList(params);
        ExcelUtil<MallOrderResult> util = new ExcelUtil<>(MallOrderResult.class);
        util.exportExcel(response, list, "商城订单数据");
    }

    /**
     * 获取商城订单详情
     */
    @PreAuthorize("@ss.hasPermi('mall:mallOrder:query')")
    @GetMapping(value = "/{orderId}/detail")
    public R<MallOrderResult> getDetail(@PathVariable("orderId") Long orderId) {
        MallOrderResult result = mallOrderService.selectMallOrderResult(orderId);
        if (result == null) {
            throw new ServiceException("订单不存在");
        }
        MallOrderItemParams itemParams = MallOrderItemParams.builder().orderId(orderId).build();
        result.setItems(mallOrderItemService.selectMallOrderItemResultList(itemParams));
        return ok(result);
    }

    /**
     * 发货
     */
    @PreAuthorize("@ss.hasPermi('mall:mallOrder:deliver')")
    @Log(title = "商城订单", businessType = BusinessType.UPDATE)
    @PostMapping("/{orderId}/deliver")
    public R<Void> deliver(@PathVariable("orderId") Long orderId) {
        mallOrderService.deliverMallOrder(orderId);
        return ok();
    }

    /**
     * 关闭订单
     */
    @PreAuthorize("@ss.hasPermi('mall:mallOrder:close')")
    @Log(title = "商城订单", businessType = BusinessType.UPDATE)
    @PostMapping("/{orderId}/close")
    public R<Void> close(@PathVariable("orderId") Long orderId, @RequestBody(required = false) CloseOrderParams params) {
        String cancelReason = params != null ? params.getCancelReason() : null;
        mallOrderService.closeMallOrder(orderId, cancelReason);
        return ok();
    }

    /**
     * 获取商城订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('mall:mallOrder:query')")
    @GetMapping(value = "/{orderId}")
    public R<MallOrderResult> getInfo(@PathVariable("orderId") Long orderId) {
        return ok(mallOrderService.selectMallOrderResult(orderId));
    }

    /**
     * 新增商城订单
     */
    @PreAuthorize("@ss.hasPermi('mall:mallOrder:add')")
    @Log(title = "商城订单", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Void> add(@RequestBody MallOrder mallOrder) {
        return toR(mallOrderService.insertMallOrder(mallOrder));
    }

    /**
     * 修改商城订单
     */
    @PreAuthorize("@ss.hasPermi('mall:mallOrder:edit')")
    @Log(title = "商城订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Void> edit(@RequestBody MallOrder mallOrder) {
        return toR(mallOrderService.updateMallOrder(mallOrder));
    }

    /**
     * 删除商城订单
     */
    @PreAuthorize("@ss.hasPermi('mall:mallOrder:remove')")
    @Log(title = "商城订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{orderIds}")
    public R<Void> remove(@PathVariable Long[] orderIds) {
        List<Long> idList = Convert.toLongList(orderIds);
        return toR(mallOrderService.logicDeleteMallOrderByIdList(idList));
    }
}
