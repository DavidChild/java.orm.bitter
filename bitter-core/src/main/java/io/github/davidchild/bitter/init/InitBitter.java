package io.github.davidchild.bitter.init;

import com.alibaba.druid.pool.DruidDataSource;
import io.github.davidchild.bitter.connection.DatabaseType;
import io.github.davidchild.bitter.connection.SessionDbType;
import io.github.davidchild.bitter.db.Bitter;
import io.github.davidchild.bitter.extra.BitterSpringUtils;
import io.github.davidchild.bitter.tools.BitterLogUtil;
import io.github.davidchild.bitter.tools.CoreStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
@AutoConfigureOrder(-1)
public class InitBitter {

    private final String moduleName = "flexible and high-performance MySQL Java orm enhanced framework and data caching framework.";

    @PostConstruct
    public void helloUserBitter() {
        BitterLogUtil.getInstance().info(" \n" +
                "--------------------------------------------------------------------------------------------------------\n"
                + " _          _ _                           _     _ _   _            \n"
                + "| |        | | |                         | |   (_) | | |           \n"
                + "| |__   ___| | | ___     _   _ ___  ___  | |__  _| |_| |_ ___ _ __ \n"
                + "| '_ \\ / _ \\ | |/ _ \\   | | | / __|/ _ \\ | '_ \\| | __| __/ _ \\ '__|\n"
                + "| | | |  __/ | | (_) |  | |_| \\__ \\  __/ | |_) | | |_| ||  __/ |   \n"
                + "|_| |_|\\___|_|_|\\___( )  \\__,_|___/\\___| |_.__/|_|\\__|\\__\\___|_|   \n"
                + "                    |/                                             ");
        BitterLogUtil.getInstance().info("version：{}", "2.1.5-RELEASE");
        BitterLogUtil.getInstance().info("Bulbhead by David-Child, MARCH 3, 2024");
        BitterLogUtil.getInstance().info("Figlet release 2.1.5 --  MARCH 3, 2024 ");
        BitterLogUtil.getInstance().info("github：{}", "https://github.com/davidchild/java.orm.bitter.git");
        BitterLogUtil.getInstance().info(moduleName);
        BitterLogUtil.getInstance().info(" \n"
                + "--------------------------------------------------------------------------------------------------------\n");

    }

    @Autowired
    private BitterSpringUtils springUtils;

    @Autowired
    BitterConfig config;


    @Bean
    public void AutoInitBitterDataSource() throws SQLException {

        if (config != null) {
            BitterConfig.getInstance().setCache(config.getCache());
            BitterConfig.getInstance().setSqlLog(config.isSqlLog());
            if (config.getDataSourceClass() != null && CoreStringUtils.isNotEmpty(config.getDataSourceClass())) {
                BitterConfig.getInstance().setDataSourceClass(config.getDataSourceClass());
                AbstractRoutingDataSource dataSource = springUtils.getBean(BitterConfig.getInstance().getDataSourceClass());
                if (dataSource != null) Bitter.setDbSources(dataSource);
            } else {
                AbstractRoutingDataSource dataSource;
                dataSource = springUtils.getBean(AbstractRoutingDataSource.class);
                if (dataSource != null) {
                    Bitter.setDbSources(dataSource);
                    SessionDbType.setSessionDbType(getDataBaseId((DataSource) dataSource));
                    return;
                }
                DruidDataSource druidDataSource = springUtils.getBean(DruidDataSource.class);
                if (druidDataSource != null) {
                    Bitter.setDbSources(druidDataSource);
                    SessionDbType.setSessionDbType(getDataBaseId((DataSource) druidDataSource));
                    return;
                }
                DataSource c = springUtils.getBean(DataSource.class);
                if (druidDataSource != null) {
                    Bitter.setDbSources(dataSource);
                    SessionDbType.setSessionDbType(getDataBaseId((DataSource) c));
                    return;
                }
            }
        }
    }

    private DatabaseType getDataBaseId(DataSource dataSource) throws SQLException {
        Connection conn = dataSource.getConnection();
        String dbTypeName = conn.getMetaData().getDatabaseProductName();
        BitterLogUtil.getInstance().info("bitter checked the db type name is :" + dbTypeName);
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
        if (dbTypeName != null && dbTypeName != "") {
            return DatabaseType.valueOf(dbTypeName);
        }
        return DatabaseType.MySQL;
    }
}
