package com.rsg.rls.controller;

import com.rsg.rls.model.LinkRequest;
import com.rsg.rls.model.LinkResponse;
import com.rsg.rls.service.URLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
public class URLcontroller {

    @Autowired
    private URLService urlService;


    @GetMapping("/getbykey")
    public Mono<ResponseEntity<Object>> getLink(@RequestParam String key) {
        return urlService.getByKey(key)
                .map(url -> ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT)
                        .header("Location", url.getUrl())
                        .build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    @PostMapping("/findURL")
    public Mono<LinkResponse> findURL(@RequestBody LinkRequest request) {
        return urlService.generateURL(request.getURL())
                .map(LinkResponse::new);
    }
}
