package com.sacks.codeexercise.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sacks.codeexercise.model.entities.Customer;
import com.sacks.codeexercise.model.entities.Order;
import com.sacks.codeexercise.model.entities.OrderStatus;
import com.sacks.codeexercise.model.entities.OrderStatusHistory;
import com.sacks.codeexercise.model.entities.Product;
import com.sacks.codeexercise.repository.CustomerRepository;
import com.sacks.codeexercise.repository.OrderStatusHistoryRepository;
import com.sacks.codeexercise.repository.OrderStatusRepository;
import com.sacks.codeexercise.repository.OrdersRepository;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final CustomerRepository customerRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final OrderStatusHistoryRepository orderStatusHistoryRepository;
    private final OrdersRepository ordersRepository;

    private Map<String,Integer> statusesMap = new HashMap<>();

    private static final String CANCELLED_NOT_ENOUGH_STOCK_STATUS = "Cancelled. Not Enough stock";
    private static final String CANCELLED_NOT_ENOUGH_AMOUNT_IN_WALLET_STATUS = "Cancelled. Not Enough money in customer wallet";
    private static final String CANCELLED_NOT_PRODUCTS_IN_ORDER_AFTER_REFUND_STATUS = "Cancelled. Not products in order after refund";
    private static final String ORDER_DELIVERED_STATUS = "Delivered";

    public DashboardServiceImpl(CustomerRepository customerRepository,
        OrderStatusRepository orderStatusRepository,
        OrderStatusHistoryRepository orderStatusHistoryRepository,
        OrdersRepository ordersRepository) {
        this.customerRepository = customerRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.orderStatusHistoryRepository = orderStatusHistoryRepository;
        this.ordersRepository = ordersRepository;
    }

    @Override
    public List<List<String>> createDashboardForUser() {
        List<Customer> customers = getAllCustomers();
        statusesMap = createStatusesMap();
        List<List<String>> dashboardInformationList = new ArrayList<>();

        customers.forEach(customer -> {
            addOrdersForCustomerToDashboardInformation(customer,dashboardInformationList,CANCELLED_NOT_ENOUGH_STOCK_STATUS);
            addOrdersForCustomerToDashboardInformation(customer,dashboardInformationList,CANCELLED_NOT_ENOUGH_AMOUNT_IN_WALLET_STATUS);
            addOrdersForCustomerToDashboardInformation(customer,dashboardInformationList,CANCELLED_NOT_PRODUCTS_IN_ORDER_AFTER_REFUND_STATUS);
            addOrdersForCustomerToDashboardInformation(customer,dashboardInformationList,ORDER_DELIVERED_STATUS);
        });

        return dashboardInformationList;

    }

    private List<Customer> getAllCustomers(){
        List<Customer> customers = customerRepository.findAll();
        return customers;
    }

    private List<OrderStatus> getAllOrderStatus(){
        List<OrderStatus> orderStatuses = orderStatusRepository.findAll();
        return orderStatuses;
    }

    private Map<String,Integer> createStatusesMap(){
        Map<String,Integer> statusesMap = new HashMap<>();
        List<OrderStatus> orderStatuses = getAllOrderStatus();
        orderStatuses.forEach(orderStatus -> {
            statusesMap.put(orderStatus.getStatus(),orderStatus.getStatusId());
        });
        return statusesMap;
    }

    private List<Order> getOrdersByCustomerAndStatus(int statusId, String username){
        List<Order> orderStatusHistories = new ArrayList<>();
        Optional<List<Order>> orderStatusHistoryList = ordersRepository.findOrdersByStatusAndCustomer(statusId,username);
        if(orderStatusHistoryList.isPresent()){
            return orderStatusHistoryList.get();
        }
        return orderStatusHistories;
    }

    private void addOrdersForCustomerToDashboardInformation(Customer customer,List<List<String>> dashboardInformationList,String status){
        int cancelledNotEnoughStockStatusId = statusesMap.get(status);
        List<Order> cancelledNotStockOrders = getOrdersByCustomerAndStatus(cancelledNotEnoughStockStatusId,customer.getUsername());
        List<String> dataForCSVFile = new ArrayList<>();
        if (!cancelledNotStockOrders.isEmpty()) {
            cancelledNotStockOrders.forEach(order -> {
                dataForCSVFile.add(customer.getUsername());
                Double initialAmountInWallet = customer.getInitialAmountInWallet();
                dataForCSVFile.add(Double.toString(initialAmountInWallet));
                Double currentAmountInWallet = customer.getCurrentAmountInWallet();
                dataForCSVFile.add(Double.toString(currentAmountInWallet));
                Long orderId = order.getOrderId();
                dataForCSVFile.add(Long.toString(orderId));
                dataForCSVFile.add(status);

                List<Product> products = order.getProducts();
                String productsInOrder = "";
                if (!products.isEmpty()){
                    productsInOrder = products.stream().map(Product::getName)
                        .collect(Collectors.joining(", "));
                }
                dataForCSVFile.add(productsInOrder);
                if (status.contains("Cancelled")){
                    dataForCSVFile.add("");
                    dataForCSVFile.add("");
                    dataForCSVFile.add("");
                    dataForCSVFile.add("");
                } else {
                    //Days of each status if the order is not cancelled. Void value as this is a cancelled order
                    Optional<List<OrderStatusHistory>> orderStatusHistoryList = orderStatusHistoryRepository
                        .findOrderStatusHistoryByOrderIdOrderByStatusIdAsc(orderId);
                    if (orderStatusHistoryList.isPresent()) {
                        orderStatusHistoryList.get().forEach(orderStatusHistory -> {
                            Integer days = orderStatusHistory.getCompletedStatusInDays();
                            dataForCSVFile.add(Integer.toString(days));
                        });

                    } else {
                        dataForCSVFile.add("");
                        dataForCSVFile.add("");
                        dataForCSVFile.add("");
                        dataForCSVFile.add("");
                    }
                }
                dashboardInformationList.add(dataForCSVFile);
            });
        }
    }
}
