package io.github.davidchild.bitter.excutequery;

import io.github.davidchild.bitter.basequery.BaseQuery;
import io.github.davidchild.bitter.parbag.ExecuteParBagPage;
import io.github.davidchild.bitter.tools.CoreStringUtils;

public class MyPageManage {

    public static void selectPageData(BaseQuery baseQuery,String where,String order) {
        baseQuery.setCommandText("");
        baseQuery.clearParameters();
        getPageDataBySelect(baseQuery,where,order);
    }

    public static void selectPageDataCount(BaseQuery baseQuery,String where,String order) {
        baseQuery.setCommandText("");
        baseQuery.clearParameters();
        getPageDataCountBySelect(baseQuery,where,order);
    }

    private static void getPageDataBySelect(BaseQuery query,String where,String order) {
        ExecuteParBagPage bag = (ExecuteParBagPage) (query.getExecuteParBag());
        BaseQuery sqlTemp = new BaseQuery();
        sqlTemp.setCommandText(bag.getCommandText());
        sqlTemp.getExecuteParBag().setDynamics(bag.getDynamics());
        StringBuilder sqlSelect = new StringBuilder(sqlTemp.getCommandText());
        if (!bag.getIsPage()) {
            bag.setPageIndex(1);
            bag.setPageSize(1);
        }
        if (CoreStringUtils.isNotEmpty(where)) {
            String where_sql = String.format(" WHERE %s ", where);
            sqlSelect.append("\n");
            sqlSelect.append(where_sql);
        }
        if (CoreStringUtils.isNotEmpty(order)) {
            String orderBy = (CoreStringUtils.isEmpty(order) ? "" : String.format(" ORDER BY %s ", order));
            sqlSelect.append("\n");
            sqlSelect.append(orderBy);
        }

        if (bag.getPageIndex() == 1) {
            sqlSelect.append(" LIMIT " + "0" + ",").append(bag.getPageSize()).append(";");
        } else {
            sqlSelect.append(" LIMIT ").append(((bag.getPageIndex() - 1) * bag.getPageSize())).append(",")
                    .append(bag.getPageSize()).append(" ;");
        }
        try {
            sqlSelect.append("\n");
            if (CoreStringUtils.isNotEmpty(bag.getPreWith())) {
                sqlTemp.setCommandText(bag.getPreWith() + "\n" + sqlSelect.append(sqlSelect));
            } else {
                sqlTemp.setCommandText(sqlSelect.toString());
            }
            query.setCommandText(sqlTemp.getCommandText());
            query.resetParameters(sqlTemp.getExecuteParBag().getDynamics());
        } finally {

        }
    }

    private static void getPageDataCountBySelect(BaseQuery query,String where,String order) {
        ExecuteParBagPage bag = (ExecuteParBagPage) (query.getExecuteParBag());
        String selectTable = bag.getTableName();
        BaseQuery sqlTemp = new BaseQuery();
        sqlTemp.setCommandText(bag.getCommandText());
        sqlTemp.getExecuteParBag().setDynamics(bag.getDynamics());

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
        if (CoreStringUtils.isNotEmpty(where)) {
            String where_sql = String.format(" WHERE %s ", where);
            sqlCount.append("\n");
            sqlCount.append(where_sql);
        }
        sqlCount.append(";");
        try {
            if (CoreStringUtils.isNotEmpty(bag.getPreWith())) {
                sqlTemp.setCommandText(bag.getPreWith() + "\n" + sqlCount);
            } else {
                sqlTemp.setCommandText(sqlCount.toString());
            }
            query.setCommandText(sqlTemp.getCommandText());
            query.resetParameters(sqlTemp.getExecuteParBag().getDynamics());

        } finally {

        }
    }
}
