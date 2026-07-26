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
import com.demo.mall.domain.MallCategory;
import com.demo.mall.params.MallCategoryParams;
import com.demo.mall.result.MallCategoryResult;
import com.demo.mall.service.IMallCategoryService;
import com.demo.common.core.text.Convert;
import com.demo.common.utils.poi.ExcelUtil;
import com.demo.common.core.page.TableDataInfo;

/**
 * 商城商品分类Controller
 *
 * @api.group 管理端.商城.商品分类
 *
 * @author demo
 * @date 2026-06-20
 */
@RestController
@RequestMapping("/web/mall/mallCategory")
@AllArgsConstructor
public class MallCategoryController extends BaseController {

    private final IMallCategoryService mallCategoryService;

    /**
     * 查询商城商品分类列表
     */
    @PreAuthorize("@ss.hasPermi('mall:mallCategory:list')")
    @GetMapping("/list")
    public TableDataInfo list(MallCategoryParams params) {
        startPage();
        List<MallCategoryResult> list = mallCategoryService.selectMallCategoryResultList(params);
        return getDataTable(list);
    }

    /**
     * 导出商城商品分类列表
     */
    @PreAuthorize("@ss.hasPermi('mall:mallCategory:export')")
    @Log(title = "商城商品分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MallCategoryParams params) {
        List<MallCategoryResult> list = mallCategoryService.selectMallCategoryResultList(params);
        ExcelUtil<MallCategoryResult> util = new ExcelUtil<>(MallCategoryResult.class);
        util.exportExcel(response, list, "商城商品分类数据");
    }

    /**
     * 获取商城商品分类详细信息
     */
    @PreAuthorize("@ss.hasPermi('mall:mallCategory:query')")
    @GetMapping(value = "/{categoryId}")
    public R<MallCategoryResult> getInfo(@PathVariable("categoryId") Long categoryId) {
        return ok(mallCategoryService.selectMallCategoryResult(categoryId));
    }

    /**
     * 新增商城商品分类
     */
    @PreAuthorize("@ss.hasPermi('mall:mallCategory:add')")
    @Log(title = "商城商品分类", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Void> add(@RequestBody MallCategory mallCategory) {
        return toR(mallCategoryService.insertMallCategory(mallCategory));
    }

    /**
     * 修改商城商品分类
     */
    @PreAuthorize("@ss.hasPermi('mall:mallCategory:edit')")
    @Log(title = "商城商品分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Void> edit(@RequestBody MallCategory mallCategory) {
        return toR(mallCategoryService.updateMallCategory(mallCategory));
    }

    /**
     * 删除商城商品分类
     */
    @PreAuthorize("@ss.hasPermi('mall:mallCategory:remove')")
    @Log(title = "商城商品分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{categoryIds}")
    public R<Void> remove(@PathVariable Long[] categoryIds) {
        List<Long> idList = Convert.toLongList(categoryIds);
        return toR(mallCategoryService.logicDeleteMallCategoryByIdList(idList));
    }
}
