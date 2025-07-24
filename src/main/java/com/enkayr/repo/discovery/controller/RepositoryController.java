package com.enkayr.repo.discovery.controller;

import com.enkayr.repo.discovery.model.Repository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;

@Controller
public class RepositoryController {

    @QueryMapping
    public List<Repository> find(@Argument LocalDate createdFrom,
                                 @Argument String language) {
        return List.of();
    }

}
