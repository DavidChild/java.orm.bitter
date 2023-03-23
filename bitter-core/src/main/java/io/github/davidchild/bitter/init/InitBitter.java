package io.github.davidchild.bitter.init;

import com.alibaba.druid.pool.DruidDataSource;
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

@Configuration
@AutoConfigureOrder(-1)
public class InitBitter {

    private final String moduleName = "bitter 灵活,性能优越的 mysql java  orm 增强型框架.\n";

    @PostConstruct
    public void helloUserBitter() {
        BitterLogUtil.getInstance().info(moduleName);
        BitterLogUtil.getInstance().info("Bulbhead by David child, 23december2022\n");
        BitterLogUtil.getInstance().info("Figlet release 1.3.4 -- december 23, 2022 \n");
        BitterLogUtil.getInstance().info("\n" +
                " _   _  ____  __    __    _____    ____  ____  ____  ____  ____  ____ \n" +
                "( )_( )( ___)(  )  (  )  (  _  )  (  _ \\(_  _)(_  _)(_  _)( ___)(  _ \\\n" +
                " ) _ (  )__)  )(__  )(__  )(_)(    ) _ < _)(_   )(    )(   )__)  )   /\n" +
                "(_) (_)(____)(____)(____)(_____)  (____/(____) (__)  (__) (____)(_)\\_)\n");
        BitterLogUtil.getInstance().info("version：{}", "1.3.4-RELEASE\n");
        BitterLogUtil.getInstance().info("github：{}", "https://github.com/davidchild/java.orm.bitter.git");


    }

    @Autowired
    private BitterSpringUtils springUtils;

    @Autowired
    BitterConfig config;


    @Bean
    public void AutoInitBitterDataSource() {

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
                    return;
                }
                DruidDataSource druidDataSource = springUtils.getBean(DruidDataSource.class);
                if (druidDataSource != null) {
                    Bitter.setDbSources(druidDataSource);
                    return;
                }
                DataSource c = springUtils.getBean(DataSource.class);
                if (druidDataSource != null) {
                    Bitter.setDbSources(dataSource);
                    return;
                }

            }
        }


    }

}
