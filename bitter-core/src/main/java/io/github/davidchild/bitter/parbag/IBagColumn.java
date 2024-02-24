package io.github.davidchild.bitter.parbag;

import io.github.davidchild.bitter.basequery.SubColumnStatement;

import java.util.List;

public interface IBagColumn {
    public List<SubColumnStatement> getColumnContain();
    public default Boolean CheckedHaveColumn(){
        List<SubColumnStatement> container = this.getColumnContain();
        if(container != null && container.size()>0){
            return  true;
        }else {
            return false;
        }
    }
}
