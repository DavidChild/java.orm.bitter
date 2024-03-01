package io.github.davidchild.bitter.basequery;

import io.github.davidchild.bitter.BaseModel;
import lombok.Data;

@Data
public class SubUpdateColumnStatement {

    private String columnName;
    private Object columnValue;
    private String dbFieldName;
    private boolean waitUpdate;



    public <T extends BaseModel> SubUpdateColumnStatement column(String field,Object value) {
        this.setColumnValue(value);
        this.setWaitUpdate(true);
        return toField(field);
    }


    private SubUpdateColumnStatement toField(String field) {
        this.setColumnName(field);
        return this;
    }

}
