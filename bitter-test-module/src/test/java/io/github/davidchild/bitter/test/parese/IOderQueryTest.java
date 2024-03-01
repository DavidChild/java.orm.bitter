package io.github.davidchild.bitter.test.parese;


import io.github.davidchild.bitter.connection.RunnerParam;
import io.github.davidchild.bitter.db.db;
import io.github.davidchild.bitter.excutequery.OrderHandler;
import io.github.davidchild.bitter.op.page.PageQuery;
import io.github.davidchild.bitter.parbag.IBagOrder;
import io.github.davidchild.bitter.test.business.entity.TUser;
import lombok.var;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class IOderQueryTest {

    @Test
    public  void testIOderQuery() {
        PageQuery queryTest = new PageQuery("");
        queryTest.orderBy("username desc");
        RunnerParam order = OrderHandler.getOrder( (IBagOrder) queryTest.getSingleQuery().getBagOp());
        assertEquals(" ORDER BY username desc", order.getCommand());

    }

    @Test
    public  void testIOderQueryMore() {
        PageQuery queryTest = new PageQuery("");
        queryTest.orderBy("username desc");
        queryTest.orderBy("age asc");

         RunnerParam order = OrderHandler.getOrder( (IBagOrder) queryTest.getSingleQuery().getBagOp());
        assertEquals(" ORDER BY username desc, age asc", order.getCommand());
    }

    @Test
    public  void testIOderQueryMore2() {
        PageQuery queryTest = new PageQuery("");
        queryTest.thenDesc("username");
        queryTest.thenAsc("age");
        RunnerParam order = OrderHandler.getOrder( (IBagOrder) queryTest.getSingleQuery().getBagOp());
        assertEquals(" ORDER BY username desc, age asc", order.getCommand());
    }


    @Test
    public  void testIOderQueryMore3() {
        PageQuery queryTest = new PageQuery("");
        queryTest.orderBy("username desc");
        queryTest.thenDesc("username");
        queryTest.thenAsc("age");
        RunnerParam order = OrderHandler.getOrder( (IBagOrder) queryTest.getSingleQuery().getBagOp());
        assertEquals(" ORDER BY username desc, username desc, age asc", order.getCommand());
    }

    @Test
    public  void testIOderQueryGen() {
        var query = db.findQuery(TUser.class).orderBy("username desc").thenAsc(TUser::getNickname);
        RunnerParam order = OrderHandler.getOrder( (IBagOrder) query.getSingleQuery().getBagOp());
        assertEquals(" ORDER BY username desc, nickname asc", order.getCommand());
    }

    @Test
    public  void testIOderQueryGen2() {
        var query = db.findQuery(TUser.class).orderBy("username desc").thenAsc(TUser::getNickname).thenDesc(TUser::getPhone);
        RunnerParam order = OrderHandler.getOrder( (IBagOrder) query.getSingleQuery().getBagOp());
        assertEquals(" ORDER BY username desc, nickname asc, phone desc", order.getCommand());
    }

    @Test
    public  void testIOderQueryNone() {
        PageQuery queryTest = new PageQuery("");
        RunnerParam order = OrderHandler.getOrder( (IBagOrder) queryTest.getSingleQuery().getBagOp());
        assertEquals("", order.getCommand());
    }


}
