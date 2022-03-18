package com.sacks.codeexercise.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sacks.codeexercise.model.entities.Customer;
import com.sacks.codeexercise.model.entities.Order;
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
    private static final int MAXIMUM_QUANTITY_OF_PRODUCT = 300;

    private static final double MINIMUM_PRODUCT_PRICE = 1.0;
    private static final double MAXIMUM_PRODUCT_PRICE = 200.0;

    private static final double MINIMUM_AMOUNT_IN_CUSTOMER_WALLET = 100.0;
    private static final double MAXIMUM_AMOUNT_IN_CUSTOMER_WALLET  = 20000.0;

    private static final int MINIMUM_NUMBER_OF_PRODUCT = 1;
    private static final int MAXIMUM_NUMBER_OF_PRODUCT = 5;

    private static final int MINIMUM_PRODUCT_ID = 1;
    private static final int MAXIMUM_PRODUCT_ID = 20;

    @Autowired
    public SimulateServiceImpl(CustomerRepository customerRepository, ProductRepository productRepository, OrderStatusRepository orderStatusRepository){
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.orderStatusRepository = orderStatusRepository;
    }

    @Override
    public void simulateSystem() {
        createCustomersInDatabase();
        List<Product> productList = createProductsInDatabase();
        List<OrderStatus> orderStatusList = createOrderStatusInDatabase();
        createOrdersInDatabase(productList, orderStatusList);
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
        return orderStatuses;
    }

    private void createOrdersInDatabase(List<Product> productList,
        List<OrderStatus> orderStatusList){
        Order customerOrder = new Order();

        int numberOfStatusEstimatedDays = generateRandomIntNumber(1,5);
        customerOrder.setEstimatedDays(numberOfStatusEstimatedDays);

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
        //If there is enough products in stocks, update the quantity
        orderProductList.forEach(product -> product.setQuantity(product.getQuantity()-1));
        //save to Database
        productRepository.saveAll(orderProductList);

        Double sum = orderProductList.stream()
            .collect(Collectors.summingDouble(Product::getPrice));


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
