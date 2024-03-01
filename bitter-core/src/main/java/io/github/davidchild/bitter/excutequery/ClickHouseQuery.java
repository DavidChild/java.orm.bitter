package io.github.davidchild.bitter.excutequery;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.BaseQuery;
import io.github.davidchild.bitter.basequery.StatementHandeUtil;
import io.github.davidchild.bitter.connection.RunnerParam;
import io.github.davidchild.bitter.dbtype.DataValue;
import io.github.davidchild.bitter.parbag.IBagColumn;
import io.github.davidchild.bitter.parbag.IBagOrder;
import io.github.davidchild.bitter.parbag.IBagUpdateColumn;
import io.github.davidchild.bitter.parbag.IBagWhere;

import java.util.List;

public class ClickHouseQuery extends BaseQuery {

    @Override
    protected RunnerParam insertCommandText(boolean isOutIdentity, BuildParams buildParams) {
        RunnerParam runnerParam = new RunnerParam();
        runnerParam.appendOnlyParams(buildParams.getObjectParams());
        runnerParam.setOutIdentity(isOutIdentity);
        String fields = "";
        String values = "";
        for (DataValue filed : buildParams.getFiledProperties()) {
            if (!filed.getIsIdentity()) {
                if (filed.getValue() != null) {
                    fields += filed.getDbName() + ',';
                    values += "?,";
                    StatementHandeUtil.setRunnerParamContainer(runnerParam, filed.getValue());
                } else {
                    fields += filed.getDbName() + ',';
                    values += "null,";
                }
            }
        }
        runnerParam.setCommand(String.format("INSERT INTO %s (%s) VALUES (%s);", buildParams.getTableName(),
                fields.substring(0, fields.length() - 1), values.substring(0, values.length() - 1)));
        return runnerParam;
    }

    @Override
    protected RunnerParam bachInsert(BuildParams buildParams) {
        RunnerParam runnerParam = new RunnerParam();
        runnerParam.appendOnlyParams(buildParams.getObjectParams());
        if (buildParams.getBatchData() == null && buildParams.getBatchData().size() < 1) return null;
        List<DataValue> dataValues = buildParams.getBatchData().get(0);
        String fields = "";
        for (DataValue filed : dataValues) {
            if (!filed.getIsIdentity()) {
                fields += filed.getDbName() + ',';
            }
        }
        StringBuilder values = new StringBuilder(" ");
        for (List<DataValue> q : buildParams.getBatchData()) {
            String value = "(";
            for (DataValue f : q)
                if (!f.getIsIdentity()) {
                    if (f.getValue() == null) {
                        value += "null,";
                    } else {
                        value += "?,";
                        StatementHandeUtil.setRunnerParamContainer(runnerParam, f.getValue());
                    }

                }
            value = value.substring(0, value.length() - 1) + "),";
            values.append("\n");
            values.append(value);
        }
        String command = values.toString();
        runnerParam.setCommand(String.format("INSERT INTO %s (%s) VALUES %s;", buildParams.getTableName(),
                fields.substring(0, fields.length() - 1), command.substring(0, command.length() - 1)));
        return runnerParam;

    }

    // create delete sql
    @Override
    protected <T extends BaseModel> RunnerParam deleteCommandText(BuildParams buildParams) {
        RunnerParam runnerParam = new RunnerParam();
        runnerParam.appendOnlyParams(buildParams.getObjectParams());
        RunnerParam runner_param_where = WhereHandler.getWhere(buildParams.getData(), (IBagWhere) buildParams.getBagOp(), buildParams.getFiledProperties(), buildParams.getKeyInfo());
        runnerParam.appendOnlyParams(runner_param_where);
        runnerParam.setCommand(String.format("%s%s;", "ALTER FROM " + buildParams.getTableName() +" DELETE ", runner_param_where.getCommand()));
        return runnerParam;
    }

    @Override
    protected RunnerParam updateCommandText(BuildParams buildParams) {
        RunnerParam runnerParam = new RunnerParam();
        runnerParam.appendOnlyParams(buildParams.getObjectParams());
        StringBuilder updateSQL = new StringBuilder(); // update语句
        updateSQL.append(" ALTER  ").append(buildParams.getTableName()).append(" UPDATE");
        RunnerParam runner_update_column = UpdateColumnHandler.getUpdateColumnSet(buildParams.getData(), (IBagUpdateColumn) buildParams.getBagOp(), buildParams.getFiledProperties());
        RunnerParam runner_where = WhereHandler.getWhere(buildParams.getData(), (IBagWhere) buildParams.getBagOp(), buildParams.getFiledProperties(), buildParams.getKeyInfo());

        runnerParam.appendOnlyParams(runner_update_column);
        runnerParam.appendOnlyParams(runner_where);

        runnerParam.setCommand(String.format("%s%s%s;", updateSQL, runner_update_column.getCommand(), runner_where.getCommand()));
        return runnerParam;
    }

