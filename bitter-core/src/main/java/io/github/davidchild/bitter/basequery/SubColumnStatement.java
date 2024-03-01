package io.github.davidchild.bitter.basequery;

import lombok.Data;

@Data
public class SubColumnStatement {

    private String columnName = "";

    public SubColumnStatement column(String field) {
      return   toField(field);
    }

    private SubColumnStatement toField(String field) {
        this.setColumnName(field);
        return this;
    }
}
