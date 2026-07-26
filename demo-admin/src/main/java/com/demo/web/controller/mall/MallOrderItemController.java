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
import com.demo.mall.domain.MallOrderItem;
import com.demo.mall.params.MallOrderItemParams;
import com.demo.mall.result.MallOrderItemResult;
import com.demo.mall.service.IMallOrderItemService;
import com.demo.common.core.text.Convert;
import com.demo.common.utils.poi.ExcelUtil;
import com.demo.common.core.page.TableDataInfo;

/**
 * 商城订单明细Controller
 *
 * @api.group 管理端.商城.订单明细
 *
 * @author demo
 * @date 2026-06-20
 */
@RestController
@RequestMapping("/web/mall/mallOrderItem")
@AllArgsConstructor
public class MallOrderItemController extends BaseController {

    private final IMallOrderItemService mallOrderItemService;

    /**
     * 查询商城订单明细列表
     */
    @PreAuthorize("@ss.hasPermi('mall:mallOrderItem:list')")
    @GetMapping("/list")
    public TableDataInfo list(MallOrderItemParams params) {
        startPage();
        List<MallOrderItemResult> list = mallOrderItemService.selectMallOrderItemResultList(params);
        return getDataTable(list);
    }

    /**
     * 导出商城订单明细列表
     */
    @PreAuthorize("@ss.hasPermi('mall:mallOrderItem:export')")
    @Log(title = "商城订单明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MallOrderItemParams params) {
        List<MallOrderItemResult> list = mallOrderItemService.selectMallOrderItemResultList(params);
        ExcelUtil<MallOrderItemResult> util = new ExcelUtil<>(MallOrderItemResult.class);
        util.exportExcel(response, list, "商城订单明细数据");
    }

    /**
     * 获取商城订单明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('mall:mallOrderItem:query')")
    @GetMapping(value = "/{orderItemId}")
    public R<MallOrderItemResult> getInfo(@PathVariable("orderItemId") Long orderItemId) {
        return ok(mallOrderItemService.selectMallOrderItemResult(orderItemId));
    }

    /**
     * 新增商城订单明细
     */
    @PreAuthorize("@ss.hasPermi('mall:mallOrderItem:add')")
    @Log(title = "商城订单明细", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Void> add(@RequestBody MallOrderItem mallOrderItem) {
        return toR(mallOrderItemService.insertMallOrderItem(mallOrderItem));
    }

    /**
     * 修改商城订单明细
     */
    @PreAuthorize("@ss.hasPermi('mall:mallOrderItem:edit')")
    @Log(title = "商城订单明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Void> edit(@RequestBody MallOrderItem mallOrderItem) {
        return toR(mallOrderItemService.updateMallOrderItem(mallOrderItem));
    }

    /**
     * 删除商城订单明细
     */
    @PreAuthorize("@ss.hasPermi('mall:mallOrderItem:remove')")
    @Log(title = "商城订单明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{orderItemIds}")
    public R<Void> remove(@PathVariable Long[] orderItemIds) {
        List<Long> idList = Convert.toLongList(orderItemIds);
        return toR(mallOrderItemService.logicDeleteMallOrderItemByIdList(idList));
    }
}
