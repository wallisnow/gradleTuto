package com.gradle.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        UserWebClient uwc = new UserWebClient();
        System.out.println(uwc.getResult());
    }

}
