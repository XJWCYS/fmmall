package com.cys.fmmall.service;

import com.cys.fmmall.entity.ShoppingCart;
import com.cys.fmmall.vo.ResultVO;

public interface ShoppingCartService {
    //添加购物车
    ResultVO addShoppingCart(ShoppingCart cart);
    //购物车列表
    ResultVO listShoppingCartByUserId(int userId);
    //修改购物车商品数量
    ResultVO updateCartnumByCartid(int cartNum,int cartId);
    //删除购物车商品
    ResultVO delCartnumByCartid(int cartId);
    //查询选中的购物车商品
    ResultVO selectShopcartByCids(String cids);
}
