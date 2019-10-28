package com.billing.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {

	public static void main(String[] args) {

		int[] list = {1,2,3,4};

		Arrays.stream(list).boxed().map((i) -> {
			if(i <0 ){
				return i*(-1);
			}else{
				return i;
			}
		}).sorted().findFirst().orElse(0);


		SpringApplication.run(ConfigServerApplication.class, args);
	}



}
