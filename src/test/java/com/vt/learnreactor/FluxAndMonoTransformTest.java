package com.vt.learnreactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FluxAndMonoTransformTest {

    private List<String> names = Arrays.asList("Vasu", "Jack", "Joe", "Jen");

    @Test
    public void transformUsingMap() {
        Flux<String> namesFlux = Flux.fromIterable(names).map(e -> e.startsWith("J") ? e.toUpperCase() : e);
        StepVerifier.create(namesFlux.log())
                .expectNext("Vasu", "JACK", "JOE", "JEN")
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
        Flux<String> namesFlux = Flux.fromIterable(names).filter(e -> e.length() > 3).map(e -> e.toUpperCase()).repeat(1);
        StepVerifier.create(namesFlux.log())
                .expectNext("VASU", "JACK")
                .expectNext("VASU", "JACK")
                .verifyComplete();
    }

    @Test
    public void transformUsingFlatMap() {
        Flux<String> namesFlux = Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E"))
                .flatMap(t -> Flux.fromIterable(externalCall(t)));//external call which returns flux

        StepVerifier.create(namesFlux.log())
                .expectNextCount(10)
                .verifyComplete();
    }

    @Test
    public void transformUsingFlatMap_usingParallel() {
        Flux<String> namesFlux = Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E"))
                .window(2) //waits for 2, passes 2 fluxes to flatMap => Flux<Flux<String>>
                .flatMap(f -> f.map(this::externalCall).subscribeOn(Schedulers.parallel()))
                .flatMap(Flux::fromIterable)
                ;

        StepVerifier.create(namesFlux.log())
                .expectNextCount(10)
                .verifyComplete();
    }

    @Test
    public void transformUsingFlatMap_usingParallel_maintain_order() {
        Flux<String> namesFlux = Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E"))
                .window(2) //waits for 2, passes 2 fluxes to flatMap => Flux<Flux<String>>
//                .concatMap(f -> f.map(this::externalCall).subscribeOn(Schedulers.parallel())) /** maintains order but sequential.
                .flatMapSequential(f -> f.map(this::externalCall).subscribeOn(Schedulers.parallel()))
                .flatMap(Flux::fromIterable)
                ;

        StepVerifier.create(namesFlux.log())
                .expectNextCount(10)
                .verifyComplete();
    }

    private List<String> externalCall(String t) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Arrays.asList(t, t+" dbValue");
    }

}
