package io.github.davidchild.bitter.samples.customdatasource.annotation;

public enum DataSourceType {
    /**
     * main db repo
     */
    MASTER,

    /**
     * readonly db repo
     */
    SLAVE,

    /**
     * sharding repo
     */
    SHARDING
}
