package com.vt.learnreactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

public class FluxAndMonoTransformTest {

    private List<String> names = Arrays.asList("Vasu", "Jack", "Joe", "Jen");

    @Test
    public void transformUsingMap() {
        Flux<String> namesFlux = Flux.fromIterable(names).map(e -> e.startsWith("J")?e.toUpperCase():e);
        StepVerifier.create(namesFlux.log())
                .expectNext("Vasu","JACK", "JOE", "JEN")
                .verifyComplete();
    }

    @Test
    public void transformUsingMap_Length() {
        Flux<Integer> namesFlux = Flux.fromIterable(names).map(e -> e.length());
        StepVerifier.create(namesFlux.log())
                .expectNext(4, 4, 3, 3)
                .verifyComplete();
    }

    @Test
    public void transformUsingMap_Length_Repeat() {
        Flux<Integer> namesFlux = Flux.fromIterable(names).map(e -> e.length()).repeat(1);
        StepVerifier.create(namesFlux.log())
                .expectNext(4, 4, 3, 3)
                .expectNext(4, 4, 3, 3)
                .verifyComplete();
    }

    @Test
    public void transformUsingMap_filter() {
        Flux<String> namesFlux = Flux.fromIterable(names).filter(e -> e.length()>3).map(e -> e.toUpperCase()).repeat(1);
        StepVerifier.create(namesFlux.log())
                .expectNext("VASU", "JACK")
                .expectNext("VASU", "JACK")
                .verifyComplete();
    }
}
