package io.github.davidchild.bitter.test.tools;

import io.github.davidchild.bitter.tools.JsonUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JsonUtilTest {

    @Test
    public void testJsonSerialize() {
        ResignApply entity = new ResignApply();
        entity.isHire = 1;
        entity.oaNo = "20220713LZSQ00030";
        String json  = JsonUtil.object2String(entity);
        System.out.println(json);
    }


    @Test
    public void testIngoreCase1Mapper() {
        String json = "{\"oaNo\":\"20220713LZSQ00030\",\"isHire\":1}";
        ResignApply entity = JsonUtil.string2Object(json, ResignApply.class);
        assertEquals(true, entity.isHire ==1 );

    }

    @Test
    public void testIngoreCase2Mapper() {
        String json = "{\"oano\":\"20220713LZSQ00030\",\"ishire\":1}";
        ResignApply entity = JsonUtil.string2Object(json, ResignApply.class);
        assertEquals(true, entity.isHire ==1 );
    }

    @Test
    public void testUnknownFieldMapper() {
        String json = "{\"oano\":\"20220713LZSQ00030\",\"ishire\":1,\"password\":\"7778887778887\"}";
        ResignApply entity = JsonUtil.string2Object(json, ResignApply.class);
        assertEquals(true, entity.isHire ==1 );
    }


}


