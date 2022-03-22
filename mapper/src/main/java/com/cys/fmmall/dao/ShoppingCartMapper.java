package com.cys.fmmall.dao;

import com.cys.fmmall.entity.ShoppingCart;
import com.cys.fmmall.entity.ShoppingCartVO;
import com.cys.fmmall.general.GeneralDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartMapper extends GeneralDao<ShoppingCart> {
    //按照id查询购物车
    List<ShoppingCartVO> selectShopcartByUserId(int userId);
    //修改购物车商品数量
    int updateCartnumByCartid(@Param("cartId")int cartId,@Param("cartNum") int cartNum);
    //查询要结算的购物车商品
    List<ShoppingCartVO> selectShopcartByCids(List<Integer> cids);
}