package com.enkayr.repo.discovery.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
@EnableConfigurationProperties(GitHubRepoConfig.GithubProperties.class)
public class GitHubRepoConfig {

    @Bean
    public RestTemplate githubRestTemplate(GithubProperties githubProperties, ObjectMapper objectMapper) {
        return new RestTemplateBuilder()
                .defaultHeader(HttpHeaders.ACCEPT, "application/vnd.github+json")
                .rootUri(githubProperties.getBaseUrl())
                .connectTimeout(githubProperties.getConnectTimeout())
                .readTimeout(githubProperties.getReadTimeout())
                .messageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .build();
    }


    @Getter
    @Validated
    @RequiredArgsConstructor
    @ConfigurationProperties("github")
    public static class GithubProperties {
        @NotBlank
        private final String baseUrl;
        private final int defaultFetchCount;
        private final Duration connectTimeout;
        private final Duration readTimeout;
    }


}
