package io.github.davidchild.bitter.samples.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.davidchild.bitter.db.db;
import io.github.davidchild.bitter.op.insert.Insert;
import io.github.davidchild.bitter.samples.business.entity.TStudent;
import io.github.davidchild.bitter.samples.business.mapper.TStudentMapper;
import io.github.davidchild.bitter.samples.business.service.ITStudentService;
import org.springframework.stereotype.Service;

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
        return entity.insert().submit();
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
        return db.findQuery(TStudent.class).where(f -> f.getName().contains(name)).find().fistOrDefault(); // fistOrDefault 数据库没有也会返回一条空对象,在接下来的使用中,避免业务性空指针
    }

    @Override
    public List<TStudent> queryList(String name) {
        return db.findQuery(TStudent.class).where(f -> f.getName().contains(name)).setSize(3).thenDesc(TStudent::getId).thenAsc(TStudent::getName).find();
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
        if (student.haveKeyValue()) // haveKeyValue 用来判断此数据库对象的主键字段是否存在有值
        {
            return student.delete().submit() > -1;
        }
        return true;
    }
}
