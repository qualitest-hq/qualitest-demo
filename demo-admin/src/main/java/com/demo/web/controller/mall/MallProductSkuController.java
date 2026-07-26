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
import com.demo.mall.domain.MallProductSku;
import com.demo.mall.params.MallProductSkuParams;
import com.demo.mall.result.MallProductSkuResult;
import com.demo.mall.service.IMallProductSkuService;
import com.demo.common.core.text.Convert;
import com.demo.common.utils.poi.ExcelUtil;
import com.demo.common.core.page.TableDataInfo;

/**
 * 商城商品SKUController
 *
 * @api.group 管理端.商城.商品SKU
 *
 * @author demo
 * @date 2026-06-20
 */
@RestController
@RequestMapping("/web/mall/mallProductSku")
@AllArgsConstructor
public class MallProductSkuController extends BaseController {

    private final IMallProductSkuService mallProductSkuService;

    /**
     * 查询商城商品SKU列表
     */
    @PreAuthorize("@ss.hasPermi('mall:mallProductSku:list')")
    @GetMapping("/list")
    public TableDataInfo list(MallProductSkuParams params) {
        startPage();
        List<MallProductSkuResult> list = mallProductSkuService.selectMallProductSkuResultList(params);
        return getDataTable(list);
    }

    /**
     * 导出商城商品SKU列表
     */
    @PreAuthorize("@ss.hasPermi('mall:mallProductSku:export')")
    @Log(title = "商城商品SKU", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MallProductSkuParams params) {
        List<MallProductSkuResult> list = mallProductSkuService.selectMallProductSkuResultList(params);
        ExcelUtil<MallProductSkuResult> util = new ExcelUtil<>(MallProductSkuResult.class);
        util.exportExcel(response, list, "商城商品SKU数据");
    }

    /**
     * 获取商城商品SKU详细信息
     */
    @PreAuthorize("@ss.hasPermi('mall:mallProductSku:query')")
    @GetMapping(value = "/{skuId}")
    public R<MallProductSkuResult> getInfo(@PathVariable("skuId") Long skuId) {
        return ok(mallProductSkuService.selectMallProductSkuResult(skuId));
    }

    /**
     * 新增商城商品SKU
     */
    @PreAuthorize("@ss.hasPermi('mall:mallProductSku:add')")
    @Log(title = "商城商品SKU", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Void> add(@RequestBody MallProductSku mallProductSku) {
        return toR(mallProductSkuService.insertMallProductSku(mallProductSku));
    }

    /**
     * 修改商城商品SKU
     */
    @PreAuthorize("@ss.hasPermi('mall:mallProductSku:edit')")
    @Log(title = "商城商品SKU", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Void> edit(@RequestBody MallProductSku mallProductSku) {
        return toR(mallProductSkuService.updateMallProductSku(mallProductSku));
    }

    /**
     * 删除商城商品SKU
     */
    @PreAuthorize("@ss.hasPermi('mall:mallProductSku:remove')")
    @Log(title = "商城商品SKU", businessType = BusinessType.DELETE)
    @DeleteMapping("/{skuIds}")
    public R<Void> remove(@PathVariable Long[] skuIds) {
        List<Long> idList = Convert.toLongList(skuIds);
        return toR(mallProductSkuService.logicDeleteMallProductSkuByIdList(idList));
    }
}
