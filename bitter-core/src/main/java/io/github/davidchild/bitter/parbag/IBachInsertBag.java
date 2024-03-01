package io.github.davidchild.bitter.parbag;

import io.github.davidchild.bitter.dbtype.DataValue;

import java.util.List;

public  interface  IBachInsertBag extends  IBagOp {
    public List<List<DataValue>>  getBachData();
    public  String getTableName();
}
