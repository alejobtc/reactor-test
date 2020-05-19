package com.exampleWebFlux.demo.config;

import com.exampleWebFlux.demo.document.Item;
import com.exampleWebFlux.demo.repository.ItemReactorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

@Component
public class ItemInit implements CommandLineRunner {

    @Autowired
    ItemReactorRepository itemReactorRepository;

    @Override
    public void run(String... args) throws Exception {
        initialize();
    }

    public List<Item> data(){
        return Arrays.asList(new Item(null,"Samsung Cel", 500.0),
                new Item(null,"Samsung TV", 200.0),
                new Item(null,"Samsung Fan", 350.0),
                new Item("123","Samsung Fan", 350.0));
    }

    public void initialize(){
        itemReactorRepository.deleteAll()
                .thenMany(Flux.fromIterable(data()))
                .flatMap(itemReactorRepository::save)
                .thenMany(itemReactorRepository.findAll())
                .subscribe((item)->{
                    System.out.println("Item de iniciación: "+ item);
                });
    }
}
