package com.exampleWebFlux.demo.FluxAndMono;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static reactor.core.scheduler.Schedulers.parallel;

public class FluxMonoMapTest {
    List<String> names = Arrays.asList("Jaime","Cristian","Camilo","Juan","Sergio");
    List<Integer> numeros = Arrays.asList(21,2,3,432,34,23,2,23,2,221);

    @Test
    public void transformUsingMap(){
        Flux<String> namesFlux = Flux.fromIterable(names)
                .map(s->s.toUpperCase()).log("Flux nombres");
        StepVerifier.create(namesFlux)
                .expectNext("JAIME","CRISTIAN","CAMILO","JUAN","SERGIO")
                .verifyComplete();

    }

    @Test
    public void oddHundred(){
        Flux<Integer> numFlux = Flux.fromIterable(numeros)
                .filter((x)->x%2!=0)
                .map((x)->x*100);
        numFlux.subscribe(System.out::println);
    }

    @Test
    public void transformUsingFlatMap(){
        Flux<String> names = Flux.fromIterable(Arrays.asList("A","B","C","D","E","F"))
                .flatMap((s) -> {
                    return Flux.fromIterable(convertToList(s));
                });

        names.subscribe();
    }

    private List<String> convertToList(String s){
        System.out.println(Arrays.asList(s,"New Value"));
        return Arrays.asList(s,"New Value");
    }



    public String operation(String s)
    {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return s.toLowerCase();
    }
}
