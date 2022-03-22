package com.cys.fmmall.controller;

import com.cys.fmmall.entity.ShoppingCart;
import com.cys.fmmall.service.ShoppingCartService;
import com.cys.fmmall.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/shop")
@CrossOrigin
@Api(value = "购物车页面",tags = "购物车管理")
public class ShopCartController {
    @Resource
    private ShoppingCartService shoppingCartService;
    @PostMapping("/add")
    @ApiOperation("添加购物车接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "ShoppingCart",name = "cart", value = "添加购物车的订单信息",required = true),
            @ApiImplicitParam(dataType = "String",name = "token", value = "令牌",required = true)
    })
    public ResultVO addShoppingCart(@RequestBody ShoppingCart cart, @RequestHeader("token") String token){
        ResultVO resultVO = shoppingCartService.addShoppingCart(cart);
        return resultVO;
    }
    @GetMapping("/list")
    @ApiOperation("购物车列表信息接口")
    @ApiImplicitParam(dataType = "int",name = "userId", value = "用户ID",required = true)
    public  ResultVO listShoppingCartByUserId(Integer userId,@RequestHeader String token){
        return shoppingCartService.listShoppingCartByUserId(userId);
    }

    @PutMapping("/update/{cid}/{cnum}")
    @ApiOperation("购物车商品修改接口")
    public ResultVO updateCartnumByCartid(@PathVariable("cid")Integer cid,@PathVariable("cnum")Integer cnum,
                                            @RequestHeader("token")String token){
        return shoppingCartService.updateCartnumByCartid(cid,cnum);
    }
    @DeleteMapping("/del/{cid}")
    @ApiOperation("购物车商品删除接口")
    public ResultVO delCartnumByCartid(@PathVariable("cid") Integer cid,@RequestHeader String token){
        return shoppingCartService.delCartnumByCartid(cid);
    }


    @GetMapping("/listbycids")
    @ApiOperation("购物车选中商品信息接口")
    @ApiImplicitParam(dataType = "String",name = "cids", value = "选中id",required = true)
    public  ResultVO selectShopcartByCids(String cids,@RequestHeader String token){
        return shoppingCartService.selectShopcartByCids(cids);
    }


}
