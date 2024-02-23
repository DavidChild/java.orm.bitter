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

import java.util.ArrayList;
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
    @Test
    public void testExecuteQuery10()  {
        this.beforeInit();
        List<TUser> lt = db.findQuery("select * from t_user")
                .setSize(10)
                .find().toModelList(TUser.class);   // test executeQuery
        assertEquals(true, lt.size()>0);
    }

    @Test
    public void testExecuteQuery11()  {
        this.beforeInit();
        List<TUser> lt = db.findQuery("select * from t_user")
                .orderBy("username desc")
                .setSize(10)
                .find().toModelList(TUser.class);   // test executeQuery
        assertEquals(true, lt.size()>0);
    }

    @Test
    public void testExecuteQuery12()  {
        this.beforeInit();
        List<TUser> lt = db.findQuery("select * from t_user")
                .where("id = ? and username = ?","1552178014981849090","hjb")
                .orderBy("username desc")
                .setSize(10).find().toModelList(TUser.class);   // test executeQuery
        assertEquals(true, lt.size()>0);
    }
    @Test
    public void testExecuteQuery13()  {
        this.beforeInit();
        List<TUser> lt = db.findQuery("select * from t_user")
                .where("id = ? and username = ?","1552178014981849090","hjb")
                .where("id = ?","1552178014981849090")
                .orderBy("username desc")
                .setSize(10).find().toModelList(TUser.class);   // test executeQuery
        assertEquals(true, lt.size()>0);
    }

    @Test
    public void testExecuteQuery14()  {
        this.beforeInit();
        List<TUser> lt = db.findQuery("select * from t_user")
                .whereLeq("id","1552178014981849090")
                .orderBy("username desc").
                setSize(10).find().toModelList(TUser.class);   // test executeQuery
        assertEquals(true, lt.size()>0);
    }

    @Test
    public void testExecuteQuery15()  {
        this.beforeInit();
        List<TUser> lt = db.findQuery("select * from t_user")
                .whereEq("id","1552178014981849090")
                .whereLt("id","1552178014981849089")
                .orderBy("username desc")
                .setSize(10).find().toModelList(TUser.class);   // test executeQuery
        assertEquals(true, lt.size()==0);
    }

    @Test
    public void testExecuteQuery16()  {
        this.beforeInit();
        List<TUser> lt = db.findQuery("select * from t_user")
                .whereEq("id","1552178014981849090")
                .whereGeq("id","1552178014981849089")
                .orderBy("username desc")
                .setSize(10).find().toModelList(TUser.class);   // test executeQuery
        assertEquals(true, lt.size()==1);
    }

    @Test
    public void testExecuteQuery17()  {
        this.beforeInit();
        List<TUser> lt = db.findQuery("select * from t_user")
                .whereEq("id","1552178014981849090")
                .whereGt("id","1552178014981849089")
                .orderBy("username desc")
                .setSize(10).find().toModelList(TUser.class);   // test executeQuery
        assertEquals(true, lt.size()==1);
    }


    @Test
    public void testExecuteQuery18()  {
        this.beforeInit();
        List<String > ids = new ArrayList<>();
        ids.add("1552178014981849090");
        ids.add("1552178014981849089");
        List<TUser> lt = db.findQuery("select * from t_user")
                .whereEq("id","1552178014981849090")
                .whereIn("id",ids)
                .orderBy("username desc")
                .setSize(10).find().toModelList(TUser.class);   // test executeQuery
        assertEquals(true, lt.size()==1);
    }

    @Test
    public void testExecuteQuery19()  {
        this.beforeInit();
        List<String > ids = new ArrayList<>();
        ids.add("1552178014981849090");
        ids.add("1552178014981849089");
        List<TUser> lt = db.findQuery("select * from t_user")
                .whereEq("id","1552178014981849090")
                .whereNotIn("id",ids)
                .orderBy("username desc")
                .setSize(10).find().toModelList(TUser.class);   // test executeQuery
        assertEquals(false, lt.size()==1);
    }

    @Test
    public void testExecuteQuery20()  {
        this.beforeInit();
        List<String > ids = new ArrayList<>();
        ids.add("1552178014981849090");
        ids.add("1552178014981849089");
        List<TUser> lt = db.findQuery("select * from t_user")
                .whereAllLike("id","98184")
                .orderBy("username desc")
                .setSize(10).find().toModelList(TUser.class);   // test executeQuery
        assertEquals(true, lt.size()==1);
    }

    @Test
    public void testExecuteQuery21()  {
        this.beforeInit();
        List<String > ids = new ArrayList<>();
        ids.add("1552178014981849090");
        ids.add("1552178014981849089");
        List<TUser> lt = db.findQuery("select * from t_user")
                .wherePreLike("id","1552178014")
                .orderBy("username desc")
                .setSize(10).find().toModelList(TUser.class);   // test executeQuery
        assertEquals(true, lt.size()==1);
    }

    @Test
    public void testExecuteQuery22()  {
        this.beforeInit();
        List<String > ids = new ArrayList<>();
        ids.add("1552178014981849090");
        ids.add("1552178014981849089");
        List<TUser> lt = db.findQuery("select * from t_user")
                .whereEndLike("id","81849090")
                .orderBy("username desc")
                .setSize(10).find().toModelList(TUser.class);   // test executeQuery
        assertEquals(true, lt.size()==1);
    }

}
