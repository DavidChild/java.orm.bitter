package io.github.davidchild.bitter.test.core;

import io.github.davidchild.bitter.datatable.DataTable;
import io.github.davidchild.bitter.op.page.PageQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DqlTest2 {

    @Test
    public void testQueryGen1() {

        String sql =
                "SELECT  swu.phone,  swu.create_time,  swu.third_type,  " +
                        "swu.did FROM  dipin_invited_user iu  " +
                        "LEFT JOIN sys_wait_users swu ON iu.phone = swu.phone";

        PageQuery pq = new PageQuery(sql);
        pq.thenDesc("swu.create_time");
        pq.take(0).skip(10);
        DataTable dt = pq.getData();
        assertEquals(true, dt.size() > 0);

    }

}
