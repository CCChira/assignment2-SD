package com.example.assignment2;

import com.example.assignment2.Utils.TokenGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Assignment2Application {

    public static void main(String[] args) {
        TokenGenerator generator = new TokenGenerator("alabalaportocala");
        System.out.println(generator.generateToken());
        SpringApplication.run(Assignment2Application.class, args);
    }

}
