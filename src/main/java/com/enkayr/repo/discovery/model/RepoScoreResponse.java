package com.enkayr.repo.discovery.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class RepoScoreResponse {

    private List<Repository> repositories;
    private int totalItems;

}
