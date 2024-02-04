package io.github.davidchild.bitter;

import io.github.davidchild.bitter.dbtype.DataValue;
import io.github.davidchild.bitter.impl.IBaseModelOp;
import io.github.davidchild.bitter.op.delete.DeleteIns;
import io.github.davidchild.bitter.op.insert.Insert;
import io.github.davidchild.bitter.op.update.UpdateIns;
import io.github.davidchild.bitter.tools.CoreStringUtils;
import io.github.davidchild.bitter.tools.CoreUtils;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
public class BaseModel implements Serializable, IBaseModelOp {

    @Override
    public Insert insert() {
        return new Insert(this, true);
    }

    @Override
    public UpdateIns update() {
        return new UpdateIns(this);
    }

    @Override
    public DeleteIns delete() {
        return new DeleteIns(this);
    }

    @Override
    public boolean save() {
        if (!this.hasKeyValue()) {
            return insert().submit() >= 0;
        } else {
            return this.update().submit() > 0;
        }
    }

    @Override
    public boolean hasKeyValue() {
        DataValue key = CoreUtils.getTypeKey(this.getClass(), this);
        if (key != null && CoreStringUtils.isNotNull(key.getValue())) {
            return true;
        } else {
            return false;
        }
    }

}
