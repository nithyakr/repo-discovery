package com.enkayr.repo.discovery.service.rule;

import com.enkayr.repo.discovery.model.Repository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ForksBasedScoreRule implements ScoreRule {

    @Override
    public void adjustScore(Repository repository) {

        log.debug("Adjusting forks based score for the repo [{}] with forks [{}]", repository.getName(), repository.getForksCount());
        if (repository.getForksCount() > 5) {
            repository.addScore(10);
        } else if (repository.getForksCount() > 3) {
            repository.addScore(5);
        } else if (repository.getForksCount() > 0) {
            repository.addScore(3);
        } else {
            log.debug("No forks based score for the repository [{}]", repository.getName());
        }
    }
}
