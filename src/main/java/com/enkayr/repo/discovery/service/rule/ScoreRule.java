package com.enkayr.repo.discovery.service.rule;

import com.enkayr.repo.discovery.model.Repository;

/**
 * Base rule for repository scoring.
 * Any adjustment to the scoring algorithm needs to implement this score Rule and
 * update the score of the repository based on the need.
 */
public interface ScoreRule {

    void updateScore(Repository repository);

}
