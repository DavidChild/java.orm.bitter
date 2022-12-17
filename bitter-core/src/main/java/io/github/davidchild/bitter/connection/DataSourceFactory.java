package io.github.davidchild.bitter.connection;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DataSourceFactory {

    private static DataSourceType dataSourceType;

    private static DruidDataSource druidDataSource;

    private static AbstractRoutingDataSource dynamicSource;

    public static void setDataSourceAbstractRoutingDataSources(AbstractRoutingDataSource dataSources) {

        if (dynamicSource != null)
            return;
        dynamicSource = dataSources;

    }

    public static AbstractRoutingDataSource getDynamicSource() {
        if (dynamicSource == null) {
            // auto find the datasource of this project

        }
        return dynamicSource;
    }
}
