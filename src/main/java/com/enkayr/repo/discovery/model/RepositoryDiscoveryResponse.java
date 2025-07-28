package com.enkayr.repo.discovery.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RepositoryDiscoveryResponse {

    private List<Repository> repositories;
    private int totalItems;

}
