package io.github.davidchild.bitter.samples.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.davidchild.bitter.samples.business.entity.TDept;
import io.github.davidchild.bitter.samples.business.mapper.TDeptMapper;
import io.github.davidchild.bitter.samples.business.service.ITDeptService;
import org.springframework.stereotype.Service;

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


}
