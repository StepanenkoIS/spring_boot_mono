package is.springbootmono.handlers;

import is.springbootmono.errorhandling.HandlerRequiredException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


@Component
public class Handler implements HandlerInterface {

    /*
    Обработка ошибок с помощью onErrorReturn,
    при получении исключения от метода sayHello(), возвращается строка по умолчанию
    */
    public Mono<ServerResponse> handleRequest1(ServerRequest request) {

        return sayHello(request).onErrorReturn("Hello, Stranger")
                .flatMap(s -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .bodyValue(s));
    }

    /*
    Обработка ошибок с помощью onErrorResume,
    при получении исключения от метода sayHello(), возвращается строка,
    состоящая из динамически полученного сообщения об ошибке, добавляемого к строке «Error»
    */
    public Mono<ServerResponse> handleRequest2(ServerRequest request) {

        return sayHello(request)
                .flatMap(s -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .bodyValue(s))
                .onErrorResume(e -> Mono.just("Error " + e.getMessage())
                        .flatMap(s -> ServerResponse.ok()
                                .contentType(MediaType.TEXT_PLAIN)
                                .bodyValue(s)));
    }

    /*
    Обработка ошибок с помощью onErrorResume,
    при получении исключения от метода sayHello(),
    вызывается резервный служебный метод sayHelloFallback()
    */
    public Mono<ServerResponse> handleRequest3(ServerRequest request) {
        return sayHello(request)
                .flatMap(s -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .bodyValue(s))
                .onErrorResume(e -> sayHelloFallback()
                        .flatMap(s -> ServerResponse.ok()
                                .contentType(MediaType.TEXT_PLAIN)
                                .bodyValue(s)));
    }

    /*
    Обработка ошибок с помощью onErrorResume, при получении исключения от метода sayHello(),
    генерируется настраиваемое исключение с сообщением: «username is required»
    */
    public Mono<ServerResponse> handleRequest4(ServerRequest request) {
        return ServerResponse.ok()
                .body(sayHello(request)
                        .onErrorResume(e -> Mono.error(new HandlerRequiredException(
                                HttpStatus.BAD_REQUEST,
                                "username is required", e))), String.class);
    }


    /*
    Обработка ошибок на глобальном уровне
    */
    public Mono<ServerResponse> handleRequest5(ServerRequest request) {
        return ServerResponse.ok()
                .body(sayHello(request), String.class);
    }


    /*Основный служебный метод, который объединяет строку «Hello» и имя пользователя*/
    private Mono<String> sayHello(ServerRequest request) {
        try {
            return Mono.just("Hello, " + request.queryParam("name")
                    .get());
        } catch (Exception e) {
            return Mono.error(e);
        }
    }

    /*Резервный служебный метод, который возвраает строку по умолчанию */
    private Mono<String> sayHelloFallback() {
        return Mono.just("Hello, Stranger");
    }

}
