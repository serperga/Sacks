package com.sacks.codeexercise.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sacks.codeexercise.model.OrderUpdateInformation;
import com.sacks.codeexercise.model.entities.Customer;
import com.sacks.codeexercise.model.entities.Order;
import com.sacks.codeexercise.model.entities.OrderStatus;
import com.sacks.codeexercise.model.entities.OrderStatusHistory;
import com.sacks.codeexercise.model.entities.Product;
import com.sacks.codeexercise.repository.CustomerRepository;
import com.sacks.codeexercise.repository.OrderStatusHistoryRepository;
import com.sacks.codeexercise.repository.OrderStatusRepository;
import com.sacks.codeexercise.repository.OrdersRepository;
import com.sacks.codeexercise.repository.ProductRepository;

@Service
public class SimulateServiceImpl implements SimulateService {

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final OrderStatusHistoryRepository orderStatusHistoryRepository;
    private final OrdersRepository orderRepository;

    private final UpdateOrderService updateOrderService;

    private static final int NUMBER_OF_CUSTOMERS = 200;
    private static final int NUMBER_OF_PRODUCTS = 20;

    private static final int MINIMUM_QUANTITY_OF_PRODUCT = 1;
    private static final int MAXIMUM_QUANTITY_OF_PRODUCT = 300;

    private static final double MINIMUM_PRODUCT_PRICE = 1.0;
    private static final double MAXIMUM_PRODUCT_PRICE = 200.0;

    private static final double MINIMUM_AMOUNT_IN_CUSTOMER_WALLET = 100.0;
    private static final double MAXIMUM_AMOUNT_IN_CUSTOMER_WALLET  = 20000.0;

    private static final int MINIMUM_NUMBER_OF_PRODUCT = 1;
    private static final int MAXIMUM_NUMBER_OF_PRODUCT = 6;

    private static final int MINIMUM_PRODUCT_ID = 1;
    private static final int MAXIMUM_PRODUCT_ID = 20;

