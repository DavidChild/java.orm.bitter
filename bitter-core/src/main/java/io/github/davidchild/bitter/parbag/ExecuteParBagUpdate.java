package io.github.davidchild.bitter.parbag;

import io.github.davidchild.bitter.parse.BitterPredicate;

import java.util.ArrayList;
import java.util.List;

public class ExecuteParBagUpdate<T> extends ExecuteParBag {

    public List<BitterPredicate<T>> condition;
    private boolean isReSetValueInUpdatePair = false;
    private List<UpdatePair> updatePairs;

    public ExecuteParBagUpdate() {
        updatePairs = new ArrayList<>();
    }


    public void SetUpdatePair(UpdatePair updatePair) {
        this.updatePairs.add(updatePair);
    }

    public List<UpdatePair> getUpdatePairs() {
        if (updatePairs != null && updatePairs.size() > 0)
            return updatePairs;
        if (isReSetValueInUpdatePair)
            return updatePairs;
        if (this.getData() != null && (!isReSetValueInUpdatePair)) {
            this.getProperties().forEach(field -> {
                if ((!field.isIdentity || !field.isKey)) {
                    UpdatePair k = new UpdatePair(field.getClassInnerFieldName(), field.getValue());
                    k.setDbFieldName(field.getDbFieldName());
                    this.updatePairs.add(k);
                }
            });
            isReSetValueInUpdatePair = true;
        }
        return updatePairs;
    }
}
