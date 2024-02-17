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
        this.webClient = webClientBuilder.baseUrl(properties.baseUrl())
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + properties.accessToken())
                .defaultHeader(HttpHeaders.ACCEPT, "application/json")
                .build();
    }

    public Mono<List<GithubRepositoryResponse>> getRepositories(String username) {
        return webClient.get()
                .uri(properties.userReposUri(), username)
                .retrieve()
                .bodyToFlux(GithubRepositoryResponse.class)
                .flatMap(repo -> getBranches(username, repo.repositoryName())
                        .map(branches -> {
                            return new GithubRepositoryResponse(repo.repositoryName(), repo.owner(), branches, repo.fork());
                        })
                        .filter(response -> !response.fork()))
                .collectList();
    }

    public Mono<List<Branch>> getBranches(String username, String repo) {
        return webClient.get()
                .uri(properties.userRepoBranchesUri(), username, repo)
                .retrieve()
                .bodyToFlux(Branch.class)
                .collectList();
    }

}
