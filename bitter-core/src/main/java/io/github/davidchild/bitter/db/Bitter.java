package io.github.davidchild.bitter.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import io.github.davidchild.bitter.connection.DataSourceFactory;
import io.github.davidchild.bitter.op.scope.ThrowingConsumer;

public class Bitter {

    public static void setDbSources(AbstractRoutingDataSource dataSources) {
        DataSourceFactory.setDataSourceAbstractRoutingDataSources(dataSources);

    }

    public static void setDbSources(AbstractRoutingDataSource dataSources, ThrowingConsumer<BitterConfig> fn) {
        DataSourceFactory.setDataSourceAbstractRoutingDataSources(dataSources);
        fn.accept(BitterConfig.getInstance());
    }

}
