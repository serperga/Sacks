package com.sacks.codeexercise.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sacks.codeexercise.model.entities.Customer;
import com.sacks.codeexercise.model.entities.OrderStatus;
import com.sacks.codeexercise.model.entities.OrderStatusHistory;
import com.sacks.codeexercise.model.entities.Product;
import com.sacks.codeexercise.repository.CustomerRepository;
import com.sacks.codeexercise.repository.OrderStatusRepository;
import com.sacks.codeexercise.repository.ProductRepository;

@Service
public class SimulateServiceImpl implements SimulateService {

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderStatusRepository orderStatusRepository;

    private static final int NUMBER_OF_CUSTOMERS = 200;
    private static final int NUMBER_OF_PRODUCTS = 20;

    private static final int MINIMUM_QUANTITY_OF_PRODUCT = 1;
    private static final int MAXIMUM_QUANTITY_OF_PRODUCT = 100;

    private static final double MINIMUM_PRODUCT_PRICE = 1.0;
    private static final double MAXIMUM_PRODUCT_PRICE = 200.0;

    private static final double MINIMUM_AMOUNT_IN_CUSTOMER_WALLET = 100.0;
    private static final double MAXIMUM_AMOUNT_IN_CUSTOMER_WALLET  = 20000.0;

    @Autowired
    public SimulateServiceImpl(CustomerRepository customerRepository, ProductRepository productRepository, OrderStatusRepository orderStatusRepository){
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.orderStatusRepository = orderStatusRepository;
    }

    @Override
    public void simulateSystem() {
        createCustomersInDatabase();
        createProductsInDatabase();
        createOrderStatusInDatabase();
    }

    private void createCustomersInDatabase(){
        for (int i = 0; i < NUMBER_OF_CUSTOMERS; i++) {
            double amountInWallet = generateRandomAmountOfMoneyInCustomerWallet(MINIMUM_AMOUNT_IN_CUSTOMER_WALLET,MAXIMUM_AMOUNT_IN_CUSTOMER_WALLET);

            Customer customer = new Customer();
            customer.setUsername("user"+i);
            customer.setInitialAmountInWallet(amountInWallet);
            customer.setCurrentAmountInWallet(amountInWallet);

            customer = customerRepository.save(customer);
        }
    }

    private void createProductsInDatabase(){
        for (int i = 0; i < NUMBER_OF_PRODUCTS; i++) {

            int productQuantity = generateRandomNumberOfProducts(MINIMUM_QUANTITY_OF_PRODUCT,MAXIMUM_QUANTITY_OF_PRODUCT);
            double price = generateRandomPriceForProduct(MINIMUM_PRODUCT_PRICE,MAXIMUM_PRODUCT_PRICE);

            Product product = new Product();
            product.setName("Product" + i);
            product.setQuantity(productQuantity);
            product.setPrice(price);

            product = productRepository.save(product);
        }
    }

    private void createOrderStatusInDatabase(){

        List<OrderStatus> orderStatuses = new ArrayList<>();

        Set<OrderStatusHistory> orderStatusHistorySet = new HashSet<>();

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
        OrderStatus statusCancelled = new OrderStatus(6,"Cancelled");
        orderStatuses.add(statusCancelled);

        orderStatusRepository.saveAll(orderStatuses);

    }

    private int generateRandomNumberOfProducts(int minimumQuantityOfProduct, int maximumQuantityOfProduct){
        Random r = new Random();
        int result = r.nextInt(maximumQuantityOfProduct-minimumQuantityOfProduct) + minimumQuantityOfProduct;

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
}
