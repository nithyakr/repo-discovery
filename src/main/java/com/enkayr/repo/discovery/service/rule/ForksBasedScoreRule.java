package com.enkayr.repo.discovery.service.rule;

import com.enkayr.repo.discovery.config.RuleConfig;
import com.enkayr.repo.discovery.model.Repository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ForksBasedScoreRule implements ScoreRule {

    @Override
    public void updateScore(Repository repository, RuleConfig.RuleProperties ruleProperties) {

        log.debug("Adjusting forks based score for the repo [{}] with forks [{}]", repository.getName(), repository.getForksCount());
        if (repository.getForksCount() > 5000) {
            var topTierWeight = ruleProperties.getDynamicRuleProperties().getOrDefault("forks-top-weight", "1");
            repository.addScore(repository.getForksCount() * Integer.parseInt(topTierWeight));
        } else if (repository.getForksCount() > 3000) {
            var midTierWeight = ruleProperties.getDynamicRuleProperties().getOrDefault("forks-mid-weight", "1");
            repository.addScore(repository.getForksCount() * Integer.parseInt(midTierWeight));
        } else if (repository.getForksCount() > 1000) {
            var botTierWeight = ruleProperties.getDynamicRuleProperties().getOrDefault("forks-bot-weight", "1");
            repository.addScore(repository.getForksCount() * Integer.parseInt(botTierWeight));
        } else {
            log.debug("No forks based score for the repository [{}]", repository.getName());
        }
    }
}
