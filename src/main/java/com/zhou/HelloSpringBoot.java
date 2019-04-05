package com.zhou;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloSpringBoot {
    @GetMapping("/hello")
    public String say(){
        return "helloSpringBoot";
    }
}
