package com.exampleWebFlux.demo.controller;

import com.exampleWebFlux.demo.document.Item;
import com.exampleWebFlux.demo.repository.ItemReactorRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@AutoConfigureWebTestClient
public class ItemControllerTest {
    @Autowired
    WebTestClient webTestClient;

    @Autowired
    ItemReactorRepository itemReactorRepository;

    public List<Item> data(){
        return Arrays.asList(new Item(null,"Samsung Cel", 500.0),
                new Item(null,"Samsung TV", 200.0),
                new Item(null,"Samsung Fan", 350.0),
                new Item("123","Samsung Fan", 350.0));
    }

    //@Before
    public void setUp(){
        itemReactorRepository.deleteAll()
                .thenMany(Flux.fromIterable(data()))
                .flatMap(itemReactorRepository::save)
                .doOnNext((item->{
                    System.out.println("Item insertado: "+item);
                }))
                .blockLast();
    }


    public void getAll(){
        webTestClient.get().uri("/allItems")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Item.class)
                .hasSize(4)
                .consumeWith((response)->{
                    List<Item> itemList = response.getResponseBody();
                    itemList.forEach((item)->{
                        Assert.assertTrue(item.getId()!=null);
                    });
                });
    }
}
