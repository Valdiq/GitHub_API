package org.vladstasyshyn.githubapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.vladstasyshyn.githubapi.properties.GithubApiProperties;

@SpringBootApplication
@EnableConfigurationProperties(GithubApiProperties.class)
@EnableCaching
public class GithubRestApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(GithubRestApiApplication.class, args);
    }
}
