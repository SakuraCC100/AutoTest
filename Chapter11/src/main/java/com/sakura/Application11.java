package com.sakura;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PreDestroy;

@SpringBootApplication
@EnableScheduling
public class Application11 {

    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        Application11.context = SpringApplication.run(Application11.class,args);
    }

    @PreDestroy
    public void close(){
        Application11.context.close();
    }

}
