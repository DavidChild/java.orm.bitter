package io.github.davidchild.bitter.basequery;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.parbag.ITopSizeBag;

public interface ITopSizeQuery<OP extends BaseQuery,T extends BaseModel>  {
    public default OP setSize(int topSize) {
        ITopSizeBag topSizeBag =  ((ITopSizeBag)((OP)this).getSingleQuery().getBagOp());
        topSizeBag.setTopSize(topSize);
        return (OP)this;
    }

}
