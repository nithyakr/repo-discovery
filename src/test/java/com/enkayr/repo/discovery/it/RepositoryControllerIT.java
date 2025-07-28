package com.enkayr.repo.discovery.it;

import com.enkayr.repo.discovery.model.Repository;
import com.enkayr.repo.discovery.service.RepositoryQueryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class RepositoryControllerIT {

    @Autowired
    private GraphQlTester graphQlTester;

    @MockitoBean
    private RepositoryQueryService repositoryQueryService;

    @Test
    void shouldReturnRepositoriesWithScores() {
        var repo = Repository.builder()
                .name("demo-repo")
                .fullName("demo-org/demo-repo")
                .pushedAt(OffsetDateTime.now().minusMonths(2))
                .stargazersCount(4000)
                .forksCount(3500)
                .score(0)
                .build();

        Mockito.when(repositoryQueryService.queryRepositories(Mockito.any(), Mockito.anyString()))
                .thenReturn(List.of(repo));

        var query = """
            query {
              find(createdFrom: \"2024-01-01\", language: \"Java\") {
                name
                score
              }
            }
            """;

        graphQlTester.document(query)
                .execute()
                .path("find[0].name").entity(String.class).isEqualTo("demo-repo")
                .path("find[0].score").entity(Integer.class).satisfies(score ->
                        assertThat(score).isEqualTo(5 + 5 + 10)
                );
    }

    @Test
    void shouldReturnEmptyListIfNoRepos() {
        Mockito.when(repositoryQueryService.queryRepositories(Mockito.any(), Mockito.anyString()))
                .thenReturn(Collections.emptyList());

        var query = """
            query {
              find(createdFrom: \"2024-01-01\", language: \"Kotlin\") {
                name
                score
              }
            }
            """;

        graphQlTester.document(query)
                .execute()
                .path("find").entityList(Repository.class).hasSize(0);
    }

    @Test
    void shouldAssignZeroScoreWhenNoRuleMatches() {
        var repo = Repository.builder()
                .name("low-activity-repo")
                .pushedAt(OffsetDateTime.now().minusYears(2))
                .stargazersCount(50)
                .forksCount(10)
                .score(0)
                .build();

        Mockito.when(repositoryQueryService.queryRepositories(Mockito.any(), Mockito.anyString()))
                .thenReturn(List.of(repo));

        var query = """
            query {
              find(createdFrom: \"2023-01-01\", language: \"Python\") {
                name
                score
              }
            }
            """;

        graphQlTester.document(query)
                .execute()
                .path("find[0].score").entity(Integer.class).isEqualTo(0);
    }

    @Test
    void shouldMatch3MonthsTimeRange() {
        var pushedAt = OffsetDateTime.now().minusMonths(2);
        var repo = Repository.builder()
                .name("active-repo")
                .pushedAt(pushedAt)
                .stargazersCount(0)
                .forksCount(0)
                .score(0)
                .build();

        Mockito.when(repositoryQueryService.queryRepositories(Mockito.any(), Mockito.anyString()))
                .thenReturn(List.of(repo));

        var query = """
            query {
              find(createdFrom: \"2024-01-01\", language: \"Go\") {
                name
                score
              }
            }
            """;

        graphQlTester.document(query)
                .execute()
                .path("find[0].score").entity(Integer.class).isEqualTo(10);
    }
}

