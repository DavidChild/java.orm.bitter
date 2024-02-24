package io.github.davidchild.bitter.tools;

import io.github.davidchild.bitter.basequery.BaseQuery;

import java.util.UUID;

public class StatementHandeUtil {
    public static void setParamInBagContainer(BaseQuery query, Object value){
          query.getExecuteParBag().getDynamics().put(UUID.randomUUID().toString(),value);
    }
}
