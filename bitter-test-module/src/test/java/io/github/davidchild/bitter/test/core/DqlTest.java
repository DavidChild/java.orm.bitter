package io.github.davidchild.bitter.test.core;

import io.github.davidchild.bitter.datatable.DataTable;
import io.github.davidchild.bitter.db.db;
import io.github.davidchild.bitter.test.business.entity.TUser;
import io.github.davidchild.bitter.test.initMockData.CreateBaseMockSchema;
import io.github.davidchild.bitter.tools.DateUtils;
import lombok.var;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DqlTest extends CreateBaseMockSchema {



    @Test
    public void testQueryGen1()  {
        this.beforeInit();
        Date s = DateUtils.parseDate("2022-8-10");
        List<TUser> list = db.findQuery(TUser.class).thenAsc(TUser::getId).thenDesc(TUser::getPhone).find();
        assertEquals(true, list.size()>0);

    }
    @Test
    public void testQueryGen2() {
        this.beforeInit();
        List<TUser> list =
                db.findQuery(TUser.class).select(TUser::getId, TUser::getUsername, TUser::getAvatar)
                        .setSize(10).thenAsc(TUser::getId).find();

        assertEquals(true, list.get(0).id != null && list.get(0).getAvatar() != null && list.get(0).getUsername() != null && list.get(0).getPhone() == null);
    }


    @Test
    public void testQueryGen3()  {
        Date s = DateUtils.parseDate("2022-8-10");
        List<TUser> list =
                db.findQuery(TUser.class).where(t -> t.getCreateTime().after(s)).thenAsc(TUser::getId).find();
        assertEquals(true, list.size() > 0);
    }
    @Test
    public void testQueryGen4()  {
        this.beforeInit();
        List<TUser> list =
                db.findQuery(TUser.class).select(TUser::getId, TUser::getUsername, TUser::getAvatar)
                        .setSize(10).thenAsc(TUser::getId).find();
        assertEquals(true, list.size()>0);
    }

    @Test
    public void testQueryGen5()  {
        this.beforeInit();
        Date s = DateUtils.parseDate("2022-8-10");
        List<TUser> list = db.findQuery(TUser.class).where(t -> t.getCreateTime().after(s))
                .where(t -> t.getUsername().contains("hj")).thenAsc(TUser::getId).find();
        assertEquals(true, list.size() > 0);
    }


    @Test
    public void testExecuteQuery1()  {
        this.beforeInit();
        DataTable mapOrigin = db.findQuery("select * from t_user").find();
        List<TUser> lt = db.findQuery("select * from t_user").find().toModelList(TUser.class);   // test executeQuery
        assertEquals(true, lt.size()>0);
    }

    @Test
    public void testExecuteQuery2()  {
        this.beforeInit();
        var user_models = db.findQuery("select * from t_user").find().toModelList(TUser.class, true); // test executeQuery and mapUnderscoreToCamelCase
        assertEquals(true, user_models.size()>0);
    }

    @Test
    public void testExecuteQuery3()  {
        this.beforeInit();
        var query = db.findQuery("select * from t_user");
        query.where(" 1=1 ");
        query.where("id > '1552205343773896705'");
        var users  = query.find().toBList(TUser.class);
        assertEquals(true, users.size()>0);
    }

    @Test
    public void testExecuteQuery4()  {
        this.beforeInit();
        Integer value = db.findQuery("select count(0) from t_user").find().tryGetFirstRowFirstColumnValue(0);  // Get the data in the first row and column, go to the specified type, and give the default value
        assertEquals(true, value>0);
    }

    @Test
    public void testExecuteQuery5()  {
        this.beforeInit();
        String id = db.findQuery("select * from t_user").find().tryGetFirstRowFirstColumnValue("id").toString(); // Get the field value of the column specified in the first row and the first column
        assertEquals(true, StringUtils.isNoneBlank(id));
    }
    @Test
    public void testQueryById() throws Exception {
        this.beforeInit();
        var sysUser = db.findQuery(TUser.class, "1552178014981849090").find();
        assertEquals(true, sysUser.hasKeyValue());

        if (sysUser.hasKeyValue()) {
            String t = "";
        } else {

        }

    }
}
