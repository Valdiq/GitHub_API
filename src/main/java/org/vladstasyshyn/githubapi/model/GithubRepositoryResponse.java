package org.vladstasyshyn.githubapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({"repositoryName", "owner", "branches"})
public record GithubRepositoryResponse(@JsonProperty("name") String repositoryName, @JsonProperty("owner") Owner owner,
                                       List<Branch> branches, @JsonIgnore boolean fork) {
    public GithubRepositoryResponse(String repositoryName, Owner owner, List<Branch> branches, boolean fork) {
        if (branches == null) {
            this.branches = new ArrayList<>();
        } else {
            this.branches = List.copyOf(branches);
        }
        this.repositoryName = repositoryName;
        this.owner = owner;
        this.fork = fork;
    }

    public List<Branch> branches() {
        return List.copyOf(branches);
    }
}