    @Override
    protected RunnerParam selectCommandText(BuildParams buildParams) {
        RunnerParam runnerParam = new RunnerParam();
        runnerParam.appendOnlyParams(buildParams.getObjectParams());
        StringBuilder selectSQL = new StringBuilder(); // 语句
        StringBuilder whereSQL = new StringBuilder(); // 条件语句
        StringBuilder orderSQL = new StringBuilder(); // 条件语句
        selectSQL.append("SELECT ");
        RunnerParam runner_columns = SelectColumnHandler.getColumn((IBagColumn) buildParams.getBagOp());
        selectSQL.append(runner_columns.getCommand());
        selectSQL.append(" FROM  " + buildParams.getTableName());
        RunnerParam runner_param_where = WhereHandler.getWhere(buildParams.getData(), (IBagWhere) buildParams.getBagOp(), buildParams.getFiledProperties(), buildParams.getKeyInfo());
        runnerParam.appendOnlyParams(runner_param_where);
        whereSQL.append(runner_param_where.getCommand());

        RunnerParam runner_order = OrderHandler.getOrder((IBagOrder) buildParams.getBagOp());
        orderSQL.append(runner_order.getCommand());

        if (buildParams.getTopSize() > 0) {
            runnerParam.setCommand(String.format("%s%s%s%s", selectSQL, whereSQL, runner_order.getCommand(), " LIMIT 0," + buildParams.getTopSize() + " "));
        } else {
            runnerParam.setCommand(String.format("%s%s%s", selectSQL, whereSQL, runner_order.getCommand()));
        }
        return runnerParam;
    }

    @Override
    protected RunnerParam countCommandText(BuildParams buildParams) {
        RunnerParam runnerParam = new RunnerParam();
        runnerParam.appendOnlyParams(buildParams.getObjectParams());
        StringBuilder whereSQL = new StringBuilder(); // 条件语句
        String selectSQL = "SELECT COUNT(1) " + "FROM  " + buildParams.getTableName() + " ";
        RunnerParam runner_param_where = WhereHandler.getWhere(buildParams.getData(), (IBagWhere) buildParams.getBagOp(), buildParams.getFiledProperties(), buildParams.getKeyInfo());
        runnerParam.appendOnlyParams(runner_param_where);

        whereSQL.append(runner_param_where.getCommand());
        runnerParam.setCommand(String.format("%s%s;", selectSQL, whereSQL));
        return runnerParam;
    }


    // create execute sql
    @Override
    protected RunnerParam executeCommandText(BuildParams buildParams) {
        RunnerParam runnerParam = new RunnerParam();
        runnerParam.appendOnlyParams(buildParams.getObjectParams());
        runnerParam.setCommand(String.format("%s;", buildParams.getExecuteCommand()));
        return runnerParam;

    }

    @Override
    protected RunnerParam querySelectCommandText(BuildParams buildParams) {
        RunnerParam runnerParam = new RunnerParam();
        runnerParam.appendOnlyParams(buildParams.getObjectParams());
        RunnerParam runner_param_where = WhereHandler.getWhere(buildParams.getData(), (IBagWhere) buildParams.getBagOp(), buildParams.getFiledProperties(), buildParams.getKeyInfo());
        runnerParam.appendOnlyParams(runner_param_where);
        RunnerParam runner_order = OrderHandler.getOrder((IBagOrder) buildParams.getBagOp());
        if (buildParams.getTopSize() > 0) {
            runnerParam.setCommand(String.format("%s%s%s%s", buildParams.getExecuteCommand(), runner_param_where.getCommand(), runner_order.getCommand(), " LIMIT 0," + buildParams.getTopSize() + " "));
        } else {
            runnerParam.setCommand(String.format("%s%s%s;", buildParams.getExecuteCommand(), runner_param_where.getCommand(), runner_order.getCommand()));
        }
        return runnerParam;
    }

    @Override
    protected RunnerParam pageCommandText(BuildParams buildParams) {
        return CkPageManage.selectPageData(buildParams);
    }

    @Override
    protected RunnerParam pageCountText(BuildParams buildParams) {
        return CkPageManage.selectPageDataCount(buildParams);
    }
}
