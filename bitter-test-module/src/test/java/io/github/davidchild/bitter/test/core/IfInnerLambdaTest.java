package io.github.davidchild.bitter.test.core;

import io.github.davidchild.bitter.functional.IfInnerLambda;
import io.github.davidchild.bitter.test.init.CreateBaseMockSchema;
import io.github.davidchild.bitter.test.business.entity.TStudent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class IfInnerLambdaTest extends CreateBaseMockSchema {
    @Test
    public void ifInnerLambdaTest()  {
        TStudent tStudent = new TStudent();
        tStudent.setName("hjb");
        IfInnerLambda lambda1 = ()-> tStudent.getName() == "hjb";
        assertEquals(true, lambda1.IfOrNot());

        IfInnerLambda lambda2 = ()-> tStudent.getName() == "cbl";
        assertEquals(false, lambda2.IfOrNot());

    }

}
