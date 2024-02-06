package io.github.davidchild.bitter.test.initMockData;

import io.github.davidchild.bitter.db.db;

public class H2MockDataInit {
    public static void  InitTable(){
        db.execute("drop table t_dept",null).submit();
        db.execute("drop table t_user",null).submit();
        db.execute("drop table t_student",null).submit();
        db.execute(TableStruct.student,null).submit();
        db.execute(TableStruct.dept,null).submit();
        db.execute(TableStruct.user,null).submit();
    }
}
