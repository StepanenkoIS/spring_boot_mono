package is.springbootmono.handlers;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface HandlerInterface {

    Mono<ServerResponse> handleRequest1(ServerRequest request);

    Mono<ServerResponse> handleRequest2(ServerRequest request);

    Mono<ServerResponse> handleRequest3(ServerRequest request);

    Mono<ServerResponse> handleRequest4(ServerRequest request);

    Mono<ServerResponse> handleRequest5(ServerRequest request);
}

