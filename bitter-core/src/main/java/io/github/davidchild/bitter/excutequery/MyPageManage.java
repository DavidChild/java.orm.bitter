package io.github.davidchild.bitter.excutequery;

import io.github.davidchild.bitter.basequery.BaseQuery;
import io.github.davidchild.bitter.parbag.ExecuteParBagPage;
import io.github.davidchild.bitter.tools.CoreStringUtils;

import java.util.LinkedHashMap;

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

    private static void getPageDataBySelect(BaseQuery query) {
        ExecuteParBagPage bag = (ExecuteParBagPage) (query.getExecuteParBag());
        BaseQuery sqlTemp = new BaseQuery();

        sqlTemp.setCommandText(bag.getCommandText());
        sqlTemp.setExecuteParBag(query.getExecuteParBag());
        sqlTemp.getExecuteParBag().setDynamics(new LinkedHashMap<>());

        StringBuilder sqlSelect = new StringBuilder(sqlTemp.getCommandText());
        if (!bag.getIsPage()) {
            bag.setPageIndex(1);
            bag.setPageSize(1);
        }
        String where = WhereHandler.getWhere(sqlTemp);
        if (CoreStringUtils.isNotEmpty(where)) {
            sqlSelect.append("\n");
            sqlSelect.append(where);
        }
        String order = OrderHandler.getOrder(sqlTemp);
        if (CoreStringUtils.isNotEmpty(order)) {
            sqlSelect.append("\n");
            sqlSelect.append(order);
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

    private static void getPageDataCountBySelect(BaseQuery query) {
        ExecuteParBagPage bag = (ExecuteParBagPage) (query.getExecuteParBag());
        String selectTable = bag.getTableName();
        BaseQuery sqlTemp = new BaseQuery();

        sqlTemp.setCommandText(bag.getCommandText());
        sqlTemp.setExecuteParBag(query.getExecuteParBag());
        sqlTemp.getExecuteParBag().setDynamics(new LinkedHashMap<>());

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
        String where = WhereHandler.getWhere(sqlTemp);
        if (CoreStringUtils.isNotEmpty(where)) {
            sqlCount.append("\n");
            sqlCount.append(where);
        }
        sqlCount.append(";");

        if (CoreStringUtils.isNotEmpty(bag.getPreWith())) {
            sqlTemp.setCommandText(bag.getPreWith() + "\n" + sqlCount);
        } else {
            sqlTemp.setCommandText(sqlCount.toString());
        }
        query.setCommandText(sqlTemp.getCommandText());
        query.resetParameters(sqlTemp.getExecuteParBag().getDynamics());

    }

}
