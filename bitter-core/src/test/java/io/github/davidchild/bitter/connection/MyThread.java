package io.github.davidchild.bitter.connection;

import io.github.davidchild.bitter.annotation.DataSourceType;
import io.github.davidchild.bitter.db.db;

public class MyThread extends Thread {
    public void run() {
        db.doSet(DataSourceType.MASTER, (ret) -> {
            String sql = "select * from sys_user";
            try {

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return 1;
        });
    }
}
