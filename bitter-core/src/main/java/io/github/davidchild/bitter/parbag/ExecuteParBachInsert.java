package io.github.davidchild.bitter.parbag;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.ExecuteEnum;
import io.github.davidchild.bitter.dbtype.DataValue;
import io.github.davidchild.bitter.op.insert.Insert;

import java.util.ArrayList;
import java.util.List;

public class ExecuteParBachInsert<T extends BaseModel> extends ExecuteParBag implements  IBachInsertBag  {
    public ExecuteParBachInsert(){
        this.setExecuteEnum(ExecuteEnum.BachInsert);
    }

    List<Insert<T>> list = new ArrayList<>();

    public List<Insert<T>> getList() {
        return list;
    }

    public void setList(List<Insert<T>> list) {
        this.list = list;
    }

    @Override
    public List<List<DataValue>> getBachData() {
        List<List<DataValue>> data_values = new ArrayList<>();
        if(this.list != null&& this.list.size() >0){
            this.list.forEach(item->{
                data_values.add(item.getSingleQuery().getBagOp().getProperties());
            });
        }
        return  data_values;
    }

    @Override
    public String getTableName() {
        if(this.getList().size() < 1){
            return  "";
        }
        return this.getList().get(0).getSingleQuery().getBagOp().getTableName();
    }

    @Override
    public ExecuteParBag getParBag() {
        return this;
    }
}
