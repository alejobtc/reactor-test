package com.exampleWebFlux.demo.FluxAndMono;

import org .junit.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class FluxMonoWithTimeTest {
    @Test
    public void infiniteSequence(){
       Flux<Long> infiniteFlux =  Flux.interval(Duration.ofMillis(200)).log();

       infiniteFlux.subscribe((element)->System.out.println("Value" + element));



    }
}
