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
    private static final String OUT_FOR_DELIVERY_STATUS = "Out for delivery";
    private static final String PICKED_BY_CARRIER_STATUS = "Carrier picked up";
    private static final String PACKAGED_STATUS = "Packaged";
    private static final String SENT_TO_WAREHOUSE_STATUS = "Sent to Warehouse";
    private static final String ORDERED_STATUS = "Ordered";

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
    public List<List<String>> createDashboardForUser(String username) {

        statusesMap = createStatusesMap();
        Customer customer = customerRepository.findByUsername(username);
        List<List<String>> dashboardInformationList = new ArrayList<>();

        createDashboardInformationForCustomer(customer,dashboardInformationList);

        createMaxMinAvgSectionDorDashboard(dashboardInformationList);

        return dashboardInformationList;

    }

    @Override
    public List<List<String>> createDashboardForUsers() {
        List<Customer> customers = getAllCustomers();
        statusesMap = createStatusesMap();
        List<List<String>> dashboardInformationList = new ArrayList<>();

        customers.forEach(customer -> {
            createDashboardInformationForCustomer(customer,dashboardInformationList);
        });

        createMaxMinAvgSectionDorDashboard(dashboardInformationList);

        return dashboardInformationList;
    }

    private void createDashboardInformationForCustomer(Customer customer,List<List<String>> dashboardInformationList){
        addOrdersForCustomerToDashboardInformation(customer,dashboardInformationList,CANCELLED_NOT_ENOUGH_STOCK_STATUS);
        addOrdersForCustomerToDashboardInformation(customer,dashboardInformationList,CANCELLED_NOT_ENOUGH_AMOUNT_IN_WALLET_STATUS);
        addOrdersForCustomerToDashboardInformation(customer,dashboardInformationList,CANCELLED_NOT_PRODUCTS_IN_ORDER_AFTER_REFUND_STATUS);
        addOrdersForCustomerToDashboardInformation(customer,dashboardInformationList,ORDER_DELIVERED_STATUS);
    }

    private void createMaxMinAvgSectionDorDashboard(List<List<String>> dashboardInformationList){
        addBlankLinesToDashboardData(dashboardInformationList);
        addBlankLinesToDashboardData(dashboardInformationList);
        addBlankLinesToDashboardData(dashboardInformationList);

        addMaximumMinimumAndAverageOfDaysForEachStatusToDashboardInformation(ORDERED_STATUS,dashboardInformationList);
        addMaximumMinimumAndAverageOfDaysForEachStatusToDashboardInformation(SENT_TO_WAREHOUSE_STATUS,dashboardInformationList);
        addMaximumMinimumAndAverageOfDaysForEachStatusToDashboardInformation(PACKAGED_STATUS,dashboardInformationList);
        addMaximumMinimumAndAverageOfDaysForEachStatusToDashboardInformation(PICKED_BY_CARRIER_STATUS,dashboardInformationList);
        addMaximumMinimumAndAverageOfDaysForEachStatusToDashboardInformation(OUT_FOR_DELIVERY_STATUS,dashboardInformationList);
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
                        dataForCSVFile.add("");
                    }
                }
                dashboardInformationList.add(dataForCSVFile);
            });
        }
    }

    private void addBlankLinesToDashboardData(List<List<String>> dashboardInformationList){
        List<String> blankLine = new ArrayList<>();
        for (int i = 0; i <10; i++){
            blankLine.add("");
        }
        dashboardInformationList.add(blankLine);
    }
    private void addMaximumMinimumAndAverageOfDaysForEachStatusToDashboardInformation(String status,List<List<String>> dashboardInformationList){
        int statusToCalculate = statusesMap.get(status).intValue();
        Optional<List<OrderStatusHistory>> orderStatusHistoryList = orderStatusHistoryRepository.findOrderStatusHistoryByStatusId(statusToCalculate);
        List<OrderStatusHistory> orderStatusHistories = orderStatusHistoryList.get();

        int min = orderStatusHistories.stream().mapToInt(OrderStatusHistory::getCompletedStatusInDays).min().orElse(0);
        int max = orderStatusHistories.stream().mapToInt(OrderStatusHistory::getCompletedStatusInDays).max().orElse(0);
        double intAverage = orderStatusHistories.stream()
            .mapToInt(OrderStatusHistory::getCompletedStatusInDays)
            .average()
            .orElse(0.0);

        List<String> data = new ArrayList<>();
        String minTitle = "minimum days for " + status + "Status is " + min + "days";
        String maxTitle = "maximum days for " + status + "Status is " + max + "days";
        String avgTitle = "average days for " + status + "Status is " + intAverage + "days";
        data.add(minTitle);
        data.add(maxTitle);
        data.add(avgTitle);

        dashboardInformationList.add(data);
    }
}
