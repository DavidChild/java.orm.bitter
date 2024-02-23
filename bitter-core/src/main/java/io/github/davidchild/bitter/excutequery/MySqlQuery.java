package io.github.davidchild.bitter.excutequery;

import io.github.davidchild.bitter.basequery.BaseQuery;
import io.github.davidchild.bitter.dbtype.DataValue;
import io.github.davidchild.bitter.op.insert.Insert;
import io.github.davidchild.bitter.parbag.*;
import io.github.davidchild.bitter.tools.CoreStringUtils;

import java.util.ArrayList;
import java.util.List;

public class MySqlQuery extends BaseQuery {



    @Override
    protected void insertCommandText(boolean isOutIdentity) {
        super.insertCommandText(isOutIdentity);
        ExecuteParBagInsert bagPar = (ExecuteParBagInsert) this.executeParBag;
        String fields = "";
        String values = "";

        for (DataValue filed : bagPar.getProperties()) {
            if (!filed.getIsIdentity()) {
                if (filed.getValue() != null) {
                    fields += filed.getDbName() + ',';
                    values += "?,";
                    this.parameters.add(filed.getValue());
                } else {
                    fields += filed.getDbName() + ',';
                    values += "null,";
                }
            }
        }
        bagPar.RemoveProperties();
        this.commandText = String.format("INSERT INTO %s (%s) VALUES (%s);", bagPar.getTableName(),
                fields.substring(0, fields.length() - 1), values.substring(0, values.length() - 1));
    }

    @Override
    protected void bachInsert() {
        super.bachInsert();
        List<Insert> list = ((ExecuteParBachInsert) this.executeParBag).getList();
        if (list == null && list.size() <= 0)
            return;
        ExecuteParBagInsert bagPar = (ExecuteParBagInsert) list.get(0).getExecuteParBag();
        String fields = "";
        for (DataValue filed : bagPar.getProperties()) {
            if (!filed.getIsIdentity()) {
                fields += filed.getDbName() + ',';
            }

        }
        StringBuilder values = new StringBuilder(" ");
        for (BaseQuery q : list) {
            String value = "(";
            ExecuteParBagInsert bag = (ExecuteParBagInsert) q.getExecuteParBag();
            for (DataValue f : bag.getProperties())
                if (!f.getIsIdentity()) {
                    if (f.getValue() == null) {
                        value += "null,";
                    } else {
                        value += "?,";
                        this.parameters.add(f.getValue());
                    }

                }
            value = value.substring(0, value.length() - 1) + "),";
            values.append("\n");
            values.append(value);
            bag.RemoveProperties();
        }
        String command = values.toString();
        this.commandText = String.format("INSERT INTO %s (%s) VALUES %s;", bagPar.getTableName(),
                fields.substring(0, fields.length() - 1), command.substring(0, command.length() - 1));

    }

    // create delete sql
    @Override
    protected void deleteCommandText() {
        super.deleteCommandText();
        ExecuteParBagDelete bagPar = (ExecuteParBagDelete) this.executeParBag;
        StringBuilder whereSQL = new StringBuilder(); // delete condition

        if (bagPar.condition != null || (bagPar.subStatementCondition != null && bagPar.subStatementCondition.size()> 0)) {
            whereSQL.append(" WHERE ");
            whereSQL.append(this.setWhere(bagPar.getCondition(),bagPar.getSubStatementCondition()));
        } else if (bagPar.getData() != null) {
            whereSQL.append(" WHERE ");
            Object keyValue = bagPar.getKeyInfo().getValue();
            String keyFiled = bagPar.getKeyInfo().getDbName();

            this.parameters.add(keyValue);
            whereSQL.append(String.format("%s=%s", keyFiled, "?"));
        }
        bagPar.RemoveProperties();
        this.commandText = String.format("%s%s;", // delete sql text
                "DELETE FROM " + bagPar.getTableName() // delete condition
                , whereSQL);

    }

