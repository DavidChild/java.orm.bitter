package io.github.davidchild.bitter.samples.customdatasource;

import io.github.davidchild.bitter.tools.BitterLogUtil;

public class ThreadLocalForDataSource {

    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    public static String getDataSourceType() {
        return CONTEXT_HOLDER.get();
    }

    public static void setDataSourceType(String dsType) {
        BitterLogUtil.getInstance().info("switch {} the datasource", dsType);
        CONTEXT_HOLDER.set(dsType);
    }

    public static void clearDataSourceType() {
        CONTEXT_HOLDER.remove();
    }
}
