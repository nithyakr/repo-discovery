package com.enkayr.repo.discovery.service.rule;

import com.enkayr.repo.discovery.config.RuleConfig;
import com.enkayr.repo.discovery.model.Repository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StarBasedScoreRule implements ScoreRule {


    @Override
    public void updateScore(Repository repository, RuleConfig.RuleProperties ruleProperties) {
        log.debug("Adjusting star based score for the repo [{}] with star gazers [{}]", repository.getName(), repository.getForksCount());
        if (repository.getStargazersCount() > 5000) {
            var topTierWeight = ruleProperties.getDynamicRuleProperties().getOrDefault("star-top-weight", "1");
            repository.addScore(repository.getStargazersCount() * Integer.parseInt(topTierWeight));
        } else if (repository.getStargazersCount() > 3000) {
            var midTierWeight = ruleProperties.getDynamicRuleProperties().getOrDefault("star-mid-weight", "1");
            repository.addScore(repository.getStargazersCount() * Integer.parseInt(midTierWeight));
        } else if (repository.getStargazersCount() > 1000) {
            var botTierWeight = ruleProperties.getDynamicRuleProperties().getOrDefault("star-bot-weight", "1");
            repository.addScore(repository.getStargazersCount() * Integer.parseInt(botTierWeight));
        } else {
            log.debug("No Stars based score for the repository [{}]", repository.getName());
        }
    }
}
