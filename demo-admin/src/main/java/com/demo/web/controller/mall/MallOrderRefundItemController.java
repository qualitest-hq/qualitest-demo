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
import com.demo.mall.domain.MallOrderRefundItem;
import com.demo.mall.params.MallOrderRefundItemParams;
import com.demo.mall.result.MallOrderRefundItemResult;
import com.demo.mall.service.IMallOrderRefundItemService;
import com.demo.common.core.text.Convert;
import com.demo.common.utils.poi.ExcelUtil;
import com.demo.common.core.page.TableDataInfo;

/**
 * 商城退款明细Controller
 *
 * @api.group 管理端.商城.退款明细
 *
 * @author demo
 * @date 2026-06-20
 */
@RestController
@RequestMapping("/web/mall/mallOrderRefundItem")
@AllArgsConstructor
public class MallOrderRefundItemController extends BaseController {

    private final IMallOrderRefundItemService mallOrderRefundItemService;

    /**
     * 查询商城退款明细列表
     */
    @PreAuthorize("@ss.hasPermi('mall:mallOrderRefundItem:list')")
    @GetMapping("/list")
    public TableDataInfo list(MallOrderRefundItemParams params) {
        startPage();
        List<MallOrderRefundItemResult> list = mallOrderRefundItemService.selectMallOrderRefundItemResultList(params);
        return getDataTable(list);
    }

    /**
     * 导出商城退款明细列表
     */
    @PreAuthorize("@ss.hasPermi('mall:mallOrderRefundItem:export')")
    @Log(title = "商城退款明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MallOrderRefundItemParams params) {
        List<MallOrderRefundItemResult> list = mallOrderRefundItemService.selectMallOrderRefundItemResultList(params);
        ExcelUtil<MallOrderRefundItemResult> util = new ExcelUtil<>(MallOrderRefundItemResult.class);
        util.exportExcel(response, list, "商城退款明细数据");
    }

    /**
     * 获取商城退款明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('mall:mallOrderRefundItem:query')")
    @GetMapping(value = "/{refundItemId}")
    public R<MallOrderRefundItemResult> getInfo(@PathVariable("refundItemId") Long refundItemId) {
        return ok(mallOrderRefundItemService.selectMallOrderRefundItemResult(refundItemId));
    }

    /**
     * 新增商城退款明细
     */
    @PreAuthorize("@ss.hasPermi('mall:mallOrderRefundItem:add')")
    @Log(title = "商城退款明细", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Void> add(@RequestBody MallOrderRefundItem mallOrderRefundItem) {
        return toR(mallOrderRefundItemService.insertMallOrderRefundItem(mallOrderRefundItem));
    }

    /**
     * 修改商城退款明细
     */
    @PreAuthorize("@ss.hasPermi('mall:mallOrderRefundItem:edit')")
    @Log(title = "商城退款明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Void> edit(@RequestBody MallOrderRefundItem mallOrderRefundItem) {
        return toR(mallOrderRefundItemService.updateMallOrderRefundItem(mallOrderRefundItem));
    }

    /**
     * 删除商城退款明细
     */
    @PreAuthorize("@ss.hasPermi('mall:mallOrderRefundItem:remove')")
    @Log(title = "商城退款明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{refundItemIds}")
    public R<Void> remove(@PathVariable Long[] refundItemIds) {
        List<Long> idList = Convert.toLongList(refundItemIds);
        return toR(mallOrderRefundItemService.logicDeleteMallOrderRefundItemByIdList(idList));
    }
}
