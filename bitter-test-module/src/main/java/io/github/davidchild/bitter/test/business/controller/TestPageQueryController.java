package io.github.davidchild.bitter.test.business.controller;

import io.github.davidchild.bitter.basequery.SubStatementEnum;
import io.github.davidchild.bitter.bitterlist.BMap;
import io.github.davidchild.bitter.op.page.PageQuery;
import io.github.davidchild.bitter.test.business.entity.TStudent;
import io.github.davidchild.bitter.test.business.vo.VoInUser;
import io.github.davidchild.bitter.test.business.vo.VoOutUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/business/t-page-query")
public class TestPageQueryController {
    @GetMapping("/query-data")
    public List<VoOutUser> queryData(@RequestParam VoInUser vo) {
        String sql = "select \n"
                + "us.username,\n"
                + "us.id as user_id,\n"
                + "dept.dept_name as dept_name\n"
                + " from \n" //-- important:  if sql set into the PageQuery object, the  'form'  in SQL statements must contain a space char before it,as this
                + "t_user us \n"
                + "left join t_dept dept on dept.dept_id= us.dept_id";
        // condition
        PageQuery page = new PageQuery(sql);
        page.where("1=1");
        page.where("dept_id>?", vo.getDeptId());
        page.where("create_time>?", vo.getDate());

        // page.whereNotBlank("name like concat('%',?,'%')", vo.getName());  // name like '%xx%' 匹配
        page.whereNotBlank(page.createSubStatement().toField("us.name").join(SubStatementEnum.ALLLIKE, null, vo.getName())); // name like '%xx%' 匹配
        page.whereNotBlank(page.createSubStatement().toField("us.dept_id").join(SubStatementEnum.IN, null, vo.getDeptIds())); // dept_id in (1,2,3)
        page.whereNotNull("age = ?", vo.getAge());
        //page.where("IFNULL(us.username,'') = ?", "zhangSan");

        // order by
        page.thenASC("us.username");
        page.thenDESC("us.create_time");

        // set the page number and page size
        page.skip(1).take(10);
        BMap data = page.getData();  // get the data
        Integer count = page.getCount(); // get the data recode
        return data.toBList(VoOutUser.class); // convert to the target class collection

    }

    @GetMapping("/query-list")
    public List<TStudent> queryList(String name) {
        List<String> list = new ArrayList<>();
        list.add("david");

        List<String> list2 = new ArrayList<>();
        list2.add("david");
        list2.add("hjb");

        List<String> list3 = new ArrayList<>();
        PageQuery pq = new PageQuery("select * from t_student");
        pq.whereNotBlank(pq.createSubStatement().toField("name").join(SubStatementEnum.ALLLIKE, "", "hjb")); //ok
        pq.whereNotBlank(pq.createSubStatement().toField("name").join(SubStatementEnum.PRELIKE, "", "hjb2")); //ok
        pq.whereNotBlank(pq.createSubStatement().toField("name").join(SubStatementEnum.ENDLIKE, "", "hjb3")); //ok
        pq.whereNotBlank(pq.createSubStatement().toField("name").join(SubStatementEnum.PRELIKE, "xxh", new String())); //ok
        pq.whereNotBlank(pq.createSubStatement().toField("name").join(SubStatementEnum.PRELIKE, null, new String()));
        pq.whereNotBlank(pq.createSubStatement().toField("name").join(SubStatementEnum.IN, "", list)); //ok
        pq.whereNotBlank(pq.createSubStatement().toField("name").join(SubStatementEnum.IN, "", list)); //ok
        pq.whereNotBlank(pq.createSubStatement().toField("name").join(SubStatementEnum.IN, "", list2)); //ok
        pq.whereNotBlank(pq.createSubStatement().toField("name").join(SubStatementEnum.IN, "", list3)); //ok
        pq.whereNotBlank(pq.createSubStatement().toField("name").join(SubStatementEnum.IN, "", new String())); //ok
        pq.whereNotBlank(pq.createSubStatement().toField("name").join(SubStatementEnum.IN, "david", new String())); //ok
        pq.whereNotBlank(pq.createSubStatement().toField("name").join(SubStatementEnum.IN, null, list3)); //ok
        pq.skip(1).take(10);
        return pq.getData().toBList(TStudent.class);

    }
}
