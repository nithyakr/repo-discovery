package com.enkayr.repo.discovery.service;

import com.enkayr.repo.discovery.model.Repository;
import com.enkayr.repo.discovery.service.rule.ScoreRule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Detects all the implemented scoring rules and executes them all against the repositories.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ScoringPipeline {

    private final List<ScoreRule> scoringRules;

    public void executeScoreRules(Repository repository) {
        scoringRules.forEach(rule -> rule.updateScore(repository));
        log.info("Score for Repo {} -> {}", repository.getName(), repository.getScore());
    }


}
