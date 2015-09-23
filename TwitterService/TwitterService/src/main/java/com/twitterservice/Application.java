package com.twitterservice;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import com.twitterservice.repository.ApiRepository;
import com.twitterservice.repository.TwitterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan(basePackageClasses = com.twitterservice.service.ApiRestService.class)
public class Application {

    @Autowired
    TwitterRepository repository;
     @Autowired
    ApiRepository apirepo;
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
