package com.enkayr.repo.discovery.rule;

import com.enkayr.repo.discovery.service.rule.UpdateRecencyBasedScoreRule;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TimeRangeTest {

    @Test
    void testTimeRangeSeries() {
        var now = OffsetDateTime.now();

        assertTrue(UpdateRecencyBasedScoreRule.TimeRange.THREE_MONTHS.includes(now.minusMonths(2)));
        assertFalse(UpdateRecencyBasedScoreRule.TimeRange.THREE_MONTHS.includes(now.minusMonths(4)));

        assertTrue(UpdateRecencyBasedScoreRule.TimeRange.SIX_MONTHS.includes(now.minusMonths(5)));
        assertFalse(UpdateRecencyBasedScoreRule.TimeRange.SIX_MONTHS.includes(now.minusMonths(7)));

        assertTrue(UpdateRecencyBasedScoreRule.TimeRange.ONE_YEAR.includes(now.minusMonths(11)));
        assertFalse(UpdateRecencyBasedScoreRule.TimeRange.ONE_YEAR.includes(now.minusMonths(13)));
    }
}

