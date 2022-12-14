package io.github.davidchild.bitter.connection;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import io.github.davidchild.bitter.entity.TUserInfo;
import io.github.davidchild.bitter.op.find.FindQuery;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StopWatch;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Properties;

public class DataAccessTest {
    DataSource dataSource;
    @Autowired
    private ApplicationContext applicationContext;

    public void SetDataSource(ApplicationContext applicationContext) throws Exception {
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
            // List<Map<String, Object>> lt2 = DataAccess.excuteQuery(dbStatementCached, sql, null);
            System.out.println(
                    "Memory used:" + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
            watch.stop();

            System.out.println("SQL execution time:" + watch.getLastTaskTimeMillis());
            watch.start("Mapping execution time——" + i + " total time:");
            // List<SysUnemploymentUser> mydata = dbStatementCached.ListMap2JavaBean(lt2,
            // SysUnemploymentUser.class);
            watch.stop();
            System.out.println("Mapping execution time:" + watch.getLastTaskTimeMillis());
            Thread.sleep(1000);
        }
        String ls = "";


    }

    @Test
    public void testFindQuer() throws Exception {
        String sql = "select * from sys_user";

        StopWatch watch = new StopWatch();
        MysqlDbStatementCached dbStatementCached = new MysqlDbStatementCached();
        FindQuery<TUserInfo> find = new FindQuery<>(TUserInfo.class);
        Integer count = find.select(TUserInfo::getUsername, TUserInfo::getId).thenDesc(TUserInfo::getUsername)
                .setSize(1).where(w -> w.getUsername() == "hjb").FindCount();
    }

    @Test
    public void testExcuteQueryAsync() throws Exception {
        for (Integer i = 0; i < 11; i++) {
            MyThread myThread1 = new MyThread();
            myThread1.run();
            // myThread1.start();

        }
    }
}
