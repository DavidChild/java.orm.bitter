package io.github.davidchild.bitter.samples.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.davidchild.bitter.db.db;
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
}
