package com.enkayr.repo.discovery.controller;

import com.enkayr.repo.discovery.model.Repository;
import com.enkayr.repo.discovery.model.RepositoryDiscoveryResponse;
import com.enkayr.repo.discovery.service.RepositoryFindService;
import com.enkayr.repo.discovery.service.RepositoryQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class RepositoryController {

    private final RepositoryFindService repositoryFindService;

    @QueryMapping
    public RepositoryDiscoveryResponse find(@Argument LocalDate createdFrom,
                                            @Argument String language) {
        log.info("Request received to find and score repositories from [{}] based on [{}]", createdFrom, language);
        return repositoryFindService.findRepositories(createdFrom, language);
    }

}
