package io.github.davidchild.bitter.test.initMockData;

public class TableStruct {
        final  static  String  student = SchemaH2.getSchemaH2("classpath:db/h2__students.sql");
        final  static  String  dept = SchemaH2.getSchemaH2("classpath:db/h2__dept.sql");
        final  static  String  user = SchemaH2.getSchemaH2("classpath:db/h2__user.sql");


        final  static  String  student_ck = SchemaH2.getSchemaH2("classpath:db/ck__students.sql");
        final  static  String  dept_ck = SchemaH2.getSchemaH2("classpath:db/ck__dept.sql");
        final  static  String  user_ck = SchemaH2.getSchemaH2("classpath:db/ck__user.sql");

}
