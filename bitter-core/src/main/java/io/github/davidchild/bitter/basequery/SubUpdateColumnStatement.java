package io.github.davidchild.bitter.basequery;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.op.FieldFunction;
import lombok.Data;

@Data
public class SubUpdateColumnStatement {

    private String columnName;
    private Object columnValue;
    private String dbFieldName;
    private boolean waitUpdate;

    private BaseQuery bq;

    public SubUpdateColumnStatement(BaseQuery bq) {
        this.setBq(bq);
    }

    public <T extends BaseModel> SubUpdateColumnStatement column(FieldFunction<T> field,Object value) {
        this.setColumnValue(value);
        this.setWaitUpdate(true);
        return toField(field);


    }
    public <T extends BaseModel> SubUpdateColumnStatement column(String field,Object value) {
        this.setColumnValue(value);
        this.setWaitUpdate(true);
        return toField(field);
    }


    private SubUpdateColumnStatement toField(String field) {
        this.setColumnName(field);
        return this;
    }
    private  <T extends BaseModel> SubUpdateColumnStatement toField(FieldFunction<T> field) {
        String filedName = this.getBq().getExecuteParBag().getDbFieldByInnerClassFiled(field);
        return toField(filedName);
    }
}
