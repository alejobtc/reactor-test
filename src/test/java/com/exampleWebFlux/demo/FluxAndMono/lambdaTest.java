package com.exampleWebFlux.demo.FluxAndMono;

import org.junit.Test;


public class lambdaTest {

    public interface Carro {
        int acelerar(int x);

        default Carro andTher(Carro carro2){
            return (x) -> this.acelerar(x) + carro2.acelerar(x);
        }
    }


    public class Mustang implements Carro{

        @Override
        public int acelerar(int x){
            return 5*x;
        }
    }

    @Test
    public void testLambda(){

        Mustang carro1 = new Mustang();
        Carro carro2 = (x) -> {
            return 3*x;
        };
        System.out.println(carro1.andTher(carro2).acelerar(3));
        System.out.println(carro1.acelerar(3));
        System.out.println(carro2.acelerar(2));

    }

}
