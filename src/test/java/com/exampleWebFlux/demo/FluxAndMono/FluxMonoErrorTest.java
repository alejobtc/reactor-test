package com.exampleWebFlux.demo.FluxAndMono;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxMonoErrorTest {
      @Test
    public void fluxErrorHandling(){
          Flux<String> stringFlux = Flux.just("A","B","C")
                  .concatWith(Flux.error(new RuntimeException("Exception Ocurred")))
                  .concatWith((Flux.just("D")))
                  .onErrorResume((e)->{
                      System.out.println("Exception is : "+e);
                      return  Flux.just("default","default1");
                  });

          StepVerifier.create(stringFlux.log())
                  .expectSubscription()
                  .expectNext("A","B","C")
                  //.expectError(RuntimeException.class)
                  //.verify();
                    .expectNext("default","default1")
                  .verifyComplete();

      }
}
