package org.vladstasyshyn.githubapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.vladstasyshyn.githubapi.model.Branch;
import org.vladstasyshyn.githubapi.model.GithubRepositoryResponse;
import org.vladstasyshyn.githubapi.properties.GithubApiProperties;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GithubService {

    private final WebClient webClient;

    private final GithubApiProperties properties;

    @Autowired
    public GithubService(WebClient.Builder webClientBuilder, GithubApiProperties properties) {
        this.properties = properties;
        this.webClient = webClientBuilder.baseUrl(properties.getBaseUrl())
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + properties.getAccessToken())
                .defaultHeader(HttpHeaders.ACCEPT, "application/json")
                .build();
    }

    public Mono<List<GithubRepositoryResponse>> getRepositories(String username) {
        return webClient.get()
                .uri(properties.getUserReposUri(), username)
                .retrieve()
                .bodyToFlux(GithubRepositoryResponse.class)
                .flatMap(repo -> getBranches(username, repo.getRepositoryName())
                        .map(branches -> {
                            repo.setBranches(branches);
                            return repo;
                        })
                        .filter(response -> !response.isFork()))
                .collectList();
    }

    public Mono<List<Branch>> getBranches(String username, String repo) {
        return webClient.get()
                .uri(properties.getUserRepoBranchesUri(), username, repo)
                .retrieve()
                .bodyToFlux(Branch.class)
                .collectList();
    }

}
