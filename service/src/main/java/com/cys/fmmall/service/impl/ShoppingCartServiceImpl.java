package com.cys.fmmall.service.impl;

import com.cys.fmmall.dao.ShoppingCartMapper;
import com.cys.fmmall.entity.ShoppingCart;
import com.cys.fmmall.entity.ShoppingCartVO;
import com.cys.fmmall.service.ShoppingCartService;
import com.cys.fmmall.vo.ResStatus;
import com.cys.fmmall.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    public ResultVO addShoppingCart(ShoppingCart cart) {
        cart.setCartTime(sdf.format(new Date()));
        int insert = shoppingCartMapper.insert(cart);
        if (insert > 0){
            ResultVO resultVO = new ResultVO(ResStatus.OK,"success",null);
            return resultVO;
        }else{
            ResultVO resultVO = new ResultVO(ResStatus.NO,"fail",null);
            return resultVO;
        }

    }

    @Transactional(propagation = Propagation.SUPPORTS)//隔离级别
    public ResultVO listShoppingCartByUserId(int userId) {
        List<ShoppingCartVO> shoppingCarts = shoppingCartMapper.selectShopcartByUserId(userId);
        ResultVO resultVO = new ResultVO(ResStatus.OK, "success", shoppingCarts);
        return resultVO;
    }

    @Override
    public ResultVO updateCartnumByCartid(int cartId,int cartNum) {
        int i = shoppingCartMapper.updateCartnumByCartid(cartId,cartNum);
        if(i>0){
            return new ResultVO(ResStatus.OK,"修改成功",null);
        }else{
            return new ResultVO(ResStatus.NO,"修改失败",null);
        }
    }

    @Override
    public ResultVO delCartnumByCartid(int cartId) {
        int i = shoppingCartMapper.deleteByPrimaryKey(cartId);
        if(i>0){
            return new ResultVO(ResStatus.OK,"删除成功",null);
        }else {
            return new ResultVO(ResStatus.NO,"删除失败",null);
        }
    }

    @Override
    public ResultVO selectShopcartByCids(String cids) {
        String[] str = cids.split(",");
        List<Integer> cartIds = new ArrayList<>();
        for (int i = 0; i < str.length; i++) {
            cartIds.add(Integer.parseInt(str[i]));
        }
        List<ShoppingCartVO> shoppingCartVOs = shoppingCartMapper.selectShopcartByCids(cartIds);
        return new ResultVO(ResStatus.OK,"success",shoppingCartVOs);
    }
}