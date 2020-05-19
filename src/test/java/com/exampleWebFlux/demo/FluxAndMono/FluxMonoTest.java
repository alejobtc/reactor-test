package com.exampleWebFlux.demo.FluxAndMono;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxMonoTest {

    @Test
    public void FluxTest(){
        Flux<String> stringFlux = Flux.just("Alejandro","Santiago","Juan")
                .concatWith(Flux.error(new RuntimeException("Tenemos un problema")))
                .concatWith((Flux.just("After error")))
                .log();
        stringFlux.subscribe(System.out::println,
                (e)->System.out.println("El error es "+e),
                ()->System.out.println("Completado"));
    }

    @Test
    public void fluxTestElements_WithoutError(){
        Flux<String> stringFlux = Flux.just("Alejandro","Santiago","Juan")
                .log();
        StepVerifier.create(stringFlux)
                .expectNext("Alejandro")
                .expectNext("Santiago")
                .expectNext("Juan")
                .verifyComplete();
    }

    @Test
    public void fluxTestElements_WithError(){
        Flux<String> stringFlux = Flux.just("Alejandro","Santiago","Juan")
                .concatWith(Flux.error(new RuntimeException("Tenemos un problema")))
                .log();
        StepVerifier.create(stringFlux)
                .expectNext("Alejandro")
                .expectNext("Santiago")
                .expectNext("Juan")
                //.expectError(RuntimeException.class)
                .expectErrorMessage("Tenemos un problema")
                .verify();
    }

    @Test
    public void fluxTestElementsCount_WithError(){
        Flux<String> stringFlux = Flux.just("Alejandro","Santiago","Juan")
                .concatWith(Flux.error(new RuntimeException("Tenemos un problema")))
                .log();
        StepVerifier.create(stringFlux)
                .expectNextCount(3)
                //.expectError(RuntimeException.class)
                .expectErrorMessage("Tenemos un problema")
                .verify();
    }

}
