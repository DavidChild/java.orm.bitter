package io.github.davidchild.bitter.bitterlist;

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
        try {
            if (this.size() <= 0) {
                return (OT)myType.newInstance();
            } else {
                return this.get(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
