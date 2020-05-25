package com.vt.learnreactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class ColdAndHotPublisherTest {

    @Test
    public void coldPublisherTest() throws InterruptedException {
        Flux<String> stringFlux = Flux.just("A", "B", "C", "D", "E", "F")
                .delayElements(Duration.ofSeconds(1));
        stringFlux.subscribe(e -> System.out.println("Subscriber 1 " + e));
        Thread.sleep(2000);
        //new subscriber get value from the beginning. This is called cold publisher.
        stringFlux.subscribe(e -> System.out.println("Subscriber 2 " + e));
        Thread.sleep(6000);
    }

    @Test
    public void hotPublisherTest() throws InterruptedException {
        Flux<String> stringFlux = Flux.just("A", "B", "C", "D", "E", "F")
                .delayElements(Duration.ofSeconds(1));
        ConnectableFlux<String> connectableFlux = stringFlux.publish();
        connectableFlux.connect();
        connectableFlux.subscribe(e -> System.out.println("Subscriber 1 " + e));
        Thread.sleep(3000);
        //Subscriber 2 does not receive elements from the beginning.
        connectableFlux.subscribe(e -> System.out.println("Subscriber 2 " + e));
        Thread.sleep(4000);
    }
}
