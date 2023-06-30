package io.github.davidchild.bitter.excutequery;

import io.github.davidchild.bitter.basequery.BaseQuery;
import io.github.davidchild.bitter.parbag.ExecuteParBagPage;
import io.github.davidchild.bitter.tools.CoreStringUtils;

public class MyPageManage {

    public static void selectPageData(BaseQuery baseQuery) {
        baseQuery.setCommandText("");
        baseQuery.clearParameters();
        getPageDataBySelect(baseQuery);
    }

    public static void selectPageDataCount(BaseQuery baseQuery) {
        baseQuery.setCommandText("");
        baseQuery.clearParameters();
        getPageDataCountBySelect(baseQuery);
    }

    private static void getPageDataBySelect(BaseQuery baseQuery) {
        ExecuteParBagPage bag = (ExecuteParBagPage) (baseQuery.getExecuteParBag());
        baseQuery.setDynamicParameters((bag.dynamics));
        BaseQuery sqlTemp = new BaseQuery();
        sqlTemp.setCommandText(bag.commandText);
        if (bag.dynamics != null && bag.dynamics.size() > 0) {
            bag.dynamics.forEach((k, v) -> sqlTemp.getParameters().add(v));
        }
        StringBuilder sqlSelect = new StringBuilder(sqlTemp.getCommandText());

        if (!bag.isPage) {
            bag.pageIndex = 1;
            bag.pageSize = 1;
        }
        if (CoreStringUtils.isNotEmpty(bag.whereBuilder.toString())) {
            String where = (CoreStringUtils.isEmpty(bag.whereBuilder.toString()) ? ""
                    : String.format(" WHERE %s ", bag.whereBuilder.toString()));
            sqlSelect.append("\n");
            sqlSelect.append(where);
        }
        if (CoreStringUtils.isNotEmpty(bag.getOrderBy().toString())) {
            String order;
            if (bag.getOrderBy().toString().charAt(bag.getOrderBy().toString().length() - 1) == ',') {
                order = bag.getOrderBy().substring(0, bag.getOrderBy().toString().length() - 1);
            } else {
                order = bag.getOrderBy().toString();
            }
            String orderBy =
                    (CoreStringUtils.isEmpty(bag.getOrderBy()) ? "" : String.format(" ORDER BY %s ", order));
            sqlSelect.append("\n");
            sqlSelect.append(orderBy);
        }
        if (bag.pageIndex == 1) {
            sqlSelect.append(" LIMIT " + "0" + ",").append(bag.pageSize).append(";");
        } else {
            sqlSelect.append(" LIMIT ").append(((bag.pageIndex - 1) * bag.pageSize)).append(",")
                    .append(bag.pageSize).append(" ;");
        }
        try {
            sqlSelect.append("\n");
            if (CoreStringUtils.isNotEmpty(bag.preWith)) {
                sqlTemp.setCommandText(bag.preWith + "\n" + sqlSelect.append(sqlSelect));
            } else {
                sqlTemp.setCommandText(sqlSelect.toString());
            }
            baseQuery.setCommandText(sqlTemp.getCommandText());
            baseQuery.resetParameters(sqlTemp.getParameters());
        } finally {

        }
    }

    private static void getPageDataCountBySelect(BaseQuery baseQuery) {
        ExecuteParBagPage bag = (ExecuteParBagPage) (baseQuery.getExecuteParBag());
        String selectTable = bag.pageTableName;
        baseQuery.setDynamicParameters((bag.dynamics));
        BaseQuery sqlTemp = new BaseQuery();
        sqlTemp.setCommandText(bag.commandText);
        if (bag.dynamics != null && bag.dynamics.size() > 0) {
            bag.dynamics.forEach((k, v) -> sqlTemp.getParameters().add(v));
        }
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
        if (CoreStringUtils.isNotEmpty(bag.whereBuilder.toString())) {
            sqlCount.append(CoreStringUtils.isEmpty(bag.whereBuilder.toString()) ? ""
                    : String.format(" WHERE %s; ", bag.whereBuilder.toString()));
        }
        sqlCount.append(";");
        try {
            if (CoreStringUtils.isNotEmpty(bag.preWith)) {
                sqlTemp.setCommandText(bag.preWith + "\n" + sqlCount);
            } else {
                sqlTemp.setCommandText(sqlCount.toString());
            }
            baseQuery.setCommandText(sqlTemp.getCommandText());
            baseQuery.resetParameters(sqlTemp.getParameters());
        } finally {

        }
    }
}
