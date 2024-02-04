package io.github.davidchild.bitter.impl;

import io.github.davidchild.bitter.op.delete.DeleteIns;
import io.github.davidchild.bitter.op.insert.Insert;
import io.github.davidchild.bitter.op.update.UpdateIns;

public interface IBaseModelOp {

    boolean save();

    Insert insert();

    UpdateIns update();

    DeleteIns delete();

    // Judge whether the primary key value exists. If the data is instantiated from the database, the primary key value
    // must exist
    boolean hasKeyValue();

}
