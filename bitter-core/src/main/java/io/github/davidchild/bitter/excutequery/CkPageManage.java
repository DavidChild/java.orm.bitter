package io.github.davidchild.bitter.excutequery;

import io.github.davidchild.bitter.connection.RunnerParam;
import io.github.davidchild.bitter.parbag.IBagOrder;
import io.github.davidchild.bitter.parbag.IBagWhere;
import io.github.davidchild.bitter.tools.CoreStringUtils;

public class CkPageManage {

    public static RunnerParam selectPageData(BuildParams buildParams) {
        return   getPageDataBySelect(buildParams);
    }
    public static RunnerParam selectPageDataCount(BuildParams buildParams) {
        return    getPageDataCountBySelect(buildParams);
    }
    private static RunnerParam getPageDataBySelect(BuildParams buildParams) {
        RunnerParam runnerParam = new RunnerParam();
        runnerParam.appendOnlyParams(buildParams.getObjectParams());
        StringBuilder sqlSelect = new StringBuilder(buildParams.getPageCommand());
        if (!buildParams.getIsPage()) {
            buildParams.setPageIndex(1);
            buildParams.setPageSize(1);
        }
        RunnerParam runner_where = WhereHandler.getWhere(null, (IBagWhere) buildParams.getBagOp(), buildParams.getFiledProperties(), buildParams.getKeyInfo());
        if (CoreStringUtils.isNotEmpty(runner_where.getCommand())) {
            sqlSelect.append("\n");
            sqlSelect.append(runner_where.getCommand());
            runnerParam.appendOnlyParams(runner_where);
        }
        RunnerParam runner_order = OrderHandler.getOrder((IBagOrder) buildParams.getBagOp());
        if (CoreStringUtils.isNotEmpty(runner_order.getCommand())) {
            sqlSelect.append("\n");
            sqlSelect.append(runner_order.getCommand());
        }

        if (buildParams.getPageIndex() == 1) {
            sqlSelect.append(" LIMIT " + "0" + ",").append(buildParams.getPageSize()).append(";");
        } else {
            sqlSelect.append(" LIMIT ").append(((buildParams.getPageIndex() - 1) * buildParams.getPageSize())).append(",")
                    .append(buildParams.getPageSize()).append(" ;");
        }
        sqlSelect.append("\n");
        if (CoreStringUtils.isNotEmpty(buildParams.getPreWith())) {
            runnerParam.setCommand(buildParams.getPreWith() + "\n" + sqlSelect);
        } else {
            runnerParam.setCommand(sqlSelect.toString());
        }

        return runnerParam;
    }

    private static RunnerParam getPageDataCountBySelect(BuildParams buildParams) {

        RunnerParam runnerParam = new RunnerParam();
        runnerParam.appendOnlyParams(buildParams.getObjectParams());
        String selectTable = buildParams.getTableName();

        StringBuilder sqlCount = new StringBuilder();
        sqlCount.append("/*******Search TotalCount*******/\n");
        sqlCount.append("select");
        sqlCount.append("\n");
        sqlCount.append("count(1) as totalcountcheok");
        sqlCount.append("\n");
        sqlCount.append("from");
        sqlCount.append("\n");
        sqlCount.append(selectTable);
        sqlCount.append("\n");

        RunnerParam runner_where = WhereHandler.getWhere(null, (IBagWhere) buildParams.getBagOp(), buildParams.getFiledProperties(), buildParams.getKeyInfo());
        if (CoreStringUtils.isNotEmpty(runner_where.getCommand())) {
            sqlCount.append("\n");
            sqlCount.append(runner_where.getCommand());
            runnerParam.appendOnlyParams(runner_where);
        }

        sqlCount.append(";");
        if (CoreStringUtils.isNotEmpty(buildParams.getPreWith())) {
            sqlCount.append(buildParams.getPreWith() + "\n" + sqlCount);
        } else {
            sqlCount.append(sqlCount.toString());
        }
        runnerParam.setCommand(sqlCount.toString());
        return runnerParam;
    }
}
