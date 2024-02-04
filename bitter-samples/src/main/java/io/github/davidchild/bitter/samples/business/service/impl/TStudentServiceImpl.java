package io.github.davidchild.bitter.samples.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.davidchild.bitter.basequery.SubStatementEnum;
import io.github.davidchild.bitter.db.db;
import io.github.davidchild.bitter.op.insert.Insert;
import io.github.davidchild.bitter.op.page.PageQuery;
import io.github.davidchild.bitter.samples.business.entity.TStudent;
import io.github.davidchild.bitter.samples.business.mapper.TStudentMapper;
import io.github.davidchild.bitter.samples.business.service.ITStudentService;
import lombok.var;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author davidChild
 * @since 2022-12-19
 */
@Service
public class TStudentServiceImpl extends ServiceImpl<TStudentMapper, TStudent> implements ITStudentService {
    @Override
    public Long insert(TStudent entity) {
        bachInsert(10);
        return  1L;
    }


    public boolean bachInsert(int bachNumber) {

        // you can insert bach data  by this way as below: submit all new  student objects into the db by transaction
        return db.bachInsert().doBachInsert((list) -> {
            for (int i = 0; i < bachNumber; i++) {
                TStudent studentInfo = new TStudent();
                studentInfo.setName("hjb" + i);
                studentInfo.insert().addInBachInsertPool((List<Insert<TStudent>>) list);
            }
        }).submit() > -1; //error will print to the log by slf4

    }

    @Override
    public TStudent singleQuery(String name) {
       // var query = db.findQuery("select * from dipin_release").find();
        var student = db.findQuery(TStudent.class).where(f -> f.getName().contains(name)).find().fistOrDefault(); // fistOrDefault 数据库没有也会返回一条空对象,在接下来的使用中,避免业务性空指针
       return  student;
    }


    @Override
    public List<TStudent> queryList(String name) {
        List<String> list = new ArrayList<>();
        list.add("david");

        List<String> list2 = new ArrayList<>();
        list2.add("david");
        list2.add("hjb");

        List<String> list3 = new ArrayList<>();
        // db.findQuery(TStudent.class).where(f -> f.getName().contains(name)).setSize(3).thenDesc(TStudent::getId).thenAsc(TStudent::getName).find();
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
        pq.skip(1).take(1).getData();
        return null;
    }

    @Override
    public TStudent queryById(Long id) {
        return db.findQuery(TStudent.class).where(f -> f.getId() == id).find().fistOrDefault(); // fistOrDefault 数据库没有也会返回一条空对象,在接下来的使用中,避免业务性空指针
    }

    @Override
    public Long deleteById(Long id) {
        return db.delete(TStudent.class).where(f -> f.getId() == id).submit();
    }

    public boolean deleteByModel(Long id) {
        TStudent student = db.findQuery(TStudent.class).where(f -> f.getId() == id).find().fistOrDefault(); // fistOrDefault 数据库没有也会返回一条空对象,在接下来的使用中,避免业务性空指针
        if (student.hasKeyValue()) // haveKeyValue 用来判断此数据库对象的主键字段是否存在有值
        {
            return student.delete().submit() > -1;
        }
        return true;
    }
}
