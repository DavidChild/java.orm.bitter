package io.github.davidchild.bitter.test.core;

import io.github.davidchild.bitter.db.db;
import io.github.davidchild.bitter.op.scope.DbScope;
import io.github.davidchild.bitter.test.init.CreateBaseMockSchema;
import io.github.davidchild.bitter.test.business.entity.Sex;
import io.github.davidchild.bitter.test.business.entity.TStudent;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TransactionTest extends CreateBaseMockSchema {
    @Test
    @Ignore  // Supports all types of DB transaction libraries compatible with MYSQL transaction submissions
    public void testDoScope() {
        DbScope dbScope = db.doScope(); // create a database transaction executor
        boolean is_success = dbScope.create((list) -> {
            TStudent david_child_student_wait_update = db.findQuery(TStudent.class).where(t -> t.getName().equals("david-child")).find().fistOrDefault();
            david_child_student_wait_update.setName("jb");
            david_child_student_wait_update.update().addInScope(list);

            TStudent new_student_wait_inert = new TStudent();
            new_student_wait_inert.setName("hello hjb");
            new_student_wait_inert.setSexName(Sex.man);
            new_student_wait_inert.insert().addInScope(list);

            TStudent  wait_delete_student = db.findQuery(TStudent.class).where(t -> t.getName().equals("hello hjb")).find().fistOrDefault();
            wait_delete_student.delete().addInScope(list);

        }).submit() > -1l;

        assertEquals(true,is_success);

    }
}
