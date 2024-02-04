package io.github.davidchild.bitter.op;

import io.github.davidchild.bitter.db.db;
import io.github.davidchild.bitter.entity.TStudentInfo;
import io.github.davidchild.bitter.entity.TUserInfo;
import io.github.davidchild.bitter.op.insert.Insert;
import io.github.davidchild.bitter.op.page.MyPage;
import io.github.davidchild.bitter.op.page.PageQuery;
import io.github.davidchild.bitter.op.read.ExecuteQuery;
import io.github.davidchild.bitter.op.scope.DbScope;
import io.github.davidchild.bitter.op.service.IDataServe;
import io.github.davidchild.bitter.tools.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
@ComponentScan(value = {"io.github.davidchild.bitter.*"})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})

public class TestBitter {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private IDataServe dataServe;

    @Test
    public void testPageQueryInManyThread() throws Exception {
        int threadCount = 9;
        for (int i = 0; i < threadCount; i++) {
            ThreadTest t1 = new ThreadTest();
            t1.start();
        }
        while (true) {

        }
    }

    @Test
    public void testPageQuery() throws Exception {
        String sql = "select \n" + "us.username,\n" + "us.id as user_id,\n" + "dept.dept_name as dept_name\n"
                + " from \n" + "t_user us \n" + "left join t_dept dept on dept.dept_id= us.dept_id";

        PageQuery page = new PageQuery(sql);
        page.where("IFNULL(us.username,'') = ?", "123");

        page.thenASC("us.username");
        page.thenDESC("us.create_time");

        page.skip(1).take(10);
        List<Map<String, Object>> mapList = page.getData();
        Integer count = page.getCount();
        Integer count2 = page.getCount();

        int l = db.findQuery(TUserInfo.class).FindCount();
        int i = 0;

        while (true) {
            db.findQuery(TUserInfo.class).where(t -> t.getUsername() == "hjb").thenDesc(TUserInfo::getUsername)
                    .thenAsc(TUserInfo::getUsername).find();
            MyPage p = page.getPage();
            String str = "";
            System.out.println(String.format("recode  is  {} executed ", i));
            Thread.sleep(2000);

        }

    }

    @Test
    public void testQueryByGen() throws Exception {
        Date s = DateUtils.parseDate("2022-8-10");
        List<TUserInfo> list = db.findQuery(TUserInfo.class).thenAsc(TUserInfo::getId).find();

        List<TUserInfo> list_2 =
                db.findQuery(TUserInfo.class).select(TUserInfo::getId, TUserInfo::getUsername, TUserInfo::getAvatar)
                        .setSize(10).thenAsc(TUserInfo::getId).find();
        // where sample
        List<TUserInfo> list_4 =
                db.findQuery(TUserInfo.class).where(t -> t.getCreateTime().after(s)).thenAsc(TUserInfo::getId).find();
        // more where sample
        List<TUserInfo> list_5 = db.findQuery(TUserInfo.class).where(t -> t.getCreateTime().after(s))
                .where(t -> t.getUsername().contains("123")).thenAsc(TUserInfo::getId).find();

        // test db entity model
        TUserInfo user1 = new TUserInfo();
        String test = "";
    }

    @Test
    public void testExecuteQuery() throws Exception {

        List<Map<String, Object>> mapOrigin = db.findQuery("select * from sys_user").find();
        // test executeQuery
        List<TUserInfo> lt = db.findQuery("select * from sys_user").find().toBList(TUserInfo.class);
        // test executeQuery and mapUnderscoreToCamelCase
        List<TUserInfo> list = db.findQuery("select * from sys_user").find().toBList(TUserInfo.class, true);
        ExecuteQuery query = db.findQuery("select * from sys_user");
        query.beginWhere(" 1=1 ");
        query.andWhere("id>'123456789'");
        query.andWhere("id>'1552205343773896705'");
        List<TUserInfo> ls = query.find().toBList(TUserInfo.class);

        // Get the data in the first row and column, go to the specified type, and give the default value
        Integer count = db.findQuery("select count(0) from sys_user").find().tryCase(0);
        // Get the field value of the column specified in the first row and the first column
        String id = db.findQuery("select * from sys_user limit 0,1").find().getFirstRowSomeData("id").toString();
        String t = "";
    }

    @Test
    public void testQueryById() throws Exception {
        TUserInfo sysUser = db.findQuery(TUserInfo.class, "1552178014981849090").find();
        if (sysUser.hasKeyValue()) {
            String t = "";
        } else {

        }

    }

    @Test
    public void testUpdate() throws InstantiationException, IllegalAccessException, SQLException {
        TUserInfo sysUser = db.findQuery(TUserInfo.class, "1552178014981849090").find();
        sysUser.setUsername("MyUser");
        sysUser.update().submit();
    }

    @Test
    public void testDelete() throws Exception {
        // test executeQuery and mapUnderscoreToCamelCase
        List<TUserInfo> list = db.findQuery("select * from sys_user").find().toBList(TUserInfo.class, true);
        // Delete entities directly
        TUserInfo user = list.get(list.size() - 1);
        long count_1 = user.delete().submit();
        String id = list.get(list.size() - 2).id;
        // Build Delete
        long count_2 = db.delete(TUserInfo.class).where(t -> t.getId() == id).submit();

        String t = "";
    }

    @Test
    public void testInsert() throws Exception {
        Date s = DateUtils.parseDate("2022-8-10");
        List<TUserInfo> list = db.findQuery(TUserInfo.class).thenAsc(TUserInfo::getId).find();
        TUserInfo user = list.get(0);
        user.setId("12345680");
        user.setUsername("hjb12344");
        user.setCreateTime(new Date());
        user.insert().submit();
        String test = "";

        TStudentInfo studentInfo = new TStudentInfo();
        studentInfo.setName("hjb");
        long id = studentInfo.insert().submit();

        List<TUserInfo> lt = db.findQuery("select * from sys_user").find().toBList(TUserInfo.class);
        String t = "";
    }

    @Test
    public void bachInsert() throws Exception {

        db.bachInsert().doBachInsert((list) -> {
            for (int i = 0; i < 10; i++) {
                TStudentInfo studentInfo = new TStudentInfo();
                studentInfo.setName("hjb" + i);
                studentInfo.insert().addInBachInsertPool((List<Insert>) list);
            }
        }).submit();
    }

    @Test
    public void testDoScope() {
        DbScope dbScope = db.doScope();
        boolean isSuccess = dbScope.create((list) -> {

            TStudentInfo sys =
                    db.findQuery(TStudentInfo.class).where(t -> t.getName().equals("hjb5")).find().fistOrDefault();
            sys.setName("jb");
            sys.update().addInScope(list);

            TStudentInfo studentInfo = new TStudentInfo();
            studentInfo.setName("hello hjb");
            studentInfo.insert().addInScope(list);
            TStudentInfo sys_2 =
                    db.findQuery(TStudentInfo.class).where(t -> t.getName().equals("hjb4")).find().fistOrDefault();
            sys_2.delete().addInScope(list);

        }).submit() > -1l;
        String kkk = "";
    }

    @Test
    public void testMoreDb() throws InstantiationException, IllegalAccessException, SQLException {
        TUserInfo sysUser = db.findQuery(TUserInfo.class, "1552178014981849090").find();
    }

    @Test

    public void testInspect() throws InstantiationException, IllegalAccessException, SQLException {
        TUserInfo sysUser = db.findQuery(TUserInfo.class, "1552178014981849090").find();
        dataServe.testInspcet();

    }

}
