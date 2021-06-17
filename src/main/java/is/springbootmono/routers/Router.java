package is.springbootmono.routers;

import is.springbootmono.handlers.HandlerInterface;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
public class Router {

    @Bean
    public RouterFunction<ServerResponse> routeRequest1(HandlerInterface handler) {
        return RouterFunctions.route(RequestPredicates.GET("/hello1")
                .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), handler::handleRequest1);
    }

    @Bean
    public RouterFunction<ServerResponse> routeRequest2(HandlerInterface handler) {
        return RouterFunctions.route(RequestPredicates.GET("/hello2")
                .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), handler::handleRequest2);
    }

    @Bean
    public RouterFunction<ServerResponse> routeRequest3(HandlerInterface handler) {
        return RouterFunctions.route(RequestPredicates.GET("/hello3")
                .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), handler::handleRequest3);
    }

    @Bean
    public RouterFunction<ServerResponse> routeRequest4(HandlerInterface handler) {
        return RouterFunctions.route(RequestPredicates.GET("/hello4")
                .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), handler::handleRequest4);
    }

    @Bean
    public RouterFunction<ServerResponse> routeRequest5(HandlerInterface handler) {
        return RouterFunctions.route(RequestPredicates.GET("/hello5")
                .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), handler::handleRequest5);
    }

}