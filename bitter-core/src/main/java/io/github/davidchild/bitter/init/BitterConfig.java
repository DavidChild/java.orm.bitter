package io.github.davidchild.bitter.init;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "bitter")
public class BitterConfig {
    private static BitterConfig bitterConfig;
    private boolean sqlLog;

    private String dataSourceClass;

    BitterConfig() {
        sqlLog = false;
    }

    public static BitterConfig getInstance() {
        if (bitterConfig == null) {
            bitterConfig = new BitterConfig();
        }
        return bitterConfig;
    }

}
