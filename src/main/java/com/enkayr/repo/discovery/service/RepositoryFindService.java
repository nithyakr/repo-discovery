package com.enkayr.repo.discovery.service;

import com.enkayr.repo.discovery.model.RepositoryDiscoveryResponse;

import java.time.LocalDate;

public interface RepositoryFindService {

    RepositoryDiscoveryResponse findRepositories(LocalDate createdFrom, String language);
}
