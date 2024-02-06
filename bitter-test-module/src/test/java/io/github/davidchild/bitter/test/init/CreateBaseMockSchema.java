package io.github.davidchild.bitter.test.init;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CreateBaseMockSchema {
    @Before
    public void beforeInit(){
        H2MockDataInit.InitTable();
        MockData.MockUserData1();
    }
}
