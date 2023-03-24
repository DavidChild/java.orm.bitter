package io.github.davidchild.bitter.samples.business.controller;

import io.github.davidchild.bitter.db.db;
import io.github.davidchild.bitter.op.scope.DbScope;
import io.github.davidchild.bitter.samples.business.entity.TStudent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/business/t-scope")
public class TestScopeController {

    @PostMapping("/test-scope")
    public boolean DoScope() {
        DbScope dbScope = db.doScope(); // note: db.doScope, the method will  create a  transaction Constructor
        return dbScope.create((list) -> {
            TStudent sys =
                    db.findQuery(TStudent.class).where(t -> t.getName().equals("hjb5")).find().fistOrDefault();
            sys.setName("jb");
            sys.update().addInScope(list); //The update execution is collected into pending transactions

            TStudent studentInfo = new TStudent();
            studentInfo.setName("hello hjb");
            studentInfo.insert().addInScope(list); //The insert execution is collected into pending transactions
            TStudent sys_2 =
                    db.findQuery(TStudent.class).where(t -> t.getName().equals("hjb4")).find().fistOrDefault();
            sys_2.delete().addInScope(list); // the delete-execution is collected into pending transactions

        }).submit() > -1l; // note:  if the executed transaction succeeds, it returns 1. If the transaction fails,will be return -1, error pls  see the log msg.
    }
}
