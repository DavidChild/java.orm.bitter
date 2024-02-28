package io.github.davidchild.bitter.op.read;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.*;
import io.github.davidchild.bitter.datatable.DataTable;
import io.github.davidchild.bitter.dbtype.DataValue;
import io.github.davidchild.bitter.parbag.ExecuteParBagExecute;
import io.github.davidchild.bitter.parbag.ExecuteParBagQuerySelect;
import io.github.davidchild.bitter.parbag.IBagWhere;
import io.github.davidchild.bitter.tools.BitterLogUtil;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ExecuteQuery extends DqlQuery implements IWhereQuery<ExecuteQuery,BaseModel> {

    private ExecuteParBagExecute bag;
    public ExecuteQuery(String commandText, Object... params) {
        setExecuteParBag(new ExecuteParBagQuerySelect());
        getExecuteParBag().setExecuteEnum(ExecuteEnum.ExecuteQuerySelect);
        ((ExecuteParBagQuerySelect) getExecuteParBag()).setCommandText(commandText);
        if (params != null && params.length > 0) {
            Arrays.stream(params).forEach((v) -> {
                getExecuteParBag().getDynamics().put(UUID.randomUUID().toString(), v);
            });
        }
    }
    protected <T extends BaseModel> ExecuteQuery(Class<? extends BaseModel> clazz) {
        setExecuteParBag(new ExecuteParBagQuerySelect());
        getExecuteParBag().setExecuteEnum(ExecuteEnum.ExecuteQuerySelect);
        ((ExecuteParBagQuerySelect) getExecuteParBag()).setCommandText(getCommandText());
        (getExecuteParBag()).setType(clazz);
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
            data= (T) (getExecuteParBag()).getType().newInstance();
            return  data;
        }
        String tableName = (getExecuteParBag()).getTableName();
        DataValue keyInfo = (getExecuteParBag()).getKeyInfo();
        SubWhereStatement SubWhereStatement = this.createSubWhereStatement();
        SubWhereStatement.eq(keyInfo.getDbName(),id,null);
        ((IBagWhere)(getExecuteParBag())).getWhereContainer().getSubWhereStatementCondition().add(SubWhereStatement);
        String sql = String.format("select * from %s ", tableName);
        ((ExecuteParBagQuerySelect) getExecuteParBag()).setCommandText(sql);
        List<T> st = this.getDataList();
        if (st != null && st.size() > 0) {
            data = st.get(0);
        } else {
            data = (T) (getExecuteParBag()).getType().newInstance();
        }
        return data;

    }

}
