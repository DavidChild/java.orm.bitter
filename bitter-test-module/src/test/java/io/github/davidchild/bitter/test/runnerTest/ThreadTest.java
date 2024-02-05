package io.github.davidchild.bitter.test.runnerTest;

import io.github.davidchild.bitter.db.db;
import io.github.davidchild.bitter.op.page.MyPage;
import io.github.davidchild.bitter.op.page.PageQuery;
import io.github.davidchild.bitter.test.business.entity.TUser;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Map;

public class ThreadTest extends Thread {
    @SneakyThrows
    @Override
    // Thread body, the code in the run () method will be run when the thread is started
    public void run() {
        String sql = "select \n" + "us.*,\n" + "zf.user_id as access_user_id,\n" + "dept.ancestors as access_dept_ids\n"
            + " from \n" + "sys_user us \n" + "left join zf_sys_user zf on zf.user_name = us.visit_name\n"
            + "left join zf_sys_dept dept on dept.dept_id = zf.dept_id";

        PageQuery page = new PageQuery(sql);
        page.where("IFNULL(username,'') = ?", "123");

        page.thenASC("username");
        page.thenDESC("us.create_time");

        page.skip(1).take(10);
        List<Map<String, Object>> mapList = page.getData();
        Integer count = page.getCount();
        Integer count2 = page.getCount();
        int i = 0;
        while (true) {
            db.findQuery(TUser.class).where(t -> t.getUsername() == "hjb").thenDesc(TUser::getUsername)
                .thenAsc(TUser::getUsername).find();
            MyPage p = page.getPage();
            String str = "";
            System.out.println(String.format("recode  is  {} executed ", i));
            Thread.sleep(2000);
            i++;

        }
    }
}