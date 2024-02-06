package io.github.davidchild.bitter.test.initMockData;

import io.github.davidchild.bitter.test.business.entity.Sex;
import io.github.davidchild.bitter.test.business.entity.TStudent;
import io.github.davidchild.bitter.test.business.entity.TUser;

import java.util.Date;

public class MockData {
    public  static  void MockUserData1(){

        TUser user = new TUser();
        user.setId(String.valueOf("1552178014981849090"));
        user.setUsername("hjb");
        user.setCreateTime(new Date());
        user.insert().submit();

        for(int i=0;i<10;i++){

            TUser random_user = new TUser();
            random_user.setId(String.valueOf(SnowFlakeUtils.nextId()));
            random_user.setUsername(MockRandomData.getRandomBoyName());
            random_user.setCreateTime(MockRandomData.getRandonTime());
            random_user.setUserAge(MockRandomData.randomInt(80));
            random_user.insert().submit();
        }

        TStudent student = new TStudent();
        student.setId(SnowFlakeUtils.nextId());
        student.setSexName(Sex.man);
        student.setName("david-child");
        student.insert().submit();

        TStudent student1 = new TStudent();
        student.setId(1l);
        student.setSexName(Sex.woman);
        student.setName("li-chun-gang");
        student.insert().submit();

    }

}
