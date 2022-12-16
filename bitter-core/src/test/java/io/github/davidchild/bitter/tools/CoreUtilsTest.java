package io.github.davidchild.bitter.tools;

import java.util.List;

import org.junit.Test;

import io.github.davidchild.bitter.dbtype.FieldProperty;
import io.github.davidchild.bitter.entity.TUserInfo;
import junit.framework.TestCase;

public class CoreUtilsTest extends TestCase {

    @Test
    public void testGetFieldByObject() {
        TUserInfo busInfo = new TUserInfo();
        busInfo.setUsername("davidChild");
        List<FieldProperty> files = CoreUtils.getFields(busInfo.getClass(), busInfo);
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
        List<FieldProperty> files = CoreUtils.getFields(TUserInfo.class, null);
        System.out.println(files);
    }

    @Test
    public void testGetTableNameByType() {
        String tableName = CoreUtils.getTableNameByType(TUserInfo.class);
        System.out.println(tableName);
    }
}