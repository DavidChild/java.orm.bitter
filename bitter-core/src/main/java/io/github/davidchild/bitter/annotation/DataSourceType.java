package io.github.davidchild.bitter.annotation;

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
