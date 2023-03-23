package io.github.davidchild.bitter.init;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "bitter.cache.redis.lettuce.pool")
public class BitterCacheRedisLettucePoolProperties {

    private Integer minIdle = 0;

    private Integer maxIdle = 10;

    private Integer maxActive = 30;

    private Integer maxTotal = 30;

    private Integer maxWait = 100;

}
