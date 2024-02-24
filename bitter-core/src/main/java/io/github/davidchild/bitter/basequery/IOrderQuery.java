package io.github.davidchild.bitter.basequery;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.op.FieldFunction;
import io.github.davidchild.bitter.parbag.IBagOrder;

public interface  IOrderQuery<OP extends BaseQuery,T extends BaseModel>  {
    public default OP orderBy(String order) {
        IBagOrder iBagOrder =  ((IBagOrder)((OP)this).getExecuteParBag());
        SubOrderStatement subOrderStatement = ((OP)this).createOrderStatement();
        subOrderStatement.setSubOrderStatementEnum(SubOrderStatementEnum.custom);
        subOrderStatement.custom(order);
        iBagOrder.getOrderContain().add(subOrderStatement);
        return (OP)this;
    }

    public default OP thenAsc(String filedName) {
        IBagOrder iBagOrder = ((IBagOrder)((OP)this).getExecuteParBag());
        SubOrderStatement subOrderStatement = ((OP)this).createOrderStatement();
        subOrderStatement.setSubOrderStatementEnum(SubOrderStatementEnum.custom);
        subOrderStatement.thenAsc(filedName);
        iBagOrder.getOrderContain().add(subOrderStatement);
        return (OP)this;
    }

    public default OP thenDesc(String filedName) {
        IBagOrder iBagOrder = ((IBagOrder)((OP)this).getExecuteParBag());
        SubOrderStatement subOrderStatement = ((OP)this).createOrderStatement();
        subOrderStatement.setSubOrderStatementEnum(SubOrderStatementEnum.custom);
        subOrderStatement.thenDesc(filedName);
        iBagOrder.getOrderContain().add(subOrderStatement);
        return (OP)this;
    }


    public  default OP thenAsc(FieldFunction<T> filed) {
        IBagOrder iBagOrder = ((IBagOrder)((OP)this).getExecuteParBag());
        SubOrderStatement subOrderStatement = ((OP)this).createOrderStatement();
        subOrderStatement.setSubOrderStatementEnum(SubOrderStatementEnum.custom);
        subOrderStatement.thenAsc(filed);
        iBagOrder.getOrderContain().add(subOrderStatement);
        return (OP)this;
    }

    public default  OP thenDesc(FieldFunction<T> filed) {
        IBagOrder iBagOrder = ((IBagOrder)((OP)this).getExecuteParBag());
        SubOrderStatement subOrderStatement = ((OP)this).createOrderStatement();
        subOrderStatement.setSubOrderStatementEnum(SubOrderStatementEnum.custom);
        subOrderStatement.thenDesc(filed);
        iBagOrder.getOrderContain().add(subOrderStatement);
        return (OP)this;
    }
}
