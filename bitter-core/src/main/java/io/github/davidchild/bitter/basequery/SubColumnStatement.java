package io.github.davidchild.bitter.basequery;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.op.FieldFunction;
import lombok.Data;

@Data
public class SubColumnStatement {

    private String columnName = "";

    private BaseQuery bq;

    public SubColumnStatement(BaseQuery bq) {
        this.setBq(bq);
    }


    public <T extends BaseModel> SubColumnStatement column(FieldFunction<T> field) {
       return toField(field);
    }

    public <T extends BaseModel> SubColumnStatement column(String field) {
      return   toField(field);
    }

    private SubColumnStatement toField(String field) {
        this.setColumnName(field);
        return this;
    }
    private  <T extends BaseModel> SubColumnStatement toField(FieldFunction<T> field) {
        String filedName = this.getBq().getExecuteParBag().getDbFieldByInnerClassFiled(field);
        return toField(filedName);
    }
}
