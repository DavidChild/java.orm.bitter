package io.github.davidchild.bitter.init;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("bitter.cache.caffeine")
public class BitterCacheCaffeineProperties {
    
}
