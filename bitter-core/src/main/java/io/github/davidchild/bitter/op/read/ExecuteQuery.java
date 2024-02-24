package io.github.davidchild.bitter.op.read;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.BaseQuery;
import io.github.davidchild.bitter.basequery.ExecuteEnum;
import io.github.davidchild.bitter.basequery.IWhereQuery;
import io.github.davidchild.bitter.basequery.SubWhereStatement;
import io.github.davidchild.bitter.datatable.DataTable;
import io.github.davidchild.bitter.dbtype.DataValue;
import io.github.davidchild.bitter.parbag.ExecuteParBagExecute;
import io.github.davidchild.bitter.parbag.IBagWhere;
import io.github.davidchild.bitter.tools.BitterLogUtil;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ExecuteQuery extends BaseQuery implements IWhereQuery<ExecuteQuery,BaseModel> {

    private ExecuteParBagExecute bag;
    public ExecuteQuery(String commandText, Object... params) {
        executeParBag = new ExecuteParBagExecute();
        executeParBag.setExecuteEnum(ExecuteEnum.ExecuteQuery);
        ((ExecuteParBagExecute) executeParBag).setCommandText(commandText);
        if (params != null && params.length > 0) {
            Arrays.stream(params).forEach((v) -> {
                executeParBag.getDynamics().put(UUID.randomUUID().toString(), v);
            });
        }
    }
    protected <T extends BaseModel> ExecuteQuery(Class<? extends BaseModel> clazz) {
        executeParBag = new ExecuteParBagExecute();
        executeParBag.setExecuteEnum(ExecuteEnum.ExecuteQuery);
        ((ExecuteParBagExecute) executeParBag).setCommandText(commandText);
        (executeParBag).setType(clazz);
    }

    public DataTable find() {
        try {
            DataTable dt = this.getData();
            return  dt;
        } catch (Exception ex) {
            BitterLogUtil.getInstance().error("bitter query error," + ex.getMessage());
        }
        return new DataTable();
    }
    protected <T extends BaseModel> T queryById(Object id)
            throws SQLException, InstantiationException, IllegalAccessException {

        T data;
        if(id == null){
            data= (T) (executeParBag).getType().newInstance();
            return  data;
        }
        String tableName = ((ExecuteParBagExecute) executeParBag).getTableName();
        DataValue keyInfo = ((ExecuteParBagExecute) executeParBag).getKeyInfo();
        SubWhereStatement SubWhereStatement = this.createSubWhereStatement();
        SubWhereStatement.eq(keyInfo.getDbName(),id,null);
        ((IBagWhere)(executeParBag)).getWhereContainer().getSubWhereStatementCondition().add(SubWhereStatement);
        String sql = String.format("select * from %s ", tableName);
        ((ExecuteParBagExecute) executeParBag).setCommandText(sql);
        List<T> st = this.getDataList();
        if (st != null && st.size() > 0) {
            data = st.get(0);
        } else {
            data = (T) ((ExecuteParBagExecute) executeParBag).getType().newInstance();
        }
        return data;

    }

}
