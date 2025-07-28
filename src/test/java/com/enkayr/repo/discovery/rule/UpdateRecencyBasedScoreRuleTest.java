package com.enkayr.repo.discovery.rule;

import com.enkayr.repo.discovery.config.RuleConfig;
import com.enkayr.repo.discovery.model.Repository;
import com.enkayr.repo.discovery.service.rule.UpdateRecencyBasedScoreRule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {UpdateRecencyBasedScoreRule.class})
public class UpdateRecencyBasedScoreRuleTest {

    @Autowired
    private UpdateRecencyBasedScoreRule rule;

    @Test
    void testUpdateScore_appliesOnlySixMonths() {
        var repo = Repository.builder()
                .name("test-repo")
                .pushedAt(OffsetDateTime.now().minusMonths(4))
                .build();

        rule.updateScore(repo, new RuleConfig.RuleProperties());

        assertEquals(UpdateRecencyBasedScoreRule.TimeRange.SIX_MONTHS.getScore(), repo.getScore());
    }

    @Test
    void testUpdateScore_noMatchingTimeRange() {
        var repo = Repository.builder()
                .name("old-repo")
                .pushedAt(OffsetDateTime.now().minusMonths(13))
                .build();

        rule.updateScore(repo, new RuleConfig.RuleProperties());

        assertEquals(0, repo.getScore());
    }
}
