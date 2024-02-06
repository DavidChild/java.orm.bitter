package io.github.davidchild.bitter.test.runner;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import io.github.davidchild.bitter.connection.DataAccess;
import io.github.davidchild.bitter.connection.MysqlDbStatementCached;
import io.github.davidchild.bitter.op.find.FindQuery;
import io.github.davidchild.bitter.test.business.entity.TUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Properties;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DataAccessTest {
    DataSource dataSource;
    @Autowired
    private ApplicationContext applicationContext;

    public void setDataSource(ApplicationContext applicationContext) throws Exception {
        Properties prop = new Properties();
        InputStream ins = DataAccess.class.getClassLoader().getResourceAsStream("druid.yml");
        prop.load(ins);
        // Get Connection Pool Object
        dataSource = DruidDataSourceFactory.createDataSource(prop);
        // BitterDataSourceFactory.setDbSource(dataSource);

    }

    @Test
    public void testExcuteQuery() throws Exception {

        String sql = "select * from sys_user";

        StopWatch watch = new StopWatch();
        MysqlDbStatementCached dbStatementCached = new MysqlDbStatementCached();
        for (Integer i = 0; i < 11; i++) {
            watch.start("sql excute——" + i + " total time:");
            System.out.println("Memory used:" + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
            watch.stop();
            System.out.println("SQL execution time:" + watch.getLastTaskTimeMillis());
            watch.start("Mapping execution time——" + i + " total time:");
            watch.stop();
            System.out.println("Mapping execution time:" + watch.getLastTaskTimeMillis());
            Thread.sleep(1000);
        }
    }

    @Test
    public void testFindQuer() throws Exception {
        String sql = "select * from sys_user";

        StopWatch watch = new StopWatch();
        MysqlDbStatementCached dbStatementCached = new MysqlDbStatementCached();
        FindQuery<TUser> find = new FindQuery<>(TUser.class);
        Integer count = find.select(TUser::getUsername, TUser::getId).thenDesc(TUser::getUsername)
                .setSize(1).where(w -> w.getUsername() == "hjb").FindCount();
    }

    @Test
    public void testExcuteQueryAsync() throws Exception {
        for (Integer i = 0; i < 11; i++) {
            MyThread myThread1 = new MyThread();
            myThread1.run();
            myThread1.start();

        }
    }
}
