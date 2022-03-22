package com.cys.fmmall.dao;

import com.cys.fmmall.entity.Orders;
import com.cys.fmmall.entity.OrdersVO;
import com.cys.fmmall.general.GeneralDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersMapper extends GeneralDao<Orders> {
    List<OrdersVO> selectOrders(@Param("userId")String userId,
                                @Param("status")String status,
                                @Param("start")int start,
                                @Param("limit")int limit);
    List<OrdersVO> selectAdminOrders(@Param("status")String status,
                                      @Param("start")int start,
                                     @Param("limit")int limit);
}