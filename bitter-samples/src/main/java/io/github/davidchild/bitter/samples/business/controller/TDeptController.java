package io.github.davidchild.bitter.samples.business.controller;


import io.github.davidchild.bitter.cache.cache.CacheEnum;
import io.github.davidchild.bitter.db.db;
import io.github.davidchild.bitter.samples.business.entity.TDept;
import io.github.davidchild.bitter.samples.business.service.ITDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 部门表 前端控制器
 * </p>
 *
 * @author davidChild
 * @since 2022-12-17
 */
@RestController
@RequestMapping("/business/t-dept")
public class TDeptController {
    @Autowired
    private ITDeptService deptService;

    @PostMapping("/save-dept")
    public boolean daveDept(@RequestBody TDept entity) {
        return db.doCache().createCache(10000, CacheEnum.CacheAbsolute)
                .setCacheKey("System:d", entity.getDeptId().toString()).dataFrom(boolean.class, (item) -> {
                    return deptService.save(entity);
                });

    }
}
