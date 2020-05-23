package com.vt.learnreactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

public class FluxAndMonoFilterTest {

    private List<String> names = Arrays.asList("Vasu", "Jack", "Joe", "Jen");

    @Test
    public void filterTest() {
        Flux<String> namesFlux = Flux.fromIterable(names).filter(e -> e.startsWith("J"));
        StepVerifier.create(namesFlux.log())
                .expectNext("Jack", "Joe", "Jen")
                .verifyComplete();
    }

    @Test
    public void filterTestLength() {
        Flux<String> namesFlux = Flux.fromIterable(names).filter(e -> e.length()>3);
        StepVerifier.create(namesFlux.log())
                .expectNext("Vasu","Jack")
                .verifyComplete();
    }

}
