package io.github.davidchild.bitter.op.insert;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.DmlQuery;
import io.github.davidchild.bitter.basequery.ExecuteEnum;
import io.github.davidchild.bitter.dbtype.DataValue;
import io.github.davidchild.bitter.exception.DbException;
import io.github.davidchild.bitter.parbag.ExecuteParBagInsert;
import io.github.davidchild.bitter.tools.CoreStringUtils;
import io.github.davidchild.bitter.tools.CoreUtils;

import java.util.List;

public class Insert<T extends BaseModel> extends DmlQuery {

    public Insert(T data) throws Exception {
        this.setExecuteParBag(new ExecuteParBagInsert());
        getExecuteParBag().setExecuteEnum(ExecuteEnum.Insert);
        getExecuteParBag().setData(data);
        checkedIdentityValue();
    }

    public Insert(T data, boolean isOutIdentity)  {
        this.setExecuteParBag(new ExecuteParBagInsert());
        getExecuteParBag().setExecuteEnum(ExecuteEnum.Insert);
        getExecuteParBag().setData(data);
        checkedIdentityValue();
    }

    public Insert<T> SetTableName(String tableName) {
        getExecuteParBag().setTableName(tableName);
        return this;
    }

    public void addInBachInsertPool(List<Insert<T>> insertList) {
        insertList.add(this);
    }

    private void checkedIdentityValue() {
        T data = (T) getExecuteParBag().getData();
        DataValue key = CoreUtils.getTypeKey(data.getClass(), data);
        if (key != null && ((ExecuteParBagInsert) getExecuteParBag()).isOutIdentity() && CoreStringUtils.isNotNull(key.getValue())) {
            throw new DbException(
                    "bitter：insert error :violation of primary key self growth constraint: the insert statement has a primary key self growth ID, and cannot have a self growth value when inserting");
        }
    }

}
