package io.github.davidchild.bitter.db;

import lombok.Data;

@Data
public class BitterConfig {
    private static BitterConfig bitterConfig;
    private boolean enableSqlLog;

    private BitterConfig() {
        this.enableSqlLog = false;
    }

    public static BitterConfig getInstance() {
        if (bitterConfig == null) {
            bitterConfig = new BitterConfig();
        }
        return bitterConfig;
    }
}
