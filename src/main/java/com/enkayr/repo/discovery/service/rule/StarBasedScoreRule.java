package com.enkayr.repo.discovery.service.rule;

import com.enkayr.repo.discovery.model.Repository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StarBasedScoreRule implements ScoreRule {


    @Override
    public void updateScore(Repository repository) {
        log.debug("Adjusting star based score for the repo [{}] with star gazers [{}]", repository.getName(), repository.getForksCount());
        if (repository.getStargazersCount() > 5000) {
            repository.addScore(10);
        } else if (repository.getStargazersCount() > 3000) {
            repository.addScore(5);
        } else if (repository.getStargazersCount() > 1000) {
            repository.addScore(3);
        } else {
            log.debug("No Stars based score for the repository [{}]", repository.getName());
        }
    }
}
