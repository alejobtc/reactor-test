package com.exampleWebFlux.demo.FluxAndMono;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class FluxMonoFactoryTest {
    List<String> names = Arrays.asList("Willie","Crisitian","Caminlo","Juan","Sandro");

    @Test
    public void fluxUSingIterable(){
        Flux<String> namesF = Flux.fromIterable(names);

        StepVerifier.create(namesF)
                .expectNext("Willie","Crisitian","Caminlo","Juan","Sandro")
                .verifyComplete();
    }

    @Test
    public void fluxUsingArray(){
        String[] names = new String[]{"Willie","Crisitian","Caminlo","Juan","Sandro"};
        Flux<String> namesF = Flux.fromArray(names);
        StepVerifier.create(namesF)
                .expectNext("Willie","Crisitian","Caminlo","Juan","Sandro")
                .verifyComplete();
    }

    @Test
    public void fluxUsingStream(){
        Flux<String> namesFlux = Flux.fromStream(names.stream());
        StepVerifier.create(namesFlux)
                .expectNext("Willie","Crisitian","Caminlo","Juan","Sandro")
                .verifyComplete();
    }

    @Test
    public void monoUSingJustOrEmpty(){
        Mono<String> mono = Mono.justOrEmpty(null);
        StepVerifier.create(mono.log())
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void monoUSingSupplier(){
        Supplier<String> stringSupplier = () -> "adam";
        Mono<String> stringMono = Mono.fromSupplier(stringSupplier);
        StepVerifier.create(stringMono.log())
                .expectNext("adam")
                .verifyComplete();
    }

    @Test
    public void fluxUsingRange(){
        Flux<Integer> intFlux = Flux.range(1,5).log();
        intFlux.subscribe(System.out::println);
        StepVerifier.create(intFlux)
                .expectNext(1,2,3,4,5)
                .verifyComplete();
    }
}

