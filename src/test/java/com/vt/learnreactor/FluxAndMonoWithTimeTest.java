package com.vt.learnreactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class FluxAndMonoWithTimeTest {

    @Test
    public void infiniteSequence() {
        Flux<Long> longFlux = Flux.interval(Duration.ofMillis(200));

        longFlux.subscribe(System.out::println);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void infiniteSequenceTest() {
        Flux<Long> longFlux = Flux.interval(Duration.ofMillis(200))
                .take(5);
        StepVerifier.create(longFlux.log())
                .expectSubscription()
                .expectNext(0L, 1L, 2L, 3L, 4L)
                .verifyComplete();
    }

    @Test
    public void infiniteSequenceMap() {
        Flux<Integer> longFlux = Flux.interval(Duration.ofMillis(200))
                .map(Long::intValue)
                .take(3);
        StepVerifier.create(longFlux.log())
                .expectSubscription()
                .expectNext(0, 1, 2)
                .verifyComplete();
    }
}
