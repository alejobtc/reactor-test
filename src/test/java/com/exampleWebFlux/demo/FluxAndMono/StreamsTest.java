package com.exampleWebFlux.demo.FluxAndMono;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class StreamsTest {


    @Test
    public void createStreams(){
        List<Integer> list = Arrays.asList(1,2,3,8,4,5);
        Stream<Integer> stream = list.stream();

        Stream<Integer> streammap = stream
                .filter((x)-> x>3)
                .map((x)->x+2)
                .sorted();

        System.out.println(streammap.collect(toList()).toString());
       // System.out.println(stream.reduce((a,b)->a+b));


    }

}
