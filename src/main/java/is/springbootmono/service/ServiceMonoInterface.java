package is.springbootmono.service;

import reactor.core.publisher.Mono;

public interface ServiceMonoInterface {
    Mono<String> getStringNonBlocked(String srt);
    String getStringBlocked(String srt);
}
