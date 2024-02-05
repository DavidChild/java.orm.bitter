package io.github.davidchild.bitter.test.Init;

import cn.hutool.core.io.FileUtil;
import org.springframework.util.ResourceUtils;

import java.io.File;

public class SchemaH2 {
    public  static  String getSchemaH2(String resourcePath)  {
        try {
           File file = ResourceUtils.getFile(resourcePath);
           String sql = FileUtil.readUtf8String(file);
           return  sql;
        }catch (Exception ex){
            return null;
        }


    }
}
