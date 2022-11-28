package com.jzetech;

import com.jzetech.test.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class Main {

    @Autowired
    Test test;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
