package io.github.davidchild.bitter.samples.business.controller;

import io.github.davidchild.bitter.db.db;
import io.github.davidchild.bitter.samples.business.entity.TStudent;
import io.github.davidchild.bitter.samples.business.entity.TUser;
import io.github.davidchild.bitter.samples.customdatasource.annotation.TargetSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("/business/t-more-db")

public class TestMoreDbController {

    @TargetSource("SLAVE") // TargetSource that is  user defined more db target annotations
    @GetMapping("/more-db-test-slave")
    public TUser queryData() throws SQLException, InstantiationException, IllegalAccessException {
        TUser sysUser = db.findQuery(TUser.class, "1552178014981849090").find();
        return sysUser;
    }

    @TargetSource("MASTER") // TargetSource that is  user defined more db target annotations
    @PostMapping("/more-db-test-master")
    public boolean saveData(TStudent student) {
        return student.save();
    }
}

