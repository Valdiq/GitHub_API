package org.vladstasyshyn.githubapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({"name", "lastCommit"})
public class Branch {
    @JsonProperty("name")
    private String name;

    @JsonProperty("commit")
    private Commit lastCommit;
}
