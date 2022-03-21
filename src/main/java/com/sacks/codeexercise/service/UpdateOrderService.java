package com.sacks.codeexercise.service;

import com.sacks.codeexercise.model.entities.Order;
import com.sacks.codeexercise.model.entities.OrderUpdateInformation;

public interface UpdateOrderService {

    Order updateOrder(OrderUpdateInformation order, Long orderId);
}
