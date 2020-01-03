package com.sma.aws.properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration;

@SpringBootApplication(exclude = ContextStackAutoConfiguration.class) // avoid an exception when running at local
public class AwsSecretPropertiesApplication {
    public static void main(String[] args) {
        SpringApplication.run(AwsSecretPropertiesApplication.class, args);
    }

}

