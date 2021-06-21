package is.springbootmono.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.concurrent.TimeUnit;


@Component
public class ServiceMono implements ServiceMonoInterface {

    @Override
    public Mono<String> getStringNonBlocked() {
        return Mono.just("<br>Test return Mono<String> after 5 seconds of waiting (non-blocked)</br>").delayElement(Duration.ofSeconds(5));

    }

    @Override
    public String getStringBlocked() {

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "<br>Test return Mono<String> after 5 seconds of waiting (blocked!!!!)</br>";
    }

}
