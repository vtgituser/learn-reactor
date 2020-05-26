package com.vt.learnreactor.router;

import com.vt.learnreactor.handler.SampleHandlerFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class RouterFunctionConfig {

    @Bean
    public RouterFunction<ServerResponse> router(SampleHandlerFunction handlerFunction){
        return RouterFunctions.route(GET("/functional/handle"), handlerFunction::handleFlux);
    }

    @Bean
    public RouterFunction<ServerResponse> routerMono(SampleHandlerFunction handlerFunction){
        return RouterFunctions.route(GET("/functional/handleMono"), handlerFunction::handleMono);
    }
}
