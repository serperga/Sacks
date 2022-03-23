package com.sacks.codeexercise.service;

import com.sacks.codeexercise.repository.CustomerRepository;
import com.sacks.codeexercise.repository.OrderStatusHistoryRepository;
import com.sacks.codeexercise.repository.OrderStatusRepository;
import com.sacks.codeexercise.repository.OrdersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DashboardServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private OrderStatusRepository orderStatusRepository;
    @Mock
    private OrderStatusHistoryRepository orderStatusHistoryRepository;
    @Mock
    private OrdersRepository ordersRepository;

    private DashboardServiceImpl dashboardService;
    @BeforeEach
    void init(){
        dashboardService = new DashboardServiceImpl(customerRepository, orderStatusRepository,
            orderStatusHistoryRepository, ordersRepository);
    }

}