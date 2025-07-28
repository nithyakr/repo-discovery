package com.enkayr.repo.discovery.service.rule;

import com.enkayr.repo.discovery.model.Repository;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Comparator;

@Slf4j
@Component
public class UpdateRecencyBasedScoreRule implements ScoreRule {

    @Override
    public void updateScore(Repository repository) {
        var matchedRange = Arrays.stream(TimeRange.values())
                .sorted(Comparator.comparingInt(TimeRange::getMonths))
                .filter(range -> range.includes(repository.getPushedAt()))
                .findFirst();

        matchedRange.ifPresentOrElse(range -> {
            log.debug("Repo [{}] pushed at {}. Adding score [{}]",
                    repository.getName(), repository.getPushedAt(), range.getScore());
            repository.addScore(range.getScore());
        }, () -> repository.addScore(0));
    }

    @Getter
    public enum TimeRange {
        THREE_MONTHS(3, 10),
        SIX_MONTHS(6, 5),
        ONE_YEAR(12, 3);

        private final int months;
        private final int score;

        TimeRange(int months, int score) {
            this.months = months;
            this.score = score;
        }

        public boolean includes(OffsetDateTime pushedAt) {
            var cutoff = OffsetDateTime.now().minusMonths(months);
            return pushedAt != null && pushedAt.isAfter(cutoff);
        }
    }

}
