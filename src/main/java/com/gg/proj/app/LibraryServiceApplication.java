package com.gg.proj.app;

import com.gg.proj.consumer.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * This is the App class for spring boot, it contains the main, and a demo method which is ran at startup
 * Annotation here are for springboot to know where beans are stored
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.gg.proj.app", "com.gg.proj.util", "com.gg.proj.service", "com.gg.proj.business"})
@EnableJpaRepositories(basePackages = {"com.gg.proj.consumer"})
@EntityScan(basePackages = "com.gg.proj.model")
public class LibraryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryServiceApplication.class, args);
    }

    /**
     * This method is launched at start, it can be used to perform utilities task during development
     *
     * @param repository the bookrepo
     * @return CommandLineRunner
     */
    @Bean
    public CommandLineRunner demo(BookRepository repository) {
        return (args) -> {
        };
    }
}

