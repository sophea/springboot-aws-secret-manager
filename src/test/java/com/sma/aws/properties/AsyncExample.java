package com.sma.aws.properties;
/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

import lombok.extern.slf4j.Slf4j;
import net.jodah.failsafe.Failsafe;
import net.jodah.failsafe.RetryPolicy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * @author Mak Sophea
 * @date : 1/20/2020
 **/
@Slf4j
public class AsyncExample {
    static ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
    static RetryPolicy<Object> retryPolicy = new RetryPolicy<>()
            .withDelay(Duration.ofSeconds(30))
            .onFailedAttempt(e -> log.error("Connection attempt failed", e.getLastFailure()))
            .onRetry(e -> log.warn("Failure #{}. Retrying.", e.getAttemptCount()));;
    static Service service = new Service();

    public static class Service {
        AtomicInteger failures = new AtomicInteger();

        // Fail 3 times then succeed
        CompletableFuture<Boolean> connect() {
            CompletableFuture<Boolean> future = new CompletableFuture<>();
            executor.submit(() -> {
                if (failures.getAndIncrement() < 3) {
                    log.error("error");
                    future.completeExceptionally(new RuntimeException());
                } else
                    future.complete(true);
//                if (failures.getAndIncrement() < 3) {
//                    log.error("call service {}", failures.get());
//                    RestTemplate template = new RestTemplate();
//                    ResponseEntity<String> result = template.getForEntity("http://localhost:8080/actuator/info", String.class);
//                    future.completeExceptionally(new RuntimeException());
//                } else {
//                    future.complete(true);
//                }
                log.info("Done");
            });
            return future;
        }
    }

    public static void main(String... args) throws Throwable {
        Failsafe.with(retryPolicy)
                .with(executor)
                .getAsyncExecution(execution -> service.connect().whenComplete((result, failure) -> {
                    if (execution.complete(result, failure))
                        log.info("Success");
                    else if (!execution.retry())
                        log.info("Connection attempts failed");
                }));

    }
}