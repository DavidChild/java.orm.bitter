package io.github.davidchild.bitter.test.core;

import io.github.davidchild.bitter.db.db;
import io.github.davidchild.bitter.op.insert.Insert;
import io.github.davidchild.bitter.test.business.entity.Sex;
import io.github.davidchild.bitter.test.business.entity.TStudent;
import io.github.davidchild.bitter.test.business.entity.TUser;
import io.github.davidchild.bitter.test.initMockData.SnowFlakeUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DmlForCkTest  extends DmlTest {
    @Test
    public void updateNullTest()  {
        this.beforeInit();
        TUser sysUser =  new TUser();
        sysUser.setId("1552178014981849090");
        sysUser.setUsername("MyUser");
        sysUser.setNickname(null);
        long affect = sysUser.update().submit();  // 受影响的行数
        assertEquals(affect,1);
    }

    @Test
    @Override
    public void bachInsertTest() {
        this.beforeInit();
        boolean bl = db.bachInsert().doBachInsert((list) -> {
            for (int i = 0; i < 10; i++) {
                // CLICKHOUSE 批量插入时候 需要保证 对象的字段为null 的值 给赋予一个默认值
                TStudent studentInfo = new TStudent();
                studentInfo.setName("hjb" + i);
                studentInfo.setSexName(Sex.man);
                studentInfo.setId(SnowFlakeUtils.nextId());
                studentInfo.insert().addInBachInsertPool((List<Insert>) list);
            }
        }).submit() > 0;
        assertEquals(true,bl);
    }

}
