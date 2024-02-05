package io.github.davidchild.bitter.test.test;

import io.github.davidchild.bitter.db.db;
import io.github.davidchild.bitter.op.page.MyPage;
import io.github.davidchild.bitter.op.page.PageQuery;
import io.github.davidchild.bitter.op.read.ExecuteQuery;
import io.github.davidchild.bitter.test.Init.CreateBaseMockSchema;
import io.github.davidchild.bitter.test.business.entity.TUser;
import io.github.davidchild.bitter.test.runnerTest.ThreadTest;
import io.github.davidchild.bitter.tools.DateUtils;
import org.junit.Test;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class TestDQL extends CreateBaseMockSchema {
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

        int l = db.findQuery(TUser.class).FindCount();
        int i = 0;

        while (true) {
            db.findQuery(TUser.class).where(t -> t.getUsername() == "hjb").thenDesc(TUser::getUsername)
                    .thenAsc(TUser::getUsername).find();
            MyPage p = page.getPage();
            String str = "";
            System.out.println(String.format("recode  is  {} executed ", i));
            Thread.sleep(2000);

        }

    }

    @Test
    public void testQueryByGen() throws Exception {
        Date s = DateUtils.parseDate("2022-8-10");
        List<TUser> list = db.findQuery(TUser.class).thenAsc(TUser::getId).find();

        List<TUser> list_2 =
                db.findQuery(TUser.class).select(TUser::getId, TUser::getUsername, TUser::getAvatar)
                        .setSize(10).thenAsc(TUser::getId).find();
        // where sample
        List<TUser> list_4 =
                db.findQuery(TUser.class).where(t -> t.getCreateTime().after(s)).thenAsc(TUser::getId).find();
        // more where sample
        List<TUser> list_5 = db.findQuery(TUser.class).where(t -> t.getCreateTime().after(s))
                .where(t -> t.getUsername().contains("123")).thenAsc(TUser::getId).find();

        // test db entity model
        TUser user1 = new TUser();
        String test = "";
    }

    @Test
    public void testExecuteQuery() throws Exception {

        List<Map<String, Object>> mapOrigin = db.findQuery("select * from sys_user").find();
        // test executeQuery
        List<TUser> lt = db.findQuery("select * from sys_user").find().toBList(TUser.class);
        // test executeQuery and mapUnderscoreToCamelCase
        List<TUser> list = db.findQuery("select * from sys_user").find().toBList(TUser.class, true);
        ExecuteQuery query = db.findQuery("select * from sys_user");
        query.beginWhere(" 1=1 ");
        query.andWhere("id>'123456789'");
        query.andWhere("id>'1552205343773896705'");
        List<TUser> ls = query.find().toBList(TUser.class);

        // Get the data in the first row and column, go to the specified type, and give the default value
        Integer count = db.findQuery("select count(0) from sys_user").find().tryCase(0);
        // Get the field value of the column specified in the first row and the first column
        String id = db.findQuery("select * from sys_user limit 0,1").find().getFirstRowSomeData("id").toString();
        String t = "";
    }

    @Test
    public void testQueryById() throws Exception {
        TUser sysUser = db.findQuery(TUser.class, "1552178014981849090").find();
        if (sysUser.hasKeyValue()) {
            String t = "";
        } else {

        }

    }
}
