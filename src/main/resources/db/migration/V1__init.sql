CREATE SEQUENCE repository_seq;

CREATE TABLE repository (
    id BIGINT PRIMARY KEY,
    name VARCHAR NOT NULL,
    full_name VARCHAR NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    language VARCHAR NOT NULL,
    watchers_count INTEGER,
    forks_count INTEGER,
    stargazers_count INTEGER
)