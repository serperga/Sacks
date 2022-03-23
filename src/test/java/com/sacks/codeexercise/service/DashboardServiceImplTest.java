package com.sacks.codeexercise.service;

import com.sacks.codeexercise.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DashboardServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    private DashboardServiceImpl dashboardService;
    @BeforeEach
    void init(){
        dashboardService = new DashboardServiceImpl(customerRepository);
    }

}