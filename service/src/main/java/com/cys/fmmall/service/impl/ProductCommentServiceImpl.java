package com.cys.fmmall.service.impl;

import com.cys.fmmall.dao.OrdersMapper;
import com.cys.fmmall.dao.ProductCommentsMapper;
import com.cys.fmmall.entity.Orders;
import com.cys.fmmall.entity.ProductComments;
import com.cys.fmmall.entity.ProductCommentsVO;
import com.cys.fmmall.service.ProductCommentService;
import com.cys.fmmall.utils.PageHelper;
import com.cys.fmmall.vo.ResStatus;
import com.cys.fmmall.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Service
public class ProductCommentServiceImpl implements ProductCommentService {
    @Autowired
    private ProductCommentsMapper productCommentsMapper;
    @Autowired
    private OrdersMapper ordersMapper;
    @Override
    public ResultVO selectCommentsByProductId(String productId, int pageNum, int limit) {
        //查询总记录数
        Example example = new Example(ProductComments.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId",productId);
        int count = productCommentsMapper.selectCountByExample(example);
        //根据总记录数计算总页数
        int pageCount = count%limit== 0? count/limit:count/limit+1;
        int start = (pageNum-1)*limit;
        List<ProductCommentsVO> productCommentsVOs = productCommentsMapper.selectCommentsByProductId(productId, start, limit);
        PageHelper<ProductCommentsVO> pageHelper = new PageHelper<>(count,pageCount,productCommentsVOs);
        ResultVO resultVO = new ResultVO(ResStatus.OK,"success",pageHelper);
        return resultVO;
    }

    @Override
    public ResultVO selectCommentsCountById(String productId) {
        //查询好评数
        Example example = new Example(ProductComments.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId",productId);
        int count = productCommentsMapper.selectCountByExample(example);
        criteria.andEqualTo("commType",1);
        int good = productCommentsMapper.selectCountByExample(example);
        //查询中评数
        Example example1 = new Example(ProductComments.class);
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andEqualTo("productId",productId);
        criteria1.andEqualTo("commType",0);
        int mid = productCommentsMapper.selectCountByExample(example1);
        //查询差评数
        Example example2 = new Example(ProductComments.class);
        Example.Criteria criteria2 = example2.createCriteria();
        criteria2.andEqualTo("productId",productId);
        criteria2.andEqualTo("commType",-1);
        int bad = productCommentsMapper.selectCountByExample(example2);
        //好评率
        double pacent = Double.parseDouble(good+"")/Double.parseDouble(count+"")*100;
        String favorableRate = (pacent+"").substring(0,(pacent+"").lastIndexOf(".")+2);
        if("N".equalsIgnoreCase(favorableRate)){
            favorableRate = "0.00";
        }
        Map<String,Object> map = new HashMap<>();
        map.put("good",good);
        map.put("mid",mid);
        map.put("bad",bad);
        map.put("favorableRate",favorableRate);
        return new ResultVO(ResStatus.OK,"success",map);
    }

    @Override
    public ResultVO addComment(String orderId,ProductComments productComments) {
        String commId = UUID.randomUUID().toString().replace("-", "");
        productComments.setCommId(commId);
        productComments.setSepcName(new Date(System.currentTimeMillis()));
        int insert = productCommentsMapper.insert(productComments);
        if(insert!=0){
            Orders orders = new Orders();
            orders.setOrderId(orderId);
            orders.setStatus("5");
            ordersMapper.updateByPrimaryKeySelective(orders);
            return new ResultVO(ResStatus.OK,"添加成功",null);
        }else {
            return new ResultVO(ResStatus.NO,"添加失败",null);
        }
    }
}
