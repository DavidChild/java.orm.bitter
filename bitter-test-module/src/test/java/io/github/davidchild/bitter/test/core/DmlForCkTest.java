package io.github.davidchild.bitter.test.core;

import io.github.davidchild.bitter.test.business.entity.TUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
}
