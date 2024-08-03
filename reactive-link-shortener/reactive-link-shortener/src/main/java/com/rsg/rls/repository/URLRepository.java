package com.rsg.rls.repository;

import com.rsg.rls.model.Link;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class URLRepository {

    private final ReactiveRedisOperations<String, String> reactiveRedisOperations;


    public URLRepository(ReactiveRedisOperations reactiveRedisOperations) {
        this.reactiveRedisOperations = reactiveRedisOperations;
    }

    public Mono<Link> save(Link link) {
        return reactiveRedisOperations.opsForValue()
                .set(link.getKey(), link.getUrl())
                .map(__ -> link);
    }

    public Mono<Link> findByKey(String key) {
        return reactiveRedisOperations.opsForValue()
                .get(key)
                .map(x -> new Link(x, key));
    }
    
}
