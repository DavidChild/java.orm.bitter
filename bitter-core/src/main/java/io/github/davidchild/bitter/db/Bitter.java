package io.github.davidchild.bitter.db;

import io.github.davidchild.bitter.connection.DataSourceFactory;
import io.github.davidchild.bitter.init.BitterConfig;
import io.github.davidchild.bitter.op.scope.ThrowingConsumer;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class Bitter {

    public static void setDbSources(AbstractRoutingDataSource dataSources) {
        DataSourceFactory.setDataSourceAbstractRoutingDataSources(dataSources);
    }


    public static void setDbSources(ThrowingConsumer<BitterConfig> fn) {
        fn.accept(BitterConfig.getInstance());
    }

    public static void setDbSources(AbstractRoutingDataSource dataSources, ThrowingConsumer<BitterConfig> fn) {
        DataSourceFactory.setDataSourceAbstractRoutingDataSources(dataSources);
        fn.accept(BitterConfig.getInstance());
    }


}
