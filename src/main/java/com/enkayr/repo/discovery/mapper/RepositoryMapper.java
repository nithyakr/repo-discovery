package com.enkayr.repo.discovery.mapper;

import com.enkayr.repo.discovery.model.Repository;
import com.enkayr.repo.discovery.model.github.GitHubSearchResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper
public interface RepositoryMapper {

    @Mapping(target = "score", constant = "0")
    Repository fromGitHub(GitHubSearchResponse.GitHubRepository repo);

    default OffsetDateTime toInstant(Instant source) {
        return source.atOffset(ZoneOffset.UTC);
    }

}
