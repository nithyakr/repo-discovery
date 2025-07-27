package com.enkayr.repo.discovery.controller;

import com.enkayr.repo.discovery.model.Repository;
import com.enkayr.repo.discovery.service.RepositoryFindService;
import com.enkayr.repo.discovery.service.RepositoryQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class RepositoryController {

    private final RepositoryFindService repositoryFindService;

    @QueryMapping
    public List<Repository> find(@Argument LocalDate createdFrom,
                                 @Argument String language) {
        return repositoryFindService.findRepositories(createdFrom, language);
    }

}
