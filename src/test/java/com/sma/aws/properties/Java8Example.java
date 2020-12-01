package com.sma.aws.properties;

/**
 * @author Mak Sophea
 * @date : 1/20/2020
 **/
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

import net.jodah.failsafe.Failsafe;
import net.jodah.failsafe.RetryPolicy;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Java8Example {
    @SuppressWarnings("unused")
    public static void main(String... args) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
        RetryPolicy<Object> retryPolicy = new RetryPolicy<>().withMaxRetries(10);

        // Create a retryable functional interface
        Function<String, String> bar = value -> Failsafe.with(retryPolicy).get(() -> value + "bar1");
        System.out.println(bar.apply("foo"));

        // Create a retryable Stream operation
        List<String> data = Failsafe.with(retryPolicy).get(() -> Stream.of("foo")
                .map(value -> Failsafe.with(retryPolicy).get(() -> value + "bar2"))
                .collect(Collectors.toList()));
        for(String item : data) {
            System.out.println(item);
        }

        // Create a individual retryable Stream operation
        Stream.of("foo").map(value -> Failsafe.with(retryPolicy).get(() -> value + "bar3")).forEach(System.out::println);

        // Create a retryable CompletableFuture
        Failsafe.with(retryPolicy).with(executor).getStageAsync(() -> CompletableFuture.supplyAsync(() -> "foo")
                .thenApplyAsync(value -> value + "bar4")
                .thenAccept(System.out::println))
        ;
//
//        // Create an individual retryable CompletableFuture stages
//        CompletableFuture.supplyAsync(() -> Failsafe.with(retryPolicy).get(() -> "foo"))
//                .thenApplyAsync(value -> Failsafe.with(retryPolicy).get(() -> value + "bar5"))
//                .thenAccept(System.out::println);
    }
}