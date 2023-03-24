package io.github.davidchild.bitter.samples.customdatasource.annotation;

public enum DataTargetType {
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
