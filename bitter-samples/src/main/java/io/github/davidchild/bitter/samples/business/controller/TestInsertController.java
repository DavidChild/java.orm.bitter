package io.github.davidchild.bitter.samples.business.controller;

import io.github.davidchild.bitter.db.db;
import io.github.davidchild.bitter.samples.business.entity.TStudent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/business/t-insert")
public class TestInsertController {
    @GetMapping("/test-insert-by-model")
    public Long insertData(TStudent student) {
        //note: error will be return -1,if error,pls see the error log msg.
        return student.insert().submit(); // Whether it's ID self growth or snowflake algorithm, the insertion returns the primary key value LONG type
    }

    @GetMapping("/test-insert-by-command")
    public boolean insertDataComand(TStudent student) {
        String sql = "INSERT INTO `t_student`(`name`, `sex`) VALUES ('david-child', '男');";
        return db.execute(sql).submit() > -1; //note: error will be return -1,if error,pls see the error log msg.
    }


    @PostMapping("/test-save-data")
    public boolean saveData(TStudent student) {
        // note: error will be return false, or success,will return true, if error,pls see the error log.
        return student.save(); //If the student primary key value has a value, the object is updated to db, and if it does not exist, the entity object is inserted to db.
    }


}
