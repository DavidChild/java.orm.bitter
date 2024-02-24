package io.github.davidchild.bitter.test.runner;

import io.github.davidchild.bitter.datatable.DataTable;
import io.github.davidchild.bitter.db.db;
import io.github.davidchild.bitter.op.page.MyPage;
import io.github.davidchild.bitter.op.page.PageQuery;
import io.github.davidchild.bitter.test.business.entity.TUser;
import lombok.SneakyThrows;

public class ThreadTest extends Thread {
    @SneakyThrows
    @Override
    // Thread body, the code in the run () method will be run when the thread is started
    public void run() {
        String sql = "select  user.* from t_user user \n" +
                     "left join t_dept dept on dept.dept_id = user.id";

        PageQuery page = new PageQuery();
        page.where("IFNULL(user.username,'') = ?", "123");

        page.thenASC("user.username");
        page.thenDESC("us.create_time");

        page.skip(1).take(10);
        DataTable mapList = page.getData();
        Integer count = page.getCount();
        Integer count2 = page.getCount();
        int i = 0;
        while (true) {
            db.findQuery(TUser.class).where(t -> t.getUsername() == "david-child").thenDesc(TUser::getUsername)
                .thenAsc(TUser::getUsername).find();
            MyPage p = page.getPage();
            String str = "";
            System.out.println(String.format("recode  is  {} executed ", i));
            Thread.sleep(2000);
            i++;

        }
    }
}