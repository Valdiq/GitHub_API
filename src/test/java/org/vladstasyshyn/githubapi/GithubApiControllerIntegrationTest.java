package org.vladstasyshyn.githubapi;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.vladstasyshyn.githubapi.model.Branch;
import org.vladstasyshyn.githubapi.model.Commit;
import org.vladstasyshyn.githubapi.model.GithubRepositoryResponse;
import org.vladstasyshyn.githubapi.model.Owner;
import org.vladstasyshyn.githubapi.properties.GithubApiProperties;
import org.vladstasyshyn.githubapi.service.GithubService;
import reactor.core.publisher.Mono;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class GithubApiControllerIntegrationTest {

    @LocalServerPort
    private int randomPort;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private GithubApiProperties properties;

    @MockBean
    private GithubService githubService;

    @Test
    public void getNotForksRepositoriesShouldReturnNotForksRepositoriesWhenValidUsernameProvided() {
        //given
        Owner owner = new Owner("testLogin");
        Commit firstCommit = new Commit("someSha");
        Commit secondCommit = new Commit("someOtherSha");
        Branch firstBranch = new Branch("FirstBranch", firstCommit);
        Branch secondBranch = new Branch("FirstBranch", secondCommit);
        GithubRepositoryResponse response = new GithubRepositoryResponse("RepoName", owner, List.of(firstBranch, secondBranch), false);

        //when
        Mockito.when(githubService.getRepositories(owner.login())).thenReturn(Mono.just(List.of(response)));
        var webResponse = webTestClient.get()
                .uri(createUriWithPort(randomPort) + "/github/repos/" + owner.login())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + properties.accessToken())
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange();

        //then
        webResponse.expectStatus().isOk()
                .expectBodyList(GithubRepositoryResponse.class)
                .contains(response);
        Mockito.verify(githubService).getRepositories(owner.login());
    }

    @Test
    public void getNotForksRepositoriesShouldNOTReturnNotForksRepositoriesWhenInvalidUsernameProvided() {
        //given
        Owner owner = new Owner("ownerinvalidname");
        Commit firstCommit = new Commit("someSha");
        Commit secondCommit = new Commit("someOtherSha");
        Branch firstBranch = new Branch("FirstBranch", firstCommit);
        Branch secondBranch = new Branch("FirstBranch", secondCommit);
        GithubRepositoryResponse response = new GithubRepositoryResponse("RepoName", owner, List.of(firstBranch, secondBranch), false);

        //when
        Mockito.when(githubService.getRepositories(owner.login())).thenThrow(WebClientResponseException.NotFound.class);
        var webResponse = webTestClient.get()
                .uri(createUriWithPort(randomPort) + "/github/repos/" + owner.login())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + properties.accessToken())
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange();

        //then
        webResponse.expectStatus().isNotFound();
        Mockito.verify(githubService).getRepositories(owner.login());
    }

    private String createUriWithPort(int port) {
        return "http://localhost:" + port;
    }

}
