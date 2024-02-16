package org.vladstasyshyn.githubapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.List;

@Data
@JsonPropertyOrder({"repositoryName", "owner", "branches"})
public class GithubRepositoryResponse {

    @JsonProperty("name")
    private String repositoryName;

    @JsonProperty("owner")
    private Owner owner;

    private List<Branch> branches;

    @JsonIgnore
    private boolean fork;
}
