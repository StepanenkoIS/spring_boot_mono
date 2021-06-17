package is.springbootmono.service;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Component
public class ServiceMono implements ServiceMonoInterface {

    @Override
    public Mono<String> getStringNonBlocked(String text) {
        return Mono.just(text).delayElement(Duration.ofSeconds(10));

    }

    @Override
    public String getStringBlocked(String text) {

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return text;
    }

}
