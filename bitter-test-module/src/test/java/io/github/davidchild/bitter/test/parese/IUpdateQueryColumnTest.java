package io.github.davidchild.bitter.test.parese;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.connection.RunnerParam;
import io.github.davidchild.bitter.db.db;
import io.github.davidchild.bitter.excutequery.UpdateColumnHandler;
import io.github.davidchild.bitter.parbag.IBagUpdateColumn;
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

        IBagUpdateColumn bagUpdateColumn = (IBagUpdateColumn) (query.getSingleQuery().getBagOp());
        BaseModel data  = query.getSingleQuery().getBagOp().getData();
        var fieldProperties = query.getSingleQuery().getBagOp().getProperties();
        var keyInfo = query.getSingleQuery().getBagOp().getKeyInfo();
        RunnerParam updateInsColumns  = UpdateColumnHandler.getUpdateColumnSet(
                data
                ,bagUpdateColumn
                ,fieldProperties
        );


        String   expect =" username=?, email=null, phone=null, password=null, nickname=?, avatar=null, telephone=null, create_by=null, create_time=null, update_by=null, update_time=null, user_age=null ";
        assertEquals(expect, updateInsColumns.getCommand());
    }

    @Test
    public  void testIUpdateColumnsQuery() {
        var query = db.update(TUser.class).setColum(TUser::getUsername,"david").setColum(TUser::getNickname,"david");
        IBagUpdateColumn bagUpdateColumn = (IBagUpdateColumn) (query.getSingleQuery().getBagOp());
        BaseModel data  = query.getSingleQuery().getBagOp().getData();
        var fieldProperties = query.getSingleQuery().getBagOp().getProperties();
        var keyInfo = query.getSingleQuery().getBagOp().getKeyInfo();
        RunnerParam updateInsColumns  = UpdateColumnHandler.getUpdateColumnSet(
                data
                ,bagUpdateColumn
                ,fieldProperties
        );
        System.out.println(updateInsColumns);
        String expect = " username=?, nickname=? ";
        assertEquals(expect, updateInsColumns.getCommand());
    }

    @Test
    public  void testIUpdateColumnsQuery2() {
        var query = db.update(TUser.class).setColum("username","david").setColum(TUser::getNickname,"david");
        IBagUpdateColumn bagUpdateColumn = (IBagUpdateColumn) (query.getSingleQuery().getBagOp());
        BaseModel data  = query.getSingleQuery().getBagOp().getData();
        var fieldProperties = query.getSingleQuery().getBagOp().getProperties();
        var keyInfo = query.getSingleQuery().getBagOp().getKeyInfo();
        RunnerParam updateInsColumns  = UpdateColumnHandler.getUpdateColumnSet(
                data
                ,bagUpdateColumn
                ,fieldProperties
        );
        System.out.println(updateInsColumns);
        String expect = " username=?, nickname=? ";
        assertEquals(expect, updateInsColumns.getCommand());
    }
}
