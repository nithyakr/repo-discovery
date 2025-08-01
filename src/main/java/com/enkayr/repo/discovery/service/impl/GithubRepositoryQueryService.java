package com.enkayr.repo.discovery.service.impl;

import com.enkayr.repo.discovery.config.GitHubRepoConfig;
import com.enkayr.repo.discovery.mapper.RepositoryMapper;
import com.enkayr.repo.discovery.model.RepositoryDiscoveryResponse;
import com.enkayr.repo.discovery.model.github.GitHubSearchResponse;
import com.enkayr.repo.discovery.service.RepositoryQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
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
    public RepositoryDiscoveryResponse queryRepositories(LocalDate createdFrom, String language) {

        var rawQuery = String.format("language:%s+created:>%s", language, createdFrom);

        var uri = UriComponentsBuilder
                .fromPath("/search/repositories")
                .queryParam("q", "{q}")
                .queryParam("sort", "created")
                .queryParam("order", "desc")
                .queryParam("per_page", githubProperties.getDefaultFetchCount())
                .build()
                .expand(rawQuery)
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
            var items = responseBody.getItems().stream()
                    .map(repositoryMapper::fromGitHub)
                    .toList();
            return new RepositoryDiscoveryResponse(
                    items,
                    responseBody.getTotalItems() == 0
                            ? githubProperties.getDefaultFetchCount()
                            : responseBody.getTotalItems()
            );
        }

        log.info("No repositories found for language: {} from created Date: {}", language, createdFrom);
        return new RepositoryDiscoveryResponse(List.of(), 0);
    }

}
