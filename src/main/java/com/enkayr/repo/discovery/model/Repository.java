package com.enkayr.repo.discovery.model;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class Repository {
    private String name;
    private RepoOwner owner;
    private Integer score;
    private Instant createdAt;
    private Instant updatedAt;
    private Integer starGazersCount;
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
