package com.vt.learnreactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class FluxAndMonoCombineTest {

    @Test
    public void combineUsingMerge(){
        Flux<String> flux1 = Flux.just("A", "B", "C");
        Flux<String> flux2 = Flux.just("D", "E", "F");
        Flux<String> merge = Flux.merge(flux1, flux2);
        StepVerifier.create(merge.log())
                .expectSubscription()//Verify subscription event
                .expectNext("A", "B", "C")
                .expectNext("D", "E", "F")
                .verifyComplete();
    }

    @Test
    public void combineUsingMerge_withDelay(){
        Flux<String> flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofSeconds(1));
        Flux<String> flux2 = Flux.just("D", "E", "F").delayElements(Duration.ofSeconds(1));
        Flux<String> merge = Flux.merge(flux1, flux2);
        StepVerifier.create(merge.log())
                .expectSubscription()//Verify subscription event
//                .expectNext("A", "B", "C")
//                .expectNext("D", "E", "F")
                .expectNextCount(6)
                .verifyComplete();
    }

    @Test
    public void combineUsingConcat(){
        Flux<String> flux1 = Flux.just("A", "B", "C");
        Flux<String> flux2 = Flux.just("D", "E", "F");
        Flux<String> merge = Flux.concat(flux1, flux2);
        StepVerifier.create(merge.log())
                .expectSubscription()//Verify subscription event
                .expectNext("A", "B", "C")
                .expectNext("D", "E", "F")
                .verifyComplete();
    }

    @Test
    public void combineUsingConcat_withDelay(){
        Flux<String> flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofSeconds(1));
        Flux<String> flux2 = Flux.just("D", "E", "F").delayElements(Duration.ofSeconds(1));
        Flux<String> merge = Flux.concat(flux1, flux2); //concat maintains order, unlike merge
        StepVerifier.create(merge.log())
                .expectSubscription()//Verify subscription event
                .expectNext("A", "B", "C")
                .expectNext("D", "E", "F")
//                .expectNextCount(6)
                .verifyComplete();
    }

    @Test
    public void combineUsingZip(){
        Flux<String> flux1 = Flux.just("A", "B", "C");
        Flux<String> flux2 = Flux.just("D", "E", "F");
        Flux<String> merge = Flux.zip(flux1, flux2, String::concat);
        StepVerifier.create(merge.log())
                .expectSubscription()//Verify subscription event
                .expectNext("AD", "BE", "CF")
                .verifyComplete();
    }
}
