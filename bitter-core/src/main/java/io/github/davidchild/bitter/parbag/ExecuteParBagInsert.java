package io.github.davidchild.bitter.parbag;

import com.baomidou.mybatisplus.annotation.IdType;

public class ExecuteParBagInsert extends ExecuteParBag {

    public boolean isOutIdentity() {
        if (getKeyInfo() != null && this.getKeyInfo().getDbIdType() == IdType.AUTO)
            return true;
        else
            return false;
    }

}
