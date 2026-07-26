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
import com.demo.mall.domain.MallCart;
import com.demo.mall.params.MallCartParams;
import com.demo.mall.result.MallCartResult;
import com.demo.mall.service.IMallCartService;
import com.demo.common.core.text.Convert;
import com.demo.common.utils.poi.ExcelUtil;
import com.demo.common.core.page.TableDataInfo;

/**
 * 商城购物车Controller
 *
 * @api.group 管理端.商城.购物车
 *
 * @author demo
 * @date 2026-06-20
 */
@RestController
@RequestMapping("/web/mall/mallCart")
@AllArgsConstructor
public class MallCartController extends BaseController {

    private final IMallCartService mallCartService;

    /**
     * 查询商城购物车列表
     */
    @PreAuthorize("@ss.hasPermi('mall:mallCart:list')")
    @GetMapping("/list")
    public TableDataInfo list(MallCartParams params) {
        startPage();
        List<MallCartResult> list = mallCartService.selectMallCartResultList(params);
        return getDataTable(list);
    }

    /**
     * 导出商城购物车列表
     */
    @PreAuthorize("@ss.hasPermi('mall:mallCart:export')")
    @Log(title = "商城购物车", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MallCartParams params) {
        List<MallCartResult> list = mallCartService.selectMallCartResultList(params);
        ExcelUtil<MallCartResult> util = new ExcelUtil<>(MallCartResult.class);
        util.exportExcel(response, list, "商城购物车数据");
    }

    /**
     * 获取商城购物车详细信息
     */
    @PreAuthorize("@ss.hasPermi('mall:mallCart:query')")
    @GetMapping(value = "/{cartId}")
    public R<MallCartResult> getInfo(@PathVariable("cartId") Long cartId) {
        return ok(mallCartService.selectMallCartResult(cartId));
    }

    /**
     * 新增商城购物车
     */
    @PreAuthorize("@ss.hasPermi('mall:mallCart:add')")
    @Log(title = "商城购物车", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Void> add(@RequestBody MallCart mallCart) {
        return toR(mallCartService.insertMallCart(mallCart));
    }

    /**
     * 修改商城购物车
     */
    @PreAuthorize("@ss.hasPermi('mall:mallCart:edit')")
    @Log(title = "商城购物车", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Void> edit(@RequestBody MallCart mallCart) {
        return toR(mallCartService.updateMallCart(mallCart));
    }

    /**
     * 删除商城购物车
     */
    @PreAuthorize("@ss.hasPermi('mall:mallCart:remove')")
    @Log(title = "商城购物车", businessType = BusinessType.DELETE)
    @DeleteMapping("/{cartIds}")
    public R<Void> remove(@PathVariable Long[] cartIds) {
        List<Long> idList = Convert.toLongList(cartIds);
        return toR(mallCartService.logicDeleteMallCartByIdList(idList));
    }
}