    @Override
    protected void updateCommandText() {
        super.updateCommandText();
        ExecuteParBagUpdate bagPar = (ExecuteParBagUpdate) this.executeParBag;
        StringBuilder updateSQL = new StringBuilder(); // update语句
        StringBuilder whereSQL = new StringBuilder(); // update条件语句
        updateSQL.append(" update  ").append(bagPar.getTableName()).append(" set ");
        if (bagPar.getData() != null) {
            for (DataValue p : bagPar.getProperties()) {
                if (((!p.getIsKey()) && (!p.getIsIdentity())))
                    if (p.getValue() != null) {
                        updateSQL.append(String.format("%s=%s,", p.getDbName(), "?"));
                        this.parameters.add(p.getValue());
                    } else {
                        updateSQL.append(String.format("%s=null,", p.getDbName()));
                    }
            }
            whereSQL.append(" WHERE ");
            Object keyValue = bagPar.getKeyInfo().getValue();
            String keyName = bagPar.getKeyInfo().getDbName();
            whereSQL.append(String.format("%s=?", keyName));
            this.parameters.add(keyValue);
        }
        else if (bagPar.getUpdatePairs() != null && bagPar.getUpdatePairs().size() > 0) {
            bagPar.getUpdatePairs().forEach(up -> {
                if (((UpdatePair) up).getColumnValue() != null) {
                    updateSQL.append(String.format("%s=?,", ((UpdatePair) up).getDbFieldName()));
                    this.parameters.add(((UpdatePair) up).getColumnValue());
                } else {
                    updateSQL.append(String.format("%s=null,", ((UpdatePair) up).getDbFieldName()));
                }

            });
            if (bagPar.condition != null || (bagPar.subStatementCondition != null && bagPar.subStatementCondition.size()> 0)) {
                whereSQL.append(" WHERE ");
                whereSQL.append(this.setWhere(bagPar.condition,bagPar.subStatementCondition));
            }
        }
        bagPar.RemoveProperties();
        this.commandText =
                String.format("%s%s;", updateSQL.substring(0, updateSQL.toString().length() - 1), whereSQL.toString());
    }
    @Override
    protected void selectCommandText() {
        super.selectCommandText();
        ExecuteParBagSelect bagPar = (ExecuteParBagSelect) this.executeParBag;
        StringBuilder selectSQL = new StringBuilder(); // 语句
        StringBuilder whereSQL = new StringBuilder(); // 条件语句
        StringBuilder order = new StringBuilder(); // 条件语句
        if (bagPar.orders != null && bagPar.orders.size() > 0) {
            order.append(" ORDER BY ");
            List<String> orderList = new ArrayList<>();
            for (Object orderPair : bagPar.getOrders()) {
                OrderPair orderPair1 = (OrderPair) orderPair;
                orderList.add(orderPair1.getOrderName() + " " + orderPair1.getOrderBy().name());
            }

            order.append(String.join(",", orderList));
        }
        selectSQL.append("SELECT ");
        if (bagPar.selectColumns != null && bagPar.selectColumns.size() > 0) {
            selectSQL.append(String.join(",", bagPar.selectColumns));
        } else {
            selectSQL.append(" * ");
        }
        selectSQL.append(" FROM  " + bagPar.getTableName());
        String where = "";
        if (bagPar.condition != null || (bagPar.subStatementCondition != null && bagPar.subStatementCondition.size()> 0)) {
            where = this.setWhere(bagPar.condition,bagPar.subStatementCondition);
        }
        if (CoreStringUtils.isNotEmpty(where)) {
            whereSQL.append(" WHERE ");
            whereSQL.append(where);
        }
        if (bagPar.topSize != null && bagPar.topSize > 0) {
            this.commandText =
                    String.format("%s%s%s%s", selectSQL, whereSQL, order, " LIMIT 0," + bagPar.topSize + " ");
        } else {
            this.commandText = String.format("%s%s%s", selectSQL, whereSQL, order);
        }
        bagPar.RemoveProperties();
    }

    @Override
    protected void countCommandText() {
        super.selectCommandText();
        ExecuteParBagCount bagPar = (ExecuteParBagCount) this.executeParBag;
        StringBuilder whereSQL = new StringBuilder(); // 条件语句
        // 语句
        String selectSQL = "SELECT COUNT(1) " + "FROM  " + bagPar.getTableName() + " ";
        String where = "";
        if (bagPar.condition != null || (bagPar.subStatementCondition != null && bagPar.subStatementCondition.size()> 0)) {
            where = this.setWhere(bagPar.condition,bagPar.subStatementCondition);
        }
        if (CoreStringUtils.isNotEmpty(where)) {
            whereSQL.append(" WHERE ");
            whereSQL.append(where);
        }
        bagPar.RemoveProperties();
        this.commandText = String.format("%s%s;", selectSQL, whereSQL);
    }

    @Override
    protected void createScope() {

        super.createScope();
        this.scopeCommands = new ArrayList<>();
        this.scopeParams = new ArrayList<>();
        this.scopeSuccess = ((ExecuteParScope) this.executeParBag).getScopeResult();
        if (!((ExecuteParScope) this.executeParBag).getScopeResult()) {
            return;
        }
        List<BaseQuery> list = ((ExecuteParScope) this.executeParBag).getList();
        if (list == null && list.size() <= 0)
            return;
        for (BaseQuery q : list) {
            q.convert();
            this.getScopeCommands().add(q.getCommandText());
            if (q.getParameters() != null || q.getParameters().size() > 0) {
                this.getScopeParams().add(q.getParameters());
            } else {
                this.getScopeParams().add(new ArrayList<>());
            }
        }
    }

    // create execute sql
    @Override
    protected void executeCommandText() {
        super.executeCommandText();
        StringBuilder whereSQL = new StringBuilder();
        ExecuteParBagExecute bagPar = (ExecuteParBagExecute) this.executeParBag;
        this.setDynamicParameters(bagPar.getParaMap());
        String where = "";
        if ((bagPar.getSubStatements()  != null && bagPar.getSubStatements() .size()> 0)) {
            where = this.setWhere(null,bagPar.getSubStatements());
        }
        if (CoreStringUtils.isNotEmpty(where)) {
            whereSQL.append(" WHERE ");
            whereSQL.append(where);
        }
        this.commandText = String.format("%s%s;", bagPar.getCommandText(), whereSQL);
        bagPar.RemoveProperties();

    }

    @Override
    protected void pageCommandText() {
        MyPageManage.selectPageData(this);
    }

    @Override
    protected void pageCountText() {
        MyPageManage.selectPageDataCount(this);
    }

}
