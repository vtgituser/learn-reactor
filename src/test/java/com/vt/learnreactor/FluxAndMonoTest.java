package com.vt.learnreactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxAndMonoTest {

    @Test
    public void fluxTest() {
        Flux<String> stringFlux = Flux.just("Hello", "World")
//                .concatWith(Flux.error(new RuntimeException("FluxError")))
                .concatWithValues("Reactor");
        stringFlux.subscribe(System.out::println, System.err::println, () -> System.out.println("Flux completed."));
    }

    @Test
    public void fluxTestElementsWithoutError() {
        Flux<String> stringFlux = Flux.just("Hello", "World", "Reactor").log();

        StepVerifier.create(stringFlux)
                .expectNext("Hello")
                .expectNext("World")
                .expectNext("Reactor")
                .verifyComplete();
    }

    @Test
    public void fluxTestElementsWithError() {
        Flux<String> stringFlux = Flux.just("Hello", "World", "Reactor")
                .concatWith(Flux.error(new RuntimeException("FluxError"))).log();

        StepVerifier.create(stringFlux)
                .expectNext("Hello")
                .expectNext("World")
                .expectNext("Reactor")
//                .expectError(RuntimeException.class)
                .expectErrorMessage("FluxError")
                .verify();
    }

    @Test
    public void fluxTestElementsWithError2() {
        Flux<String> stringFlux = Flux.just("Hello", "World", "Reactor")
                .concatWith(Flux.error(new RuntimeException("FluxError"))).log();

        StepVerifier.create(stringFlux)
                .expectNext("Hello","World","Reactor")
//                .expectError(RuntimeException.class)
                .expectErrorMessage("FluxError")
                .verify();
    }

    @Test
    public void fluxTestElementsCount_withError() {
        Flux<String> stringFlux = Flux.just("Hello", "World", "Reactor")
                .concatWith(Flux.error(new RuntimeException("FluxError"))).log();

        StepVerifier.create(stringFlux)
                .expectNextCount(3)
//                .expectError(RuntimeException.class)
                .expectErrorMessage("FluxError")
                .verify();
    }
}
