package io.github.davidchild.bitter.parbag;

import io.github.davidchild.bitter.basequery.SubUpdateColumnStatement;

import java.util.List;

interface IBagUpdateBase extends  IBagBase {
    public List<SubUpdateColumnStatement> getUpdateColumnContain();
    public default Boolean CheckedHaveColumn(){
        List<SubUpdateColumnStatement> container = this.getUpdateColumnContain();
        if(container != null && container.size()>0){
            return  true;
        }else {
            return false;
        }
    }

}
