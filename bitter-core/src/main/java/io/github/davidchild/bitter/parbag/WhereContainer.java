package io.github.davidchild.bitter.parbag;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.SubWhereStatement;
import io.github.davidchild.bitter.parse.BitterPredicate;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class  WhereContainer<T extends BaseModel>  {

    private List<SubWhereStatement> SubWhereStatementCondition = new ArrayList<>();

    private List<BitterPredicate<T>> predicateCondition = new ArrayList<>();

}
