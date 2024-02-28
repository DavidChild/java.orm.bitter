package io.github.davidchild.bitter.test.parese;

import io.github.davidchild.bitter.db.db;
import io.github.davidchild.bitter.excutequery.UpdateColumnHandler;
import io.github.davidchild.bitter.test.business.entity.TUser;
import lombok.var;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class IUpdateQueryColumnTest {

    @Test
    public  void testIUpdateInsColumnsQuery() {
        TUser user = new TUser();
        user.setNickname("david");
        user.setUsername("david");
        var query = user.update();
        String  updateInsColumns = UpdateColumnHandler.getUpdateColumnSet(query);
        String   expect =" SET username=?, email=null, phone=null, password=null, nickname=?, avatar=null, telephone=null, create_by=null, create_time=null, update_by=null, update_time=null, user_age=null, id=null ";
        assertEquals(expect, updateInsColumns);
    }

    @Test
    public  void testIUpdateColumnsQuery() {
        var query = db.update(TUser.class).setColum(TUser::getUsername,"david").setColum(TUser::getNickname,"david");
        String  updateInsColumns = UpdateColumnHandler.getUpdateColumnSet(query);
        System.out.println(updateInsColumns);
        String expect = " SET username=?, nickname=? ";
        assertEquals(expect, updateInsColumns);
    }

    @Test
    public  void testIUpdateColumnsQuery2() {
        var query = db.update(TUser.class).setColum("username","david").setColum(TUser::getNickname,"david");
        String  updateInsColumns = UpdateColumnHandler.getUpdateColumnSet(query);
        System.out.println(updateInsColumns);
        String expect = " SET username=?, nickname=? ";
        assertEquals(expect, updateInsColumns);
    }
}
