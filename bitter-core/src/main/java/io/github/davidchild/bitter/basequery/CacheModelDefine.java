package io.github.davidchild.bitter.basequery;

import io.github.davidchild.bitter.dbtype.FieldProperty;

import java.util.HashMap;
import java.util.List;

public class CacheModelDefine {
    private static HashMap<String,  List<FieldProperty>>  cacheModelDefine;
    public static HashMap<String, List<FieldProperty>>  getCacheModelDefine(){
         if(cacheModelDefine==null){
               cacheModelDefine = new HashMap<>();
         }
         return  cacheModelDefine;
    }
}
