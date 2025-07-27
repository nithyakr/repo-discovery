package com.enkayr.repo.discovery.model;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.time.OffsetDateTime;

@Getter
@Builder
public class Repository {
    private String name;
    private String fullName;
    private RepoOwner owner;
    private Integer score;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private OffsetDateTime pushedAt;
    private Integer stargazersCount;
    private Integer watchersCount;
    private Integer forksCount;
    private Boolean archived;
    private Boolean disabled;

    public void addScore(Integer score) {
        this.score += score;
    }

    public void deductScore(Integer score) {
        this.score -= score;
    }

}
