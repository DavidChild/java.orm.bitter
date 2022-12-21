package io.github.davidchild.bitter.connection;

import com.alibaba.druid.pool.DruidDataSource;
import io.github.davidchild.bitter.exception.DataSourceException;
import io.github.davidchild.bitter.tools.BitterLogUtil;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.sql.Connection;

public class DataSourceFactory {

    private static DataSourceType dataSourceType;

    private static DruidDataSource druidDataSource;

    private static DataSource dataSource;

    private static AbstractRoutingDataSource dynamicSource;

    public static void setDataSourceAbstractRoutingDataSources(AbstractRoutingDataSource dataSources) {

        if (dynamicSource != null)
            return;
        dynamicSource = dataSources;
        dataSourceType = DataSourceType.AbstractRoutingDataSource;

    }

    public static void setDataSourceDruidDataSources(DruidDataSource dataSources) {
        if (dynamicSource != null)
            return;
        druidDataSource = dataSources;
        dataSourceType = DataSourceType.Druid;

    }

    public static void setDataSourceOriginDataSources(DataSource dataSources) {
        if (dynamicSource != null)
            return;
        dataSource = dataSources;
        dataSourceType = DataSourceType.OriginDataSource;

    }

    public static Connection getDataSourceConnection() {
        try {
            if (dynamicSource != null)
                return dynamicSource.getConnection();
            if (druidDataSource != null)
                return dynamicSource.getConnection();
            if (dataSource != null) return dataSource.getConnection();
        } catch (Exception ex) {
            BitterLogUtil.getInstance().error("bitter get the sql connection error.pls check your sql datasource setting and adjust that.");
            throw new DataSourceException("bitter get the sql connection error,error detail msg is ------\r\n" + ex.getMessage());
        }

        throw new DataSourceException("bitter failed to automatically obtain the DRUID data source in your project or the data source implemented by AbstractRoutingDataSource");
    }
}
