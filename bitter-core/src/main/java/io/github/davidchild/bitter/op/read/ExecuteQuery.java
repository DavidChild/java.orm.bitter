package io.github.davidchild.bitter.op.read;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.*;
import io.github.davidchild.bitter.datatable.DataTable;
import io.github.davidchild.bitter.dbtype.DataValue;
import io.github.davidchild.bitter.exception.ModelException;
import io.github.davidchild.bitter.parbag.ExecuteParBagQuerySelect;
import io.github.davidchild.bitter.parbag.IBagWhere;
import io.github.davidchild.bitter.tools.BitterLogUtil;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ExecuteQuery extends DqlQuery implements IWhereQuery<ExecuteQuery,BaseModel>,IOrderQuery<ExecuteQuery,BaseModel>,ITopSizeQuery<ExecuteQuery,BaseModel> {

    private ExecuteParBagQuerySelect selectBag = new ExecuteParBagQuerySelect();

    public ExecuteQuery(String commandText, Object... params) {
        SingleRunner runner = new SingleRunner();
        this.setQuery(runner);

        selectBag.setExecuteEnum(ExecuteEnum.ExecuteQuerySelect);
        selectBag.setCommandText(commandText);
        if (params != null && params.length > 0) {
            Arrays.stream(params).forEach((v) -> {
                selectBag.getDynamics().put(UUID.randomUUID().toString(), v);
            });
        }
        runner.setBagOp(selectBag);
    }



    protected <T extends BaseModel> ExecuteQuery(Class<? extends BaseModel> clazz) {
        SingleRunner runner = new SingleRunner();

        selectBag.setExecuteEnum(ExecuteEnum.ExecuteQuerySelect);
        selectBag.setModelType(clazz);
        runner.setBagOp(selectBag);
        this.setQuery(runner);
    }

    public DataTable find() {
        try {
            DataTable dt = this.getData();
            return dt;
        } catch (Exception ex) {
            BitterLogUtil.getInstance().error("bitter query error," + ex.getMessage());
        }
        return new DataTable();
    }

    protected <T extends BaseModel> T queryById(Object id) {

        try {
            T data;
            if (id == null) {
                data = (T) this.getSingleQuery().getBagOp().getModelType().newInstance();
                return data;
            }
            String tableName = this.getSingleQuery().getBagOp().getTableName();
            DataValue keyInfo = this.getSingleQuery().getBagOp().getKeyInfo();
            SubWhereStatement SubWhereStatement = new SubWhereStatement();
            SubWhereStatement.eq(keyInfo.getDbName(), id, null);
            ((IBagWhere) (this.getSingleQuery().getBagOp())).getWhereContainer().getSubWhereStatementCondition().add(SubWhereStatement);
            String sql = String.format("select * from %s ", tableName);
            selectBag.setCommandText(sql);
            List<T> st = this.getDataList();
            if (st != null && st.size() > 0) {
                data = st.get(0);
            } else {
                data = (T) (selectBag.getModelType()).newInstance();
            }
            return data;
        } catch (Exception ex) {
            throw new ModelException("queryById is none data that init a default data is error:" + ex.getMessage());
        }
    }

}
