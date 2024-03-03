package io.github.davidchild.bitter.test.core;

import io.github.davidchild.bitter.datatable.DataTable;
import io.github.davidchild.bitter.db.db;
import io.github.davidchild.bitter.test.initMockData.CreateBaseMockSchema;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ExecuteQueryTest extends CreateBaseMockSchema {
    @Test
    public void testCustom(){
        this.beforeInit();
        String sql = "select * from t_user";
        DataTable dt = db.findQuery(sql).where("id = ?","1552178014981849090").find();
        assertEquals(true, dt.size()>0);
    }

}
