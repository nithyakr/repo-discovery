package com.enkayr.repo.discovery.service;

import com.enkayr.repo.discovery.model.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Query service which facilitates querying code repositories from an external source based on the provided filter criteria
 */
public interface RepositoryQueryService {

    List<Repository> queryRepositories(LocalDate createdFrom, String language);

}
