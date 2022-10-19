package com.example.ib;

import com.example.ib.service.rabbit.RabbitService;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EnableRabbit
public class IbFxApplication {

	public static void main(String[] args) {
		SpringApplication.run(IbFxApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(RabbitService rabbitService){
		return args ->  {
			rabbitService.createPriceData("106,EUR/USD,1.1000,1.2000,01-06-2020 12:01:01:001");
			rabbitService.createPriceData("107,EUR/JPY,119.60,119.90,01-06-2020 12:01:02:002");
			rabbitService.createPriceData("108,GBP/USD,1.2500,1.2560,01-06-2020 12:01:02:002");
			rabbitService.createPriceData("109,GBP/USD,1.2499,1.2561,01-06-2020 12:01:02:100");
			rabbitService.createPriceData("110,EUR/JPY,119.61,119.91,01-06-2020 12:01:02:110");
		};
	}

}
