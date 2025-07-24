package com.enkayr.repo.discovery.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RepoOwner {

    private String login;
    private String url;
}
