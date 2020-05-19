package com.exampleWebFlux.demo.FluxAndMono;

import org.junit.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class BackPressureTest {


    @Test
    public void backPressureTest(){
        Flux<Integer> finiteFlux = Flux.range(1,10).log();

        StepVerifier.create(finiteFlux)
                .expectSubscription()
                .thenRequest(1)
                .expectNext(1)
                .thenRequest(1)
                .expectNext(2)
                .thenCancel()
                .verify();
    }

    @Test
    public void backPressure(){
        Flux<Integer> finiteFlux = Flux.range(1,10).log();
        finiteFlux.subscribe((elem)-> System.out.println(elem),
        (e)-> System.out.println("Excep"+ e),
        ()-> System.out.println("Done"),
        (sub)-> sub.request(10));
    }

    @Test
    public void customized(){
        Flux<Integer> finiteFlux = Flux.range(1,10).log();
        finiteFlux.subscribe(new BaseSubscriber<Integer>() {
            @Override
            protected void hookOnNext(Integer value) {
                request(1);
                System.out.println("Valor recibido:"+value);
                if(value == 5){
                    cancel();
                }
            }
        });
    }

    public void customized2(){
        Flux<Integer> finiteFlux = Flux.range(1,10).log();
        finiteFlux.subscribe((val)->{

        });
    }


    @Test
    public void connectable() throws InterruptedException {

            Flux<String> finiteFlux = Flux.just("A","B","C","D","E","F","G","H").delayElements(Duration.ofSeconds(1));
            ConnectableFlux<String> connectableFlux = finiteFlux.publish();
            connectableFlux.connect();
            connectableFlux.subscribe((s)-> System.out.println("Subscriber 1"+s));
            Thread.sleep(3000);
            connectableFlux.subscribe((s)-> System.out.println("Subscriber 2"+s));
            Thread.sleep(2000);
            connectableFlux.subscribe((s)-> System.out.println("Subscriber 3"+s));
            Thread.sleep(4000);
    }
}
