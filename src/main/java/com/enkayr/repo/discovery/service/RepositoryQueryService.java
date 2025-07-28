package com.enkayr.repo.discovery.service;

import com.enkayr.repo.discovery.model.RepositoryDiscoveryResponse;

import java.time.LocalDate;

/**
 * Query service which facilitates querying code repositories from an external source based on the provided filter criteria
 */
public interface RepositoryQueryService {

    RepositoryDiscoveryResponse queryRepositories(LocalDate createdFrom, String language);

}
