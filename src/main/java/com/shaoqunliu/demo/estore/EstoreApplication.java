package com.shaoqunliu.demo.estore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.shaoqunliu.demo.estore.repository")
public class EstoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(EstoreApplication.class, args);
    }
}
