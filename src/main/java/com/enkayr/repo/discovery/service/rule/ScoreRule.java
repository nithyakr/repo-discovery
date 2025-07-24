package com.enkayr.repo.discovery.service.rule;

import com.enkayr.repo.discovery.model.Repository;

public interface ScoreRule {

    void adjustScore(Repository repository);

}
