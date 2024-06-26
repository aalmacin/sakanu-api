package com.raidrin.sakanu.controllers;

import com.raidrin.sakanu.entities.Term;
import com.raidrin.sakanu.services.TechTermsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@RestController
@RequestMapping("/api/terms")
@RequiredArgsConstructor
public class TermController {
    private final TechTermsService techTermsService;

    @GetMapping
    public Mono<ResponseEntity<Page<Term>>> getAllTerms(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestHeader("Authorization") String token) {
        return Mono.just(techTermsService.findAllTerms(PageRequest.of(page - 1, size), token))
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/term/{id}")
    public Mono<ResponseEntity<Term>> deleteTerm(@PathVariable Long id,
                                                 @RequestHeader("Authorization") String token) {
        return Mono.just(techTermsService.findById(id, token))
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(existingTerm -> techTermsService.deleteTerm(existingTerm, token))
                .map(ResponseEntity::ok);
    }

    @GetMapping("/domains")
    public Mono<List<String>> getDomains(@RequestHeader("Authorization") String token) {
        System.out.println("Received domains request");
        return Mono.just(techTermsService.getDomains(token));
    }
}