package com.should_i_bunk.should_i_bunk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ShouldIBunkApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShouldIBunkApplication.class, args);
    }

}
