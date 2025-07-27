package com.enkayr.repo.discovery.model.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class GitHubSearchResponse {

    public List<GitHubRepository> items;
    private int totalItems;

    @Data
   public static class GitHubRepository {
        private String name;
        @JsonProperty("full_name")
        private String fullName;
        private Owner owner;
        @JsonProperty("created_at")
        private Instant createdAt;
        @JsonProperty("updated_at")
        private Instant updatedAt;
        @JsonProperty("pushed_at")
        private Instant pushedAt;
        @JsonProperty("stargazers_count")
        private Integer stargazersCount;
        @JsonProperty("watchers_count")
        private Integer watchersCount;
        @JsonProperty("forks_count")
        private Integer forksCount;
        private Boolean archived;
        private Boolean disabled;
    }

    @Data
    public static class Owner {
        private String login;
        private String url;
    }

}
