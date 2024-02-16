package org.vladstasyshyn.githubapi.properties;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "github-api")
@Data
public class GithubApiProperties {
    @NotNull
    private String accessToken;

    @NotNull
    private String baseUrl;

    @NotNull
    private String userReposUri;

    @NotNull
    private String userRepoBranchesUri;
}
