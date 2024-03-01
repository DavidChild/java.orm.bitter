package io.github.davidchild.bitter.parbag;

import io.github.davidchild.bitter.basequery.ExecuteEnum;

public class ExecuteParBagInsert extends ExecuteParBag implements  IBagOp {


    public ExecuteParBagInsert(){
        this.setExecuteEnum(ExecuteEnum.Insert);
    }
    @Override
    public ExecuteParBag getParBag() {
        return  this;
    }

}
