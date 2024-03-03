package io.github.davidchild.bitter.basequery;

import io.github.davidchild.bitter.parbag.IBagOp;

public class SingleRunner implements ISingleQuery {

    private  IBagOp bagOp;

    @Override
    public IBagOp getBagOp() {
        return this.bagOp;
    }

    @Override
    public void setBagOp(IBagOp bagOp) {
        this.bagOp = bagOp;
    }

}
