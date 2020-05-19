package com.exampleWebFlux.demo.FluxAndMono;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

public class FluxMonoCombineTest {


    @Test
    public void combineUsingMerge(){
        Flux<String> flux1 = Flux.just("D","E","F");
        Flux<String> flux2 = Flux.just("A","B","C");
        Flux<String> mergeFlux = Flux.merge(flux1,flux2);
        StepVerifier.create(mergeFlux.log())
                .expectSubscription()
                .expectNext("D","E","F","A","B","C")
                .verifyComplete();
    }

    @Test
    public void combineUsingMerge_withDelay(){
        Flux<String> flux1 = Flux.just("D","E","F").delayElements(Duration.ofSeconds(1));
        Flux<String> flux2 = Flux.just("A","B","C").delayElements(Duration.ofSeconds(1));
        Flux<String> mergeFlux = Flux.merge(flux1,flux2);
        StepVerifier.create(mergeFlux.log())
                .expectSubscription()
                .expectNextCount(6)
                //.expectNext("D","A","E","B","F","C")
                .verifyComplete();
    }

    @Test
    public void combineUsingConcat_withDelay(){
        Flux<String> flux1 = Flux.just("D","E","F").delayElements(Duration.ofSeconds(1));
        Flux<String> flux2 = Flux.just("A","B","C").delayElements(Duration.ofSeconds(1));
        Flux<String> mergeFlux = Flux.concat(flux1,flux2);
        StepVerifier.create(mergeFlux.log())
                .expectSubscription()
                .expectNextCount(9)
                //.expectNext("D","A","E","B","F","C")
                .verifyComplete();
    }

    @Test
    public void combineUsingZip(){
        Flux<String> flux1 = Flux.just("D","E","F");
        Flux<String> flux2 = Flux.just("A","B","C");
        Flux<String> flux3 = Flux.just("G","H","I");
        Flux<String> mergeFlux = Flux.zip(flux1,flux2,(t1,t2)->{
            return t1 + t2;
        }).zipWith(flux3,(t1,t2)->{
            return t1+t2;
        });
        mergeFlux.subscribe(System.out::println);
                //.expectNextCount(3)
    }

    @Test
    public void flatmapMet(){


        Flux<String> flux1 = Flux.just("d","e","F")
                .flatMap((x)->{
                     return Mono.just(x.toUpperCase());
                });

        flux1.subscribe(System.out::println);

    }

    @Test
    public void flatCombine(){
        Mono<Integer> flux1 = Flux.just(1,2,3,4)
                .reduce((x1,x2)->x1+x2);
        flux1.subscribe(System.out::println);

    }

    @Test
    public void reducetest(){
        Mono<Integer> flux1 = Flux.just(1,2,3,4)
                .reduce(Integer::sum);
        flux1.subscribe(System.out::println);
    }

}
