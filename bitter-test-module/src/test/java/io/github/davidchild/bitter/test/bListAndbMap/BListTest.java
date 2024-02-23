package io.github.davidchild.bitter.test.bListAndbMap;

import io.github.davidchild.bitter.datatable.DataTable;
import io.github.davidchild.bitter.db.db;
import io.github.davidchild.bitter.test.business.entity.TUser;
import io.github.davidchild.bitter.test.initMockData.CreateBaseMockSchema;
import lombok.var;
import org.h2.util.StringUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class BListTest  extends CreateBaseMockSchema {

    @Ignore
    public DataTable getUserOriginDataFromDb(String id){
        this.beforeInit();
        DataTable mapOrigin;
        String sql = "select * from t_user";
        if(!StringUtils.isNullOrEmpty(id)){
            mapOrigin = db.findQuery("select * from t_user").where("id=?",id).find();
        }else {
            mapOrigin = db.findQuery("select * from t_user").find();
        }

        return  mapOrigin;
    }

    public DataTable getUser(String id){
        this.beforeInit();
        DataTable mapOrigin;
        String sql = "select * from t_user";
        if(!StringUtils.isNullOrEmpty(id)){
            mapOrigin = db.findQuery("select * from t_user").where("id=?",id).find();
        }else {
            mapOrigin = db.findQuery("select * from t_user").find();
        }

        return  mapOrigin;
    }
    @Test
    public void testBMapConvert2BList() throws Exception {
        DataTable data = getUserOriginDataFromDb(null);
        var users = data.toModelList(TUser.class);
        assertEquals(true, users.size()>0);
    }

    @Test
    public void testGetFistOrDefault_1() throws Exception {
        DataTable data = getUserOriginDataFromDb(null);
        var users = data.toModelList(TUser.class);
        TUser user = users.fistOrDefault();
        assertEquals(true, user.hasKeyValue());
    }
    @Test
    public void testGetFistOrDefault_2() throws Exception {
        DataTable data = getUserOriginDataFromDb("1231778782"); // the  id is not in db.
        var users = data.toModelList(TUser.class);
        TUser user = users.fistOrDefault();  // bmap call the fistOrDefault method, that will be return a default object instance where the bmap size is null.
        assertEquals(false, user.hasKeyValue());
    }


}

