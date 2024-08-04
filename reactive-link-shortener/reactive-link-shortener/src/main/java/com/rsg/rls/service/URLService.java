package com.rsg.rls.service;

import com.rsg.rls.model.Link;
import com.rsg.rls.repository.URLRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class URLService {

    private final String baseURL;

    private final URLRepository urlRepository;

    public URLService(@Value("$app.base.url") String baseURL, URLRepository urlRepository) {
        this.baseURL = baseURL;
        this.urlRepository = urlRepository;
    }

    public Mono<Link> getByKey(String key) {
        return urlRepository.findByKey(key);
    }

    public Mono<String> generateURL(String url) {
        String randomkey = RandomStringUtils.randomAlphabetic(6);
        return urlRepository.save(new Link(url, randomkey))
                .map(x -> baseURL + x.getUrl());
    }


}
