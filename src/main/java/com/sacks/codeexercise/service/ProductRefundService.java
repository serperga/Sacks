package com.sacks.codeexercise.service;

import com.sacks.codeexercise.model.OrderRefundInformation;

public interface ProductRefundService {
    OrderRefundInformation returnProductAndGetRefund(Long orderId,Long productId);
}
