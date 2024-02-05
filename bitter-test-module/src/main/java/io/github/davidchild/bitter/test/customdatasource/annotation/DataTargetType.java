package io.github.davidchild.bitter.test.customdatasource.annotation;

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
