package io.github.davidchild.bitter.samples.business.controller;

import io.github.davidchild.bitter.db.db;
import io.github.davidchild.bitter.op.insert.Insert;
import io.github.davidchild.bitter.samples.business.entity.TStudent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/business/t-insert-batch")
public class TestInsertBatchDataController {
    @GetMapping("/test-insert-batch-data")

    public boolean insertBatchData() {
        return db.bachInsert().doBachInsert((list) -> {
            for (int i = 0; i < 10; i++) {
                TStudent studentInfo = new TStudent();
                studentInfo.setName("hjb" + i);
                studentInfo.insert().addInBachInsertPool((List<Insert>) list);
            }
        }).submit() > -1; //note:The bulk insert is transactional in nature as a whole.if error will be returns - 1, if error,you can see the error log msg.
    }
}
