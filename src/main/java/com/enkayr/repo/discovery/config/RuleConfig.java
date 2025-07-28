package com.enkayr.repo.discovery.config;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(RuleConfig.RuleProperties.class)
public class RuleConfig {

    @Getter
    @Validated
    @RequiredArgsConstructor
    @ConfigurationProperties("rule.configs")
    public static class RuleProperties {
        private final Map<String, String> dynamicRuleProperties = new HashMap<>();
    }

}
