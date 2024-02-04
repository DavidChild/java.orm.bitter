package io.github.davidchild.bitter.samples.business.controller;

import io.github.davidchild.bitter.db.db;
import io.github.davidchild.bitter.samples.business.entity.TStudent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/business/t-test-delete")
public class TestDeleteController {

    @GetMapping("/test-delete-by-id-model")
    public boolean deleteByModel(String id) {
        TStudent student = db.findQuery(TStudent.class).where(f -> f.getId().equals(id)).find().fistOrDefault();
        if (student.hasKeyValue()) {
            return student.delete().submit() > -1; //note: error will be return -1,if error,pls see the error log msg.
        }
        return true;
    }

    @GetMapping("/test-delete-by-id-command-text")
    public boolean deleteByCommand(String id) {
        String sql = "delete from t_student where id = ?";
        return db.execute(sql, id).submit() > -1; // error will be return -1,if error,pls see the error log msg.

    }


}
