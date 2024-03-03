package io.github.davidchild.bitter.basequery;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.op.FieldFunction;
import io.github.davidchild.bitter.parbag.IBagColumn;

import java.util.Arrays;

public interface IColumnQuery<OP extends  BaseQuery,T extends BaseModel>  {
    public default OP select(FieldFunction<T>... columns) {
       IBagColumn column = ((IBagColumn)((OP)(this)).getSingleQuery().getBagOp());
        if (columns != null && columns.length > 0) {
            Arrays.stream(columns).forEach(item ->{
                SubColumnStatement subColumnStatement = new SubColumnStatement();
                String filedName = column.getDbFiled(item);
                subColumnStatement.column(filedName);
                column.getColumnContain().add(subColumnStatement);
            });
        }
        return (OP)this;
    }
}
