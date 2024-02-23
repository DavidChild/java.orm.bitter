package io.github.davidchild.bitter.op.find;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.ExecuteEnum;
import io.github.davidchild.bitter.basequery.SubStatement;
import io.github.davidchild.bitter.basequery.WhereQuery;
import io.github.davidchild.bitter.datatable.BList;
import io.github.davidchild.bitter.datatable.DataTable;
import io.github.davidchild.bitter.exception.DbOrConvertException;
import io.github.davidchild.bitter.op.FieldFunction;
import io.github.davidchild.bitter.parbag.*;
import io.github.davidchild.bitter.parse.BitterPredicate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindQuery<T extends BaseModel> extends WhereQuery<T> {

    public FindQuery(Class<T> clazz) {
        executeParBag = new ExecuteParBagSelect();
        executeParBag.setExecuteEnum(ExecuteEnum.Select);
        ((ExecuteParBagSelect) executeParBag).selectColumns = new ArrayList<String>();
        ((ExecuteParBagSelect) executeParBag).orders = new ArrayList<>();
        executeParBag.setType(clazz);
    }

    @SafeVarargs
    public final FindQuery<T> select(FieldFunction<T>... columns) {
        if (columns != null && columns.length > 0) {
            Arrays.stream(columns).forEach(item -> ((ExecuteParBagSelect) executeParBag).selectColumns
                    .add(executeParBag.getDbFieldByInnerClassFiled(item)));
        }
        return this;
    }

    public FindQuery<T> thenDesc(FieldFunction<T> innerNameFn) {
        ((ExecuteParBagSelect) executeParBag).orders
                .add(new OrderPair(OrderBy.DESC, executeParBag.getDbFieldByInnerClassFiled(innerNameFn)));
        return this;
    }

    public FindQuery<T> thenAsc(FieldFunction<T> innerNameFn) {
        ((ExecuteParBagSelect) executeParBag).orders
                .add(new OrderPair(OrderBy.ASC, executeParBag.getDbFieldByInnerClassFiled(innerNameFn)));
        return this;
    }

    public FindQuery<T> where(BitterPredicate<T> condition) {
        super.Where(condition);
        ((ExecuteParBagSelect) executeParBag).condition = this.getCondition();
        return this;
    }

    public FindQuery<T> where(SubStatement subStatementCondition) {
        super.Where(subStatementCondition);
        ((ExecuteParBagSelect)executeParBag).subStatementCondition = this.getStatementCondition();
        return this;
    }

    public FindQuery<T> setSize(Integer size) {
        if (size == 0) {
            size = 1;
        }
        ((ExecuteParBagSelect) executeParBag).topSize = size;
        return this;
    }

    public int findCount() throws SQLException {
        ExecuteParBagCount countBag = new ExecuteParBagCount();
        countBag.condition = (List<BitterPredicate<T>>) ((ExecuteParBagSelect) executeParBag).condition;
        countBag.setExecuteEnum(ExecuteEnum.Count);
        countBag.setType(executeParBag.getType());
        this.executeParBag = countBag;

        DataTable objectMap = this.getData();
        if (objectMap != null && objectMap.size() > 0) {
            Object count = objectMap.get(0).values().stream().findFirst().orElse(null);
            if (count == null)
                return 0;
            return Integer.parseInt(count.toString());
        }
        return 0;
    }

    public BList<T> find() {
        try {
            List<T> list = this.getDataList();
            return new BList(this.getExecuteParBag().getType(), list);
        } catch (Exception ex) {
            throw new DbOrConvertException(ex.getMessage());
        }

    }

}
