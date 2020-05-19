package com.exampleWebFlux.demo.FluxAndMono;

import org.junit.Test;
import sun.swing.plaf.windows.ClassicSortArrowIcon;

import java.util.function.Function;

public class ejemploLambda {

    @Test
    public void ejemLambda(){
        //Carro carro = new Mustang();

        /* creacion de clase anonima interna*/
        doingSomething(new Carro(){
            @Override
            public int acelerar(){
                return 400;
            }
        });


        Function<Integer,Integer> f = x -> x+2;
        Function<Integer,Integer> g = x -> x*2;
        Function<Integer,Integer> h = f.andThen(g);
        Function<Integer,Integer> i = f.compose(g);

        System.out.println(h.apply(4));

        Carro carro1 = () -> 500;
        Carro carro3 = () -> 3 ;
        Mustang carro2 = new Mustang();
        System.out.println( carro1.andThen(carro3).acelerar() );



    }


    class Mustang implements Carro{

        @Override
        public int acelerar(){
            return 400;
        }
    }

    void doingSomething(Carro carro){
        System.out.println("Acelerar");
        System.out.println(carro.acelerar());
    }

    public interface Carro {
        int acelerar();

        default Carro andThen(Carro otroCarro){
            return () ->  this.acelerar() + otroCarro.acelerar();
        }
    }
}
