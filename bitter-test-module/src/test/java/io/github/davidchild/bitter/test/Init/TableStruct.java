package io.github.davidchild.bitter.test.Init;

public class TableStruct {
        final  static  String  student = SchemaH2.getSchemaH2("classpath:db/h2__students.sql");
        final  static  String  dept = SchemaH2.getSchemaH2("classpath:db/h2__dept.sql");
        final  static  String  user = SchemaH2.getSchemaH2("classpath:db/h2__user.sql");
}
