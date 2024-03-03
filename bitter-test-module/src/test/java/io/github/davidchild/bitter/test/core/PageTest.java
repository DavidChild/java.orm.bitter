package io.github.davidchild.bitter.test.core;

import io.github.davidchild.bitter.datatable.DataTable;
import io.github.davidchild.bitter.db.db;
import io.github.davidchild.bitter.functional.IfInnerLambda;
import io.github.davidchild.bitter.op.page.PageQuery;
import io.github.davidchild.bitter.test.business.entity.TUser;
import io.github.davidchild.bitter.test.initMockData.CreateBaseMockSchema;
import io.github.davidchild.bitter.test.runner.ThreadTest;
import lombok.var;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PageTest  extends CreateBaseMockSchema {
    @Test
    @Ignore
    public void testPageQueryInManyThread()  {
        int threadCount = 9;
        for (int i = 0; i < threadCount; i++) {
            ThreadTest t1 = new ThreadTest();
            t1.start();
        }
    }

    @Test
    public void testPageDataRecodeCount() {
        this.beforeInit();
        String sql = "select  user.* from t_user user \n" +
                "left join t_dept dept on dept.dept_id = user.dept_id";
        PageQuery page = new PageQuery(sql);
        page.where("user.username = ?", "hjb");
        page.thenAsc("user.username");
        page.thenDesc("user.create_time");
        Integer totalCount = page.getCount();
        assertEquals(true, totalCount == 1);
    }


    @Test
    @Ignore
    public void testPageQueryThread() throws SQLException, InterruptedException {
        this.beforeInit();
        String sql = "select  user.* from t_user user \n" +
                "left join t_dept dept on dept.dept_id = user.dept_id";
        PageQuery page = new PageQuery(sql);
        page.where("user.username = ?", "123");
        page.thenAsc("user.username");
        page.thenDesc("user.create_time");
        page.skip(1).take(10);
        DataTable mapList = page.getData();
        Integer count = page.getCount();
        var query_count = db.findQuery(TUser.class).findCount();
        var users = db.findQuery(TUser.class).where(t -> t.getUsername() == "david-child").thenDesc(TUser::getUsername).thenAsc(TUser::getUsername).find();
        Thread.sleep(2000);

    }



    @Test
    public void testPageQueryPageAndCount() throws SQLException, InterruptedException {
        this.beforeInit();
        String sql = "select  user.* from t_user user \n" +
                "left join t_dept dept on dept.dept_id = user.dept_id";
        PageQuery page = new PageQuery(sql);
        page.where("user.username = ?", "123");
        page.thenAsc("user.username");
        page.thenDesc("user.create_time");
        page.skip(1).take(10);
        DataTable mapList = page.getData();
        Integer count = page.getCount();
        var query_count = db.findQuery(TUser.class).findCount();
        var users = db.findQuery(TUser.class).where(t -> t.getUsername() == "david-child").thenDesc(TUser::getUsername).thenAsc(TUser::getUsername).find();
        Thread.sleep(2000);

    }

    @Test
    public void testPageQueryData() {
        this.beforeInit();
        String sql = "select  user.* from t_user user \n" +
                      "left join t_dept dept on dept.dept_id = user.dept_id";
        PageQuery page = new PageQuery(sql);
        page.where("user.username = ?", "hjb");
        page.thenAsc("user.username");
        page.thenDesc("user.create_time");
        page.skip(1).take(10);
        DataTable data = page.getData();
        assertEquals(1, data.size());

    }

    @Test
    public void testPageQuery1() {
        this.beforeInit();
        String sql = "select  user.* from t_user user \n" +
                "left join t_dept dept on dept.dept_id = user.dept_id";
        PageQuery page = new PageQuery(sql);
        page.skip(1).take(10);
        DataTable data = page.getData();
        assertEquals(true, data.size() == 10);
    }

    @Test
    public void testPageQuery3() {
        String name = null;
        this.beforeInit();
        String sql = "select  user.* from t_user user \n" +
                "left join t_dept dept on dept.dept_id = user.dept_id";
        PageQuery page = new PageQuery(sql);
        page.whereNotEmpty("user.username = ?", name);
        page.skip(1).take(10);
        DataTable data = page.getData();

        assertEquals(true, data.size() == 10);
    }


    @Test
    public void testPageQuery4() {

        this.beforeInit();

        String name = "hjb";
        String sql = "select  user.* from t_user user \n" +
                "left join t_dept dept on dept.dept_id = user.dept_id";
        PageQuery page = new PageQuery(sql);
        page.whereNotEmpty("user.username = ?", name);
        page.skip(1).take(10);
        DataTable data = page.getData();
        assertEquals(true, data.size() == 1);
    }

    @Test
    public void testPageQuery5() {

        this.beforeInit();
        String name = "hjb";
        String nickname = "";
        String sql = "select  user.* from t_user user \n" +
                "left join t_dept dept on dept.dept_id = user.dept_id";
        PageQuery page = new PageQuery(sql);
        IfInnerLambda lambda = ()-> name != null && nickname != "" ;
        page.whereNotEmpty(lambda,"user.username = ?",name);
        page.skip(1).take(10);
        DataTable data = page.getData();
        assertEquals(true, data.size() >= 10);
    }

    @Test
    public void testPageQuery6() {

        this.beforeInit();

        String name = "hjb";
        String nickname = "nickname";
        String sql = "select  user.* from t_user user \n" +
                "left join t_dept dept on dept.dept_id = user.dept_id";
        PageQuery page = new PageQuery(sql);
        IfInnerLambda lambda = ()-> name != null && nickname != "" ;
        page.whereNotEmpty(lambda,"user.username = ?",name);
        page.skip(1).take(10);
        DataTable data = page.getData();
        assertEquals(true, data.size() == 1);
    }

    @Test
    public void testPageQuery7() {

        this.beforeInit();
        String name = "hjb";
        String nickname = "";
        List<String> ids =  new ArrayList<>();
        ids.add("1552178014981849090");
        ids.add("2552178014981849090");
        String sql = "select  user.* from t_user user \n" +
                     "left join t_dept dept on dept.dept_id = user.dept_id";
        PageQuery page = new PageQuery(sql);
        IfInnerLambda lambda = ()-> name != null && nickname != "" ;
        page.whereNotEmpty(lambda,"user.username = ?",name);

        page.whereIn("id", ids); //In
       //page.whereNotBlank(page.createSubWhereStatement().in("name", ids,null)); //In

        page.skip(1).take(10);
        DataTable data = page.getData();
        assertEquals(true, data.size() == 1);
    }

    @Test
    public void testPageQuery8() {
        this.beforeInit();
        String name = "b";
        List<String> ids =  new ArrayList<>();
        String sql = "select  user.* from t_user user \n" +
                "left join t_dept dept on dept.dept_id = user.dept_id";
        PageQuery page = new PageQuery(sql);
        IfInnerLambda lambda = ()-> name != null ;
        page.wherePreLike("username",  "hjb"); //ok
        page.wherePreLike("username",  "h"); //ok
        page.whereEndLike("username",  "b"); //ok
        page.skip(1).take(10);
        DataTable data = page.getData();
        assertEquals(true, data.size() == 1);
    }


    @Test
    public void testPageQuery10() {

        this.beforeInit();
        String name = "b";
        List<String> ids = new ArrayList<>();
        String sql = "select  user.* from t_user user \n" +
                "left join t_dept dept on dept.dept_id = user.dept_id";
        PageQuery page = new PageQuery(sql);

        IfInnerLambda lambda = () -> name != null;
        page.where("user.username like ?", "%" + name + "%"); //模糊查询
        page.whereIn("name", ids); //IN

        page.skip(1).take(10);
        DataTable data = page.getData();
        assertEquals(true, data.size() == 1);
    }
    @Test
    public void testPageQuery9() {
        this.beforeInit();
        List<String> list = new ArrayList<>();
        list.add("hjb");
        List<String> list2 = new ArrayList<>();
        list2.add("david");
        list2.add("hjb");
        List<String> list3 = new ArrayList<>();
        String sql = "select  user.* from t_user user \n" +
                "left join t_dept dept on dept.dept_id = user.dept_id";
        PageQuery pq = new PageQuery(sql);
        pq.whereAllLike("username",  "hjb"); //ok
        pq.wherePreLike("username",  "h"); //ok
        pq.whereEndLike("username",  "b"); //ok
        pq.whereIn("username",  list); //ok
        pq.whereIn("username",  list2); //ok
        pq.whereIn("username", list3); //ok
        pq.skip(1).take(10);
        DataTable dt =  pq.getData();
        assertEquals(true, dt.size() == 1);
    }
    @Test
    public void testPageQueryNotIn() {
        this.beforeInit();
        List<String> list = new ArrayList<>();
        list.add("hjb");
        List<String> list2 = new ArrayList<>();
        list2.add("david");
        list2.add("hjb");
        List<String> list3 = new ArrayList<>();
        String sql = "select  user.* from t_user user \n" +
                "left join t_dept dept on dept.dept_id = user.dept_id";
        PageQuery pq = new PageQuery(sql);
        pq.whereNotIn("username",  list2); //ok
        pq.skip(1).take(10);
        DataTable dt =  pq.getData();
        assertEquals(true, dt.size() > 1);
    }

    @Test
    public void testPageQueryNotIn2() {
        this.beforeInit();
        List<String> list = new ArrayList<>();
        list.add("hjb");
        List<String> list3 = new ArrayList<>();
        String sql = "select  user.* from t_user user \n" +
                     "left join t_dept dept on dept.dept_id = user.dept_id";
        PageQuery pq = new PageQuery(sql);
        pq.whereNotIn("username",  list); //ok
        pq.skip(1).take(10);
        DataTable dt =  pq.getData();
        assertEquals(true, dt.size() > 1);
    }







}
