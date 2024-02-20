package io.github.davidchild.bitter.datatable;

import io.github.davidchild.bitter.exception.CreateDefaultInstanceException;

import java.util.ArrayList;
import java.util.List;

public class BList<OT> extends ArrayList<OT> {

    private final Class<?> myType;

    public BList(Class<OT> type, List<OT> data) {
        this.myType = type;
        if (data != null && data.size() > 0) {
            this.addAll(data);
        }
    }

    public BList(Class<?> type) {
        myType = type;
    }

    public OT fistOrDefault() {
        OT result;
        try {
            result = (OT) myType.newInstance();
            if (this.size() <= 0) {
                return result;

            } else {
                result = null;
                return this.get(0);
            }
        } catch (Exception ex) {
            throw new CreateDefaultInstanceException("the type:" + myType.getTypeName() + " is not exist   @NoArgsConstructor annotation; You can add  the annotations: @NoArgsConstructor," + " to this class");
        }
    }


}
