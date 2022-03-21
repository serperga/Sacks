package com.sacks.codeexercise.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.sacks.codeexercise.error.IncorrectUpdateStatusException;
import com.sacks.codeexercise.error.NotFoundOrderErrorException;
import com.sacks.codeexercise.model.OrderUpdateInformation;
import com.sacks.codeexercise.model.entities.Customer;
import com.sacks.codeexercise.model.entities.Order;
import com.sacks.codeexercise.model.entities.OrderStatus;
import com.sacks.codeexercise.model.entities.OrderStatusHistory;
import com.sacks.codeexercise.model.entities.Product;
import com.sacks.codeexercise.repository.OrderStatusHistoryRepository;
import com.sacks.codeexercise.repository.OrderStatusRepository;
import com.sacks.codeexercise.repository.OrdersRepository;
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
public class UpdateOrderServiceImplTest {

    @Mock
    private OrdersRepository ordersRepository;

    @Mock
    private OrderStatusRepository orderStatusRepository;

    @Mock
    private OrderStatusHistoryRepository orderStatusHistoryRepository;

    private UpdateOrderServiceImpl updateOrderService;

    @BeforeEach
    void init(){
        updateOrderService = new UpdateOrderServiceImpl(ordersRepository,orderStatusRepository,orderStatusHistoryRepository);
    }

    @Test
    public void givenAnOrderToUpdateWhenTheStatusToUpdateIsEqualOrLessThanTheCurrentOrderStatusThenAnIncorrectUpdateStatusExceptionShouldBeThrown(){

        OrderUpdateInformation orderUpdateInformation = createOrderUpdateInformation(1);
        Optional<Order> order = Optional.of(createOrder(createOrderStatusOrdered()));
        lenient().when(ordersRepository.findOrderByOrderId(1L)).thenReturn(order);

        Exception exception = assertThrows(IncorrectUpdateStatusException.class, () -> {
            updateOrderService.updateOrder(orderUpdateInformation,1L);
        });

        String expectedMessage = "Status is low or equal to current order status. Order can't be updated";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    public void givenAnOrderToUpdateWhenTheOrderDoesNotExistThenAnIncorrectUpdateStatusExceptionShouldBeThrown(){

        OrderUpdateInformation orderUpdateInformation = createOrderUpdateInformation(1);

        Exception exception = assertThrows(NotFoundOrderErrorException.class, () -> {
            updateOrderService.updateOrder(orderUpdateInformation,1L);
        });

        String expectedMessage = "Order not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    public void givenAnOrderToUpdateWhenTheStatusToUpdateIsNotTheNextStatusOfTheCurrentOrderStatusThenAnIncorrectUpdateStatusExceptionShouldBeThrown(){

        OrderUpdateInformation orderUpdateInformation = createOrderUpdateInformation(5);
        Optional<Order> order = Optional.of(createOrder(createOrderStatusOrdered()));
        lenient().when(ordersRepository.findOrderByOrderId(1L)).thenReturn(order);

        Exception exception = assertThrows(IncorrectUpdateStatusException.class, () -> {
            updateOrderService.updateOrder(orderUpdateInformation,1L);
        });

        String expectedMessage = "Status is not the next status allowed. Order can't be updated";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    public void givenAnOrderToUpdateWhenTheStatusToUpdateIsNotACorrectOneThenAnIncorrectUpdateStatusExceptionShouldBeThrown(){

        OrderUpdateInformation orderUpdateInformation = createOrderUpdateInformation(2);
        Optional<Order> order = Optional.of(createOrder(createOrderStatusOrdered()));
        Optional<OrderStatus> orderStatusEmpty = Optional.empty();
        lenient().when(ordersRepository.findOrderByOrderId(1L)).thenReturn(order);
        lenient().when(orderStatusRepository.findOrderStatusByStatusId(2)).thenReturn(orderStatusEmpty);
        Exception exception = assertThrows(IncorrectUpdateStatusException.class, () -> {
            updateOrderService.updateOrder(orderUpdateInformation,1L);
        });

        String expectedMessage = "Status is not valid. Order can't be updated";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    public void givenAnOrderToUpdateWhenTheStatusToUpdateIsCorrectOneThenOrderIsUpdated(){
        OrderUpdateInformation orderUpdateInformation = createOrderUpdateInformation(2);
        Optional<OrderStatus> orderStatusPackaged = Optional.ofNullable(createOrderStatusPackaged());
        Optional<OrderStatus> orderStatusOrdered = Optional.ofNullable(createOrderStatusOrdered());
        Optional<Order> order = Optional.of(createOrder(orderStatusOrdered.get()));
        Optional<Order> orderPackaged = Optional.of(createOrder(orderStatusPackaged.get()));
        lenient().when(ordersRepository.findOrderByOrderId(1L)).thenReturn(order);
        lenient().when(orderStatusRepository.findOrderStatusByStatusId(2)).thenReturn(orderStatusPackaged);
        OrderStatusHistory orderStatusHistory = new OrderStatusHistory(order.get().getOrderId(),order.get().getOrderStatus().getStatusId(),order.get().getBuyer().getUsername(),order.get().getEstimatedDays(),order.get().getAmount());
        lenient().when(orderStatusHistoryRepository.save(orderStatusHistory)).thenReturn(orderStatusHistory);
        lenient().when(ordersRepository.save(any())).thenReturn(orderPackaged.get());
        Order orderUpdated = updateOrderService.updateOrder(orderUpdateInformation,1L);
        assertThat(orderUpdated).isEqualTo(orderPackaged.get());
    }

    private OrderUpdateInformation createOrderUpdateInformation(int statusToUpdate){
        OrderUpdateInformation orderUpdateInformation = new OrderUpdateInformation();
        Optional<Integer> status = Optional.of(statusToUpdate);
        orderUpdateInformation.setStatus(status);

        return orderUpdateInformation;
    }
    private Order createOrder(OrderStatus orderStatus){
        Order order = new Order();
        List<Product> products = createProducts();
        List<Order> customerOrders = new ArrayList<>();
        Customer customer = createCustomer();

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
    private OrderStatus createOrderStatusPackaged(){
        OrderStatus statusPackaged = new OrderStatus(2,"Packaged");
        return statusPackaged;
    }
}