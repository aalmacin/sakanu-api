package com.raidrin.sakanu.controllers;

import com.raidrin.sakanu.services.TechTermsService;
import com.raidrin.sakanu.services.TermResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class OpenAiController {
    private final TechTermsService techTermsService;

    @GetMapping("/")
    public Mono<String> home() {
        return Mono.just("Hello World! This is a test.");
    }

    @GetMapping("/test")
    public Mono<TermResponse> getOpenAIResponse(@RequestParam String term) {
        return Mono.just(techTermsService.getTechTerm("Spring Boot", term));
    }
}

