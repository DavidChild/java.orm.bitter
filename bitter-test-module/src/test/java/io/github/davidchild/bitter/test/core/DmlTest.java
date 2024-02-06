package io.github.davidchild.bitter.test.core;

import io.github.davidchild.bitter.db.db;
import io.github.davidchild.bitter.op.insert.Insert;
import io.github.davidchild.bitter.test.business.entity.Sex;
import io.github.davidchild.bitter.test.business.entity.TStudent;
import io.github.davidchild.bitter.test.business.entity.TUser;
import io.github.davidchild.bitter.test.initMockData.CreateBaseMockSchema;
import io.github.davidchild.bitter.test.initMockData.SnowFlakeUtils;
import io.github.davidchild.bitter.tools.DateUtils;
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
    public void updateTest() throws InstantiationException, IllegalAccessException, SQLException {
        this.beforeInit();
        // query a data by the id
        TUser sysUser = db.findQuery(TUser.class, "1552178014981849090").find();
        assertEquals("1552178014981849090", sysUser.getId());

        // update the db-entity
        sysUser.setUsername("MyUser");
        boolean  bl = sysUser.update().submit()  > 0;  // 受影响的行数
        assertEquals(true,bl);

    }

    @Test
    public void deleteTest() throws Exception {
        this.beforeInit();
        List<TUser> list = db.findQuery("select * from t_user").find().toBList(TUser.class, true);
        TUser user = list.get(list.size() - 1);
        long count_1 = user.delete().submit() ;
        assertEquals(count_1,1);
        String id = list.get(list.size() - 2).id;
        long count_2 = db.delete(TUser.class).where(t -> t.getId() == id).submit();
        assertEquals(count_2,1);
    }

    @Test
    public void insertTest() throws Exception {
        this.beforeInit();
        TStudent studentInfo = new TStudent();
        studentInfo.setName("hjb5");
        studentInfo.setSexName(Sex.woman);
        studentInfo.setId(SnowFlakeUtils.nextId());
        long id = studentInfo.insert().submit();

        Date s = DateUtils.parseDate("2022-8-10");
        TUser user = new TUser();
        user.setId(String.valueOf(SnowFlakeUtils.nextId()));
        user.setUsername("hjb12344");
        user.setCreateTime(new Date());
        user.insert().submit();
        String test = "";
        List<TUser> lt = db.findQuery("select * from t_user").find().toBList(TUser.class);

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
    public void moreDbTest() throws InstantiationException, IllegalAccessException, SQLException {
        this.beforeInit();
        TUser sysUser = db.findQuery(TUser.class, "1552178014981849090").find();
    }

}
