package com.sacks.codeexercise.service;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sacks.codeexercise.error.IncorrectUpdateStatusException;
import com.sacks.codeexercise.error.NotFoundOrderErrorException;
import com.sacks.codeexercise.model.OrderUpdateInformation;
import com.sacks.codeexercise.model.entities.Order;
import com.sacks.codeexercise.model.entities.OrderStatus;
import com.sacks.codeexercise.model.entities.OrderStatusHistory;
import com.sacks.codeexercise.repository.OrderStatusHistoryRepository;
import com.sacks.codeexercise.repository.OrderStatusRepository;
import com.sacks.codeexercise.repository.OrdersRepository;

@Service
public class UpdateOrderServiceImpl implements UpdateOrderService {

    private final OrdersRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final OrderStatusHistoryRepository orderStatusHistoryRepository;

    private static final int ORDER_DELIVERED_STATUS = 5;

    @Autowired
    public UpdateOrderServiceImpl(OrdersRepository orderRepository,OrderStatusRepository orderStatusRepository,OrderStatusHistoryRepository orderStatusHistoryRepository) {
        this.orderRepository = orderRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.orderStatusHistoryRepository = orderStatusHistoryRepository;
    }

    @Override
    public Order updateOrder(OrderUpdateInformation order, Long orderId) {
        Optional<Order> orderToUpdate = orderRepository.findOrderByOrderId(orderId);
        Order orderUpdated = new Order();
        if(orderToUpdate.isPresent()){
            if (order.getStatus().isPresent() && orderToUpdate.isPresent()) {
                int statusToUpdateOrder = order.getStatus().get();
                int currentOrderStatus = orderToUpdate.get().getOrderStatus().getStatusId();
                orderUpdated = updateOrder(orderToUpdate.get(),statusToUpdateOrder,currentOrderStatus);
            }
        }else{
            throw new NotFoundOrderErrorException("Order not found. Order can't be updated");
        }
        return orderUpdated;
    }

    private Order updateOrder(Order orderToUpdate,int statusToUpdateOrder,int currentOrderStatus){
        Order orderUpdated = orderToUpdate;
        if (!isStatusToUpdateHigherThanCurrentOrderStatus(statusToUpdateOrder,currentOrderStatus)){
            throw new IncorrectUpdateStatusException("Status is low or equal to current order status. Order can't be updated");
        } else if (!isStatusToUpdateNextStatusCurrentOrderStatus(statusToUpdateOrder,currentOrderStatus)){
            throw new IncorrectUpdateStatusException("Status is not the next status allowed. Order can't be updated");
        }else{
            Optional<OrderStatus> orderStatusToUpdate = orderStatusRepository.findOrderStatusByStatusId(statusToUpdateOrder);
            if(orderStatusToUpdate.isEmpty()){
                throw new IncorrectUpdateStatusException("Status is not valid. Order can't be updated");
            }else{
                OrderStatusHistory orderStatusHistory = createOrderStatusHistory(orderToUpdate);
                orderStatusHistoryRepository.save(orderStatusHistory);

                int estimatedProcessDaysForNewStatus = 0;
                if(orderStatusToUpdate.get().getStatusId() != ORDER_DELIVERED_STATUS) {
                    estimatedProcessDaysForNewStatus = generateRandomIntNumber(1, 6);
                }

                OrderStatus orderStatus = orderStatusToUpdate.get();
                orderToUpdate.setOrderStatus(orderStatus);
                orderUpdated.setEstimatedDays(estimatedProcessDaysForNewStatus);
                orderUpdated = orderRepository.save(orderToUpdate);

                if(statusToUpdateOrder == ORDER_DELIVERED_STATUS){
                    orderStatusHistory.setStatusId(ORDER_DELIVERED_STATUS);
                    orderStatusHistory.setCompletedStatusInDays(0);
                    orderStatusHistoryRepository.save(orderStatusHistory);
                }

            }
        }

        return orderUpdated;
    }
    private boolean isStatusToUpdateHigherThanCurrentOrderStatus(int statusToUpdateOrder,int currentOrderStatus){
        return statusToUpdateOrder > currentOrderStatus;
    }

    private boolean isStatusToUpdateNextStatusCurrentOrderStatus(int statusToUpdateOrder,int currentOrderStatus){
        return statusToUpdateOrder == currentOrderStatus + 1;
    }

    private OrderStatusHistory createOrderStatusHistory(Order order){
        OrderStatusHistory orderStatusHistory = new OrderStatusHistory();

        orderStatusHistory.setOrderAmount(order.getAmount());
        orderStatusHistory.setOrderId(order.getOrderId());
        orderStatusHistory.setStatusId(order.getOrderStatus().getStatusId());
        orderStatusHistory.setUsername(order.getBuyer().getUsername());
        orderStatusHistory.setCompletedStatusInDays(order.getEstimatedDays());

        return orderStatusHistory;
    }

    private int generateRandomIntNumber(int minRange,int maxRange){
        Random r = new Random();
        int result = r.nextInt(maxRange-minRange) + minRange;

        return result;
    }
}
