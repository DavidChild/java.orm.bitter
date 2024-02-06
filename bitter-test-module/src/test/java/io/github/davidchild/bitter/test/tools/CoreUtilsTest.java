package io.github.davidchild.bitter.test.tools;

import io.github.davidchild.bitter.dbtype.DataValue;
import io.github.davidchild.bitter.test.business.entity.TUser;
import io.github.davidchild.bitter.tools.CoreUtils;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.List;


public class CoreUtilsTest extends TestCase {

    @Test
    public void testGetFieldByObject() {
        TUser busInfo = new TUser();
        busInfo.setUsername("davidChild");
        List<DataValue> files = CoreUtils.getTypeRelationData(busInfo.getClass(), busInfo);
        System.out.println(files);
    }

    @Test
    public void testGetTableNameByObject() {

        TUser busInfo = new TUser();
        String tableName = CoreUtils.getTableNameByObject(busInfo);
        System.out.println(tableName);
    }

    @Test
    public void testGetFieldsByType() {
        TUser busInfo = new TUser();
        List<DataValue> files = CoreUtils.getTypeRelationData(TUser.class, null);
        System.out.println(files);
    }

    @Test
    public void testGetTableNameByType() {
        String tableName = CoreUtils.getTableNameByType(TUser.class);
        System.out.println(tableName);
    }
}