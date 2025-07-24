package com.enkayr.repo.discovery.service.impl;

import com.enkayr.repo.discovery.config.GitHubRepoConfig;
import com.enkayr.repo.discovery.model.Repository;
import com.enkayr.repo.discovery.service.RepositoryQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GithubRepositoryQueryService implements RepositoryQueryService {

    private final GitHubRepoConfig repoConfig;
    private final RestTemplate restTemplate;

    @Override
    public List<Repository> findRepositories(LocalDate createdFrom, String language) {
        return List.of();
    }
}
