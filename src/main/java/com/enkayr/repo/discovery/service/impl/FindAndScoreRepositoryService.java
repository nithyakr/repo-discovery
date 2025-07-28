package com.enkayr.repo.discovery.service.impl;

import com.enkayr.repo.discovery.model.RepositoryDiscoveryResponse;
import com.enkayr.repo.discovery.service.RepositoryFindService;
import com.enkayr.repo.discovery.service.RepositoryQueryService;
import com.enkayr.repo.discovery.service.ScoringPipeline;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class FindAndScoreRepositoryService implements RepositoryFindService {

    private final RepositoryQueryService repositoryQueryService;
    private final ScoringPipeline scoringPipeline;

    @Override
    public RepositoryDiscoveryResponse findRepositories(LocalDate createdFrom, String language) {
        var repositories = repositoryQueryService.queryRepositories(createdFrom, language);
        repositories.getRepositories().forEach(scoringPipeline::executeScoreRules);
        return repositories;
    }
}
