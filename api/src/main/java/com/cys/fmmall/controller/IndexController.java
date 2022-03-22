package com.cys.fmmall.controller;

import com.cys.fmmall.dao.ProductMapper;
import com.cys.fmmall.service.CategoryService;
import com.cys.fmmall.service.IndexImgService;
import com.cys.fmmall.service.ProductService;
import com.cys.fmmall.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/index")
@Api(value = "提供首页显示数据的接口",tags = "主页数据")
@CrossOrigin
public class IndexController {
    @Resource
    private IndexImgService indexImgService;
    @GetMapping("/indeximg")
    @ApiOperation("轮播图")
    public ResultVO listIndexImgs(){
        return indexImgService.listIndexImgs();
    }
    @Resource
    private CategoryService categoryService;
    @GetMapping("/category-list")
    @ApiOperation("商品分类查询接⼝")
    public ResultVO listCatetory(){
        return categoryService.listCategories();
    }


    @Resource
    private ProductService productService;
    @GetMapping("/list-recommends")
    @ApiOperation("新品推荐查询接⼝")
    public ResultVO listRecommendProducts(){
        return productService.listRecommendProducts();
    }

    @GetMapping("/category-recommends")
    @ApiOperation("分类推荐查询接⼝")
    public ResultVO listFirstLevelCategories(){
        return categoryService.listFirstLevelCategories();
    }

}
