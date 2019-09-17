package com.exampleWebFlux.demo.FluxAndMono;

import org.junit.Test;
import reactor.core.publisher.Flux;

public class FluxMonoTest {
    @Test
    public void FluxTest(){
        Flux<String> stringFlux = Flux.just("Alejandro","Santiago","Juan")
                .concatWith(Flux.error(new RuntimeException("Tenemos un problema")));
        stringFlux.subscribe(System.out::println,
                (e)->System.err.println(e));
    }
}
