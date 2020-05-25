package com.vt.learnreactor.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
public class FluxAndMonoController {

    @GetMapping("/flux")
    public Flux<Integer> flux(){
        return Flux.range(1, 10).delayElements(Duration.ofMillis(300)).log();
    }

    @GetMapping(value = "/fluxStream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Long> fluxStream(){
        return Flux.interval(Duration.ofMillis(300)).log();
    }
}
