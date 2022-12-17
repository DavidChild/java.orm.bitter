package io.github.davidchild.bitter.basequery;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.connection.DataAccess;
import io.github.davidchild.bitter.exception.VisitorException;
import io.github.davidchild.bitter.excutequery.MySqlQuery;
import io.github.davidchild.bitter.parbag.ExecuteParBagInsert;
import io.github.davidchild.bitter.parse.BitterPredicate;
import io.github.davidchild.bitter.parse.BitterVisitor;
import io.github.davidchild.bitter.parse.BitterWrapper;
import io.github.davidchild.bitter.tools.BitterLogUtil;
import io.github.davidchild.bitter.tools.CoreStringUtils;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class BaseQuery extends BaseExecute implements Type {

    protected List<Map<String, Object>> getData() throws SQLException {
        this.convert();
        List<Map<String, Object>> mapData = DataAccess.executeQuery(this);
        if (mapData != null && mapData.size() > 0) {
            return mapData;
        }
        return null;
    }

    protected <T extends BaseModel> List<T> getDataList() {
        this.convert();
        return DataAccess.executeQueryReturnDataList(this);
    }

    public void addInScope(List<BaseQuery> list) {
        list.add(this);
    }

    protected void insertCommandText(boolean IsOutIdentity) {
        clearParameters();
    }

    protected void deleteCommandText() {
        clearParameters();
    }

    protected void updateCommandText() {
        clearParameters();
    }

    protected void selectCommandText() {
        clearParameters();
    }

    protected void countCommandText() {
        clearParameters();
    }

    protected void executeCommandText() {
        clearParameters();
    }

    protected void pageCommandText() {
        clearParameters();
    }

    protected void pageCountText() {
        clearParameters();
    }

    protected void bachInsert() {
        clearParameters();
    }

    protected void createScope() {
        clearParameters();
    }

    public <T extends BaseModel> String setWhere(List<BitterPredicate<T>> lambdas) {
        StringBuilder where = new StringBuilder("1=1");
        if (lambdas != null && lambdas.size() > 0) {
            lambdas.forEach(lambda -> {
                BitterVisitor visitor = new BitterVisitor(this.getExecuteParBag().getProperties(),
                        this.getExecuteParBag().getKeyInfo(), "`", "`");
                try {

                    BitterWrapper wrapper = lambda.sql(visitor);
                    String wp = wrapper.getKey().toString();
                    if (wp != null && CoreStringUtils.isNotEmpty(wp)) {
                        where.append(" \n ");
                        where.append(String.format("and ( %s )", wp));
                    }
                    if (wrapper.getValue() != null && wrapper.getValue().size() > 0) {
                        wrapper.getValue().forEach(it -> this.parameters.add(it));
                    }
                } catch (Exception e) {
                    // BitterLogUtil.getInstance().error("convert where error:" + e.getMessage(), e);
                    throw new VisitorException("can't support this where expression, please use the expression syntax already supported in bitter");//todo that can  Navigate to Instance Reference

                } finally {
                    visitor.clear();
                }
            });
        }
        return where.toString();
    }

    /**
     * If it is a self growing entity INSERT submission, - 1l returns a failure and a successful self increasing primary
     * key ID. n If it is a none self increasing entity, a 1l code is returned indicating success, and - 1l indicates
     * failure. n Batch statements, - 1l represents failure, 1 represents success n Batch adding returns 1 for success,
     * - 1 for failure n If the value is UPDATE, DELETE statement, the number of affected rows will be returned.
     * Otherwise, - 1l represents failure, and the number of affected rows will be returned successfully. n Transaction
     * execution: returns 1 if successful, and - 1 if unsuccessful; The exception record is output in the log
     */
    public Long submit() {

        Exception ex = null;
        try {
            convert();
        } catch (Exception exx) {
            BitterLogUtil.getInstance().error("bitter-executor-convert-error:" + exx.getMessage());
            return -1L;
        }
        long affectedCount = -1L;
        try {
            if (this.executeParBag.getExecuteEnum() == ExecuteEnum.Insert) {
                long aff = DataAccess.executeScalarToWriterOnlyForInsertReturnIdentity(this);
                if (aff == -1L)
                    return aff;
                if (!((ExecuteParBagInsert) this.executeParBag).isOutIdentity()) {
                    return 1L;
                }
                affectedCount = aff;
            } else if (this.executeParBag.getExecuteEnum() == ExecuteEnum.BachInsert) {
                affectedCount = DataAccess.executeInsertBach(this);
            } else if (this.executeParBag.getExecuteEnum() == ExecuteEnum.Scope) {
                if (!this.scopeSuccess)
                    return -1L;
                affectedCount = DataAccess.executeScope(this);
            } else if (this.executeParBag.getExecuteEnum() == ExecuteEnum.Delete
                    || this.executeParBag.getExecuteEnum() == ExecuteEnum.Update) {
                affectedCount = DataAccess.executeScalarToUpdateOrDeleteOnlyReturnAff(this);
            } else {
                affectedCount = DataAccess.executeNonQuery(this);
            }
            if (affectedCount == -1L) {
                if (ex != null) {
                    ex.printStackTrace();
                    BitterLogUtil.getInstance().error(ex.getMessage());
                }
            }
        } catch (Exception exx) {
            BitterLogUtil.getInstance().error(exx.getMessage());
        }
        return affectedCount;
    }

    public void resetParameters(List<Object> dynamicParameters) {
        this.parameters.clear();
        this.parameters = dynamicParameters;
    }

    public void setDynamicParameters(Map<String, Object> dynamicParameters) {
        if (dynamicParameters != null && dynamicParameters.size() > 0) {
            dynamicParameters.forEach((k, v) -> this.parameters.add(v));
        }
    }

    public MonitorInfo toMonitorInfo() {
        convert();
        MonitorInfo info = new MonitorInfo();
        info.setCommandSqlText(this.commandText);
        info.setParameters(this.parameters);
        return info;
    }

    public void convert() {
        try {
            BaseQuery qQuery = this.mapToExecuteQuery();
            qQuery.setCommandText(); // 构建对应的SQL语句
            this.parameters = qQuery.parameters;
            this.commandText = qQuery.commandText;
            this.scopeSuccess = qQuery.scopeSuccess;
            this.setScopeCommands(qQuery.getScopeCommands());
            this.setScopeParams(qQuery.getScopeParams());
        } catch (Exception ex) {
            BitterLogUtil.getInstance().error("baseQuery.convert error:" + ex.getMessage());
            throw ex;
        }
    }

    private BaseQuery mapToExecuteQuery() {
        BaseQuery vdb;
        switch (this.getDbType()) {
            default:
                vdb = new MySqlQuery();
                break;
        }
        vdb.executeParBag = this.executeParBag;
        vdb.setDbType(this.getDbType());
        vdb.commandText = this.getCommandText();
        vdb.parameters = this.parameters;
        return vdb;
    }

    private void setCommandText() {
        switch (this.getExecuteParBag().getExecuteEnum()) {
            case PageQuery:
                pageCommandText();
                break;
            case ExecuteQuery:
                executeCommandText();
                break;
            case Delete:
                deleteCommandText();
                break;
            case Select:
                selectCommandText();
                break;
            case Update:
                updateCommandText();
                break;
            case Insert:
                insertCommandText(false);
                break;
            case Count:
                countCommandText();
                break;
            case PageCount:
                pageCountText();
                break;
            case BachInsert:
                bachInsert();
                break;
            case Scope:
                createScope();
                break;
            default:
                break;
        }
    }
}
