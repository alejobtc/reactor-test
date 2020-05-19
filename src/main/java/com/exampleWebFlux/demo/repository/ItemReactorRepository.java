package com.exampleWebFlux.demo.repository;

import com.exampleWebFlux.demo.document.Item;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ItemReactorRepository extends ReactiveMongoRepository<Item,String> {

    Flux<Item> findByDescripction(String descripction);
}
