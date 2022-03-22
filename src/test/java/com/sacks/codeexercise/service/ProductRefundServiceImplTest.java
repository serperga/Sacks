package com.sacks.codeexercise.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.sacks.codeexercise.error.NotFoundOrderErrorException;
import com.sacks.codeexercise.error.ProductNotFoundException;
import com.sacks.codeexercise.model.entities.Customer;
import com.sacks.codeexercise.model.entities.Order;
import com.sacks.codeexercise.model.entities.OrderStatus;
import com.sacks.codeexercise.model.entities.Product;
import com.sacks.codeexercise.repository.OrdersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class ProductRefundServiceImplTest {

    @Mock
    private OrdersRepository ordersRepository;

    private ProductRefundServiceImpl productRefundService;

    @BeforeEach
    void init(){
        productRefundService = new ProductRefundServiceImpl(ordersRepository);
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
            productRefundService.returnProductAndGetRefund(1L,2L);
        });

        String expectedMessage = "Product not found in order. Product can not be refunded";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    private Order createOrder(){
        Order order = new Order();
        List<Product> products = createProducts();
        List<Order> customerOrders = new ArrayList<>();
        Customer customer = createCustomer();
        OrderStatus orderStatus = createOrderStatusOrdered();

        order.setEstimatedDays(1);
        order.setAmount(50.0);
        order.setProducts(products);
        order.setOrderId(15015);
        order.setOrderStatus(orderStatus);
        order.setBuyer(customer);

        customerOrders.add(order);
        customer.setOrders(customerOrders);

        return order;
    }

    private List<Product> createProducts(){
        Product product = new Product();
        product.setProductId(1);
        product.setQuantity(10);
        product.setPrice(50.0);
        product.setName("Product 1");

        List<Product> products = new ArrayList<>();
        products.add(product);

        return products;
    }

    private Customer createCustomer(){
        Customer customer = new Customer();

        customer.setCurrentAmountInWallet(2000.0);
        customer.setInitialAmountInWallet(2000.0);
        customer.setUsername("customer1");

        return customer;
    }

    private OrderStatus createOrderStatusOrdered(){
        OrderStatus statusPackaged = new OrderStatus(1,"Ordered");
        return statusPackaged;
    }
}