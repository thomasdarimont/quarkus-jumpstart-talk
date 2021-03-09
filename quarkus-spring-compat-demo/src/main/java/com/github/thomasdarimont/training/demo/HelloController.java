package com.github.thomasdarimont.training.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping
    public String hello(@RequestParam("name") String name) {
        return String.format("Hello %s %s ", name, Instant.now());
    }
}
