package io.github.davidchild.bitter.test.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.davidchild.bitter.db.db;
import io.github.davidchild.bitter.test.business.entity.TDept;
import io.github.davidchild.bitter.test.business.mapper.TDeptMapper;
import io.github.davidchild.bitter.test.business.service.ITDeptService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author davidChild
 * @since 2022-12-17
 */
@Service
public class TDeptServiceImpl extends ServiceImpl<TDeptMapper, TDept> implements ITDeptService {

    @Override
    public boolean save(TDept entity) {
        return entity.save();
    }

    public boolean update(Long id) {
        return db.update(TDept.class).setColum(TDept::getDeptName, 2).setColum(TDept::getOrderNum, 4).where(f -> f.getDeptId() == id).submit() > -1;
    }

    public boolean updateModel(Long id) {
        TDept dept = db.findQuery(TDept.class).where(f -> f.getDeptId() == id).find().fistOrDefault(); // fistOrDefault 数据库没有也会返回一条空对象,在接下来的使用中,避免业务性空指针
        if (dept.hasKeyValue()) // haveKeyValue 用来判断此数据库对象的主键字段是否存在有值{
            dept.setDeptName("change the dept name");
        dept.setCreateTime(new Date());
        return dept.update().submit() > -1;
    }
}

