package com.vt.learnreactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxAndMonoBackPressureTest {

    @Test
    public void backPressureTest() {
        Flux<Integer> integerFlux = Flux.range(1, 10);
        StepVerifier.create(integerFlux.log())
                .expectSubscription()
                .thenRequest(1)
                .expectNext(1)
                .thenRequest(1)
                .expectNext(2)
                .thenCancel()
                .verify();
    }

    @Test
    public void backPressure() {
        Flux<Integer> integerFlux = Flux.range(1, 10);
        integerFlux.log().subscribe(
                el -> System.out.println("Got " + el)
                , (ex) -> System.err.println("Exception is " + ex.getMessage()),
                () -> System.out.println("Done."),
                subscription -> subscription.request(2));
    }

    @Test
    public void backPressure_cancel() {
        Flux<Integer> integerFlux = Flux.range(1, 10);
        integerFlux.log().subscribe(
                el -> System.out.println("Got " + el)
                , (ex) -> System.err.println("Exception is " + ex.getMessage()),
                () -> System.out.println("Done."),
                subscription -> subscription.cancel());
    }

    @Test
    public void customized_backPressure() {
        Flux<Integer> integerFlux = Flux.range(1, 10);
        integerFlux.log().subscribe(new BaseSubscriber<Integer>() {
            @Override
            protected void hookOnNext(Integer value) {
                request(1);
                System.out.println("Value from Flux " + value);
                if (value == 4)
                    cancel();
            }
        });
    }
}
