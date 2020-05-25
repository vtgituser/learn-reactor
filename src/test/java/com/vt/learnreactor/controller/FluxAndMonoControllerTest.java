package com.vt.learnreactor.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@WebFluxTest
public class FluxAndMonoControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void flux_1(){
        Flux<Integer> responseBody = webTestClient.get().uri("/flux")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Integer.class)
                .getResponseBody();

        StepVerifier.create(responseBody.log())
                .expectSubscription()
                .expectNextCount(10)
                .verifyComplete();
    }

    @Test
    public void flux_2(){
        webTestClient.get().uri("/flux")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Integer.class)
                .hasSize(10);
    }

    @Test
    public void flux_3(){
        List<Integer> expected = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        EntityExchangeResult<List<Integer>> entityExchangeResult = webTestClient.get().uri("/flux")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Integer.class)
                .returnResult();
        Assert.assertEquals(expected, entityExchangeResult.getResponseBody());
    }

    @Test
    public void flux_4(){
        List<Integer> expected = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        webTestClient.get().uri("/flux")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Integer.class)
                .consumeWith((listEntityExchangeResult ->
                        Assert.assertEquals(expected, listEntityExchangeResult.getResponseBody())));
    }

    @Test
    public void fluxStream(){
        Flux<Long> responseBody = webTestClient.get().uri("/fluxStream")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Long.class)
                .getResponseBody();

        StepVerifier.create(responseBody.log())
                .expectSubscription()
                .expectNextCount(10)
                .thenCancel()
                .verify();
    }
}
