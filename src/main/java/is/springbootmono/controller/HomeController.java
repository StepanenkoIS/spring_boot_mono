package is.springbootmono.controller;


import is.springbootmono.service.ServiceMonoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;


@RestController
public class HomeController {

    @Autowired
    ServiceMonoInterface serviceMono;

    /* Обычный (синхронный) метод, возвращает строку (константу) */
    @GetMapping("/test1")
    public String getTest() {
        return "Test return String";
    }

    /* Реактивный метод, возвращает строку (константу) */
    @GetMapping("/test2")
    public Mono<String> getTestMono2() {
        return Mono.just("Test return Mono<String>");
    }

    /* Реактивный метод, вызывает не блокирующий метод сервис (non-blocked) */
    @GetMapping("/test3")
    public Mono<String> getTestMono3() {
        return serviceMono.getStringNonBlocked();
    }

    /* Реактивный метод, вызывает блокирующий метод сервис (blocked) */
    @GetMapping("/test4")
    public Mono<String> getTestMono4() {
        return Mono.just(serviceMono.getStringBlocked());
    }

    /* Реактивный метод, вызывает блокирующий метод сервис (blocked), при вызове используется Schedulers */
    @GetMapping("/test5")
    public Mono<String> getTestMono5() {
        return Mono.just(serviceMono.getStringBlocked()).publishOn(Schedulers.boundedElastic());
    }

    /* Реактивный метод, который внутри вызывает первый метод сервиса,
    получив ответ от первого, вызывает второй,
    конкатенирует ответ от первого с ответом второго и возвращает полученную строку. */
    @GetMapping("/test6")
    public Mono<String> getTestMono6() {
        Flux<String> flux =
                serviceMono.getStringNonBlocked()
                        .mergeWith(Mono.just(serviceMono.getStringBlocked()));
        Mono<String> mono = flux.reduce(String::concat);
        return mono;
    }

    /*Реактивный метод, который параллельно вызывает 5 методов сервиса,
    каждый из которых блокирует поток на 5 секунд.
    Далее, дожидаемся ответа от всех 5, конкатенируем ответы в одну строку и возвращаем.
    Общее время работы не больше 6 секунд.*/
    @GetMapping("/test7")
    public Mono<String> getTestMono7() throws InterruptedException {

        Mono<String> blockingWrapper1 = Mono.fromCallable(() -> {
            return serviceMono.getStringBlocked();
        }).subscribeOn(Schedulers.boundedElastic());

        Mono<String> blockingWrapper2 = Mono.fromCallable(() -> {
            return serviceMono.getStringBlocked();
        }).subscribeOn(Schedulers.boundedElastic());

        Mono<String> blockingWrapper3 = Mono.fromCallable(() -> {
            return serviceMono.getStringBlocked();
        }).subscribeOn(Schedulers.boundedElastic());

        Mono<String> blockingWrapper4 = Mono.fromCallable(() -> {
            return serviceMono.getStringBlocked();
        }).subscribeOn(Schedulers.boundedElastic());

        Mono<String> blockingWrapper5 = Mono.fromCallable(() -> {
            return serviceMono.getStringBlocked();
        }).subscribeOn(Schedulers.boundedElastic());

        return blockingWrapper1
                .mergeWith(blockingWrapper2)
                .mergeWith(blockingWrapper3)
                .mergeWith(blockingWrapper4)
                .mergeWith(blockingWrapper5)
                .reduce(String::concat);
    }

    /*Реактивный метод, который параллельно вызывает 5 методов сервиса,
    каждый из которых блокирует поток на 5 секунд.
    Далее, дожидаемся ответа от всех 5, конкатенируем ответы в одну строку и возвращаем.
    Общее время работы не больше 6 секунд.*/
    @GetMapping("/test8")
    public Mono<String> getTestMono8() throws InterruptedException {

        Mono<String> blockingWrapper1 = Flux.just("").publishOn(Schedulers.parallel())
                .map(s -> s + serviceMono.getStringBlocked()).reduce(String::concat);

        Mono<String> blockingWrapper2 = Flux.just("").publishOn(Schedulers.parallel())
                .map(s -> s + serviceMono.getStringBlocked()).reduce(String::concat);

        Mono<String> blockingWrapper3 = Flux.just("").publishOn(Schedulers.parallel())
                .map(s -> s + serviceMono.getStringBlocked()).reduce(String::concat);

        Mono<String> blockingWrapper4 = Flux.just("").publishOn(Schedulers.parallel())
                .map(s -> s + serviceMono.getStringBlocked()).reduce(String::concat);

        Mono<String> blockingWrapper5 = Flux.just("").publishOn(Schedulers.parallel())
                .map(s -> s + serviceMono.getStringBlocked()).reduce(String::concat);

        return blockingWrapper1
                .mergeWith(blockingWrapper2)
                .mergeWith(blockingWrapper3)
                .mergeWith(blockingWrapper4)
                .mergeWith(blockingWrapper5)
                .reduce(String::concat);

    }

    /* Реактивный метод, который внутри себя вызывает через Web Client 2-ой метод этого списка. */
    @GetMapping("/test9")
    public Mono<String> getTestMono9() {
        WebClient client = WebClient.create("http://localhost:8080");
        Mono<String> mono = client.get()
                .uri("/test{n}", "4")
                .retrieve()
                .bodyToMono(String.class);
        return mono;
    }

}
