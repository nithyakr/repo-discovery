package com.enkayr.repo.discovery.service.impl;

import com.enkayr.repo.discovery.config.GitHubRepoConfig;
import com.enkayr.repo.discovery.mapper.RepositoryMapper;
import com.enkayr.repo.discovery.model.Repository;
import com.enkayr.repo.discovery.model.github.GitHubSearchResponse;
import com.enkayr.repo.discovery.service.RepositoryQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GithubRepositoryQueryService implements RepositoryQueryService {

    private final RestTemplate restTemplate;
    private final RepositoryMapper repositoryMapper;
    private final GitHubRepoConfig.GithubProperties githubProperties;

    @Override
    public List<Repository> queryRepositories(LocalDate createdFrom, String language) {

        var q = String.format("language:%s", language);

        var uri = UriComponentsBuilder
                .fromPath("/search/repositories")
                .queryParam("q", q)
                .queryParam("sort", "created")
                .queryParam("order", "desc")
                .queryParam("per_page", githubProperties.getDefaultFetchCount())
                .build(true)
                .toUriString();

        log.info("Github search repositories uri: {}", uri);

        var response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                GitHubSearchResponse.class
        );

        var responseBody = response.getBody();
        if (responseBody != null) {
            return responseBody.getItems().stream().map(repositoryMapper::fromGitHub).toList();
        }
        log.info("No repositories found for language: {} from created Date: {}", language, createdFrom);
        return List.of();
    }
}
