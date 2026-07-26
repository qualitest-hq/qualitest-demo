package com.demo.web.apiController.mall;

import com.demo.common.annotation.Anonymous;
import com.demo.mall.apiParams.MallCategoryApiParams;
import com.demo.mall.apiResult.MallCategoryApiResult;
import com.demo.mall.service.IMallCategoryService;
import com.demo.common.core.controller.ApiController;
import com.demo.common.core.domain.R;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商城商品分类客户端 API
 *
 * @api.group 客户端.商城.商品分类
 *
 * @author demo
 * @date 2026-06-20
 */
@RestController
@RequestMapping("/api/mall/mallCategory")
@AllArgsConstructor
public class MallCategoryApiController extends ApiController {

    private final IMallCategoryService mallCategoryService;

    /**
     * 启用分类列表
     */
    @Anonymous
    @GetMapping("/enabled")
    public R<List<MallCategoryApiResult>> enabled() {
        MallCategoryApiParams params = new MallCategoryApiParams();
        params.setStatus(0);
        return ok(mallCategoryService.selectMallCategoryApiResultList(params));
    }
}
