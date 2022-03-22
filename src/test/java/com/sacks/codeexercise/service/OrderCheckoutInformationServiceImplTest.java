package com.sacks.codeexercise.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.sacks.codeexercise.error.NotFoundOrderErrorException;
import com.sacks.codeexercise.model.OrderInformation;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class OrderCheckoutInformationServiceImplTest {

    @Mock
    private OrdersRepository ordersRepository;

    private OrderCheckoutInformationServiceImpl orderCheckoutInformationService;

    @BeforeEach
    void init(){
        orderCheckoutInformationService = new OrderCheckoutInformationServiceImpl(ordersRepository);
    }

    @Test
    public void givenAnOrderIdNonExistingToRetrieveCheckoutInformationWhenServiceTriesToRetrieveInformationThenOrderNotFoundExceptionIsThrown(){

        Optional<Order> order = Optional.empty();
        lenient().when(ordersRepository.findOrderByOrderId(100L)).thenReturn(order);

        Exception exception = assertThrows(NotFoundOrderErrorException.class, () -> {
            orderCheckoutInformationService.retrieveOrderCheckoutInformation(100L);
        });

        String expectedMessage = "Order not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void givenAnOrderIdToRetrieveCheckoutInformationWhenServiceTriesToRetrieveInformationThenOrderCheckoutInformationIsRetrieved(){

        Optional<Order> order = Optional.of(createOrder());
        lenient().when(ordersRepository.findOrderByOrderId(1L)).thenReturn(order);

        OrderInformation orderInformation = orderCheckoutInformationService.retrieveOrderCheckoutInformation(1L);

        assertThat(orderInformation.getOrderId()).isEqualTo(order.get().getOrderId());
        assertThat(orderInformation.getOrderStatus()).isEqualTo(order.get().getOrderStatus().getStatus());
        assertThat(orderInformation.getAmount()).isEqualTo(order.get().getAmount());
        assertThat(orderInformation.getBuyer()).isEqualTo(order.get().getBuyer().getUsername());
        assertThat(orderInformation.getEstimatedDays()).isEqualTo(order.get().getEstimatedDays());
        assertThat(orderInformation.getProducts().size()).isEqualTo(1);
        assertThat(orderInformation.getProducts().get(0).getPrice()).isEqualTo(order.get().getProducts().get(0).getPrice());
        assertThat(orderInformation.getProducts().get(0).getProductName()).isEqualTo(order.get().getProducts().get(0).getName());
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