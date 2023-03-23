package io.github.davidchild.bitter.init;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "bitter.cache")
public class BitterCacheProperties {

  
    private boolean enabledRedis;

    private boolean enabledLocalCache;

    @Autowired
    private BitterCacheRedisProperties redis;

    @Autowired
    private BitterCacheCaffeineProperties caffeine;


}
