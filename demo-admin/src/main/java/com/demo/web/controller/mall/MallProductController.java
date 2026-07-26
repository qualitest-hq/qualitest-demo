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
import com.demo.mall.domain.MallProduct;
import com.demo.mall.params.ChangeShelfStatusParams;
import com.demo.mall.params.MallProductParams;
import com.demo.mall.result.MallProductResult;
import com.demo.mall.service.IMallProductService;
import com.demo.common.core.text.Convert;
import com.demo.common.utils.poi.ExcelUtil;
import com.demo.common.core.page.TableDataInfo;

/**
 * 商城商品SPUController
 *
 * @api.group 管理端.商城.商品SPU
 *
 * @author demo
 * @date 2026-06-20
 */
@RestController
@RequestMapping("/web/mall/mallProduct")
@AllArgsConstructor
public class MallProductController extends BaseController {

    private final IMallProductService mallProductService;

    /**
     * 查询商城商品SPU列表
     */
    @PreAuthorize("@ss.hasPermi('mall:mallProduct:list')")
    @GetMapping("/list")
    public TableDataInfo list(MallProductParams params) {
        startPage();
        List<MallProductResult> list = mallProductService.selectMallProductResultList(params);
        return getDataTable(list);
    }

    /**
     * 导出商城商品SPU列表
     */
    @PreAuthorize("@ss.hasPermi('mall:mallProduct:export')")
    @Log(title = "商城商品SPU", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MallProductParams params) {
        List<MallProductResult> list = mallProductService.selectMallProductResultList(params);
        ExcelUtil<MallProductResult> util = new ExcelUtil<>(MallProductResult.class);
        util.exportExcel(response, list, "商城商品SPU数据");
    }

    /**
     * 上架/下架
     */
    @PreAuthorize("@ss.hasPermi('mall:mallProduct:edit')")
    @Log(title = "商城商品SPU", businessType = BusinessType.UPDATE)
    @PutMapping("/changeShelfStatus")
    public R<Void> changeShelfStatus(@RequestBody ChangeShelfStatusParams params) {
        mallProductService.changeMallProductShelfStatus(params.getProductId(), params.getShelfStatus());
        return ok();
    }

    /**
     * 获取商城商品SPU详细信息
     */
    @PreAuthorize("@ss.hasPermi('mall:mallProduct:query')")
    @GetMapping(value = "/{productId}")
    public R<MallProductResult> getInfo(@PathVariable("productId") Long productId) {
        return ok(mallProductService.selectMallProductResult(productId));
    }

    /**
     * 新增商城商品SPU
     */
    @PreAuthorize("@ss.hasPermi('mall:mallProduct:add')")
    @Log(title = "商城商品SPU", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Void> add(@RequestBody MallProduct mallProduct) {
        return toR(mallProductService.insertMallProduct(mallProduct));
    }

    /**
     * 修改商城商品SPU
     */
    @PreAuthorize("@ss.hasPermi('mall:mallProduct:edit')")
    @Log(title = "商城商品SPU", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Void> edit(@RequestBody MallProduct mallProduct) {
        return toR(mallProductService.updateMallProduct(mallProduct));
    }

    /**
     * 删除商城商品SPU
     */
    @PreAuthorize("@ss.hasPermi('mall:mallProduct:remove')")
    @Log(title = "商城商品SPU", businessType = BusinessType.DELETE)
    @DeleteMapping("/{productIds}")
    public R<Void> remove(@PathVariable Long[] productIds) {
        List<Long> idList = Convert.toLongList(productIds);
        return toR(mallProductService.logicDeleteMallProductByIdList(idList));
    }
}
