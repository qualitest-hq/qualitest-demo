package com.demo.web.apiController.mall;

import com.demo.common.annotation.Anonymous;
import com.demo.mall.apiParams.MallProductApiParams;
import com.demo.mall.apiResult.MallProductApiResult;
import com.demo.mall.service.IMallProductService;
import com.demo.common.core.controller.ApiController;
import com.demo.common.core.domain.R;
import com.demo.common.core.page.TableData;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商城商品SPU客户端 API
 *
 * @api.group 客户端.商城.商品SPU
 *
 * @author demo
 * @date 2026-06-20
 */
@RestController
@RequestMapping("/api/mall/mallProduct")
@AllArgsConstructor
public class MallProductApiController extends ApiController {

    private final IMallProductService mallProductService;

    /**
     * 查询已上架商品列表
     */
    @Anonymous
    @GetMapping("/list")
    public R<TableData<MallProductApiResult>> list(MallProductApiParams params) {
        startPage();
        List<MallProductApiResult> list = mallProductService.selectMallProductApiResultList(params);
        return getTableData(list);
    }

    /**
     * 商品详情含 SKU 列表
     */
    @Anonymous
    @GetMapping("/{productId}/detail")
    public R<MallProductApiResult> detail(@PathVariable("productId") Long productId) {
        return ok(mallProductService.selectMallProductApiResult(productId));
    }

    /**
     * 获取商城商品SPU详细信息
     */
    @Anonymous
    @GetMapping("/{productId}")
    public R<MallProductApiResult> getInfo(@PathVariable("productId") Long productId) {
        return ok(mallProductService.selectMallProductApiResult(productId));
    }
}
