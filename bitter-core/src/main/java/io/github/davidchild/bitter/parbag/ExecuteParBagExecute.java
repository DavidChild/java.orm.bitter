package io.github.davidchild.bitter.parbag;

import lombok.Data;

@Data
public class ExecuteParBagExecute extends ExecuteParBag implements  ICommandBag {
    private String commandText;

    @Override
    public ExecuteParBag getParBag() {
       return  this;
    }
}
