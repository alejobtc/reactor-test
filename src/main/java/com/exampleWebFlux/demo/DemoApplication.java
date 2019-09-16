package com.exampleWebFlux.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {

		Flux.just("Alejo","Santi","Juanes")
				.map((elem)->elem.concat(" es ingeniero"))
				.subscribe(System.out::println);

		Mono.
		SpringApplication.run(DemoApplication.class, args);
	}

}
