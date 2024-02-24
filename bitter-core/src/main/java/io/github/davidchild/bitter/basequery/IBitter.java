package io.github.davidchild.bitter.basequery;

import io.github.davidchild.bitter.parbag.ExecuteParBag;

interface  IBitter<OP extends  BaseQuery> {
    public default ExecuteParBag  getQueryBag(){
         ExecuteParBag bag = ((OP)this).getExecuteParBag();
          return   bag ;
     }
}
