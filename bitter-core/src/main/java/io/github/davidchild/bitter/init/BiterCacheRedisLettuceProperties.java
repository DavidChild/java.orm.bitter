package io.github.davidchild.bitter.init;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "bitter.cache.redis.lettuce")
public class BiterCacheRedisLettuceProperties {
    @Autowired
    private BitterCacheRedisLettucePoolProperties pool;
}

