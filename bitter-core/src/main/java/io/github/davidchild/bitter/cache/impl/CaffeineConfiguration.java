package io.github.davidchild.bitter.cache.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @author : BONE
 * @date : 2021/7/28 10:42
 * Description :
 */
@Configuration
public class CaffeineConfiguration {


    @PostConstruct
    public void configCenterLog() {

    }

    /**
     * Caffeine cache
     *
     * @return cache {Key：value}
     */
    @Bean("biiterCaffeineCacheTemplate")
    @ConditionalOnMissingBean(name = "biiterCaffeineCacheTemplate")
    public Cache<String, String> caffeineCache() {
        return Caffeine.newBuilder()
                .expireAfterWrite(30, TimeUnit.MINUTES)
                .initialCapacity(128)
                .maximumSize(1024)
                .build();
    }


}
