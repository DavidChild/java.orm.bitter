package io.github.davidchild.bitter.basequery;

import java.util.UUID;

public class StatementHandeUtil {
    public static void setParamInBagContainer(BaseQuery query, Object value){
          query.getExecuteParBag().getDynamics().put(UUID.randomUUID().toString(),value);
    }
}
