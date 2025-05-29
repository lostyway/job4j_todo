package ru.job4j.todo;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Main.class);
        app.run(args);
        log.info("Application started: http://localhost:8080/");
    }

   @Bean(destroyMethod = "close")
    public SessionFactory sf() {
       final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
       return new MetadataSources(registry).buildMetadata().buildSessionFactory();
   }
}