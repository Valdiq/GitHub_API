package org.vladstasyshyn.githubapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vladstasyshyn.githubapi.model.GithubRepositoryResponse;
import org.vladstasyshyn.githubapi.service.GithubService;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/github")
public class GithubApiController {

    private final GithubService githubService;

    @GetMapping("/repos/{username}")
    public Mono<List<GithubRepositoryResponse>> getNotForksRepositories(@PathVariable String username) {
        return githubService.getRepositories(username);
    }
}
