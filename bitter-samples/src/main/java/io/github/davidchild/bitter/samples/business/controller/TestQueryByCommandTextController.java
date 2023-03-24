package io.github.davidchild.bitter.samples.business.controller;

import io.github.davidchild.bitter.db.db;
import io.github.davidchild.bitter.samples.business.entity.TUser;
import io.github.davidchild.bitter.samples.business.vo.VoOutUser;
import io.github.davidchild.bitter.tools.DateUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/business/t-query-command")
public class TestQueryByCommandTextController {
    @GetMapping("/query-list-command")
    public List<TUser> queryList(String name) {
        Date date = DateUtils.parseDate("2022-8-10");
        String sql = "select * form t_user";
        // if error ,pls see the error msg.
        return db.findQuery(sql).beginWhere("1=1").andWhere("create_time>?", date).find().toBList(TUser.class); // note: If execute success will query data recode,or not ,will return empty recode
    }

    @GetMapping("/query-by-id-command")
    public TUser queryById(String id) throws Exception {
        String sql = "select * form t_user";
        // if exception,pls see the error log msg.
        return db.findQuery(sql).beginWhere("1=1").andWhere("id=?", id).find().toBList(TUser.class).fistOrDefault(); // note: If execute success will query data recode,or not ,will return empty recode
    }

    @GetMapping("/query-join-table-to-vo")
    // database lookup sets are directly converted to vo entities
    public List<VoOutUser> queryJoinTable(String id) {
        String sql =
                "select \n"
                        + "us.username,\n"
                        + "us.id as user_id,\n"
                        + "dept.dept_name as dept_name\n"
                        + "from \n"
                        + "t_user us \n"
                        + "left join t_dept dept on dept.dept_id= us.dept_id";
        // if exception,pls see the error log msg.
        return db.findQuery(sql).beginWhere("1=1").andWhere("us.id=?", id).
                find().toBList(VoOutUser.class, true); // note: If execute success will query data recode,or not ,will return empty recode list
    }


}
