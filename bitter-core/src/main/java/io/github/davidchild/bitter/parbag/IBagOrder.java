package io.github.davidchild.bitter.parbag;

import io.github.davidchild.bitter.basequery.SubOrderStatement;

import java.util.List;

public interface IBagOrder extends IBagOp {
    public List<SubOrderStatement> getOrderContain();
    public default Boolean CheckedHaveOrder(){
        List<SubOrderStatement> container = this.getOrderContain();
        if(container != null && container.size()>0){
            return  true;
        }else {
            return false;
        }
    }
}
