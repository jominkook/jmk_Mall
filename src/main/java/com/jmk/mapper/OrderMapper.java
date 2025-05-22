package com.jmk.mapper;

import com.jmk.dto.order.OrderDto;
import com.jmk.dto.order.OrderProductDto;
import com.jmk.dto.order.OrderSelectDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface OrderMapper {
    public void insertOrder(OrderDto orderDto);

    public void insertOrderProduct(OrderProductDto orderProductDto);

    public List<OrderSelectDto> orderSelect(@Param("memberId") String memberId, @Param("adminCk") int adminCk);

    public void updateOrderProductStatus(@Param("memberOrderProductId") int memberOrderProductId, @Param("memberOrderStatus") String memberOrderStatus);

    public List<OrderProductDto> selectOrderProductsByProductId(@Param("memberOrderProductId") int memberOrderProductId);

    public void increaseStock(@Param("productId") int productId, @Param("quantity") int quantity);
}
