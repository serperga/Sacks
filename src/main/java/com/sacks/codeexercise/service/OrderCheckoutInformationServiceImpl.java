package com.sacks.codeexercise.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sacks.codeexercise.error.NotFoundOrderErrorException;
import com.sacks.codeexercise.model.OrderInformation;
import com.sacks.codeexercise.model.entities.Order;
import com.sacks.codeexercise.repository.OrdersRepository;

@Service
public class OrderCheckoutInformationServiceImpl implements
    OrderCheckoutInformationService {

    private final OrdersRepository ordersRepository;

    @Autowired
    public OrderCheckoutInformationServiceImpl(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @Override
    public OrderInformation retrieveOrderCheckoutInformation(Long id) {
        Optional<Order> order = ordersRepository.findOrderByOrderId(id);
        if(order.isPresent()){

        } else {
            throw new NotFoundOrderErrorException("Order not found");
        }
        return null;
    }
}
