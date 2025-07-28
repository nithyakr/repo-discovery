# repo-discovery

A simple service to discover GitHub public repositories and assign a popularity score based on various factors such as stars, forks, updates etc

## 1. Usage
The service is exposed a GraphQL interface where an user can query the repositories as following

POST http://localhost:8080/graphql

```json
query {
  find(createdFrom: "2024-01-01", language: "Java") {
    name
    createdAt
    owner {
        url
        login
    }
    score
    pushedAt
    forksCount
  }
}

```
## 2. Internals 

Upon a user request, the service queries the github public api to fetch matching repos for the filter criteria
Once the repos are queried, then each repository is sent through a scoring pipeline to score each repo based on the 
forks count, stars count and update frequency 

The scoring rules are implemented in different beans as following 

| Rule                        | Usage                                      |
|-----------------------------|--------------------------------------------|
| ForksBasedScoreRule         | Score based on Repository forksCount       |
| StarBasedScoreRule          | Score based on Repository stargazersCount  |
| UpdateRecencyBasedScoreRule | Score based on Repository pushedAt recency |


## 3. Extend

To change/update the scoring Algorithm, 
Simply implement a class inheriting ScoreRule Interface, override the updateScore method which has Repository as an input parameter
Make sure to add `@Component` annotation so that the new rule is detected by the app

```java
public interface ScoreRule {

    void updateScore(Repository repository);

}
```

Example implementation 

```java
@Slf4j
@Component
public class CustomScoreRule implements ScoreRule {

    @Override
    public void updateScore(Repository repository) {
        //Add the custom logic
    }
}
```
## 4. Improvements

* Accept a Repository Context with context/configurations instead of the Repository in the ScoreRule 
so that scoring algorithm can be adjusted based on contextual decisions
* Add a custom property file for the custom beans for all the adjustable configurations and pass it to all the ScoreRules
* Register this service as a github app and implement an app login flow so that more headroom will be available for the app in-terms of rate limiting
* Cache the repositories in the app or on a distributed cache like Redis so that repository querying will be much faster. But since these repos are getting updates/forks/starred often, cache needs to invalidate quickly which defeats the purpose of  caching 

