package com.exampleWebFlux.demo.FluxAndMono;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

public class FluxMonoFilterTest {
    List<String> names = Arrays.asList("Willie","Crisitian","Caminlo","Juan","Sandro");

    @Test
    public void filterTest(){
        Flux<String> flux = Flux.fromIterable(names)
                .filter(elem->elem.startsWith("C"));

        flux.subscribe(System.out::println);

        StepVerifier.create(flux)
                .expectNext("Crisitian","Caminlo")
                .verifyComplete();
    }
}
