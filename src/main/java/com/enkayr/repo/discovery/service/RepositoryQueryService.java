package com.enkayr.repo.discovery.service;

import com.enkayr.repo.discovery.model.Repository;

import java.time.LocalDate;
import java.util.List;

public interface RepositoryQueryService {

    List<Repository> findRepositories(LocalDate createdFrom, String language);

}
