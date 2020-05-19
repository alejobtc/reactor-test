package com.exampleWebFlux.demo.repository;

import com.exampleWebFlux.demo.document.Item;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@DataMongoTest
@RunWith(SpringRunner.class)
public class ItemReactiveRepositoryTest {

    @Autowired
    ItemReactorRepository itemReactorRepository;

    List<Item> itemList = Arrays.asList(new Item(null,"Samsung Cel", 400.0),
            new Item(null,"Samsung TV", 200.0),
            new Item(null,"Samsung Fan", 350.0),
            new Item("123","Samsung Fan", 350.0)
    );

    @Before
    public void setUp(){
        itemReactorRepository.deleteAll()
                .thenMany(Flux.fromIterable(itemList))
                .flatMap(itemReactorRepository::save)
                .doOnNext((item->{
                    System.out.println("Inserted item is: "+item);
                }))
                .blockLast();
    }


    @Test
    public void getAllItems(){
        StepVerifier.create(itemReactorRepository.findAll().log())
                .expectSubscription()
                .expectNextCount(4)
                .verifyComplete();

    }

    @Test
    public void getAllDescriptions(){
        StepVerifier.create(itemReactorRepository.findAll()
                .map(item->item.getDescripction())
                .log())
                .expectSubscription()
                .expectNextCount(4)
                .verifyComplete();

    }

    @Test
    public void getItemById(){
        StepVerifier.create(itemReactorRepository.findById("123"))
                .expectSubscription()
                .expectNextMatches((item)->item.getDescripction().equals("Samsung Fan"));
    }

    @Test
    public void findItemByDescription(){
        StepVerifier.create(itemReactorRepository.findByDescripction("Samsung Fan").log())
                .expectSubscription()
                .expectNextCount(2)
                .verifyComplete();
    }



    @Test
    public void saveItem(){
        Item item = new Item(null,"Google Home Mini",30.00);

        Mono<Item> itemMono = itemReactorRepository.save(item);
        StepVerifier.create(itemMono.log())
                .expectSubscription()
                .expectNextMatches(item1 -> item1.getId()!=null && item1.getDescripction().equals(item.getDescripction()))
                .verifyComplete();

    }

    @Test
    public void updateItem(){
        Flux<Item> updateItem =  itemReactorRepository.findByDescripction("Samsung TV")
                .map(item-> {
                   item.setPrice(450.0);
                   return item;
                })
                .flatMap((item)->{
                    return itemReactorRepository.save(item);
                });

        StepVerifier.create(updateItem)
                .expectSubscription()
                .expectNextMatches(item->item.getPrice().equals(450.0))
                .verifyComplete();
    }

    @Test
    public void deleteItem(){
         Mono<Void> deleteFlux = itemReactorRepository.findById("123")
                .flatMap(item->{
                    return itemReactorRepository.delete(item);
                }).log();

        StepVerifier.create(deleteFlux)
                .expectSubscription()
                .verifyComplete();

    }


}
