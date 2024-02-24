package io.github.davidchild.bitter.test.core;

import io.github.davidchild.bitter.db.db;
import io.github.davidchild.bitter.op.insert.Insert;
import io.github.davidchild.bitter.test.business.entity.Sex;
import io.github.davidchild.bitter.test.business.entity.TStudent;
import io.github.davidchild.bitter.test.business.entity.TUser;
import io.github.davidchild.bitter.test.initMockData.CreateBaseMockSchema;
import io.github.davidchild.bitter.test.initMockData.SnowFlakeUtils;
import io.github.davidchild.bitter.tools.DateUtils;
import lombok.var;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DmlTest extends CreateBaseMockSchema {


    @Autowired
    private ApplicationContext applicationContext;


    @Test
    public void updateTest1() throws InstantiationException, IllegalAccessException, SQLException {
        this.beforeInit();
        // query a data by the id
        TUser sysUser = db.findQuery(TUser.class, "1552178014981849090").find();
        sysUser.setUsername("MyUser");
        boolean  bl = sysUser.update().submit()  > 0;  // 受影响的行数
        assertEquals(true,bl);
    }
    @Test
    public void updateTest2() throws InstantiationException, IllegalAccessException, SQLException {
       this.beforeInit();
        long affect = db.update(TUser.class).setColum(TUser::getUsername,"mynameupdate").where(f->f.getId()=="1552178014981849090").submit();
        assertEquals(true,affect == 1);
    }

    @Test
    public void updateTest3() throws InstantiationException, IllegalAccessException, SQLException {
       this.beforeInit();
        long affect = db.update(TUser.class).setColum(TUser::getUsername,"mynameupdate")
                .setColum(TUser::getEmail,"email@yeah.net").where(f->f.getId()=="1552178014981849090").submit();
        assertEquals(true,affect == 1);
    }

    @Test
    public void updateTest4() throws InstantiationException, IllegalAccessException, SQLException {
       this.beforeInit();
        long affect = db.update(TUser.class).setColum(TUser::getUsername,"mynameupdate")
                .setColum(TUser::getEmail,"email@yeah.net").where(f->f.getId().contains("1552178014981849090")).submit();
        assertEquals(true,affect == 1);
    }

    @Test
    public void updateTest5() throws InstantiationException, IllegalAccessException, SQLException {
       this.beforeInit();
        long affect = db.update(TUser.class).setColum(TUser::getUsername,"mynameupdate")
                .setColum(TUser::getEmail,"email@yeah.net").where(f->f.getId().endsWith("981849090")).submit();
        assertEquals(true,affect == 1);
    }

    @Test
    public void updateTest6() throws InstantiationException, IllegalAccessException, SQLException {
       this.beforeInit();
        long affect = db.update(TUser.class).setColum(TUser::getUsername,"mynameupdate")
                .setColum(TUser::getEmail,"email@yeah.net").where(f->f.getId().startsWith("15521780149818490")).submit();
        assertEquals(true,affect == 1);
    }

    @Test
    public void updateTest7()  {
        this.beforeInit();
        TUser sysUser =  new TUser();
        sysUser.setId("1552178014981849090");
        sysUser.setUsername("MyUser");
        long affect = sysUser.update().submit();  // 受影响的行数
        assertEquals(affect,1);
    }
    @Test
    public void deleteTest()  {
        this.beforeInit();
        List<TUser> list = db.findQuery("select * from t_user").find().toModelList(TUser.class, true);
        TUser user = list.get(0);
        long count = user.delete().submit() ;
        assertEquals(count,1);

    }

    @Test
    public void deleteTest1()  {
        this.beforeInit();
        List<TUser> list = db.findQuery("select * from t_user").find().toBList(TUser.class, true);
        String id = list.get(0).getId();
        long affect = db.delete(TUser.class).where(t -> t.getId() == id).submit();
        assertEquals(affect,1);
    }

    @Test
    public void deleteTest2()  {
        this.beforeInit();
        List<TUser> list = db.findQuery("select * from t_user").find().toBList(TUser.class, true);
        String id = list.get(0).getId();
        long affect = db.delete(TUser.class).where(t -> t.getId() == id).submit();
        assertEquals(affect,1);
    }
    @Test
    public void deleteTest3()  {
        this.beforeInit();
        long affect = db.delete(TUser.class).where(f->f.getId() == "1552178014981849090").submit();
        assertEquals(1,affect);
    }
    @Test
    public void deleteTest4()  {
        this.beforeInit();
        long count = db.delete(TUser.class).where(f->f.getId() == "1552178014981849090")
                                           .where(f->f.getId() == "1552178014981849090").submit();
        assertEquals(count,1);
    }

    @Test
    public void insertTest() {
       this.beforeInit();
        TStudent studentInfo = new TStudent();
        studentInfo.setName("hjb5");
        studentInfo.setSexName(Sex.woman);
        studentInfo.setId(SnowFlakeUtils.nextId());
        long  affect = studentInfo.insert().submit();
        assertEquals(affect,1);
    }

    @Test
    public void insertTest1() {
        Date s = DateUtils.parseDate("2022-8-10");
        TUser user = new TUser();
        user.setId(String.valueOf(SnowFlakeUtils.nextId()));
        user.setUsername("hjb12344");
        user.setCreateTime(new Date());
        long affect = user.insert().submit();
        assertEquals(affect,1);
    }

    @Test
    public void insertEmunMapTest() {
        TStudent student = new TStudent();
        student.setId(SnowFlakeUtils.nextId());
        student.setSexName(Sex.man); // enum
        student.setName("david-child");
        long affect = student.insert().submit();
        assertEquals(affect,1);

    }

    @Test
    public void bachInsertTest() throws Exception {
       this.beforeInit();
       boolean bl = db.bachInsert().doBachInsert((list) -> {
            for (int i = 0; i < 10; i++) {
                TStudent studentInfo = new TStudent();
                studentInfo.setName("hjb" + i);
                studentInfo.insert().addInBachInsertPool((List<Insert>) list);
            }
        }).submit() > 0;
        assertEquals(true,bl);
    }

    @Test
    public void testQueryCount() throws Exception {
        this.beforeInit();
        var count = db.findQuery(TUser.class).findCount();
        assertEquals(true, count > 0);
    }

    @Test
    public void testQueryCount2() throws Exception {
        this.beforeInit();
        var count = db.findQuery(TUser.class).thenDESC(TUser::getUsername).findCount();
        assertEquals(true, count > 0);
    }

    @Test
    public void moreDbTest() throws InstantiationException, IllegalAccessException, SQLException {
       this.beforeInit();
        TUser sysUser = db.findQuery(TUser.class, "1552178014981849090").find();
    }

}
