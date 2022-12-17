package io.github.davidchild.bitter.op.datasource;

import io.github.davidchild.bitter.db.DoResult;
import io.github.davidchild.bitter.op.annotation.DataSourceType;
import io.github.davidchild.bitter.tools.CoreStringUtils;

import java.util.function.Function;

public class TargetAction {

    public DoResult DoSet(DataSourceType targetDb, Function<?, ?> fn) {
        DoResult ret = new DoResult();
        try {
            if (CoreStringUtils.isNotNull(targetDb)) {
                ThreadLocalForDataSource.setDataSourceType(targetDb.name());
            }
            ret.value = fn.apply(null);
            ret.nonException = true;

        } catch (Exception e) {

            ret.nonException = false;
            ret.error = e;
            ret.msg = e.getMessage();

        } finally {
            ThreadLocalForDataSource.clearDataSourceType();
        }
        return ret;
    }

    public DoResult DoSet(String targetDb, Function<?, ?> fn) {
        DoResult ret = new DoResult();
        try {
            if (CoreStringUtils.isNotNull(targetDb)) {
                ThreadLocalForDataSource.setDataSourceType(targetDb);
            }
            ret.value = fn.apply(null);
            ret.nonException = true;

        } catch (Exception e) {
            ret.nonException = false;
            ret.error = e;
            ret.msg = e.getMessage();

        } finally {
            ThreadLocalForDataSource.clearDataSourceType();
        }
        return ret;
    }

}
