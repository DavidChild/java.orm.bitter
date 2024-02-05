package io.github.davidchild.bitter.test.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.davidchild.bitter.test.business.entity.TStudent;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author davidChild
 * @since 2022-12-19
 */
public interface ITStudentService extends IService<TStudent> {

    Long insert(TStudent entity);

    TStudent singleQuery(String name);

    List<TStudent> queryList(String name);

    TStudent queryById(Long id);

    Long deleteById(Long id);
}
