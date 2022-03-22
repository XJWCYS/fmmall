package com.cys.fmmall.dao;

import com.cys.fmmall.entity.OrderItem;
import com.cys.fmmall.general.GeneralDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemMapper extends GeneralDao<OrderItem> {
    List<OrderItem> listOrderItemsByOrderId(String orderId);
}