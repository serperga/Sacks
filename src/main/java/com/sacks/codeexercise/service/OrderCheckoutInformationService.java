package com.sacks.codeexercise.service;

import com.sacks.codeexercise.model.OrderInformation;

public interface OrderCheckoutInformationService {
    OrderInformation retrieveOrderCheckoutInformation(Long id);
}
