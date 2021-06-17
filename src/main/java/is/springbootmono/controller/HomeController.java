package is.springbootmono.controller;


import is.springbootmono.service.ServiceMonoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
public class HomeController {

    @Autowired
    ServiceMonoInterface serviceMono;

    @GetMapping("/test1")
    public String getTest() {

        return "Test return String";

    }

    @GetMapping("/test2")
    public Mono<String> getTestMono2() {
        return Mono
                .just("Test return Mono<String>");
    }

    @GetMapping("/test3")
    public Mono<String> getTestMono3() {
        return serviceMono
                .getStringNonBlocked("Test return Mono<String> after 5 seconds of waiting (non-blocked)");
    }


    @GetMapping("/test4")
    public Mono<String> getTestMono4() {
        WebClient client = WebClient.create("http://localhost:8080");
        Mono<String> mono = client.get()
                .uri("/test{n}", "5")
                .retrieve()
                .bodyToMono(String.class);
        return mono;
    }

    @GetMapping("/test5")
    public Mono<String> getTestMono5() {
        return Mono.just(serviceMono
                .getStringBlocked("Test return Mono<String> after 5 seconds of waiting (blocked!!!!)"));
    }

    @GetMapping("/test6")
    public Mono<String> getTestMono6() {
        String text = "Test return Mono<String> after 5 seconds of waiting (non-blocked)<br>";

        Flux<String> flux =
                serviceMono.getStringNonBlocked(text)
                .mergeWith(serviceMono.getStringNonBlocked(text));

        Mono<String> mono = flux.reduce(String::concat);

        return mono;
    }


}
