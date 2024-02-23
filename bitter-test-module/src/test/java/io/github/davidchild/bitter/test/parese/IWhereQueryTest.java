package io.github.davidchild.bitter.test.parese;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.connection.RunnerParam;
import io.github.davidchild.bitter.db.db;
import io.github.davidchild.bitter.excutequery.WhereHandler;
import io.github.davidchild.bitter.functional.IfInnerLambda;
import io.github.davidchild.bitter.parbag.IBagWhere;
import io.github.davidchild.bitter.test.business.entity.TUser;
import lombok.var;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class IWhereQueryTest {
    @Test
    public  void testWhereModelIns() {
        TUser user = new TUser();
        user.setId("1552178014981849090");
        user.setNickname("david");
        user.setUsername("david");
        IBagWhere bagWhere = (IBagWhere) (user.update().getSingleQuery().getBagOp());
        BaseModel data  = user.update().getSingleQuery().getBagOp().getData();
        var fieldProperties = user.update().getSingleQuery().getBagOp().getProperties();
        var keyInfo = user.update().getSingleQuery().getBagOp().getKeyInfo();
        RunnerParam where  = WhereHandler.getWhere(
                data
                ,bagWhere
                ,fieldProperties
                ,keyInfo
        );
        System.out.println(where);
        String expect =" Where id=?";
        assertEquals(expect, where.getCommand());

    }

    @Test
    public  void testWhereDelete() {
        TUser user = new TUser();
        user.setId("1552178014981849090");
        user.setNickname("david");
        user.setUsername("david");
        IBagWhere bagWhere = (IBagWhere) (user.delete().getSingleQuery().getBagOp());
        BaseModel data  = user.delete().getSingleQuery().getBagOp().getData();
        var fieldProperties = user.delete().getSingleQuery().getBagOp().getProperties();
        var keyInfo = user.delete().getSingleQuery().getBagOp().getKeyInfo();
        RunnerParam where  = WhereHandler.getWhere(
                data
                ,bagWhere
                ,fieldProperties
                ,keyInfo
        );
        System.out.println(where);
        String expect =" Where id=?";
        assertEquals(expect, where.getCommand());
    }
    @Test
    public  void testWhereUpdate() {
        var update = db.update(TUser.class).where("id=?","1552178014981849090").where("username like '%da%'");
        IBagWhere bagWhere = (IBagWhere) (update.getSingleQuery().getBagOp());
        BaseModel data  = update.getSingleQuery().getBagOp().getData();
        var fieldProperties = update.getSingleQuery().getBagOp().getProperties();
        var keyInfo = update.getSingleQuery().getBagOp().getKeyInfo();
        RunnerParam where  = WhereHandler.getWhere(
                data
                ,bagWhere
                ,fieldProperties
                ,keyInfo
        );
        System.out.println(where);
        String expect =" Where 1=1 and (id=?) and (username like '%da%')";
        assertEquals(expect, where.getCommand());
    }

    @Test
    public  void testWhereUpdate2() {
        String id = null;
        var update = db.update(TUser.class).whereNotEmpty("id=?",id).where("username like '%da%'");
        IBagWhere bagWhere = (IBagWhere) (update.getSingleQuery().getBagOp());
        BaseModel data  = update.getSingleQuery().getBagOp().getData();
        var fieldProperties = update.getSingleQuery().getBagOp().getProperties();
        var keyInfo = update.getSingleQuery().getBagOp().getKeyInfo();
        RunnerParam where  = WhereHandler.getWhere(
                data
                ,bagWhere
                ,fieldProperties
                ,keyInfo
        );
        System.out.println(where);
        String expect =" Where 1=1 and (username like '%da%')";
        assertEquals(expect, where.getCommand());
    }

    @Test
    public  void testWhereUpdate3() {
        String id = "1231";
        var update = db.update(TUser.class).whereNotEmpty("id=?",id).where("username like '%da%'");
        IBagWhere bagWhere = (IBagWhere) (update.getSingleQuery().getBagOp());
         BaseModel data  = update.getSingleQuery().getBagOp().getData();
        var fieldProperties = update.getSingleQuery().getBagOp().getProperties();
        var keyInfo = update.getSingleQuery().getBagOp().getKeyInfo();
        RunnerParam where  = WhereHandler.getWhere(
                 data
                ,bagWhere
                ,fieldProperties
                ,keyInfo
                );
        System.out.println(where);
        String expect =" Where 1=1 and (id=?) and (username like '%da%')";
        assertEquals(expect, where.getCommand());
    }

    @Test
    public  void testWhereUpdate4() {
        String id = "";
        IfInnerLambda lambda = ()->id != null && id != "";
        var update = db.update(TUser.class).whereNotEmpty(lambda,"id=?",id).where("username like '%da%'");
        IBagWhere bagWhere = (IBagWhere) (update.getSingleQuery().getBagOp());
        BaseModel data  = update.getSingleQuery().getBagOp().getData();
        var fieldProperties = update.getSingleQuery().getBagOp().getProperties();
        var keyInfo = update.getSingleQuery().getBagOp().getKeyInfo();
        RunnerParam where  = WhereHandler.getWhere(
                data
                ,bagWhere
                ,fieldProperties
                ,keyInfo
        );
        System.out.println(where);
        String expect =" Where 1=1 and (username like '%da%')";
        assertEquals(expect, where.getCommand());
    }

    @Test
    public  void testWhereUpdate5() {
        String id = "";
        IfInnerLambda lambda = ()->id != null && id != "";
        var update = db.update(TUser.class).whereNotNull(lambda,"id=?",id).where("username like '%da%'");
        IBagWhere bagWhere = (IBagWhere) (update.getSingleQuery().getBagOp());
        BaseModel data  = update.getSingleQuery().getBagOp().getData();
        var fieldProperties = update.getSingleQuery().getBagOp().getProperties();
        var keyInfo = update.getSingleQuery().getBagOp().getKeyInfo();
        RunnerParam where  = WhereHandler.getWhere(
                data
                ,bagWhere
                ,fieldProperties
                ,keyInfo
        );
        System.out.println(where);
        String expect =" Where 1=1 and (username like '%da%')";
        assertEquals(expect, where.getCommand());
    }

    @Test
    public  void testWhereUpdate6() {
        String id = "";
        IfInnerLambda lambda = ()->id != null && id != "";
        var update = db.update(TUser.class).whereNotNull(lambda,"id=?",id).where("username like '%da%'").where(f->f.getPhone().contains("189"));
        IBagWhere bagWhere = (IBagWhere) (update.getSingleQuery().getBagOp());
        BaseModel data  = update.getSingleQuery().getBagOp().getData();
        var fieldProperties = update.getSingleQuery().getBagOp().getProperties();
        var keyInfo = update.getSingleQuery().getBagOp().getKeyInfo();
        RunnerParam where  = WhereHandler.getWhere(
                data
                ,bagWhere
                ,fieldProperties
                ,keyInfo
        );
        System.out.println(where);
        String expect =" Where 1=1 and ( `phone` like concat('%',?,'%') ) and (username like '%da%')";
        assertEquals(expect, where.getCommand());
    }

    @Test
    public  void testWhereUpdate7() {
        var update = db.update(TUser.class).where(f->f.getPhone().contains("189"));
        IBagWhere bagWhere = (IBagWhere) (update.getSingleQuery().getBagOp());
        BaseModel data  = update.getSingleQuery().getBagOp().getData();
        var fieldProperties = update.getSingleQuery().getBagOp().getProperties();
        var keyInfo = update.getSingleQuery().getBagOp().getKeyInfo();
        RunnerParam where  = WhereHandler.getWhere(
                data
                ,bagWhere
                ,fieldProperties
                ,keyInfo
        );
        System.out.println(where.getCommand());
        String expect =" Where 1=1 and ( `phone` like concat('%',?,'%') )";
        assertEquals(expect, where.getCommand());
    }

    @Test
    public  void testWhereUpdate8() {
        var update = db.update(TUser.class).where(f -> f.getPhone().contains("189")).where(f -> f.getUsername() == "david-child");
        IBagWhere bagWhere = (IBagWhere) (update.getSingleQuery().getBagOp());
        BaseModel data = update.getSingleQuery().getBagOp().getData();
        var fieldProperties = update.getSingleQuery().getBagOp().getProperties();
        var keyInfo = update.getSingleQuery().getBagOp().getKeyInfo();
        RunnerParam where = WhereHandler.getWhere(
                data
                , bagWhere
                , fieldProperties
                , keyInfo
        );
        System.out.println(where);
        String expect = " Where 1=1 and ( `phone` like concat('%',?,'%') ) and ( `username` = ? )";
        assertEquals(expect, where.getCommand());
    }
}
