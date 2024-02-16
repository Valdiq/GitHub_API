package org.vladstasyshyn.githubapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Commit {
    @JsonProperty("sha")
    private String sha;
}
