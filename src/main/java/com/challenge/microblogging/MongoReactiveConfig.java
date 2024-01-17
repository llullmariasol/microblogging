package com.challenge.microblogging;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
@EnableReactiveMongoRepositories(basePackages = "com.challenge.microblogging.repository")
public class MongoReactiveConfig {

}
