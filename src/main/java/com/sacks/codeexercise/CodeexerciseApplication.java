package com.sacks.codeexercise;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.sacks.codeexercise.model.entities.Buyers;
import com.sacks.codeexercise.repository.BuyerRepository;

@SpringBootApplication
public class CodeexerciseApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodeexerciseApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(BuyerRepository repository) {
		return (args) -> {
			List buyerList = new ArrayList();
			for (int i = 0; i < 200; i++) {
				Buyers buyer = new Buyers("example" + i, 200.0, 200.0);
				buyerList.add(buyer);
			}
			repository.saveAll(buyerList);
		};
	}
}
