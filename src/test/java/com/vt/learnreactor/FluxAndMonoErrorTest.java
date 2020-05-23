package com.vt.learnreactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.io.SyncFailedException;

public class FluxAndMonoErrorTest {

    @Test
    public void fluxErrorHandling_OnErrorResume(){
        Flux<String> stringFlux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("Exception :(")))
                .concatWith(Flux.just("D"))
                .onErrorResume(ex -> {
                    System.err.println(ex);
                    return Flux.just("default1", "default2");
                });

        StepVerifier.create(stringFlux.log())
                .expectSubscription()
                .expectNextCount(3)
//                .expectError(RuntimeException.class)
                .expectNext("default1", "default2")
                .verifyComplete();
    }

    @Test
    public void fluxErrorHandling_OnErrorReturn(){
        Flux<String> stringFlux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("Exception :(")))
                .concatWith(Flux.just("D"))
                .onErrorReturn("default");

        StepVerifier.create(stringFlux.log())
                .expectSubscription()
                .expectNextCount(3)
//                .expectError(RuntimeException.class)
                .expectNext("default")
                .verifyComplete();
    }
}
