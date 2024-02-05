package io.github.davidchild.bitter.test.business.controller;

import io.github.davidchild.bitter.db.db;
import io.github.davidchild.bitter.test.business.entity.TStudent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/business/t-update")
public class TestUpdateController {
    @GetMapping("/test-update-by-model")
    public boolean updateData(Long id) {
        TStudent student = db.findQuery(TStudent.class).where(f -> f.getId() == id).find().fistOrDefault();
        if (student.hasKeyValue()) {
            student.setName("hbj-david-child");
            // note: error will be return -1,if error,pls see the error log msg.
            return student.update().submit() > -1; // that will update all fields value to this db recode instance data  by the student object
        }
        return false;
    }

    @GetMapping("/test-update-by-model-column")
    public boolean updateDataSomeRecodeColumn(Long id) {
        return db.update(TStudent.class)
                .setColum(TStudent::getName, "wangLaoWu")
                .setColum(TStudent::getSexName, "男")
                .where(f -> f.getId() == id).submit() > -1;
    }

    @GetMapping("/test-update-by-command")
    public boolean insertDataByCommand(String id) {
        String sql = "update  t_student set name= 'hjb-update-david-child'  where id = ?";
        return db.execute(sql, id).submit() > -1; //note: error will be return -1,if error,pls see the error log msg.
    }
    
}
