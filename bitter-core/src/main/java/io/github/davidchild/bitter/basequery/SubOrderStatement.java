package io.github.davidchild.bitter.basequery;

import lombok.Data;

@Data
public class SubOrderStatement {

    private String statement = "";


    private String field;

    private String op;

    private  SubOrderStatementEnum subOrderStatementEnum;



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



    private SubOrderStatement join(SubOrderStatementEnum subOrderStatementEnum) {
        this.setSubOrderStatementEnum(subOrderStatementEnum);
        return this;
    }

    private SubOrderStatement toField(String field) {
        this.setField(field);
        return this;
    }


}