    private static final int ORDER_CREATED_STATUS = 0;
    private static final int ORDER_SENT_TO_WAREHOUSE_STATUS = 1;
    private static final int ORDER_PACKAGED_STATUS = 2;
    private static final int ORDER_PICKED_BY_CARRIER_STATUS = 3;
    private static final int ORDER_OUT_FOR_DELIVERY_STATUS = 4;
    private static final int ORDER_DELIVERED_STATUS = 5;
    private static final int CANCELLED_STATUS_NOT_ENOUGH_STOCK = 6;
    private static final int CANCELLED_STATUS_NOT_ENOUGH_MONEY_IN_CUSTOMER_WALLET = 7;

    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);


    @Autowired
    public SimulateServiceImpl(CustomerRepository customerRepository, ProductRepository productRepository,
        OrderStatusRepository orderStatusRepository, OrderStatusHistoryRepository orderStatusHistoryRepository,
        OrdersRepository orderRepository, UpdateOrderService updateOrderService){
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.orderStatusHistoryRepository = orderStatusHistoryRepository;
        this.orderRepository = orderRepository;
        this.updateOrderService = updateOrderService;
    }



    @Override
    public void simulateSystem() {
        List<Customer> customers = createCustomersInDatabase();
        List<Product> products = createProductsInDatabase();
        List<OrderStatus> orderStatuses = createOrderStatusInDatabase();
        createOrdersInDatabase(products, orderStatuses, customers);
        processOrdersToNextStage(ORDER_CREATED_STATUS, ORDER_SENT_TO_WAREHOUSE_STATUS);
        processOrdersToNextStage(ORDER_SENT_TO_WAREHOUSE_STATUS, ORDER_PACKAGED_STATUS);
        processOrdersToNextStage(ORDER_PACKAGED_STATUS, ORDER_PICKED_BY_CARRIER_STATUS);
        processOrdersToNextStage(ORDER_PICKED_BY_CARRIER_STATUS,ORDER_OUT_FOR_DELIVERY_STATUS);
        processOrdersToNextStage(ORDER_OUT_FOR_DELIVERY_STATUS,ORDER_DELIVERED_STATUS);
    }

    private void processOrdersToNextStage(int currentStage,int nextStage){
        OrderUpdateInformation updateInformation = new OrderUpdateInformation();
        Optional<Integer> statusToUpdate = Optional.of(nextStage);
        updateInformation.setStatus(statusToUpdate);
        Optional<List<Order>> orderList = orderRepository.findOrdersByStatus(currentStage);
        List<Order> orders = orderList.get();
        orders.forEach(order -> {
            simulateStageProcessingForeachOrder(order,updateInformation);
        });
    }
    private void simulateStageProcessingForeachOrder(Order order,OrderUpdateInformation orderUpdateInformation) {

        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSS").format(new java.util.Date());
        System.out.println("Orders update starts at : " + timeStamp);
        List<Order> orders = orderRepository.findAll();


            updateOrderService.updateOrder(orderUpdateInformation,order.getOrderId());
            int delay = order.getEstimatedDays() * 10;
            System.out.println("Delay is : " + delay);
            Runnable runnableTask = () -> {
                String timeStampUpdate = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSS").format(new java.util.Date());
                System.out.println("Order" + order.getOrderId() +" update at : " + timeStampUpdate);
                updateOrderService.updateOrder(orderUpdateInformation,order.getOrderId());
            };

            if(order.getOrderStatus().getStatusId() < ORDER_DELIVERED_STATUS){
                executorService.schedule(runnableTask,delay, TimeUnit.MILLISECONDS);
            }

    }

    private List<Customer> createCustomersInDatabase(){

        List<Customer> customers = new ArrayList<>();

        for (int i = 0; i < NUMBER_OF_CUSTOMERS; i++) {
            double amountInWallet = generateRandomAmountOfMoneyInCustomerWallet(MINIMUM_AMOUNT_IN_CUSTOMER_WALLET,MAXIMUM_AMOUNT_IN_CUSTOMER_WALLET);

            Customer customer = new Customer();
            customer.setUsername("user"+i);
            customer.setInitialAmountInWallet(amountInWallet);
            customer.setCurrentAmountInWallet(amountInWallet);

            customer = customerRepository.save(customer);
            customers.add(customer);
        }

        return customers;
    }

    private List<Product> createProductsInDatabase(){
        List<Product> productList = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_PRODUCTS; i++) {

            int productQuantity = generateRandomQuantityOfProducts(MINIMUM_QUANTITY_OF_PRODUCT,MAXIMUM_QUANTITY_OF_PRODUCT);
            double price = generateRandomPriceForProduct(MINIMUM_PRODUCT_PRICE,MAXIMUM_PRODUCT_PRICE);

            Product product = new Product();
            product.setName("Product" + i);
            product.setQuantity(productQuantity);
            product.setPrice(price);

            productList.add(product);
            product = productRepository.save(product);
        }
        return productList;
    }

    private List<OrderStatus> createOrderStatusInDatabase(){

        List<OrderStatus> orderStatuses = new ArrayList<>();

        OrderStatus statusOrdered = new OrderStatus(0,"Ordered");
        orderStatuses.add(statusOrdered);
        OrderStatus statusSentToWarehouse = new OrderStatus(1,"Sent to Warehouse");
        orderStatuses.add(statusSentToWarehouse);
        OrderStatus statusPackaged = new OrderStatus(2,"Packaged");
        orderStatuses.add(statusPackaged);
        OrderStatus statusPickedUp = new OrderStatus(3,"Carrier picked up");
        orderStatuses.add(statusPickedUp);
        OrderStatus statusOutForDelivery = new OrderStatus(4,"Out for delivery");
        orderStatuses.add(statusOutForDelivery);
        OrderStatus statusDelivered = new OrderStatus(5,"Delivered");
        orderStatuses.add(statusDelivered);
        OrderStatus statusCancelledNotEnoughInventory = new OrderStatus(6,"Cancelled. Not Enough stock");
        orderStatuses.add(statusCancelledNotEnoughInventory);
        OrderStatus statusCancelledNotEnoughMoneyInCustomerWallet = new OrderStatus(7,"Cancelled. Not Enough money in customer wallet");
        orderStatuses.add(statusCancelledNotEnoughMoneyInCustomerWallet);
        OrderStatus statusCancelledNotProductsInOrderAfterRefund = new OrderStatus(8,"Cancelled. Not products in order after refund");
        orderStatuses.add(statusCancelledNotProductsInOrderAfterRefund);

        orderStatusRepository.saveAll(orderStatuses);
        return orderStatuses;
    }

    private void createOrdersInDatabase(List<Product> productList,
        List<OrderStatus> orderStatusList, List<Customer> customers){

        customers.forEach(customer -> {
            int estimatedDaysForStatusToBeCompleted = generateRandomIntNumber(1,5);

            int numberOfProducts = generateRandomNumberOfProducts(MINIMUM_NUMBER_OF_PRODUCT,MAXIMUM_NUMBER_OF_PRODUCT);

            List<Product> orderProductList = new ArrayList<>();
            for (int j =0; j < numberOfProducts; j++){
                int productId = generateRandomProduct(MINIMUM_PRODUCT_ID,MAXIMUM_PRODUCT_ID);
                int productListIndex = productId -1;
                Product productOrdered = productList.get(productListIndex);
                orderProductList.add(productOrdered);
            }

            //Check if there is enough quantity of products
            boolean productsWithStock = true;
            int productIndex = 0;
            while(productsWithStock && productIndex<orderProductList.size()){
                Product product = orderProductList.get(productIndex);
                int productQuantity = product.getQuantity();
                if (productQuantity == 0){
                    productsWithStock = false;
                }
                productIndex++;
            }
            if (productsWithStock) {
                //If there is enough products in stocks, update the quantity
                orderProductList.forEach(product -> product.setQuantity(product.getQuantity() - 1));
                productRepository.saveAll(orderProductList);
                //Calculate OrderAmount
                Double orderAmount = orderProductList.stream()
                    .collect(Collectors.summingDouble(Product::getPrice));
                //Check if the customer wallet has enough money
                Double customerWallet = customer.getCurrentAmountInWallet();
                if (customerWallet > orderAmount){
                    OrderStatus orderStatus = orderStatusList.get(ORDER_CREATED_STATUS);
                    Order order = createOrder(customer,orderStatus,orderProductList,orderAmount,estimatedDaysForStatusToBeCompleted);
                    order = orderRepository.save(order);
                    double customerWalletAmountAfterOrderCreated = customerWallet - orderAmount;
                    customer.setCurrentAmountInWallet(customerWalletAmountAfterOrderCreated);
                    customerRepository.save(customer);
                } else {
                    //Create cancelled order with no enough money status
                    OrderStatus cancelledStatus = orderStatusList.get(CANCELLED_STATUS_NOT_ENOUGH_MONEY_IN_CUSTOMER_WALLET);
                    Order cancelledOrder = createOrder(customer,cancelledStatus,orderProductList,null,0);
                    cancelledOrder = orderRepository.save(cancelledOrder);
                    OrderStatusHistory orderCancelledToOrdersHistory = moveClosedOrderToOrderHistory(cancelledOrder,CANCELLED_STATUS_NOT_ENOUGH_MONEY_IN_CUSTOMER_WALLET);
                }
            } else {
                //Create cancelled order with no stock status
                OrderStatus cancelledStatus = orderStatusList.get(CANCELLED_STATUS_NOT_ENOUGH_STOCK);
                Order cancelledOrder = createOrder(customer,cancelledStatus,orderProductList,null,0);
                cancelledOrder = orderRepository.save(cancelledOrder);
                OrderStatusHistory orderCancelledToOrdersHistory = moveClosedOrderToOrderHistory(cancelledOrder,CANCELLED_STATUS_NOT_ENOUGH_STOCK);
            }
        });
    }

    private Order createOrder(Customer customer, OrderStatus status, List<Product> products, Double orderAmount, int daysToCompleteState){
        Order order = new Order();
        order.setBuyer(customer);
        order.setOrderStatus(status);
        order.setProducts(products);
        order.setAmount(orderAmount);
        order.setEstimatedDays(daysToCompleteState);
        order = orderRepository.save(order);

        return order;
    }

    private OrderStatusHistory moveClosedOrderToOrderHistory(Order orderToClose,int status){
        OrderStatusHistory order = new OrderStatusHistory();
        order.setOrderId(orderToClose.getOrderId());
        order.setUsername(orderToClose.getBuyer().getUsername());
        order.setStatusId(status);
        order.setCompletedStatusInDays(0);
        order = orderStatusHistoryRepository.save(order);

        return order;
    }

    private int generateRandomQuantityOfProducts(int minimumQuantityOfProduct, int maximumQuantityOfProduct){
        int result = generateRandomIntNumber(minimumQuantityOfProduct,maximumQuantityOfProduct);
        return result;
    }

    private int generateRandomNumberOfProducts(int minimumNumberOfProducts, int maximumNumberOfProduct){
        int result = generateRandomIntNumber(minimumNumberOfProducts,maximumNumberOfProduct);
        return result;
    }

    private int generateRandomProduct(int minimumProduct, int maximumProduct){
        int result = generateRandomIntNumber(minimumProduct,maximumProduct);
        return result;
    }

    private double generateRandomPriceForProduct(double minimumPrice, double maximumPrice){
        double price = generateRandomDoubleValueWithTwoDecimalsRounded(minimumPrice,maximumPrice);
        return price;
    }

    private double generateRandomAmountOfMoneyInCustomerWallet(double minimumAmountInWallet, double maximumAmountInWallet){
        double amountInWallet = generateRandomDoubleValueWithTwoDecimalsRounded(minimumAmountInWallet,maximumAmountInWallet);
        return amountInWallet;
    }

    private double generateRandomDoubleValueWithTwoDecimalsRounded(double minRange,double maxRange){

        double randomValue = new Random().doubles(minRange, maxRange).limit(1).findFirst().getAsDouble();

        BigDecimal doubleValue = new BigDecimal(randomValue).setScale(2, RoundingMode.HALF_EVEN);
        return doubleValue.doubleValue();
    }

    private int generateRandomIntNumber(int minRange,int maxRange){
        Random r = new Random();
        int result = r.nextInt(maxRange-minRange) + minRange;

        return result;
    }
}
