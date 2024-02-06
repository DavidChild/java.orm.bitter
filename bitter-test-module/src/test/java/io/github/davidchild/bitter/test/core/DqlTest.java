package io.github.davidchild.bitter.test.core;

import io.github.davidchild.bitter.db.db;
import io.github.davidchild.bitter.op.page.PageQuery;
import io.github.davidchild.bitter.test.initMockData.CreateBaseMockSchema;
import io.github.davidchild.bitter.test.business.entity.TUser;
import io.github.davidchild.bitter.test.runner.ThreadTest;
import io.github.davidchild.bitter.tools.DateUtils;
import lombok.var;
import org.apache.commons.lang3.StringUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class DqlTest extends CreateBaseMockSchema {
    @Test
    @Ignore
    public void testPageQueryInManyThread() throws Exception {
        int threadCount = 9;
        for (int i = 0; i < threadCount; i++) {
            ThreadTest t1 = new ThreadTest();
            t1.start();
        }
    }

    @Test
    public void testPageQuery() throws Exception {
        String sql = "select  user.* from t_user user \n" +
                     "left join t_dept dept on dept.dept_id = user.id";

        PageQuery page = new PageQuery(sql);
        page.where("IFNULL(user.username,'') = ?", "123");
        page.thenASC("user.username");
        page.thenDESC("user.create_time");

        page.skip(1).take(10);
        List<Map<String, Object>> mapList = page.getData();
        Integer count = page.getCount();

        var query_count = db.findQuery(TUser.class).FindCount();
        var users = db.findQuery(TUser.class).where(t -> t.getUsername() == "david-child").thenDesc(TUser::getUsername).thenAsc(TUser::getUsername).find();
        Thread.sleep(2000);

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

        List<Map<String, Object>> mapOrigin = db.findQuery("select * from t_user").find();

        List<TUser> lt = db.findQuery("select * from t_user").find().toBList(TUser.class);   // test executeQuery
        assertEquals(true, lt.size()>0);

        var user_models = db.findQuery("select * from t_user").find().toBList(TUser.class, true); // test executeQuery and mapUnderscoreToCamelCase
        assertEquals(true, user_models.size()>0);

        var query = db.findQuery("select * from t_user");
        query.beginWhere(" 1=1 ");
        query.andWhere("id>'1552205343773896705'");
        var users  = query.find().toBList(TUser.class);
        assertEquals(true, users.size()>0);

        Integer value = db.findQuery("select count(0) from t_user").find().tryCase(0);  // Get the data in the first row and column, go to the specified type, and give the default value
        assertEquals(true, value>0);

        String id = db.findQuery("select * from t_user limit 0,1").find().getFirstRowSomeData("id").toString(); // Get the field value of the column specified in the first row and the first column
        assertEquals(true, StringUtils.isNoneBlank(id));
    }

    @Test
    public void testQueryById() throws Exception {
        var sysUser = db.findQuery(TUser.class, "1552178014981849090").find();
        assertEquals(true, sysUser.hasKeyValue());

        if (sysUser.hasKeyValue()) {
            String t = "";
        } else {

        }

    }
}
