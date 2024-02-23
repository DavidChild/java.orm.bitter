package io.github.davidchild.bitter.op.find;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.*;
import io.github.davidchild.bitter.datatable.BList;
import io.github.davidchild.bitter.datatable.DataTable;
import io.github.davidchild.bitter.exception.DbOrConvertException;
import io.github.davidchild.bitter.parbag.ExecuteParBagCount;
import io.github.davidchild.bitter.parbag.ExecuteParBagSelect;

import java.util.List;

public class FindQuery<T extends BaseModel> extends DqlQuery implements IWhereQuery<FindQuery<T>,T>, IColumnQuery<FindQuery<T>,T>, IOrderQuery<FindQuery<T>,T>,ITopSizeQuery<FindQuery<T>,T> {



    public FindQuery(Class<T> clazz) {
        ExecuteParBagSelect  selectBag = new ExecuteParBagSelect();
        SingleRunner singleRunner = new SingleRunner();
        selectBag.setExecuteEnum(ExecuteEnum.Select);
        selectBag.setModelType(clazz);
        singleRunner.setBagOp(selectBag);
        this.setQuery(singleRunner);
    }

    public int findCount()  {
        SingleRunner runner = new SingleRunner();
        ExecuteParBagSelect selectBag = ((ExecuteParBagSelect) this.getSingleQuery().getBagOp().getParBag());
        ExecuteParBagCount countBag = new ExecuteParBagCount();
        countBag.setWhereCondition(selectBag.getWhereCondition());
        countBag.setExecuteEnum(ExecuteEnum.Count);
        countBag.setModelType(selectBag.getModelType());
        runner.setBagOp(countBag);
        this.setQuery(runner);
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
            ExecuteParBagSelect selectBag = ((ExecuteParBagSelect) this.getSingleQuery().getBagOp().getParBag());
            List<T> list = this.getDataList();
            return new BList(selectBag.getModelType(), list);
        } catch (Exception ex) {
            throw new DbOrConvertException(ex.getMessage());
        }

    }

}
