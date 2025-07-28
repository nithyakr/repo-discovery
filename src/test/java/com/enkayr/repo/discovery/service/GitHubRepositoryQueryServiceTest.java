package com.enkayr.repo.discovery.service;

import com.enkayr.repo.discovery.config.GitHubRepoConfig;
import com.enkayr.repo.discovery.mapper.RepositoryMapper;
import com.enkayr.repo.discovery.model.Repository;
import com.enkayr.repo.discovery.model.github.GitHubSearchResponse;
import com.enkayr.repo.discovery.service.impl.GithubRepositoryQueryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GitHubRepositoryQueryServiceTest {

    private RestTemplate restTemplate;
    private RepositoryMapper repositoryMapper;
    private GitHubRepoConfig.GithubProperties githubProperties;
    private GithubRepositoryQueryService queryService;

    @BeforeEach
    void setUp() {
        restTemplate = mock(RestTemplate.class);
        repositoryMapper = mock(RepositoryMapper.class);
        githubProperties = new GitHubRepoConfig.GithubProperties("https://api.github.com",
                30,
                Duration.ofSeconds(2),
                Duration.ofSeconds(1));
        queryService = new GithubRepositoryQueryService(restTemplate,  repositoryMapper, githubProperties);
    }

    @Test
    void testQueryRepositories_success() {
        var gitHubRepo = new GitHubSearchResponse.GitHubRepository();
        gitHubRepo.setName("sample-repo");
        var mockResponse = new GitHubSearchResponse();
        mockResponse.setItems(List.of(gitHubRepo));

        var mappedRepo = Repository.builder().name("sample-repo").build();

        when(restTemplate.exchange(anyString(), any(), any(), eq(GitHubSearchResponse.class)))
                .thenReturn(ResponseEntity.ok(mockResponse));
        when(repositoryMapper.fromGitHub(gitHubRepo)).thenReturn(mappedRepo);

        var result = queryService.queryRepositories(LocalDate.now().minusMonths(3), "Java");

        assertEquals(1, result.size());
        assertEquals("sample-repo", result.get(0).getName());
    }

    @Test
    void testQueryRepositories_nullResponseBody_returnsEmptyList() {
        when(restTemplate.exchange(anyString(), any(), any(), eq(GitHubSearchResponse.class)))
                .thenReturn(ResponseEntity.ok(null));

        var result = queryService.queryRepositories(LocalDate.now(), "Java");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testQueryRepositories_apiError_returnsEmptyList() {
        when(restTemplate.exchange(anyString(), any(), any(), eq(GitHubSearchResponse.class)))
                .thenThrow(new RuntimeException("GitHub API error"));

        assertThrows(RuntimeException.class, () ->
                queryService.queryRepositories(LocalDate.now(), "Java"));
    }
}

