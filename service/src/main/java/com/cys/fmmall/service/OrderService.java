package com.cys.fmmall.service;

import com.cys.fmmall.entity.Orders;
import com.cys.fmmall.vo.ResultVO;

import java.sql.SQLException;
import java.util.Map;

public interface OrderService {
    //添加订单
    Map<String,String> addOrder(String cids, Orders order) throws SQLException;
    //修改订单状态
    int updateOrderStatus(String orderId,String status);
    //关闭订单
    void closeOrder(String orderId);
    //查询订单
    ResultVO listOrders(String userId,String status,int pageNum,int limit);
    //管理员订单
    ResultVO listAdminOrders(String status,int pageNum);
    //订单发货
    ResultVO fhAdminOrders(String orderId);
}
