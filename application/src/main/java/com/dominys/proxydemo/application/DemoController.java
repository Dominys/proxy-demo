package com.dominys.proxydemo.application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping
    public String helloWorld() {
        return "Рожден служить!!!";
    }

}
