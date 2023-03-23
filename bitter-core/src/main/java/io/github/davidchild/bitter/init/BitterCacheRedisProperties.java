package io.github.davidchild.bitter.init;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "bitter.cache.redis")
public class BitterCacheRedisProperties {
    private List<BitterCacheRedisHostProperties> hosts;
    private Integer port;
    private Integer database = 1;
    private String password;
    private Integer timeout = 100;
    @Autowired
    private BiterCacheRedisLettuceProperties lettuce;
}


