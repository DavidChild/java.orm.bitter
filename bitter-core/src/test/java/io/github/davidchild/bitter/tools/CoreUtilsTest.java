package io.github.davidchild.bitter.tools;

import io.github.davidchild.bitter.dbtype.DataValue;
import io.github.davidchild.bitter.entity.TUserInfo;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.List;

public class CoreUtilsTest extends TestCase {

    @Test
    public void testGetFieldByObject() {
        TUserInfo busInfo = new TUserInfo();
        busInfo.setUsername("davidChild");
        List<DataValue> files = CoreUtils.getTypeRelationData(busInfo.getClass(), busInfo);
        System.out.println(files);
    }

    @Test
    public void testGetTableNameByObject() {

        TUserInfo busInfo = new TUserInfo();
        String tableName = CoreUtils.getTableNameByObject(busInfo);
        System.out.println(tableName);
    }

    @Test
    public void testGetFieldsByType() {
        TUserInfo busInfo = new TUserInfo();
        List<DataValue> files = CoreUtils.getTypeRelationData(TUserInfo.class, null);
        System.out.println(files);
    }

    @Test
    public void testGetTableNameByType() {
        String tableName = CoreUtils.getTableNameByType(TUserInfo.class);
        System.out.println(tableName);
    }
}