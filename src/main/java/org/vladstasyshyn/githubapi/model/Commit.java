package org.vladstasyshyn.githubapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Commit(@JsonProperty("sha") String sha) {
}
