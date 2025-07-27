package com.enkayr.repo.discovery.service.rule;

import com.enkayr.repo.discovery.model.Repository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ForksBasedScoreRule implements ScoreRule {

    @Override
    public void updateScore(Repository repository) {

        log.debug("Adjusting forks based score for the repo [{}] with forks [{}]", repository.getName(), repository.getForksCount());
        if (repository.getForksCount() > 5000) {
            repository.addScore(10);
        } else if (repository.getForksCount() > 3000) {
            repository.addScore(5);
        } else if (repository.getForksCount() > 1000) {
            repository.addScore(3);
        } else {
            log.debug("No forks based score for the repository [{}]", repository.getName());
        }
    }
}
