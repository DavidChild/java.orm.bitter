package io.github.davidchild.bitter.connection;

public class SessionDbType {
    private static DatabaseType  sessionDbType = DatabaseType.MySQL;
    public static void setSessionDbType(DatabaseType dbType){
        sessionDbType = dbType;
    }
    public  static   DatabaseType getSessionDbType(){
        return  sessionDbType;
    }
}
