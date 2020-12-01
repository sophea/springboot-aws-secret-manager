package com.sma.aws.properties;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.retry.annotation.EnableRetry;

import java.io.PrintStream;
@EnableRetry
@SpringBootApplication(exclude = ContextStackAutoConfiguration.class) // avoid an exception when running at local
public class AwsSecretPropertiesApplication {
//    public static void main(String[] args) {
//        SpringApplication.run(AwsSecretPropertiesApplication.class, args);
//    }

    public static void main(final String[] args) {
        new SpringApplicationBuilder(AwsSecretPropertiesApplication.class)
                .logStartupInfo(false)
                .banner(new SampleApplicationBanner())
                .run(args);
    }

    private static class SampleApplicationBanner implements Banner {

        @Override
        public void printBanner(final Environment environment, final Class<?> sourceClass, final PrintStream out) {
            final String port = environment.getProperty("server.port");
            String banner = "";
            banner += "Failsafe-Actuator sample applicaton is running!\n";
            banner += "\n";
            banner += "See the circuit breaker status:\n";
            banner += "   $ curl http://127.0.0.1:" + port + "/failsafe\n";
            banner += "Unreliable endpoint that fails every second invocation:\n";
            banner += "   $ curl http://127.0.0.1:" + port + "/unreliable\n";
            banner += "Reliable endpoint using a circuit breaker and fallback:\n";
            banner += "   $ curl http://127.0.0.1:" + port + "/reliable\n";
            banner += "Reliable endpoint using a circuit breaker with 5 second delay and fallback:\n";
            banner += "   $ curl http://127.0.0.1:" + port + "/reliableWithDelay\n";
            out.print(banner);
        }

    }

}

