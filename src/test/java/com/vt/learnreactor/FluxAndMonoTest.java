package com.vt.learnreactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
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
                .concatWith(Flux.error(new RuntimeException("FluxError")));

        StepVerifier.create(stringFlux.log())
                .expectNextCount(3)
//                .expectError(RuntimeException.class)
                .expectErrorMessage("FluxError")
                .verify();
    }

    @Test
    public void monoTest(){
        Mono<String> mono = Mono.just("Hello Mono");
        StepVerifier.create(mono.log())
                .expectNext("Hello Mono")
                .verifyComplete();
    }

    @Test
    public void monoTest_Error(){
        StepVerifier.create(Mono.error(new RuntimeException("MonoError")).log())
                .expectErrorMessage("MonoError")
                .verify();
    }
}
