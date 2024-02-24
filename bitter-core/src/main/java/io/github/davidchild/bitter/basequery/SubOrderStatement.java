package io.github.davidchild.bitter.basequery;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.op.FieldFunction;
import lombok.Data;

@Data
public class SubOrderStatement {

    private String statement = "";

    private BaseQuery bq;

    private String field;

    private String op;

    private  SubOrderStatementEnum subOrderStatementEnum;


    public SubOrderStatement(BaseQuery bq) {
        this.setBq(bq);
    }

    public SubOrderStatement thenAsc(String field) {
        toField(field);
        return join(SubOrderStatementEnum.orderAsc);
    }

    public SubOrderStatement thenDesc(String field) {
        toField(field);
        return join(SubOrderStatementEnum.orderDesc);
    }


    public SubOrderStatement custom(String order) {
        this.setStatement(order);
        return  join(SubOrderStatementEnum.custom);
    }


    public <T extends BaseModel> SubOrderStatement thenAsc(FieldFunction<T> field) {
        toField(field);
        return join(SubOrderStatementEnum.orderAsc);
    }

    public <T extends BaseModel> SubOrderStatement thenDesc(FieldFunction<T> field) {
        toField(field);
        return join(SubOrderStatementEnum.orderDesc);
    }

    private SubOrderStatement join(SubOrderStatementEnum subOrderStatementEnum) {
        this.setSubOrderStatementEnum(subOrderStatementEnum);
        return this;
    }

    private SubOrderStatement toField(String field) {
        this.setField(field);
        return this;
    }

    private  <T extends BaseModel> SubOrderStatement toField(FieldFunction<T> field) {
        String filedName = this.getBq().getExecuteParBag().getDbFieldByInnerClassFiled(field);
        return toField(filedName);
    }
}
