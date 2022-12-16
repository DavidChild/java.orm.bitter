package io.github.davidchild.bitter.op.insert;

import java.util.List;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.BaseQuery;
import io.github.davidchild.bitter.basequery.ExecuteEnum;
import io.github.davidchild.bitter.dbtype.KeyInfo;
import io.github.davidchild.bitter.exception.DbException;
import io.github.davidchild.bitter.parbag.ExecuteParBagInsert;
import io.github.davidchild.bitter.tools.CoreStringUtils;
import io.github.davidchild.bitter.tools.CoreUtils;

public class Insert<T extends BaseModel> extends BaseQuery {

    public Insert(T data) {
        this.executeParBag = new ExecuteParBagInsert();
        executeParBag.setExecuteEnum(ExecuteEnum.Insert);
        executeParBag.setData(data);
        checkedIdentityValue();
    }

    public Insert(T data, boolean isOutIdentity) {
        this.executeParBag = new ExecuteParBagInsert();
        executeParBag.setExecuteEnum(ExecuteEnum.Insert);
        executeParBag.setData(data);
        checkedIdentityValue();
    }

    public Insert<T> SetTableName(String tableName) {
        executeParBag.setTableName(tableName);
        return this;
    }

    public void addInBachInsertPool(List<Insert<T>> insertList) {
        insertList.add(this);
    }

    private void checkedIdentityValue() {
        T data = (T)executeParBag.getData();
        KeyInfo key = CoreUtils.getPrimaryField(data.getClass(), data);
        if (((ExecuteParBagInsert)executeParBag).isOutIdentity() && CoreStringUtils.isNotNull(key.getValue())) {
            throw new DbException(
                "bitter： insert error :Violation of primary key self growth constraint: the insert statement has a primary key self growth ID, and cannot have a self growth value when inserting");
        }

    }

}
