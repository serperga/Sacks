package com.sacks.codeexercise.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.sacks.codeexercise.error.NotFoundOrderErrorException;
import com.sacks.codeexercise.error.ProductNotFoundException;
import com.sacks.codeexercise.model.OrderRefundInformation;
import com.sacks.codeexercise.model.entities.Customer;
import com.sacks.codeexercise.model.entities.Order;
import com.sacks.codeexercise.model.entities.OrderStatus;
import com.sacks.codeexercise.model.entities.Product;
import com.sacks.codeexercise.repository.CustomerRepository;
import com.sacks.codeexercise.repository.OrderStatusHistoryRepository;
import com.sacks.codeexercise.repository.OrderStatusRepository;
import com.sacks.codeexercise.repository.OrdersRepository;
import com.sacks.codeexercise.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class ProductRefundServiceImplTest {

    @Mock
    private OrdersRepository ordersRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private OrderStatusHistoryRepository orderStatusHistoryRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private OrderStatusRepository orderStatusRepository;

    private ProductRefundServiceImpl productRefundService;

    @BeforeEach
    void init(){
        productRefundService = new ProductRefundServiceImpl(ordersRepository, customerRepository,
            orderStatusHistoryRepository, productRepository, orderStatusRepository);
    }

    @Test
    public void givenAnOrderIdNonExistingToRefundAProductWhenServiceTriesToFindOrderInformationInformationThenOrderNotFoundExceptionIsThrown(){

        Optional<Order> order = Optional.empty();
        lenient().when(ordersRepository.findOrderByOrderId(100L)).thenReturn(order);

        Exception exception = assertThrows(NotFoundOrderErrorException.class, () -> {
            productRefundService.returnProductAndGetRefund(100L,1L);
        });

        String expectedMessage = "Order not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void givenAnOrderThatExistTryingToRefundAProductThatNotExistInTheOrderWhenServiceTriesToRefundProductThenProductNotFoundExceptionIsThrown(){

        Optional<Order> order = Optional.of(createOrder());
        lenient().when(ordersRepository.findOrderByOrderId(1L)).thenReturn(order);

        Exception exception = assertThrows(ProductNotFoundException.class, () -> {
            productRefundService.returnProductAndGetRefund(1L,3L);
        });

        String expectedMessage = "Product not found in order. Product can not be refunded";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void givenAnOrderThatExistTryingToRefundAProductWhenServiceTriesToRefundProductThenOrderIsUpdatedAndCustomerIsRefunded(){

        Optional<Order> order = Optional.of(createOrder());
        Order orderRefunded = createOrderRefunded();
        Customer customer = createCustomer();
        Customer customerRefunded = createCustomerRefunded();
        Product productRefunded = createProductRefunded();
        lenient().when(ordersRepository.findOrderByOrderId(1L)).thenReturn(order);
        lenient().when(customerRepository.findByUsername("customer1")).thenReturn(customer);
        lenient().when(customerRepository.save(any())).thenReturn(customerRefunded);
        lenient().when(ordersRepository.save(any())).thenReturn(orderRefunded);
        lenient().when(productRepository.save(productRefunded)).thenReturn(productRefunded);

        final OrderRefundInformation orderRefundInformation = productRefundService.returnProductAndGetRefund(1L, 1L);

        assertThat(orderRefundInformation.getOrderId()).isEqualTo(orderRefunded.getOrderId());
        assertThat(orderRefundInformation.getOrderStatus()).isEqualTo(orderRefunded.getOrderStatus().getStatus());
        assertThat(orderRefundInformation.getAmount()).isEqualTo(orderRefunded.getAmount());
        assertThat(orderRefundInformation.getBuyer()).isEqualTo(orderRefunded.getBuyer().getUsername());
        assertThat(orderRefundInformation.getAmountInWalletAfterRefund()).isEqualTo(orderRefunded.getBuyer().getCurrentAmountInWallet());
        assertThat(orderRefundInformation.getProducts().get(0).getProductName()).isEqualTo(orderRefunded.getProducts().get(0).getName());
        assertThat(orderRefundInformation.getProducts().get(0).getPrice()).isEqualTo(orderRefunded.getProducts().get(0).getPrice());

    }

    private Order createOrder(){
        Order order = new Order();
        List<Product> products = createProducts();
        List<Order> customerOrders = new ArrayList<>();
        Customer customer = createCustomerRefunded();
        OrderStatus orderStatus = createOrderStatusOrdered();

        order.setEstimatedDays(1);
        order.setAmount(150.0);
        order.setProducts(products);
        order.setOrderId(1);
        order.setOrderStatus(orderStatus);
        order.setBuyer(customer);

        customerOrders.add(order);
        customer.setOrders(customerOrders);

        return order;
    }

    private Order createOrderRefunded(){
        Order order = new Order();
        List<Product> products = createProductsRefunded();
        List<Order> customerOrders = new ArrayList<>();
        Customer customer = createCustomerRefunded();
        OrderStatus orderStatus = createOrderStatusOrdered();

        order.setEstimatedDays(1);
        order.setAmount(100.0);
        order.setProducts(products);
        order.setOrderId(1);
        order.setOrderStatus(orderStatus);
        order.setBuyer(customer);

        customerOrders.add(order);
        customer.setOrders(customerOrders);

        return order;
    }

    private List<Product> createProducts(){
        Product product1 = new Product();
        product1.setProductId(1);
        product1.setQuantity(10);
        product1.setPrice(50.0);
        product1.setName("Product 1");

        Product product2 = new Product();
        product2.setProductId(2);
        product2.setQuantity(10);
        product2.setPrice(100.0);
        product2.setName("Product 2");

        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        return products;
    }

    private Customer createCustomer(){
        Customer customer = new Customer();

        customer.setCurrentAmountInWallet(1850.0);
        customer.setInitialAmountInWallet(2000.0);
        customer.setUsername("customer1");

        return customer;
    }

    private Customer createCustomerRefunded(){
        Customer customer = new Customer();

        customer.setCurrentAmountInWallet(1900.0);
        customer.setInitialAmountInWallet(2000.0);
        customer.setUsername("customer1");

        return customer;
    }

    private OrderStatus createOrderStatusOrdered(){
        OrderStatus statusPackaged = new OrderStatus(1,"Ordered");
        return statusPackaged;
    }

    private List<Product> createProductsRefunded() {
        Product product = new Product();
        product.setProductId(2);
        product.setQuantity(10);
        product.setPrice(100.0);
        product.setName("Product 2");

        List<Product> products = new ArrayList<>();
        products.add(product);

        return products;
    }

    private Product createProductRefunded() {
        Product product = new Product();
        product.setProductId(1);
        product.setQuantity(10);
        product.setPrice(100.0);
        product.setName("Product 1");

        return product;
    }
}