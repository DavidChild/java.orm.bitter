package io.github.davidchild.bitter.op.find;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.*;
import io.github.davidchild.bitter.datatable.BList;
import io.github.davidchild.bitter.datatable.DataTable;
import io.github.davidchild.bitter.exception.DbOrConvertException;
import io.github.davidchild.bitter.parbag.ExecuteParBagCount;
import io.github.davidchild.bitter.parbag.ExecuteParBagSelect;

import java.sql.SQLException;
import java.util.List;

public class FindQuery<T extends BaseModel> extends DqlQuery implements IWhereQuery<FindQuery<T>,T>, IColumnQuery<FindQuery<T>,T>, IOrderQuery<FindQuery<T>,T> {

    public FindQuery(Class<T> clazz) {
        setExecuteParBag(new ExecuteParBagSelect());
        getExecuteParBag().setExecuteEnum(ExecuteEnum.Select);
        getExecuteParBag().setType(clazz);
    }
    public FindQuery<T> setSize(Integer size) {
        if (size == 0) {
            size = 1;
        }
        ((ExecuteParBagSelect) getExecuteParBag()).setTopSize(size);
        return this;
    }

    public int findCount() throws SQLException {
        ExecuteParBagCount countBag = new ExecuteParBagCount();
        countBag.setWhereCondition(((ExecuteParBagSelect) getExecuteParBag()).getWhereCondition());
        countBag.setExecuteEnum(ExecuteEnum.Count);
        countBag.setType(getExecuteParBag().getType());
        this.setExecuteParBag(countBag);
        DataTable objectMap = this.getData();
        if (objectMap != null && objectMap.size() > 0) {
            Object count = objectMap.get(0).values().stream().findFirst().orElse(null);
            if (count == null)
                return 0;
            return Integer.parseInt(count.toString());
        }
        return 0;
    }

    public BList<T> find() {
        try {
            List<T> list = this.getDataList();
            return new BList(this.getExecuteParBag().getType(), list);
        } catch (Exception ex) {
            throw new DbOrConvertException(ex.getMessage());
        }

    }

}
