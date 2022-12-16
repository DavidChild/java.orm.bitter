package io.github.davidchild.bitter.connection;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DataSourceFactory {

    private static AbstractRoutingDataSource dynamicSource;

    public static void setDataSourceAbstractRoutingDataSources(AbstractRoutingDataSource dataSources) {

        if (dynamicSource != null)
            return;
        dynamicSource = dataSources;

    }

    public static AbstractRoutingDataSource getDynamicSource() {
        return dynamicSource;
    }
}
