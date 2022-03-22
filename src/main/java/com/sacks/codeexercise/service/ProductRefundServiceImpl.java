package com.sacks.codeexercise.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sacks.codeexercise.error.NotFoundOrderErrorException;
import com.sacks.codeexercise.error.ProductNotFoundException;
import com.sacks.codeexercise.model.OrderRefundInformation;
import com.sacks.codeexercise.model.entities.Order;
import com.sacks.codeexercise.model.entities.Product;
import com.sacks.codeexercise.repository.OrdersRepository;

@Service
public class ProductRefundServiceImpl implements ProductRefundService {

    private final OrdersRepository ordersRepository;

    @Autowired
    public ProductRefundServiceImpl(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @Override
    public OrderRefundInformation returnProductAndGetRefund(Long orderId, Long productId) {
        final Optional<Order> order = ordersRepository.findOrderByOrderId(orderId);
        if(order.isPresent()){
            List<Product> productListInOrder = order.get().getProducts();
            Optional<Product> matchingProduct = productListInOrder.stream().
                filter(product -> (product.getProductId()) == productId).
                findFirst();
            if (matchingProduct.isPresent()){

            }else{
                throw new ProductNotFoundException("Product not found in order. Product can not be refunded");
            }
        }else{
            throw new NotFoundOrderErrorException("Order not found");
        }
        return null;
    }
}
