package io.github.davidchild.bitter.op.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DruidProperties {
    @Value("${spring.datasource.druid.initialSize}")
    private int initialSize;

    @Value("${spring.datasource.druid.minIdle}")
    private int minIdle;

    @Value("${spring.datasource.druid.maxActive}")
    private int maxActive;

    @Value("${spring.datasource.druid.maxWait}")
    private int maxWait;

    @Value("${spring.datasource.druid.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${spring.datasource.druid.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    @Value("${spring.datasource.druid.maxEvictableIdleTimeMillis}")
    private int maxEvictableIdleTimeMillis;

    @Value("${spring.datasource.druid.validationQuery}")
    private String validationQuery;

    @Value("${spring.datasource.druid.testWhileIdle}")
    private boolean testWhileIdle;

    @Value("${spring.datasource.druid.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${spring.datasource.druid.testOnReturn}")
    private boolean testOnReturn;

    public DruidDataSource dataSource(DruidDataSource datasource) {
        /** Configure initialization size, minimum, maximum */
        datasource.setInitialSize(initialSize);
        datasource.setMaxActive(maxActive);
        datasource.setMinIdle(minIdle);

        /** Configure the timeout time for obtaining the connection */
        datasource.setMaxWait(maxWait);

        /** Configure how often to detect idle connections that need to be closed. The unit is milliseconds */
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);

        /** Configure the minimum and maximum lifetime of a connection in the pool, in milliseconds */
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setMaxEvictableIdleTimeMillis(maxEvictableIdleTimeMillis);

        /**
         * The SQL used to check whether the connection is valid. It is required to be a query statement. Select 'x' is
         * commonly used. If validationQuery is null, testOnBorrow, testOnReturn, and testWhileIdle will not work.
         */
        datasource.setValidationQuery(validationQuery);
        /**
         * It is recommended to set it to true, which will not affect performance and ensure security. Detect when
         * applying for connection. If the idle time is greater than timeBetweenEffectionRunsMillis, execute
         * validationQuery to check whether the connection is valid
         */
        datasource.setTestWhileIdle(testWhileIdle);
        /**
         * When applying for a connection, execute validationQuery to check whether the connection is valid. This
         * configuration will reduce performance
         */
        datasource.setTestOnBorrow(testOnBorrow);
        /**
         * When returning the connection, execute validationQuery to check whether the connection is valid. This
         * configuration will reduce the performance
         */
        datasource.setTestOnReturn(testOnReturn);
        return datasource;
    }
}
