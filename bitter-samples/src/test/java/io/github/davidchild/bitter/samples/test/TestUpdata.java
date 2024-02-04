package io.github.davidchild.bitter.samples.test;
import io.github.davidchild.bitter.db.db;
import io.github.davidchild.bitter.samples.business.entity.TStudent;
import lombok.var;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestUpdata {
    @Test
    public void TestUpdateDataSomeRecodeColumn() {
        boolean bl = db.update(TStudent.class)
                .setColum(TStudent::getName, "wangLaoWu")
                .setColum(TStudent::getSexName, "男")
                .where(f -> f.getId() == 1).submit() > -1;
        assertEquals(true, bl);

    }
    @Test
    public void TestTheHasKeyValue() {
        var student = db.findQuery(TStudent.class).where(p->p.getId()==1).find().fistOrDefault();
        boolean bl = student.hasKeyValue();
        assertEquals(true, bl);

    }


    @Test
    public void TestUpdataModel() {
        var student =db.findQuery(TStudent.class).where(p->p.getId() == 1).find().fistOrDefault();
        if(student.hasKeyValue()){
            student.setName("改变姓名值了");
            student.update().submit();
        }
        var student1 =db.findQuery(TStudent.class).where(p->p.getId() == 1).find().fistOrDefault();
        assertEquals("改变姓名值了", student1.getName());

    }

}
