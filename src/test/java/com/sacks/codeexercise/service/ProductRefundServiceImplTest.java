package com.sacks.codeexercise.service;

import java.util.Optional;

import com.sacks.codeexercise.error.NotFoundOrderErrorException;
import com.sacks.codeexercise.model.entities.Order;
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
}