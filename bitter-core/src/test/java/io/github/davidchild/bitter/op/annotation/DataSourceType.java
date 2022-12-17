package io.github.davidchild.bitter.op.annotation;

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
