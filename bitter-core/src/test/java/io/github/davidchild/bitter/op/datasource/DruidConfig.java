package io.github.davidchild.bitter.op.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import io.github.davidchild.bitter.extra.BitterSpringUtils;
import io.github.davidchild.bitter.op.annotation.DataSourceType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DruidConfig {
    @Bean
    @ConfigurationProperties("spring.datasource.druid.master")
    public DataSource masterDataSource(DruidProperties druidProperties) {
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        return druidProperties.dataSource(dataSource);
    }

    @Bean
    @ConfigurationProperties("spring.datasource.druid.slave")
    @ConditionalOnProperty(prefix = "spring.datasource.druid.slave", name = "enabled", havingValue = "true")
    public DataSource slaveDataSource(DruidProperties druidProperties) {
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        return druidProperties.dataSource(dataSource);
    }

    @Bean(name = "dynamicDataSource")
    @Primary
    public DynamicDataSource dataSource(DataSource masterDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceType.MASTER.name(), masterDataSource);
        setDataSource(targetDataSources, DataSourceType.SLAVE.name(), "slaveDataSource");
        setDataSource(targetDataSources, DataSourceType.SHARDING.name(), "shardingDataSource");

        DynamicDataSource dynamicDataSource = new DynamicDataSource(masterDataSource, targetDataSources);

        /**
         * 将 dynamicDataSource 注入 到 Bitter 框架,这里的 DynamicDataSource 继承实现了Spring 框架中的 JDBC 管理的
         * AbstractRoutingDataSource,可以将此类直接注入到对象中
         */

        return dynamicDataSource;
    }

    /**
     * set up data sources
     *
     * @param targetDataSources Alternate Data Source Collection
     * @param sourceName        Data source name
     * @param beanName          Bean name
     */
    public void setDataSource(Map<Object, Object> targetDataSources, String sourceName, String beanName) {
        try {
            DataSource dataSource = BitterSpringUtils.getBean(beanName);
            targetDataSources.put(sourceName, dataSource);
        } catch (Exception e) {
        }
    }
}
