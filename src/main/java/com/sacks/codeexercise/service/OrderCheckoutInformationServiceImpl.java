package com.sacks.codeexercise.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sacks.codeexercise.error.NotFoundOrderErrorException;
import com.sacks.codeexercise.model.OrderInformation;
import com.sacks.codeexercise.model.ProductInformation;
import com.sacks.codeexercise.model.entities.Order;
import com.sacks.codeexercise.model.entities.Product;
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
        OrderInformation orderInformation = new OrderInformation();
        if(order.isPresent()){
            orderInformation.setOrderId(order.get().getOrderId());
            orderInformation.setOrderStatus(order.get().getOrderStatus().getStatus());
            orderInformation.setAmount(order.get().getAmount());
            orderInformation.setBuyer(order.get().getBuyer().getUsername());
            orderInformation.setEstimatedDays(order.get().getEstimatedDays());

            List<Product> productsInOrder = order.get().getProducts();
            List<ProductInformation> productInformationListInOrder = new ArrayList<>();

            productsInOrder.forEach(productInOrder ->{
                ProductInformation productInformation = new ProductInformation(productInOrder.getName(),productInOrder.getPrice());
                productInformationListInOrder.add(productInformation);
            });
            orderInformation.setProducts(productInformationListInOrder);
        } else {
            throw new NotFoundOrderErrorException("Order not found");
        }
        return orderInformation;
    }
}
