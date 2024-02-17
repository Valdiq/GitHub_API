package org.vladstasyshyn.githubapi.properties;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "github-api")
public record GithubApiProperties(@NotNull String accessToken, @NotNull String baseUrl, @NotNull String userReposUri,
                                  @NotNull String userRepoBranchesUri) {

}
