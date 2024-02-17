package org.vladstasyshyn.githubapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"name", "lastCommit"})
public record Branch(@JsonProperty("name") String name, @JsonProperty("commit") Commit lastCommit) {
}
