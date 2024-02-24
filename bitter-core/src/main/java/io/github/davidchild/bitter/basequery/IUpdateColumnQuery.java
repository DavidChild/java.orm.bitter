package io.github.davidchild.bitter.basequery;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.op.FieldFunction;
import io.github.davidchild.bitter.parbag.IBagUpdateColumn;

public interface IUpdateColumnQuery<OP extends  BaseQuery,T extends BaseModel>  {
    public default OP setColum(FieldFunction<T> filed, Object object) {
        IBagUpdateColumn iBagUpdateColumn = ((IBagUpdateColumn) ((OP) this).getExecuteParBag());
        String filedName = ((OP) this).getExecuteParBag().getDbFieldByInnerClassFiled(filed);
        SubUpdateColumnStatement updateColumnStatement = ((OP) this).createUpdateColumnStatement();
        updateColumnStatement.column(filed, object);
        iBagUpdateColumn.getUpdateColumnContain().add(updateColumnStatement);
        return (OP) this;
    }
    public default OP setColum(String filed, Object object) {
        IBagUpdateColumn iBagUpdateColumn = ((IBagUpdateColumn) ((OP) this).getExecuteParBag());
        SubUpdateColumnStatement updateColumnStatement = ((OP) this).createUpdateColumnStatement();
        updateColumnStatement.column(filed, object);
        iBagUpdateColumn.getUpdateColumnContain().add(updateColumnStatement);
        return (OP) this;
    }

}
