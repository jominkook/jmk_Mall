package com.jmk.mapper;

import com.jmk.dto.order.OrderDto;
import com.jmk.dto.order.OrderProductDto;


public interface OrderMapper {
    public void insertOrder(OrderDto orderDto);
    public void insertOrderProduct(OrderProductDto orderProductDto);
}
