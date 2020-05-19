package com.exampleWebFlux.demo.controller;

import com.exampleWebFlux.demo.document.Item;
import com.exampleWebFlux.demo.repository.ItemReactorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class ItemController {

    @Autowired
    ItemReactorRepository itemReactorRepository;

    @GetMapping("/item")
    public Flux<Item> getAllItems() {
        Flux<Item> allItems = itemReactorRepository.findAll();
        return allItems;
    }

    @GetMapping("item"+"/{id}")
    public Mono<ResponseEntity<Item>> getOneItem(@PathVariable String id){
        return itemReactorRepository.findById(id)
                .map((item -> new ResponseEntity<>(item, HttpStatus.OK) ))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/item")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Item> createItem(@RequestBody Item item){
        return itemReactorRepository.save(item);
    }

    @DeleteMapping("/item/{id}")
    public Mono<Void> deleteItem(@PathVariable String id){
        return itemReactorRepository.deleteById(id);
    }

    @PutMapping("/item/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<Item>> updateItem(@PathVariable String id,@RequestBody Item item){
        return itemReactorRepository.findById(id)
                .flatMap(elem-> {
                            elem.setPrice(item.getPrice());
                            elem.setDescripction(item.getDescripction());
                            return itemReactorRepository.save(elem);
                })
                .map(elem->{
                    return new ResponseEntity<Item>(elem,HttpStatus.OK);
                })
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @GetMapping("/itemDescription")
    public Mono<Double> getAllItemsDescription() {

        Mono<Double> allItems = itemReactorRepository.findAll()
                .map(elem->elem.getPrice())
                .reduce(Double::max);


        return allItems;
    }


}
