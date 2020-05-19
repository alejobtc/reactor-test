package com.exampleWebFlux.demo.FluxAndMono;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


import java.util.Arrays;
import java.util.List;

public class FluxMonoFilterTest {
    List<String> names = Arrays.asList("Jaime","Cristian","Camilo","Juan","Sergio");
    List<Integer> numeros = Arrays.asList(21,2,3,432,34,23,2,23,2,221);
    @Test
    public void filterTest(){

        Flux<String> flux = Flux.fromIterable(names)
                .filter(elem->elem.startsWith("C"));

        flux.subscribe((elem)->{
            System.out.println(elem);
        });

        StepVerifier.create(flux)
                .expectNext("Cristian","Camilo")
                .verifyComplete();
    }


    @Test
    public void filter2Test(){

        Flux<Integer> fNum = Flux.fromIterable(numeros)
                .filter((item)->item>100);

        fNum.subscribe(System.out::println);








    }

    @Test
    public void sumList(){
        Mono<Integer> resultado = Flux.fromIterable(numeros)
                .reduce(Integer::sum)
                .log();

        resultado.subscribe(System.out::println);

        StepVerifier.create(resultado)
                .expectSubscription()
                .expectNext(21+2+3+432+34+23+2+23+2+221)
                .verifyComplete();
    }


}
