package io.github.davidchild.bitter.test.business.controller;

import io.github.davidchild.bitter.cache.cache.CacheEnum;
import io.github.davidchild.bitter.db.db;
import io.github.davidchild.bitter.test.business.entity.TStudent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;

@RestController
@RequestMapping("/business/t-cache")
public class TestCacheController {
    @GetMapping("/test-cache-simple")
    public boolean cache() {
        return db.doCache().createCache(100000, CacheEnum.CacheSlither).setCacheKey("System:d")
                .dataFrom(boolean.class, (item) -> {
                    return true;
                });
    }

    @GetMapping("/test-cache-query")
    public ArrayList query() {
        return db.doCache().createCache(100000, CacheEnum.CacheSlither).setCacheKey("System:students")
                .dataFrom(ArrayList.class, (item) -> {
                    return db.findQuery("select * from t_student").beginWhere("1=1").andWhere("name=?", "david-child").find().toBList(TStudent.class);
                });

    }


    @GetMapping("/test-cache-query-error")
    public ArrayList queryForRedisError() {
        // that the redis connection error,but the Data  can still be returned (data from db)
        return db.doCache().createCache(new Date().getTime() + 10000, CacheEnum.CacheAbsolute).setCacheKey("System:students")
                .dataFrom(ArrayList.class, (item) -> {
                    return db.findQuery("select * from t_student").beginWhere("1=1").andWhere("name=?", "david-child").find().toBList(TStudent.class);
                });

    }
}
