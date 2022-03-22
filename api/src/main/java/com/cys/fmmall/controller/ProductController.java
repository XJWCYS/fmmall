package com.cys.fmmall.controller;

import com.cys.fmmall.entity.ProductComments;
import com.cys.fmmall.service.ProductCommentService;
import com.cys.fmmall.service.ProductService;
import com.cys.fmmall.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/product")
@CrossOrigin
@Api(value = "商品详情页面",tags = "商品管理")
public class ProductController {

    @Resource
    private ProductService productService;
    @Resource
    private ProductCommentService productCommentService;
    @GetMapping("/detail-info/{pid}")
    @ApiOperation("商品基本信息接口")
    public ResultVO getProductBasicInfo(@PathVariable("pid") String pid){
        return productService.getProductBasicInfo(pid);
    }
    @GetMapping("/detail-param/{pid}")
    @ApiOperation("商品参数信息接口")
    public ResultVO getProductParamsById(@PathVariable("pid") String pid){
        return productService.getProductParamsById(pid);
    }
    @GetMapping("/detail-comment/{pid}")
    @ApiOperation("商品评价信息接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int",name = "pageNum", value = "当前页",required = true),
            @ApiImplicitParam(dataType = "int",name = "limit", value = "分页记录数",required = true)
    })
    public ResultVO getProductComment(@PathVariable("pid") String pid,int pageNum,int limit){
        return productCommentService.selectCommentsByProductId(pid,pageNum,limit);
    }
    @GetMapping("/detail-total/{pid}")
    @ApiOperation("商品评价记录接口")
    public ResultVO selectCommentsCountById(@PathVariable("pid") String pid){
        return productCommentService.selectCommentsCountById(pid);
    }
    @ApiOperation("根据类别查询商品接口")
    @GetMapping("/listbycid/{cid}")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int",name = "pageNum", value = "当前页码",required = true),
            @ApiImplicitParam(dataType = "int",name = "limit", value = "每页显示条数",required = true)
    })
    public ResultVO getProductsByCategoryId(@PathVariable("cid") int cid,int pageNum,int limit){
        return productService.selectProductByCategoryId(cid,pageNum,limit);
    }
    @ApiOperation("根据类别查询商品品牌")
    @GetMapping("/listbrands/{cid}")
    @ApiImplicitParam(dataType = "int",name = "cid", value = "三级分类id",required = true)
    public ResultVO selectBrandByCategoryId(@PathVariable("cid") int cid){
        return productService.selectBrandByCategoryId(cid);
    }
    @ApiOperation("根据关键词查询商品信息")
    @GetMapping("/listbykeyword")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "String",name = "kw", value = "搜索关键词",required = true),
            @ApiImplicitParam(dataType = "int",name = "pageNum", value = "当前页码",required = true),
            @ApiImplicitParam(dataType = "int",name = "limit", value = "每页显示条数",required = true)
    })
    public ResultVO selectProductByKeyword(String kw,int pageNum,int limit){
        return productService.selectProductByKeyword(kw,pageNum,limit);
    }
    @ApiOperation("根据搜索关键词查询商品品牌")
    @GetMapping("/listbrands")
    @ApiImplicitParam(dataType = "String",name = "kw", value = "搜索关键词",required = true)
    public ResultVO selectBrandByKeyword(String kw){
        return productService.selectBrandByKeyword(kw);
    }



    @ApiOperation("添加评论")
    @PostMapping("/addComment")
    public ResultVO addComment(String orderId,@RequestBody ProductComments productComments){
        System.out.println(productComments);
        ResultVO resultVO = productCommentService.addComment(orderId,productComments);
        return resultVO;
    }

    @GetMapping("/all")
    public ResultVO findAll(@RequestParam(value = "pageNum") int pageNum){

        ResultVO resultVO = productService.findAll(pageNum);
        return resultVO;
    }
    @PostMapping("/del")
    public ResultVO delProduct(@RequestParam(value = "productId") String productId){

        ResultVO resultVO = productService.delProduct(productId);
        return resultVO;
    }
}
