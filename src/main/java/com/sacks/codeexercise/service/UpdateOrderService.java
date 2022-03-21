package com.sacks.codeexercise.service;

import com.sacks.codeexercise.model.OrderUpdateInformation;
import com.sacks.codeexercise.model.entities.Order;

public interface UpdateOrderService {

    Order updateOrder(OrderUpdateInformation order, Long orderId);
}
