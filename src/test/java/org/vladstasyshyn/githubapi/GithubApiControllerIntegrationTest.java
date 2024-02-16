package org.vladstasyshyn.githubapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.vladstasyshyn.githubapi.properties.GithubApiProperties;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class GithubApiControllerIntegrationTest {

    @LocalServerPort
    private int randomPort;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private GithubApiProperties properties;

    @Test
    public void getNotForksRepositoriesShouldReturnNotForksRepositoriesWhenValidUsernameProvided() {
        //when
        var webResponse = webTestClient.get()
                .uri(createUriWithPort(randomPort) + "/github/repos/owner")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + properties.getAccessToken())
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange();

        //then
        webResponse.expectStatus().isOk();
    }

    @Test
    public void getNotForksRepositoriesShouldNOTReturnNotForksRepositoriesWhenInvalidUsernameProvided() {
        //when
        var webResponse = webTestClient.get()
                .uri(createUriWithPort(randomPort) + "/github/repos/ownerinvalidname")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + properties.getAccessToken())
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange();

        //then
        webResponse.expectStatus().isNotFound();
    }

    private String createUriWithPort(int port) {
        return "http://localhost:" + port;
    }

}
