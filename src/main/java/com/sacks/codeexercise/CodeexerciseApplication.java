package com.sacks.codeexercise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CodeexerciseApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodeexerciseApplication.class, args);
	}

/*	@Bean
	public CommandLineRunner demo(CustomerRepository customerRepository, OrdersRepository ordersRepository) {
		return (args) -> {
			for (int i = 0; i < 200; i++) {
				List ordersList = new ArrayList();
				Customer customer = new Customer();
				customer.setUsername("user"+i);
				customer.setInitialAmountInWallet(200.0);
				customer.setCurrentAmountInWallet(200.0);

				Order customerOrder = new Order();
				customerOrder.setAmount(200.0);
				customerOrder.setStatus("Created");
				customerOrder.setEstimatedDays(1);

				ordersList.add(customerOrder);
				customerOrder.setBuyer(customer);

				customer.setOrders(ordersList);
				customer = customerRepository.save(customer);

			}
		};
	}*/
}
