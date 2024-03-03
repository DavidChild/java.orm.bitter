package io.github.davidchild.bitter.op.insert;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.DmlQuery;
import io.github.davidchild.bitter.basequery.SingleRunner;
import io.github.davidchild.bitter.dbtype.DataValue;
import io.github.davidchild.bitter.exception.DbException;
import io.github.davidchild.bitter.parbag.ExecuteParBagInsert;
import io.github.davidchild.bitter.tools.CoreStringUtils;
import io.github.davidchild.bitter.tools.CoreUtils;

import java.util.List;

public class Insert<T extends BaseModel> extends DmlQuery {
    ExecuteParBagInsert  insertBag = new ExecuteParBagInsert();

    public Insert(T data) throws Exception {
        SingleRunner runner = new SingleRunner();

        this.setQuery(runner);
        insertBag.setData(data);
        runner.setBagOp(insertBag);
        this.setQuery(runner);
        checkedIdentityValue();
    }

    public Insert(T data, boolean isOutIdentity)  {
        SingleRunner runner = new SingleRunner();
        insertBag.setData(data);
        runner.setBagOp(insertBag);
        this.setQuery(runner);
        checkedIdentityValue();
    }

    public Insert<T> SetTableName(String tableName) {
        insertBag.setTableName(tableName);
        return this;
    }

    public void addInBachInsertPool(List<Insert<T>> insertList) {
        insertList.add(this);
    }

    private void checkedIdentityValue() {
        T data = (T) insertBag.getData();
        DataValue key = CoreUtils.getTypeKey(data.getClass(), data);
        if (key != null && insertBag.getIsOutIdentity() && CoreStringUtils.isNotNull(key.getValue())) {
            throw new DbException(
                    "bitter：insert error :violation of primary key self growth constraint: the insert statement has a primary key self growth ID, and cannot have a self growth value when inserting");
        }
    }

}
