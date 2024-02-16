package org.vladstasyshyn.githubapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Owner {
    @JsonProperty("login")
    private String login;
}
