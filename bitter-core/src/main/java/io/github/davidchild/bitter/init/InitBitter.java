package io.github.davidchild.bitter.init;

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

@Configuration
@AutoConfigureOrder(-1)
public class InitBitter {

    @PostConstruct
    public void helloUserBitter() {
        BitterLogUtil.getInstance().info("Hello,use bitter.");
        BitterLogUtil.getInstance().info("Git-address:DavidChild/java.orm.bitter.git");
    }

    @Autowired
    private BitterSpringUtils springUtils;

    @Autowired
    BitterConfig config;

    @Bean
    public void AutoInitBitterDataSource() {

        if (config != null) {
            //
            BitterConfig.getInstance().setSqlLog(config.isSqlLog());
            if (config.getDataSourceClass() != null && CoreStringUtils.isNotEmpty(config.getDataSourceClass())) {
                BitterConfig.getInstance().setDataSourceClass(config.getDataSourceClass());
                //init custom dataSource

            } else {
                // auto find  impl the AbstractRoutingDataSource class  bean
                AbstractRoutingDataSource dataSource = springUtils.getBean(AbstractRoutingDataSource.class);
                if (dataSource != null) Bitter.setDbSources(dataSource);

            }
        }


    }

}
