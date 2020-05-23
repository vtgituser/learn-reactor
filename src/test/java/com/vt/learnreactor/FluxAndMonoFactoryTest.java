package com.vt.learnreactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class FluxAndMonoFactoryTest {

    private List<String> names = Arrays.asList("Vasu", "Jack", "Joe", "Jen");

    @Test
    public void fluxUsingIterable() {
        Flux<String> namesFlux = Flux.fromIterable(names);
        StepVerifier.create(namesFlux.log())
                .expectNext("Vasu", "Jack", "Joe", "Jen")
                .verifyComplete();
    }

    @Test
    public void fluxUsingArray() {
        String[] namesArray = new String[]{"Vasu", "Jack", "Joe", "Jen"};

        StepVerifier.create(Flux.fromArray(namesArray).log())
                .expectNext("Vasu", "Jack", "Joe", "Jen")
                .verifyComplete();
    }

    @Test
    public void fluxUsingStream() {
        StepVerifier.create(Flux.fromStream(names.stream()).log())
                .expectNext("Vasu", "Jack", "Joe", "Jen")
                .verifyComplete();
    }

    @Test
    public void monoUsingJustOrEmpty() {
        //nothing is there to commit, just verify complete.
        StepVerifier.create(Mono.justOrEmpty(null).log()).verifyComplete();

    }

    @Test
    public void monoUsingSupplier() {
        Supplier<String> supplier = () -> "Vasu";
        StepVerifier.create(Mono.fromSupplier(supplier).log()).expectNext("Vasu").verifyComplete();
    }

    @Test
    public void fluxUsingRange(){
        StepVerifier.create(Flux.range(1, 10).log()).expectNextCount(10).verifyComplete();
    }
}
