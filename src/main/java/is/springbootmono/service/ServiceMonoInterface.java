package is.springbootmono.service;

import reactor.core.publisher.Mono;

public interface ServiceMonoInterface {
    Mono<String> getStringNonBlocked();
    String getStringBlocked();
}
