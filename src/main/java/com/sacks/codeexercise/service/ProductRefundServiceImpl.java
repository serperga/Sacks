package com.sacks.codeexercise.service;

import org.springframework.stereotype.Service;

import com.sacks.codeexercise.model.OrderRefundInformation;

@Service
public class ProductRefundServiceImpl implements ProductRefundService {

    @Override
    public OrderRefundInformation returnProductAndGetRefund(Long orderId, Long productId) {
        return null;
    }
}
